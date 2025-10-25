package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.dto.shader.ShaderCreateDto;
import danis.galimullin.diplomback.dto.shader.ShaderResponseDto;
import danis.galimullin.diplomback.exception.ResourceNotFoundException;
import danis.galimullin.diplomback.model.Shader;
import danis.galimullin.diplomback.model.User;
import danis.galimullin.diplomback.repository.ShaderRepository;
import danis.galimullin.diplomback.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShaderServiceImpl implements ShaderService {
    private final ShaderRepository shaderRepository;
    private final UserRepository userRepository;

    public ShaderServiceImpl(ShaderRepository shaderRepository, UserRepository userRepository) {
        this.shaderRepository = shaderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ShaderResponseDto> getALlVisibleShaders() {
        return shaderRepository
                .findAllByVisibility(true).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShaderResponseDto> getAllShaders() {
        return shaderRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ShaderResponseDto getShaderById(Long id) {
        Optional<Shader> optionalShader = shaderRepository.findById(id);
        if (optionalShader.isEmpty())
            throw new ResourceNotFoundException("Shader with id " + id + " not found");

        return this.toDto(optionalShader.get());
    }

    @Override
    public Shader saveShader(ShaderCreateDto shaderDto) {
        Shader shader = this.toEntity(shaderDto);
        return shaderRepository.save(shader);
    }

    @Override
    @Transactional
    public void patchById(Long id, String title, String description, String code, Boolean visibility) {
        Optional<Shader> optionalShader = shaderRepository.findById(id);
        if (optionalShader.isEmpty()) {
            throw new ResourceNotFoundException("Shader with id " + id + " not found");
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
            throw new ResourceNotFoundException("Shader with id " + id + " not found");
        }
        shaderRepository.deleteById(id);
    }

    private Shader toEntity(ShaderCreateDto shaderDto) {
        Shader shaderEntity = new Shader();
        shaderEntity.setTitle(shaderDto.title());
        shaderEntity.setDescription(shaderDto.description());
        shaderEntity.setCode(shaderDto.code());
        User user = userRepository.findById(shaderDto.userId()).orElseThrow();
        shaderEntity.setUser(user);
        user.getShaders().add(shaderEntity);
        if (shaderDto.originId() != null) {
            Optional<Shader> optionalShader = shaderRepository.findById(shaderDto.originId());
            shaderEntity.setOrigin(optionalShader.orElse(null));
        }
        return shaderEntity;
    }

    private ShaderResponseDto toDto(Shader shader) {
        return new ShaderResponseDto(
                shader.getId(),
                shader.getTitle(),
                shader.getDescription(),
                shader.getCode(),
                shader.getCreatedAt(),
                shader.getUpdatedAt(),
                shader.getVisibility(),
                shader.getUser().getId(),
                (shader.getOrigin() != null) ? shader.getOrigin().getId() : null
        );
    }
}
