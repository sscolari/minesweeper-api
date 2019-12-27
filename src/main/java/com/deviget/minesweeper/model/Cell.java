package com.deviget.minesweeper.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cell implements Serializable {

    private boolean mine = false;
    private boolean revealed = false;

    private Cell upperLeft;
    private Cell left;
    private Cell bottomLeft;
    private Cell upper;
    private Cell bottom;
    private Cell upperRight;
    private Cell right;
    private Cell bottomRight;

    private List<Cell> adjacentCells = new ArrayList<>();
    private Integer adjacentMinesCount;
    private boolean flag = false;

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public int getAdjacentMinesCount() {
        if (adjacentMinesCount == null) {
            adjacentMinesCount = (int) adjacentCells.stream().filter(Cell::isMine).count();
        }
        return adjacentMinesCount;
    }

    public void setUpperLeft(Cell upperLeft) {
        this.upperLeft = upperLeft;
        adjacentCells.add(upperLeft);
    }

    public void setLeft(Cell left) {
        this.left = left;
        adjacentCells.add(left);
    }

    public void setBottomLeft(Cell bottomLeft) {
        this.bottomLeft = bottomLeft;
        adjacentCells.add(bottomLeft);
    }

    public void setUpper(Cell upper) {
        this.upper = upper;
        adjacentCells.add(upper);
    }

    public void setBottom(Cell bottom) {
        this.bottom = bottom;
        adjacentCells.add(bottom);
    }

    public void setUpperRight(Cell upperRight) {
        this.upperRight = upperRight;
        adjacentCells.add(upperRight);
    }

    public void setRight(Cell right) {
        this.right = right;
        adjacentCells.add(right);
    }

    public void setBottomRight(Cell bottomRight) {
        this.bottomRight = bottomRight;
        adjacentCells.add(bottomRight);
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public boolean reveal() {
        this.setRevealed(true);
        if (mine) {
            return true;
        } else {
            if (getAdjacentMinesCount() == 0) {
                adjacentCells.stream().forEach(Cell::revealAdjacent);
            }
            return false;
        }
    }

    private void revealAdjacent() {
        if (!mine && !revealed) {
            this.setRevealed(true);
            if (getAdjacentMinesCount() == 0) {
                adjacentCells.stream().forEach(Cell::revealAdjacent);
            }
        }
    }

    public void toggleFlag() {
        flag = !flag;
    }

    public boolean isFlag() {
        return flag;
    }
}
