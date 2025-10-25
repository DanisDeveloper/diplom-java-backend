package danis.galimullin.diplomback.controller;

import danis.galimullin.diplomback.dto.shader.ShaderCreateDto;
import danis.galimullin.diplomback.dto.shader.ShaderResponseDto;
import danis.galimullin.diplomback.model.Shader;
import danis.galimullin.diplomback.service.ShaderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shaders")
public class ShaderController {

    private final ShaderService shaderService;

    public ShaderController(ShaderService shaderService) {
        this.shaderService = shaderService;
    }

    @GetMapping("/{id}")
    public ShaderResponseDto getShader(@PathVariable Long id) {
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
    public Shader save(@RequestBody ShaderCreateDto shader) {
        return shaderService.saveShader(shader);
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
}
