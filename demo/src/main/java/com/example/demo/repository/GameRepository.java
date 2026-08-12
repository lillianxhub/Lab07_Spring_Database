package com.example.demo.repository;

public class GameRepository {
    @Repository
    public interface GameRepository extends JpaRepository<Game, Long> {
    }
}
