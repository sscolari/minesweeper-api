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

    public Board(String id, Integer rows, Integer cols, Integer mines) {
        this.id = id;
        cells = new Cell[rows][cols];
        this.mines = mines;
        initializeCells();
        initializeMines(rows, cols, mines);
        initializeAdjacentCells(rows, cols);
        print();
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

    public void print() {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                Cell cell = cells[row][col];
                if (cell.isRevealed()) {
                    if (cell.isMine()) {
                        System.out.print("X ");
                    } else {
                        System.out.print(cell.getAdjacentMinesCount() + " ");
                    }
                } else {
                    System.out.print("# ");
                }
            }
            System.out.println(" ");
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

    public void reveal(Integer x, Integer y) {
        Cell currentCell = cells[x][y];
        boolean mine = currentCell.reveal();
        if (mine) {
            lost = true;
        }
        calculateEndGame();
    }

    public void toggleFlag(Integer x, Integer y) {
        Cell currentCell = cells[x][y];
        currentCell.toggleFlag();
        calculateEndGame();
    }

    public void calculateEndGame() {
        if (lost) {
            endGame = true;
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
            return;
        }
        endGame = !hasHiddenCells;
    }

    public boolean isEndGame() {
        return endGame;
    }
}
