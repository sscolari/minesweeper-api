package com.deviget.minesweeper.services;

import com.deviget.minesweeper.model.Board;
import com.deviget.minesweeper.model.Cell;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    private static final int MAX_ATTEMPTS = 100;
    private Map<String, Board> games = new HashMap<>();

    public Board newGame(Integer rows, Integer cols, Integer mines) {
        String id = UUID.randomUUID().toString();
        Board board = new Board(id, rows, cols);
        initializeMines(board, rows, cols, mines);

        games.put(id, board);
        return board;
    }

    private void initializeMines(Board board, Integer rows, Integer cols, Integer mines) {
        int currentMines = 0;
        int attempts = 0;
        Random random = new Random();
        Cell[][] cells = board.getCells();
        while (currentMines < mines && attempts < MAX_ATTEMPTS) {
            int x = random.nextInt(rows);
            int y = random.nextInt(cols);
            if (!cells[x][y].getHasMine()) {
                cells[x][y].setHasMine(true);
                currentMines++;
            }
            attempts++;
        }
        if (attempts == MAX_ATTEMPTS) {
            throw new RuntimeException("Error initializing board of " + rows + " rows and " + cols + " cols with " + mines + " mines.");
        }

    }

    public Board getGame(String id) {
        return games.get(id);
    }




}
