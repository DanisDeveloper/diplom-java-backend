package danis.galimullin.diplomback.controller;

import danis.galimullin.diplomback.dto.comment.CommentCreateDto;
import danis.galimullin.diplomback.dto.comment.CommentResponseDto;
import danis.galimullin.diplomback.dto.shader.ShaderResponseDto;
import danis.galimullin.diplomback.dto.shader.ShaderUpsertDto;
import danis.galimullin.diplomback.service.CommentService;
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
    private final CommentService commentService;

    public ShaderController(ShaderService shaderService,
                            LikeService likeService,
                            CommentService commentService) {
        this.shaderService = shaderService;
        this.likeService = likeService;
        this.commentService = commentService;
    }

    // ========== SHADERS ==========
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

    // ========== LIKES ==========
    @GetMapping("/{shaderId}/like")
    public Boolean isShaderLiked(@PathVariable Long shaderId, Principal principal) {
        return likeService.isShaderLiked(shaderId, principal.getName());
    }

    @PostMapping("/{shaderId}/like")
    public void likeShader(@PathVariable Long shaderId, Principal principal) {
        likeService.likeShader(shaderId, principal.getName());
    }

    @DeleteMapping("/{shaderId}/like")
    public void unlikeShader(@PathVariable Long shaderId, Principal principal) {
        likeService.unlikeShader(shaderId, principal.getName());
    }

    // ========== COMMENTS ==========

    @GetMapping("/{shaderId}/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long shaderId) {
        return commentService.getAllShaderComments(shaderId);
    }

    @PostMapping("/{shaderId}/comment")
    public CommentResponseDto comment(@PathVariable Long shaderId, @RequestBody CommentCreateDto commentCreateDto, Principal principal) {
        return commentService.commentShader(shaderId, commentCreateDto.text(), principal.getName());
    }

    @PatchMapping("/{shaderId}/comments/{commentId}")
    public void comment(@PathVariable Long shaderId,
                        @PathVariable Long commentId,
                        @RequestParam Boolean hidden) {
        // TODO возможно стоит добавить функционал проверки, что пользователь авторизован
        // TODO добавить функционал по изменению комментария
        commentService.setHiddenCommentStatus(commentId, hidden);
    }
}
