package kz.timshowtime.controllers;

import kz.timshowtime.enums.Mode;
import kz.timshowtime.models.Game;
import kz.timshowtime.models.Player;
import kz.timshowtime.models.Score;
import kz.timshowtime.services.GameService;
import kz.timshowtime.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/game")
public class GameController {
    private final Game game;
    private final GameService gameService;
    private final PlayerService playerService;

    @Autowired
    public GameController(Game game, GameService gameService, PlayerService playerService) {
        this.game = game;
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("game", game);
        return "game/index";
    }

    @GetMapping("/new-game")
    public String newGame() {
        playerService.findAll().forEach(p -> playerService.delete(p.getId()));
        return "redirect: /players/intro";
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        List<Player> playerList = playerService.findAll().stream().sorted().toList();
        boolean empty = true;
        if (playerList.size() > 0) { // finished game -> player list and list of scores
            List<Score> scoreList = playerService.initializeScoreList(playerList.get(0)).getScoreList();
            if (scoreList.size() > 0) {
                empty = false;
                model.addAttribute("players", playerList);
                model.addAttribute("date", scoreList.get(scoreList.size()-1).getDate());
            }
        }
        model.addAttribute("emptyList", empty);
        model.addAttribute("listSize", playerList.size());
        return "game/menu";
    }

    @GetMapping("/start")
    public String start() {
        gameService.setDefault(game, playerService);
        return "redirect:/game";
    }

    @PostMapping("/commitValues")
    public String commit(@ModelAttribute("game") Game game) {
        gameService.makeProcess(this.game, game);
        if (this.game.getMode().equals(Mode.TRUMPSLESS)) {
            return "redirect: /game/trumpsless";
        }

        return "redirect:/game";
    }

    @GetMapping("/trumpsless")
    public String indexTrumpsless(Model model) {
        model.addAttribute("game", game);
        return "game/trumpsless";
    }

    @PostMapping("/commitValues/trumpsless")
    public String commitTrumpsless(@ModelAttribute("game") Game game) {
        gameService.calculateValues(this.game, game);
        gameService.incrementExtraRoundForNotDefaultMode(this.game);
        if (this.game.getMode().equals(Mode.MISERE)) {
            return "redirect: /game/misere";
        }

        return "redirect: /game/trumpsless";
    }

    @GetMapping("/misere")
    public String indexMisere(Model model) {
        model.addAttribute("game", game);
        return "game/misere";
    }

    @PostMapping("/commitValues/misere")
    public String commitMisere(@ModelAttribute("game") Game game) {
        gameService.misereCalculate(this.game, game);
        gameService.incrementExtraRoundForNotDefaultMode(this.game);
        if (this.game.getMode().equals(Mode.DARK)) {
            return "redirect: /game/dark";
        }
        return "redirect: /game/misere";
    }

    @GetMapping("/dark")
    public String indexDark(Model model) {
        model.addAttribute("game", game);
        return "game/dark";
    }

    @PostMapping("/commitValues/dark")
    public String commitDark(@ModelAttribute("game") Game game) {
        gameService.calculateValues(this.game, game);
        gameService.incrementExtraRoundForNotDefaultMode(this.game);
        if (this.game.getMode().equals(Mode.GOLD)) {
            return "redirect: /game/gold";
        }
        return "redirect: /game/dark";
    }

    @GetMapping("/gold")
    public String indexGold(Model model) {
        model.addAttribute("game", game);
        return "game/gold";
    }

    @PostMapping("/commitValues/gold")
    public String commitGold(@ModelAttribute("game") Game game) {
        gameService.goldCalculate(this.game, game);
        gameService.incrementExtraRoundForNotDefaultMode(this.game);
        if (this.game.getMode().equals(Mode.DEFAULT)) {
            return "redirect: /game/results";
        }
        return "redirect: /game/gold";
    }

    @GetMapping("/results")
    public String getResults(Model model) {
        playerService.setScore(game.getPlayerList());
        playerService.setResults();
        List<Player> playerList = playerService.findAll().stream().sorted().toList();
        game.setPlayerList(playerList);
        model.addAttribute("game", game);
        return "game/results";
    }
}