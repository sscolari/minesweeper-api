package com.deviget.minesweeper.services;

import com.deviget.minesweeper.model.Board;
import com.deviget.minesweeper.model.Cell;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    private Map<String, Board> games = new HashMap<>();

    public Board newGame(Integer rows, Integer cols, Integer mines) {
        String id = UUID.randomUUID().toString();
        Board board = new Board(id, rows, cols, mines);
        games.put(id, board);
        return board;
    }

    public Board getGame(String id) {
        return games.get(id);
    }

    public Board reveal(String id, Integer x, Integer y) {
        Board board = games.get(id);
        board.reveal(x, y);
        board.calculateEndGame();
        board.print();
        return board;
    }

    public Board flag(String id, Integer x, Integer y) {
        Board board = games.get(id);
        board.toggleFlag(x, y);
        board.print();
        return board;
    }
}
