package video2.number.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import video2.number.models.MatchDifficulty;
import video2.number.models.MatchStatus;
import video2.number.models.User;
@Getter
@Setter
@NoArgsConstructor
public class MatchDto {
    private Long id;
    private MatchDifficulty dificultad;
    private Integer intentos;
}
