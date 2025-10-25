package danis.galimullin.diplomback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity(name = "shaders")
@Data
@RequiredArgsConstructor
public class Shader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private Date createdAt = new Date();

    @Column(nullable = false)
    private Date updatedAt = new Date();

    @Column(nullable = false)
    private Boolean visibility = true;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @OneToOne
    private Shader origin;
}
