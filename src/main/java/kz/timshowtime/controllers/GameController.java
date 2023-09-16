package kz.timshowtime.controllers;

import kz.timshowtime.enums.Mode;
import kz.timshowtime.models.Game;
import kz.timshowtime.models.Player;
import kz.timshowtime.models.SavedScore;
import kz.timshowtime.services.GameService;
import kz.timshowtime.services.PlayerService;
import kz.timshowtime.services.SavedPlayerScoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/game")
public class GameController {
    private Game game;
    private final GameService gameService;
    private final PlayerService playerService;
    private final SavedPlayerScoresService savedPlayerScoresService;

    @Autowired
    public GameController(GameService gameService, PlayerService playerService, SavedPlayerScoresService savedPlayerScoresService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.savedPlayerScoresService = savedPlayerScoresService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("game", game);
        return "game/index";
    }

//    @GetMapping("/new-game")
//    public String newGame() { // radical new game --> delete all of players
//        playerService.findAll().forEach(p -> playerService.delete(p.getId()));
//        return "redirect: /players/intro";
//    }

    @GetMapping("/menu")
    public String menu(Model model) {
        List<Player> playerList = playerService.findAll();
        boolean hasOrNot = gameService.findAll().size() > 0;
        boolean empty = true;
        if (playerList.size() > 0) { // finished game -> player list and list of scores
            playerList = playerList.stream().sorted().toList();
            empty = false;
            model.addAttribute("players", playerList);
        }
        model.addAttribute("unFinishedGames", hasOrNot);
        model.addAttribute("emptyList", empty);
        model.addAttribute("listSize", playerList.size());
        return "game/menu";
    }

    @GetMapping("/start")
    public String start() {
        gameService.setDefault(game, playerService.findAll());
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

    @GetMapping("/selection")
    public String selector(Model model) {
        model.addAttribute("playersForSelect", playerService.findAll());
        return "game/selection";
    }

    @PatchMapping("/checkedPlayers")
    public String checkedPlayers(@RequestParam("selectedIds") List<Integer> selectedIds) {
        List<Player> playerList = new ArrayList<>();
        selectedIds.forEach(id -> playerList.add(playerService.findOne(id)));
        Collections.shuffle(playerList);
        this.game = new Game();
        gameService.setDefault(game, playerList);
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
        int gameId = game.getId();
        if (gameService.findOne(gameId) != null) {
            gameService.removePlayersGames(gameId);
            gameService.delete(gameId);
        }
        List<Player> playerList = game.getPlayerList();
        playerService.setScore(playerList);
        playerService.setResults(playerList);
        model.addAttribute("resultList", playerList.stream().sorted().toList());
        return "game/results";
    }

    @PostMapping("/saveAndQuit")
    public String saveAndQuit() {
        // если эта игра уже есть в базе, т.е в списке незавершенных
        if (gameService.findOne(game.getId()) != null) {
            gameService.update(game.getId(), game);
            game.getPlayerList().forEach(p ->
                    savedPlayerScoresService.updateSaveScore(p.getCurrentScore(), p.getId(), game.getId()));
        } else {
            gameService.save(game);
            playerService.setGames(game.getPlayerList(), game);
            game.getPlayerList().forEach(p -> playerService.commitScoreAndQuit(p, p.getCurrentScore(), game));
        }
        return "redirect:/game/menu";
    }

    @GetMapping("/continue")
    public String getContinue(Model model) {
        List<Game> gameList = gameService.initPlayerList();
        model.addAttribute("gameList", gameList);
        return "game/continue";
    }

    @PostMapping("/{id}/continue")
    public String continueGame(@PathVariable("id") int id) {
        Game game = gameService.initSaveScore(id);
        List<Player> playerList = new ArrayList<>();
        for (SavedScore savedScore : game.getSavedScoreList()) {
            Player player = savedScore.getPlayer();
            player.setCurrentScore(savedScore.getSaveScore());
            playerList.add(player);
        }
        game.setPlayerList(playerList);
        this.game = game;
        return "redirect:/game/modeDistributor";
    }

    @PostMapping("/{id}/delete")
    public String deleteGame(@PathVariable("id") int gameId) {
        gameService.removePlayersGames(gameId);
        gameService.delete(gameId);
        return "redirect: /game/continue";
    }


    @GetMapping("/modeDistributor")
    public String modeDistributor() {
        switch (this.game.getMode().getTitle()) {
            case "Стандартный": return "redirect:/game";
            case "Безкозырка": return "redirect:/game/trumpsless";
            case "Мизер": return "redirect:/game/misere";
            case "Темная": return "redirect:/game/dark";
            case "Золотая": return "redirect:/game/gold";
            default: return null;
        }
    }
}