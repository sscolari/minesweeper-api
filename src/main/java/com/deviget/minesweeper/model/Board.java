package com.deviget.minesweeper.model;

import java.io.Serializable;
import java.util.Random;

public class Board implements Serializable {

    private static final int MAX_ATTEMPTS = 100;

    private final String id;
    private final Cell[][] cells;
    private final int mines;
    private boolean lost = false;
    private boolean endGame = false;
    private long startTime;
    private long endTime;

    public Board(String id, Integer rows, Integer cols, Integer mines) {
        this.id = id;
        cells = new Cell[rows][cols];
        this.mines = mines;
        initializeCells();
        initializeMines(rows, cols, mines);
        initializeAdjacentCells(rows, cols);
    }

    private void initializeCells() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    private void initializeAdjacentCells(Integer rows, Integer cols) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Cell currentCell = cells[row][col];
                if (row != 0 && col != 0) {
                    currentCell.setUpperLeft(cells[row-1][col-1]);
                }
                if (col != 0) {
                    currentCell.setLeft(cells[row][col-1]);
                }
                if (row != rows-1 && col != 0) {
                    currentCell.setBottomLeft(cells[row+1][col-1]);
                }
                if (row != 0) {
                    currentCell.setUpper(cells[row-1][col]);
                }
                if (row != rows-1) {
                    currentCell.setBottom(cells[row+1][col]);
                }
                if (row != 0 && col != cols-1) {
                    currentCell.setUpperRight(cells[row-1][col+1]);
                }
                if (col != cols-1) {
                    currentCell.setRight(cells[row][col+1]);
                }
                if (row != rows-1 && col != cols-1) {
                    currentCell.setBottomRight(cells[row+1][col+1]);
                }
            }
        }
    }

    private void initializeMines(Integer rows, Integer cols, Integer mines) {
        int currentMines = 0;
        int attempts = 0;
        Random random = new Random();
        while (currentMines < mines && attempts < MAX_ATTEMPTS) {
            int x = random.nextInt(rows);
            int y = random.nextInt(cols);
            if (!cells[x][y].isMine()) {
                cells[x][y].setMine(true);
                currentMines++;
            }
            attempts++;
        }
        if (attempts == MAX_ATTEMPTS) {
            throw new RuntimeException("Error initializing board of " + rows + " rows and " + cols + " cols with " + mines + " mines.");
        }

    }

    public String getId() {
        return id;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public boolean isLost() {
        return lost;
    }

    public boolean reveal(Integer x, Integer y) {
        if (endGame)  {
            return false;
        }
        if (x > cells.length || y > cells[0].length) {
            return false;
        }
        Cell currentCell = cells[x][y];
        boolean mine = currentCell.reveal();
        if (mine) {
            lost = true;
        }
        calculateEndGame();
        return true;
    }

    public boolean toggleFlag(Integer x, Integer y) {
        if (endGame)  {
            return false;
        }
        if (x > cells.length || y > cells[0].length) {
            return false;
        }
        Cell currentCell = cells[x][y];
        currentCell.toggleFlag();
        calculateEndGame();
        return true;
    }

    public void calculateEndGame() {
        if (lost) {
            endGame = true;
            endTime = System.currentTimeMillis();
            return;
        }
        int flags = 0;
        boolean hasHiddenCells = false;
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                if (cells[row][col].isFlag() && cells[row][col].isMine()) {
                    flags++;
                }
                if (!cells[row][col].isRevealed() && !cells[row][col].isMine()) {
                    hasHiddenCells = true;
                }
            }
        }
        if (flags != 0 && flags == mines) {
            endGame = true;
        } else {
            endGame = !hasHiddenCells;
        }
        if (endGame) {
            endTime = System.currentTimeMillis();
        }
    }

    public boolean isEndGame() {
        return endGame;
    }

    public void startGame() {
        startTime = System.currentTimeMillis();
    }

    public long getTime() {
        if (endTime == 0) {
            return 0;
        }
        return endTime - startTime;
    }

}
