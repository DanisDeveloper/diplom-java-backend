package danis.galimullin.diplomback.dto.user;

import danis.galimullin.diplomback.model.User;

public record UserStateDto(
        Long id,
        String name
) {
    static public UserStateDto fromUser(User user) {
        return new UserStateDto(
                user.getId(),
                user.getName()
        );
    }
}
