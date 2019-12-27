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




}
