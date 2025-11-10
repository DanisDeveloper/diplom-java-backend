package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.dto.shader.ShaderResponseDto;
import danis.galimullin.diplomback.dto.shader.ShaderUpsertDto;
import danis.galimullin.diplomback.exception.ShaderNotFoundException;
import danis.galimullin.diplomback.exception.UserNotFoundException;
import danis.galimullin.diplomback.mapper.ShaderMapper;
import danis.galimullin.diplomback.model.Shader;
import danis.galimullin.diplomback.model.SortOption;
import danis.galimullin.diplomback.model.User;
import danis.galimullin.diplomback.repository.ShaderRepository;
import danis.galimullin.diplomback.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ShaderServiceImpl implements ShaderService {
    private final ShaderRepository shaderRepository;
    private final UserRepository userRepository;
    private final ShaderMapper shaderMapper;
    private final RedisTemplate<String, String> redisTemplate;

    public ShaderServiceImpl(ShaderRepository shaderRepository,
                             UserRepository userRepository,
                             ShaderMapper shaderMapper,
                             RedisTemplate<String, String> redisTemplate) {
        this.shaderRepository = shaderRepository;
        this.userRepository = userRepository;
        this.shaderMapper = shaderMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Page<ShaderResponseDto> getAllVisibleShaders(String searchQuery, Integer page, Integer pageSize, SortOption sortOption) {
        var property = getSort(sortOption);
        Pageable pageable = PageRequest.of(page, pageSize, property);

        return shaderRepository
                .findAllByVisibilityAndTitleContainingIgnoreCaseAndDeletedFalse(true, searchQuery, pageable)
                .map(shaderMapper::toShaderResponseDto);
    }

    @Override
    public Page<ShaderResponseDto> getAllUserShaders(String username, String currentUsername, Integer page, Integer pageSize, SortOption sortOption) {
        var property = getSort(sortOption);
        Pageable pageable = PageRequest.of(page, pageSize, property);

        return shaderRepository
                .findAllVisibleOrOwnedBy(username, currentUsername, pageable)
                .map(shaderMapper::toShaderResponseDto);
    }

    @Override
    public ShaderResponseDto getShaderById(Long id) {
        Optional<Shader> optionalShader = shaderRepository.findById(id);
        if (optionalShader.isEmpty())
            throw new ShaderNotFoundException();

        return shaderMapper.toShaderResponseDto(optionalShader.get());
    }

    @Override
    public ShaderResponseDto saveShader(ShaderUpsertDto shaderDto, Principal principal) {
        User user = userRepository
                .findByName(principal.getName())
                .orElseThrow(UserNotFoundException::new);

        Shader origin = Optional.ofNullable(shaderDto.originId())
                .flatMap(shaderRepository::findById)
                .orElse(null);
        if (shaderDto.originId() != null)
            origin = shaderRepository.findById(shaderDto.originId()).orElse(null);

        Shader shader = shaderMapper.toEntity(shaderDto);
        shader.setUser(user);
        shader.setOrigin(origin);
        Shader savedShader = shaderRepository.save(shader);

        return shaderMapper.toShaderResponseDto(savedShader);
    }

    @Override
    public ShaderResponseDto saveShader(Long id, ShaderUpsertDto shaderUpsertDto) {
        Shader shader = shaderRepository.findById(id).orElseThrow(ShaderNotFoundException::new);
        shaderMapper.updateEntityFromDto(shader, shaderUpsertDto);

        if (shaderUpsertDto.originId() != null)
            shader.setOrigin(shaderRepository.getReferenceById(shaderUpsertDto.originId()));

        shaderRepository.save(shader);
        return shaderMapper.toShaderResponseDto(shader);
    }

    @Override
    @Transactional
    public void patchById(Long id, String title, String description, String code, Boolean visibility) {
        Optional<Shader> optionalShader = shaderRepository.findById(id);
        if (optionalShader.isEmpty()) {
            throw new ShaderNotFoundException();
        }
        Shader shader = optionalShader.get();
        if (title != null && !title.isEmpty()) shader.setTitle(title);
        if (description != null) shader.setDescription(description);
        if (code != null) shader.setCode(code);
        if (visibility != null) shader.setVisibility(visibility);
    }

    @Override
    public void deleteShaderById(Long id) {
        if (!shaderRepository.existsById(id)) {
            throw new ShaderNotFoundException();
        }
        shaderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void incrementViews(Long shaderId, String userIP) {
        String key = "view:shader:" + shaderId + ":" + userIP;
        Boolean alreadyViewed = redisTemplate.hasKey(key);
        if (!alreadyViewed) {
            shaderRepository.incrementViews(shaderId);
            redisTemplate.opsForValue().set(key, "1", 24, TimeUnit.HOURS);
        }
    }


    private Sort getSort(SortOption sortOption) {
        return switch (sortOption) {
            case LIKED -> Sort.by("likes").descending();
            case COMMENTED -> Sort.by("comments").descending();
            case VIEWED -> Sort.by("views").descending();
            default -> Sort.by("createdAt").descending(); // NEWEST
        };
    }

}
