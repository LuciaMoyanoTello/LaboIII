package video2.number.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import video2.number.dtos.*;
import video2.number.models.Match;
import video2.number.models.MatchDifficulty;
import video2.number.models.Round;
import video2.number.models.User;
import video2.number.services.UserService;

@RestController
@RequestMapping("/guess-number/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){

        User user = userService.createUser(userDto.getEmail(),userDto.getUserName());

        UserDto userDtoMap = modelMapper.map(user,UserDto.class);

        return ResponseEntity.ok(userDtoMap);
    }

    @PostMapping("/{userId}/matches")
    public ResponseEntity<MatchDto> createPartida(@PathVariable Long userId, @RequestBody CreateUserMatchDto createUserMatchDto){
        Match match = userService.createPatida(userId, createUserMatchDto.getDificultad());
        MatchDto matchDto = modelMapper.map(match,MatchDto.class);
        return ResponseEntity.ok(matchDto);
    }

    @PostMapping("/{userId}/matches/{matchId}")
    public ResponseEntity<RoundDto> playPartida(@PathVariable Long userId,
                                                @PathVariable Long matchId,
                                                @RequestBody PlayUserMatchDto playUserMatchDto){
        Round round = userService.playUserMatch(userId,matchId,playUserMatchDto.getNumber());
        MatchDto matchDto = modelMapper.map(round.getMatch(),MatchDto.class);
        RoundDto roundDto = modelMapper.map(round, RoundDto.class);
        roundDto.setMatchDto(matchDto);
        return ResponseEntity.ok(roundDto);
    }
}
