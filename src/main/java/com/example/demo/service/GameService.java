package com.example.demo.service;

import com.example.demo.repository.GameRepository;
import com.example.demo.strategy.DiscountContext;
import com.example.demo.strategy.DiscountStrategy;
import com.example.demo.model.Game;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final DiscountContext discountContext;

    public GameService(GameRepository gameRepository, DiscountContext discountContext) {
        this.gameRepository = gameRepository;
        this.discountContext = discountContext;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game getGameById(Long id) {
        return gameRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
    }

    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    public Game updateGame(Long id, Game updatedGame) {
        Game existingGame = getGameById(id);
        existingGame.setTitle(updatedGame.getTitle());
        existingGame.setGenre(updatedGame.getGenre());
        existingGame.setPlatform(updatedGame.getPlatform());
        existingGame.setRating(updatedGame.getRating());
        existingGame.setReleaseDate(updatedGame.getReleaseDate());
        existingGame.setPrice(updatedGame.getPrice());
        existingGame.setDiscountType(updatedGame.getDiscountType());
        return gameRepository.save(existingGame);
    }

    public void deleteGame(Long id) {
        Game existingGame = getGameById(id);
        gameRepository.delete(existingGame);
    }

    public double calculateFinalPrice(Game game) {
        DiscountStrategy discountStrategy = discountContext.getStrategy(game.getDiscountType());
        return discountStrategy.calculatePrice(game.getPrice());
    }

    public List<Map<String, Object>> getAllGamesWithFinalPrice() {
        List<Game> games = gameRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Game game : games) {
            Map<String, Object> gameData = new HashMap<>();
            gameData.put("game", game);
            gameData.put("finalPrice", calculateFinalPrice(game));
            result.add(gameData);
        }

        return result;
    }
}
