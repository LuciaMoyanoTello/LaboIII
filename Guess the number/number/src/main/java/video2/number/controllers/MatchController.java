package video2.number.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import video2.number.services.MatchService;

@RestController
@RequestMapping("/guess-number/matches")
public class MatchController {
    @Autowired
    private MatchService matchService;
}
