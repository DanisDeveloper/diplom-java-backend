package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.configuration.RedisConfig;
import danis.galimullin.diplomback.dto.shader.ShaderResponseDto;
import danis.galimullin.diplomback.dto.shader.ShaderUpsertDto;
import danis.galimullin.diplomback.exception.ShaderNotFoundException;
import danis.galimullin.diplomback.exception.UserNotFoundException;
import danis.galimullin.diplomback.mapper.ShaderMapper;
import danis.galimullin.diplomback.model.Shader;
import danis.galimullin.diplomback.model.User;
import danis.galimullin.diplomback.repository.ShaderRepository;
import danis.galimullin.diplomback.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    public List<ShaderResponseDto> getALlVisibleShaders() {
        return shaderRepository
                .findAllByVisibility(true).stream()
                .map(shaderMapper::toShaderResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShaderResponseDto> getAllShaders() {
        return shaderRepository.findAll().stream()
                .map(shaderMapper::toShaderResponseDto)
                .collect(Collectors.toList());
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
        if(!alreadyViewed){
            shaderRepository.incrementViews(shaderId);
            redisTemplate.opsForValue().set(key, "1", 24, TimeUnit.HOURS);
        }
    }

}
