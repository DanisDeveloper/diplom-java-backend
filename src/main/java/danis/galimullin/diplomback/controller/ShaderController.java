package danis.galimullin.diplomback.controller;

import danis.galimullin.diplomback.dto.shader.ShaderResponseDto;
import danis.galimullin.diplomback.dto.shader.ShaderUpsertDto;
import danis.galimullin.diplomback.service.LikeService;
import danis.galimullin.diplomback.service.ShaderService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/shaders")
public class ShaderController {

    private final ShaderService shaderService;
    private final LikeService likeService;

    public ShaderController(ShaderService shaderService, LikeService likeService) {
        this.shaderService = shaderService;
        this.likeService = likeService;
    }

    @GetMapping("/{id}")
    public ShaderResponseDto getShaderById(@PathVariable Long id) {
        return shaderService.getShaderById(id);
    }

    @GetMapping
    public List<ShaderResponseDto> getAllShaders() {
        return shaderService.getAllShaders();
    }

    //    @GetMapping
//    public List<ShaderResponseDto> getAllVisibleShaders() {
//        return shaderService.getALlVisibleShaders();
//    }
//
    @PostMapping
    public ShaderResponseDto save(@RequestBody ShaderUpsertDto shader, Principal principal) {
        return shaderService.saveShader(shader, principal);
    }

    @PutMapping("/{id}")
    public ShaderResponseDto save(@PathVariable Long id, @RequestBody ShaderUpsertDto shader) {
        return shaderService.saveShader(id, shader);
    }

    @PatchMapping("/{id}")
    public void patch(
            @PathVariable Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) Boolean visibility) {
        shaderService.patchById(id, title, description, code, visibility);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        shaderService.deleteShaderById(id);
    }

//
//    @PostMapping("/{shaderId}/like")
//    public void likeShader(@PathVariable Long shaderId) {
//        likeService.likeShader(shaderId);
//    }
//
//    @DeleteMapping("/{shaderId}/like")
//    public void unlikeShader(@PathVariable Long shaderId) {
//        likeService.unlikeShader(shaderId);
//    }
}
