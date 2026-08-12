package com.example.demo.controller;

import com.example.demo.model.Game;
import com.example.demo.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("")
    public String getAllGames(Model model) {
        model.addAttribute("games", gameService.getAllGamesWithFinalPrice());
        return "games/list";
    }

    @GetMapping("/add")
    public String showAddGameForm(Model model) {
        model.addAttribute("game", new Game());
        return "games/add";
    }

    @PostMapping("/save")
    public String saveGame(Game game, RedirectAttributes redirectAttributes) {
        gameService.saveGame(game);
        redirectAttributes.addFlashAttribute("message", "เพิ่มเกม \"" + game.getTitle() + "\" สำเร็จ!");
        return "redirect:/games";
    }

    @GetMapping("/edit/{id}")
    public String showEditGameForm(@PathVariable Long id, Model model) {
        model.addAttribute("game", gameService.getGameById(id));
        return "games/edit";
    }

    @PostMapping("/update/{id}")
    public String updateGame(@PathVariable Long id, Game game, RedirectAttributes redirectAttributes) {
        gameService.updateGame(id, game);
        redirectAttributes.addFlashAttribute("message", "อัปเดตเกม \"" + game.getTitle() + "\" สำเร็จ!");
        return "redirect:/games";
    }

    @GetMapping("/delete/{id}")
    public String confirmDeleteGame(@PathVariable Long id, Model model) {
        model.addAttribute("game", gameService.getGameById(id));
        return "games/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteGame(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Game game = gameService.getGameById(id);
        String title = game.getTitle();
        gameService.deleteGame(id);
        redirectAttributes.addFlashAttribute("message", "ลบเกม \"" + title + "\" สำเร็จ!");
        return "redirect:/games";
    }
}
