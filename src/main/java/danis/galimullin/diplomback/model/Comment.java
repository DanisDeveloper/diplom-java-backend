package danis.galimullin.diplomback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity(name = "comments")
@Data
@RequiredArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String text;
    private Boolean hidden = false;
    private Date createdAt = new Date();

    @ManyToOne
    private User user;

    @ManyToOne
    private Shader shader;
}
