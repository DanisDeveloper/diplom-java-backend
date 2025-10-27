package danis.galimullin.diplomback.controller;

import danis.galimullin.diplomback.dto.shader.ShaderUpsertDto;
import danis.galimullin.diplomback.dto.shader.ShaderResponseDto;
import danis.galimullin.diplomback.service.ShaderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shaders")
public class ShaderController {

    private final ShaderService shaderService;

    public ShaderController(ShaderService shaderService) {
        this.shaderService = shaderService;
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
    public ShaderResponseDto save(@RequestBody ShaderUpsertDto shader) {
        return shaderService.saveShader(shader);
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
}
