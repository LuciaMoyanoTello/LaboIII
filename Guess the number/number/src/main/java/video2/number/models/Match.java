package video2.number.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Match {
    private Long id;
    private User user;
    private MatchDifficulty dificultad;
    private Integer numeroMaquina;
    private Integer intentos;
    private MatchStatus status;
}
