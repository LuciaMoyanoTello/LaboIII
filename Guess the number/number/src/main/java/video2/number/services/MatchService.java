package video2.number.services;

import org.springframework.stereotype.Service;
import video2.number.models.Match;
import video2.number.models.MatchDifficulty;
import video2.number.models.Round;
import video2.number.models.User;

@Service
public interface MatchService {
    Match createMatch(User user, MatchDifficulty dificultad);
    Match getMatchById(Long matchId);
    Round playMatch(Match match, Integer num);
}
