package danis.galimullin.diplomback.repository;

import danis.galimullin.diplomback.model.Shader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShaderRepository  extends JpaRepository<Shader, Long> {
    List<Shader> findAllByVisibility(boolean visibility);
}
