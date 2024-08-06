package video2.number.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import video2.number.models.MatchDifficulty;
import video2.number.models.MatchStatus;
import video2.number.models.User;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "matches")
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne //muchos match van a pertenecer a un usuario
    private UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    private MatchDifficulty dificultad;

    private Integer numeroMaquina;

    private Integer intentos;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    //cuando se creo la partida
    @Column
    private LocalDateTime createdAt;

    //cuando se actualizó la partida
    @Column
    private LocalDateTime updatedAt;
}
