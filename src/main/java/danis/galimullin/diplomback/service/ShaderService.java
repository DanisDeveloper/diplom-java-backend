package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.dto.shader.ShaderCreateDto;
import danis.galimullin.diplomback.dto.shader.ShaderResponseDto;
import danis.galimullin.diplomback.model.Shader;

import java.util.List;

public interface ShaderService {
    List<ShaderResponseDto> getALlVisibleShaders();

    List<ShaderResponseDto> getAllShaders();

    ShaderResponseDto getShaderById(Long id);

    Shader saveShader(ShaderCreateDto shader);

    void deleteShaderById(Long id);

    void patchById(Long id, String title, String description, String code, Boolean visibility);
}
