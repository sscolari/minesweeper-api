package com.deviget.minesweeper.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cell implements Serializable {

    private boolean mine = false;
    private boolean visible = false;

    private Cell upperLeft;
    private Cell left;
    private Cell bottomLeft;
    private Cell upper;
    private Cell bottom;
    private Cell upperRight;
    private Cell right;
    private Cell bottomRight;

    private List<Cell> adjacent = new ArrayList<>();

    public boolean getMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public int getAdjacentMines() {
        return (int) adjacent.stream().filter(Cell::getMine).count();
    }

    public void setUpperLeft(Cell upperLeft) {
        this.upperLeft = upperLeft;
        adjacent.add(upperLeft);
    }

    public void setLeft(Cell left) {
        this.left = left;
        adjacent.add(left);
    }

    public void setBottomLeft(Cell bottomLeft) {
        this.bottomLeft = bottomLeft;
        adjacent.add(bottomLeft);
    }

    public void setUpper(Cell upper) {
        this.upper = upper;
        adjacent.add(upper);
    }

    public void setBottom(Cell bottom) {
        this.bottom = bottom;
        adjacent.add(bottom);
    }

    public void setUpperRight(Cell upperRight) {
        this.upperRight = upperRight;
        adjacent.add(upperRight);
    }

    public void setRight(Cell right) {
        this.right = right;
        adjacent.add(right);
    }

    public void setBottomRight(Cell bottomRight) {
        this.bottomRight = bottomRight;
        adjacent.add(bottomRight);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
