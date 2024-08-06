package video2.number.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import video2.number.models.MatchDifficulty;
@Getter
@Setter
@NoArgsConstructor
public class CreateUserMatchDto {
    private MatchDifficulty dificultad;
}
