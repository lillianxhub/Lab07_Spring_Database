package com.example.demo.controller;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/{id}")
    public Game getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @GetMapping("/save")
    public Game saveGame(@RequestBody Game game) {
        return gameService.SaveGame(game);
    }

    @GetMapping("/edit")
    public Game editGame(@PathVariable Long id, @RequestBody Game updatedGame) {
        return gameService.EditGame(id, updatedGame);
    }

    @GetMapping("/update/{id}")
    public Game updateGame(@PathVariable Long id, @RequestBody Game updatedGame) {
        return gameService.updateGame(id, updatedGame);
    }

    @GetMapping("/delete/{id}")
    public Game confirmDeleteGame(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @GetMapping("/delete/{id}")
    public void deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
    }
}
