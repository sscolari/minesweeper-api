package com.deviget.minesweeper.model;

import java.io.Serializable;

public class Board implements Serializable {

    private final String id;
    private final Cell[][] cells;

    public Board(String id, Integer rows, Integer cols) {
        this.id = id;
        cells = new Cell[rows][cols];
        initializeCells();
    }

    private void initializeCells() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public String getId() {
        return id;
    }

    public Cell[][] getCells() {
        return cells;
    }
}
