package com.deviget.minesweeper.services;

import com.deviget.minesweeper.model.Board;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class GameService {

    private Map<String, Board> games = new HashMap<>();

    public Board newGame(Integer rows, Integer cols, Integer mines) {
        String id = UUID.randomUUID().toString();
        Board board = new Board(id, rows, cols, mines);
        board.startGame();
        games.put(id, board);
        return board;
    }

    public Board getGame(String id) {
        return games.get(id);
    }

    public Board reveal(String id, Integer x, Integer y) {
        Board board = games.get(id);
        if (board == null) {
            return null;
        }
        boolean result = board.reveal(x, y);
        if (result == false) {
            throw new IllegalArgumentException("Invalid action");
        }
        board.calculateEndGame();
        return board;
    }

    public Board flag(String id, Integer x, Integer y) {
        Board board = games.get(id);
        if (board == null) {
            return null;
        }
        boolean result = board.toggleFlag(x, y);
        if (result == false) {
            throw new IllegalArgumentException("Invalid action");
        }
        return board;
    }
}
