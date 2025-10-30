package danis.galimullin.diplomback.repository;

import danis.galimullin.diplomback.model.Shader;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShaderRepository extends JpaRepository<Shader, Long> {
    List<Shader> findAllByVisibility(boolean visibility);

    @Modifying
    @Query("UPDATE shaders s SET s.views = s.views + 1 WHERE s.id = :shaderId")
    void incrementViews(@Param("shaderId") Long shaderId);
}
