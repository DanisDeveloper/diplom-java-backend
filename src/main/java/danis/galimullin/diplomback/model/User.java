package danis.galimullin.diplomback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
@Data
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String hashedPassword;

    private Date createdAt = new Date();
    private String avatarUrl;
    private String backgroundUrl;

    @Column(columnDefinition = "TEXT")
    private String biography = "";

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Shader> shaders = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

}
