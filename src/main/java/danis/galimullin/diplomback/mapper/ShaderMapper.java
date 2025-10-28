package danis.galimullin.diplomback.mapper;

import danis.galimullin.diplomback.dto.shader.ShaderOriginDto;
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
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ShaderRepository shaderRepository;

    public ShaderMapper(UserMapper userMapper, UserRepository userRepository, ShaderRepository shaderRepository) {
        this.userMapper = userMapper;
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
        shader.setUser(userRepository.getReferenceById(shaderDto.userId()));
        if (shaderDto.originId() != null)
            shader.setOrigin(shaderRepository.getReferenceById(shaderDto.originId()));
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
                userMapper.toUserStateDto(shader.getUser()),
                (shader.getOrigin() == null) ? null : toShaderOriginDto(shader.getOrigin())
        );
    }

    public ShaderOriginDto toShaderOriginDto(Shader shader) {
        return new ShaderOriginDto(
                shader.getId(),
                shader.getTitle()
        );
    }
}
