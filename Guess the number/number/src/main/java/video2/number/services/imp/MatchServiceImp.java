package video2.number.services.imp;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.stereotype.Service;
import video2.number.entities.MatchEntity;
import video2.number.entities.UserEntity;
import video2.number.models.*;
import video2.number.repositories.MatchRepository;
import video2.number.services.MatchService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class MatchServiceImp implements MatchService {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final Random random = new Random();

    @Override
    public Match createMatch(User user, MatchDifficulty dificultad) {
        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setUserEntity(modelMapper.map(user, UserEntity.class));
        matchEntity.setDificultad(dificultad);

        //ver cuantas partidas se puede hacer para adivinar el num
        switch (dificultad){
            case HARD -> matchEntity.setIntentos(5);
            case MEDIUM -> matchEntity.setIntentos(8);
            case EASY -> matchEntity.setIntentos(10);
        }

        //números del 1 al 100
        matchEntity.setNumeroMaquina(random.nextInt(101)); //0 a 100
        matchEntity.setStatus(MatchStatus.PLAYING);
        matchEntity.setCreatedAt(LocalDateTime.now());
        matchEntity.setUpdatedAt(LocalDateTime.now());

        //guardar la partida
        MatchEntity matchEntitySave = matchRepository.save(matchEntity);

        return modelMapper.map(matchEntitySave,Match.class);
    }

    @Override
    public Match getMatchById(Long matchId) {
        Optional<MatchEntity> matchEntity = matchRepository.findById(matchId);
        if(matchEntity.isEmpty()){
            throw new EntityNotFoundException();
        } else {
            return modelMapper.map(matchEntity.get(), Match.class);
        }
    }

    @Override
    public Round playMatch(Match match, Integer num) {
        Round round = new Round();
        round.setMatch(match);
        if(match.getStatus().equals(MatchStatus.FINISH)){
            //si está terminado tirar un error
            return null;
        }

        if(match.getNumeroMaquina().equals(num)){
            //TODO: calcular score y dar respuesta
            match.setStatus(MatchStatus.FINISH);
            round.setRespuesta("GANÓ");
        } else {
            //Los números de intentos se le resta 1
            match.setIntentos(match.getIntentos() -1);
            //si se quedó sin intentos
            if(match.getIntentos().equals(0)){
                match.setStatus(MatchStatus.FINISH);
                round.setRespuesta("PERDIÓ");
            } else {
                if(num > match.getNumeroMaquina()){
                    //responder que es menor al número que pasó por param
                    round.setRespuesta("Menor");
                } else {
                    //responder mayor
                    round.setRespuesta("Mayor");
                }
            }
        }
        UserEntity userEntity = modelMapper.map(match.getUser(), UserEntity.class);
        MatchEntity matchEntity = modelMapper.map(match, MatchEntity.class);
        matchEntity.setUserEntity(userEntity);
        matchEntity.setUpdatedAt(LocalDateTime.now());
        matchRepository.save(matchEntity);
        return round;
    }
}
