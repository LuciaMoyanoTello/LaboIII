package video2.number.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @JsonProperty("username")
    private String userName;

    @Email
    private String email;
}
