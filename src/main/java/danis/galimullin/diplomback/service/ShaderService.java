package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.dto.shader.ShaderResponseDto;
import danis.galimullin.diplomback.dto.shader.ShaderUpsertDto;
import danis.galimullin.diplomback.model.SortOption;
import org.springframework.data.domain.Page;

import java.security.Principal;

public interface ShaderService {
    Page<ShaderResponseDto> getAllVisibleShaders(String searchQuery, Integer page, Integer pageSize, SortOption sortOption);

    Page<ShaderResponseDto> getAllUserShaders(String username, String currentUsername, Integer page, Integer pageSize, SortOption sortOption);

    ShaderResponseDto getShaderById(Long id);

    ShaderResponseDto saveShader(ShaderUpsertDto shaderDto, Principal principal);

    ShaderResponseDto saveShader(Long id, ShaderUpsertDto shader);

    void deleteShaderById(Long id);

    void patchById(Long id, String title, String description, String code, Boolean visibility);

    void incrementViews(Long shaderId, String userIP);
}
