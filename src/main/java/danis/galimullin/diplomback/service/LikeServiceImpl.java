package danis.galimullin.diplomback.service;

import danis.galimullin.diplomback.exception.LikeAlreadyExists;
import danis.galimullin.diplomback.exception.ShaderNotFoundException;
import danis.galimullin.diplomback.exception.UserNotFoundException;
import danis.galimullin.diplomback.model.Like;
import danis.galimullin.diplomback.model.Shader;
import danis.galimullin.diplomback.model.User;
import danis.galimullin.diplomback.repository.LikeRepository;
import danis.galimullin.diplomback.repository.ShaderRepository;
import danis.galimullin.diplomback.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final ShaderRepository shaderRepository;

    public LikeServiceImpl(
            LikeRepository likeRepository,
            UserRepository userRepository,
            ShaderRepository shaderRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.shaderRepository = shaderRepository;
    }

    @Override
    @Transactional
    public void likeShader(Long shaderId, String username) {
        if(likeRepository.existsByShaderIdAndUserName(shaderId, username)) {
            throw new LikeAlreadyExists();
        }
        User user = userRepository.findByName(username).orElseThrow(UserNotFoundException::new);
        Shader shader = shaderRepository.findById(shaderId).orElseThrow(ShaderNotFoundException::new);
        Like like = new Like();
        like.setUser(user);
        like.setShader(shader);
        likeRepository.save(like);
    }

    @Override
    @Transactional
    public void unlikeShader(Long shaderId, String username) {
        likeRepository.deleteByShaderIdAndUserName(shaderId, username);
    }

    @Override
    public Boolean isShaderLiked(Long shaderId, String username) {
        return likeRepository.existsByShaderIdAndUserName(shaderId, username);
    }

}
