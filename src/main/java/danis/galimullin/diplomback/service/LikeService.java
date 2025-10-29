package danis.galimullin.diplomback.service;

public interface LikeService {
    void likeShader(Long shaderId, String username);
    void unlikeShader(Long shaderId, String username);
    Boolean isShaderLiked(Long shaderId, String username);
}
