package video2.number.services;

import org.springframework.stereotype.Service;
import video2.number.models.Match;
import video2.number.models.MatchDifficulty;
import video2.number.models.Round;
import video2.number.models.User;

@Service
public interface UserService {
    User createUser(String email, String userName);
    Match createPatida(Long userId, MatchDifficulty dificultad);
    Round playUserMatch(Long userId, Long matchId, Integer num);
}
