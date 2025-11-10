package danis.galimullin.diplomback.repository;

import danis.galimullin.diplomback.model.Shader;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShaderRepository extends JpaRepository<Shader, Long> {
    Page<Shader> findAllByVisibilityAndTitleContainingIgnoreCaseAndDeletedFalse(boolean visibility, String searchQuery, Pageable pageable);

    @Query("""
            SELECT s FROM shaders s
            WHERE s.user.name = :username
                AND (s.visibility = true OR s.user.name = :currentUser)
            """)
    Page<Shader> findAllVisibleOrOwnedBy(
            @Param("username") String username,
            @Param("currentUser") String currentUser,
            Pageable pageable
    );

    @Modifying
    @Query("UPDATE shaders s SET s.views = s.views + 1 WHERE s.id = :shaderId")
    void incrementViews(@Param("shaderId") Long shaderId);

    @Modifying
    @Query("UPDATE shaders s SET s.likes = s.likes + 1 WHERE s.id = :shaderId")
    void incrementLikes(@Param("shaderId") Long shaderId);

    @Modifying
    @Query("UPDATE shaders s SET s.likes = s.likes - 1 WHERE s.id = :shaderId")
    void decrementLikes(@Param("shaderId") Long shaderId);

    @Modifying
    @Query("UPDATE shaders s SET s.comments = s.comments + 1 WHERE s.id = :shaderId")
    void incrementComments(@Param("shaderId") Long shaderId);
}
