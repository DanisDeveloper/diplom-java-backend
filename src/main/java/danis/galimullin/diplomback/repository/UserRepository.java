package danis.galimullin.diplomback.repository;

import danis.galimullin.diplomback.dto.user.UserProfileDto;
import danis.galimullin.diplomback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE users u SET u.avatarUrl = :avatarUrl WHERE  u.name = :username")
    void updateAvatar(@Param("username") String username, @Param("avatarUrl") String avatarUrl);

    @Modifying
    @Query("UPDATE users u SET u.backgroundUrl = :backgroundUrl WHERE  u.name = :username")
    void updateBackground(@Param("username") String username, @Param("backgroundUrl") String backgroundUrl);

    @Modifying
    @Query("UPDATE users u SET u.hashedPassword = :hashedPassword WHERE  u.name = :username")
    void updatePassword(@Param("username") String username, @Param("hashedPassword") String hashedPassword);

    @Query("""
                SELECT new danis.galimullin.diplomback.dto.user.UserProfileDto(
                    u.id,
                    u.name,
                    u.createdAt,
                    u.avatarUrl,
                    u.backgroundUrl,
                    u.biography,
                    new danis.galimullin.diplomback.dto.user.UserStatsDto(
                        count(s),
                        coalesce(sum(s.likes), 0L) ,
                        coalesce(sum(s.comments), 0L) ,
                        coalesce(sum(s.views), 0L)
                    )
                )
                FROM users u
                LEFT JOIN u.shaders s
                WHERE u.name = :username
                GROUP BY u
            """)
    Optional<UserProfileDto> findProfileWithStats(@Param("username") String username);
}
