package com.example.demo.service;

import com.example.demo.repository.GameRepository;
import com.example.demo.strategy.DiscountContext;

public class GameService {
    @Service
    private final GameRepository gameRepository;
    private final DiscountContext discountContext;

    public GameService(GameRepository gameRepository, DiscountContext discountContext) {
        this.gameRepository = gameRepository;
        this.discountContext = discountContext;
    }

    public list<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + id));
    }

    public Game SaveGame(Game game) {
        return gameRepository.save(game);
    }

    public Game EditGame(Long id, Game updatedGame) {
        Game existingGame = getGameById(id);
        existingGame.setTitle(updatedGame.getTitle());
        existingGame.setGenre(updatedGame.getGenre());
        existingGame.setPlatform(updatedGame.getPlatform());
        existingGame.setRating(updatedGame.getRating());
        existingGame.setReleaseDate(updatedGame.getReleaseDate());
        existingGame.setPrice(updatedGame.getPrice());
        return gameRepository.save(existingGame);
    }

    public Game updateGame(Long id, Game updatedGame) {
        Game existingGame = getGameById(id);
        existingGame.setTitle(updatedGame.getTitle());
        existingGame.setGenre(updatedGame.getGenre());
        existingGame.setPlatform(updatedGame.getPlatform());
        existingGame.setRating(updatedGame.getRating());
        existingGame.setReleaseDate(updatedGame.getReleaseDate());
        existingGame.setPrice(updatedGame.getPrice());
        return gameRepository.save(existingGame);
    }
}
