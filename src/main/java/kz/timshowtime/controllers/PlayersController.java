package kz.timshowtime.controllers;

import kz.timshowtime.models.Player;
import kz.timshowtime.models.PlayersCount;
import kz.timshowtime.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/players")
public class PlayersController {
    private final PlayerService playerService;

    @Autowired
    public PlayersController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("players", playerService.findAll());
        return "players/index";
    }

    @GetMapping("/intro")
    public String helloPage(@ModelAttribute("count") PlayersCount count) {
        return "players/countOfPlayers";
    }

    @GetMapping("/{id}/edit")
    public String getUpdate(@PathVariable("id") int id, Model model) {
        model.addAttribute("updatePlayer", playerService.findOne(id));
        return "players/edit";
    }

    @PatchMapping("/{id}")
    public String updatePlayer(@PathVariable("id") int id,
                         @ModelAttribute("updatePlayer") Player player) {
        if (player.getName().equals("")) player.setName("Дурак_Который_Забыл_Ввести_Имя#" + id);
        playerService.update(id, player);
        return "redirect:/players";
    }

    @PatchMapping("/setPlayers")
    public String setCount(Model model, @ModelAttribute("count") @Valid PlayersCount count,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "players/countOfPlayers";
        }
        PlayersCount.createPlayerList(count.getValue(), count);
        model.addAttribute("players", count);
        return "players/setPlayers";
    }

    @PostMapping()
    public String setPlayers(@ModelAttribute("players") PlayersCount players) {
        playerService.noNamePlayers(players.getPlayerList());
        players.getPlayerList().forEach(playerService::save);
        return "redirect:/players";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("player") Player player) {
        return "players/newPlayer";
    }

    @PostMapping("/singlePlayerCreate")
    public String createOnePlayer(@ModelAttribute("player") Player player) {
        if (player.getName().equals("")) player.setName("Дурак_Который_Забыл_Ввести_Имя");
        playerService.save(player);
        return "redirect:/players";
    }

    @DeleteMapping("/{id}")
    public String deletePlayer(@PathVariable("id") int id) {
        playerService.delete(id);
        return "redirect:/players";
    }

    @GetMapping("/{id}/info")
    public String getInfo(@PathVariable("id") int id, Model model) {
        Player player = playerService.initializeScoreList(playerService.findOne(id));
        model.addAttribute("playerName", player.getName());
        model.addAttribute("scoreList", player.getScoreList().stream().sorted().toList());
        return "players/information";
    }

}