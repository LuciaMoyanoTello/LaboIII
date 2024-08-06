package video2.number.services.imp;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import video2.number.entities.UserEntity;
import video2.number.models.Match;
import video2.number.models.MatchDifficulty;
import video2.number.models.Round;
import video2.number.models.User;
import video2.number.repositories.UserRepository;
import video2.number.services.MatchService;
import video2.number.services.UserService;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchService matchService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User createUser(String email, String userName) {
        //es optional porque en el repo está asi
        Optional<UserEntity> userEntityOptional = userRepository.getByEmail(email);

        if(userEntityOptional.isPresent()) {//ve si trae algo, es bool
            //Envia error
            return null;
        } else {
            //crear nuevo usuario
            UserEntity userEntity = new UserEntity();
            userEntity.setUserName(userName);
            userEntity.setEmail(email);
            //guardar en db
            UserEntity userEntitySave = userRepository.save(userEntity);
            //La clase devuelve un usuario para eso necesito mappear
            return modelMapper.map(userEntitySave,User.class);
        }
    }

    @Override
    public Match createPatida(Long userId, MatchDifficulty dificultad) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(userEntity.isEmpty()){
            throw new EntityNotFoundException();
        } else {
            User user = modelMapper.map(userEntity.get(), User.class);
            return matchService.createMatch(user,dificultad);
        }
    }

    @Override
    public Round playUserMatch(Long userId, Long matchId, Integer num) {
        Match match = matchService.getMatchById(matchId);
        if(!match.getUser().getId().equals(userId)){
            return null;
        } else {
            return matchService.playMatch(match, num);
        }
    }
}
