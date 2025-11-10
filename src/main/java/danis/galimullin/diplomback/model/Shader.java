package danis.galimullin.diplomback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.util.Date;

@Entity(name = "shaders")
@Data
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE shaders SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Shader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String code;

    @Column(nullable = false)
    private Date createdAt = new Date();

    @Column(nullable = false)
    private Date updatedAt = new Date();

    @Column(nullable = false)
    private Boolean visibility = true;

    private Long views = 0L;
    private Long likes = 0L;
    private Long comments = 0L;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    private Shader origin;

    @Column(nullable = false)
    private Boolean deleted = false;
}
