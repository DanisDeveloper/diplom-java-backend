package danis.galimullin.diplomback.dto.user;

public record UserRegisterDto(
        String name,
        String email,
        String password
) { }
