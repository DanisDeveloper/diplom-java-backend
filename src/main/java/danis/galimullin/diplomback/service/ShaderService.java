package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.dto.shader.ShaderResponseDto;
import danis.galimullin.diplomback.dto.shader.ShaderUpsertDto;

import java.security.Principal;
import java.util.List;

public interface ShaderService {
    List<ShaderResponseDto> getALlVisibleShaders();

    List<ShaderResponseDto> getAllShaders();

    ShaderResponseDto getShaderById(Long id);

    ShaderResponseDto saveShader(ShaderUpsertDto shaderDto, Principal principal);

    ShaderResponseDto saveShader(Long id, ShaderUpsertDto shader);

    void deleteShaderById(Long id);

    void patchById(Long id, String title, String description, String code, Boolean visibility);
}
