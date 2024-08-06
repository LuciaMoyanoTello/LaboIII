package video2.number.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;
}
