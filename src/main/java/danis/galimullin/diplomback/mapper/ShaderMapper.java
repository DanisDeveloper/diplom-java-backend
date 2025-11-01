package danis.galimullin.diplomback.mapper;

import danis.galimullin.diplomback.dto.shader.ShaderOriginDto;
import danis.galimullin.diplomback.dto.shader.ShaderResponseDto;
import danis.galimullin.diplomback.dto.shader.ShaderUpsertDto;
import danis.galimullin.diplomback.model.Shader;
import org.springframework.stereotype.Component;

@Component
public class ShaderMapper {
    private final UserMapper userMapper;

    public ShaderMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Shader toEntity(ShaderUpsertDto shaderDto) {
        Shader shader = new Shader();
        updateEntityFromDto(shader, shaderDto);
        return shader;
    }

    public void updateEntityFromDto(
            Shader shader,
            ShaderUpsertDto shaderDto
    ) {
        shader.setTitle(shaderDto.title());
        shader.setDescription(shaderDto.description());
        shader.setCode(shaderDto.code());
        shader.setVisibility(shaderDto.visibility());
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
                shader.getViews(),
                shader.getLikes(),
                shader.getComments(),
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
