package danis.galimullin.diplomback.repository;

import danis.galimullin.diplomback.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    void deleteByShaderIdAndUserName(Long shaderId, String name);
    Boolean existsByShaderIdAndUserName(Long shaderId, String name);
}
