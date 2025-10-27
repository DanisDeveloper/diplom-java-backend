package danis.galimullin.diplomback.mapper;

import danis.galimullin.diplomback.dto.shader.ShaderResponseDto;
import danis.galimullin.diplomback.dto.shader.ShaderUpsertDto;
import danis.galimullin.diplomback.model.Shader;
import danis.galimullin.diplomback.model.User;
import danis.galimullin.diplomback.repository.ShaderRepository;
import danis.galimullin.diplomback.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShaderMapper {
    private final UserRepository userRepository;
    private final ShaderRepository shaderRepository;

    public ShaderMapper(UserRepository userRepository, ShaderRepository shaderRepository) {
        this.userRepository = userRepository;
        this.shaderRepository = shaderRepository;
    }

    public Shader toEntity(ShaderUpsertDto shaderDto) {
        Shader shader = new Shader();
        updateEntityFromDto(shader, shaderDto);
        return shader;
    }

    public void updateEntityFromDto(Shader shader, ShaderUpsertDto shaderDto) {
        shader.setTitle(shaderDto.title());
        shader.setDescription(shaderDto.description());
        shader.setCode(shaderDto.code());
        shader.setVisibility(shaderDto.visibility());
        User user = userRepository.findById(shaderDto.userId()).orElseThrow();
        shader.setUser(user);
        user.getShaders().add(shader);
        if (shaderDto.originId() != null) {
            Optional<Shader> optionalShader = shaderRepository.findById(shaderDto.originId());
            shader.setOrigin(optionalShader.orElse(null));
        }
    }


    public ShaderResponseDto toShaderResponseDto(Shader shader) {
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
