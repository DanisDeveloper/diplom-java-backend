package danis.galimullin.diplomback;

import danis.galimullin.diplomback.model.Role;
import danis.galimullin.diplomback.model.Shader;
import danis.galimullin.diplomback.model.User;
import danis.galimullin.diplomback.repository.RoleRepository;
import danis.galimullin.diplomback.repository.ShaderRepository;
import danis.galimullin.diplomback.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DiplomBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiplomBackApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(
            UserRepository userRepository,
            ShaderRepository shaderRepository,
            RoleRepository roleRepository) {
        return args -> {
//            User user = userRepository.save(new User());
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        };
    }
}
