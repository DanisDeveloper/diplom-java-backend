package danis.galimullin.diplomback.dto.user;

public record UserStatsDto(
        Long totalShaders,
        Long totalLikes,
        Long totalComments,
        Long totalViews
) {}
