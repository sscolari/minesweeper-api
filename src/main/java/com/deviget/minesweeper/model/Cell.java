package com.deviget.minesweeper.model;

import java.io.Serializable;

public class Cell implements Serializable {

    private boolean hasMine = false;

    public Cell() {

    }

    public boolean getHasMine() {
        return hasMine;
    }

    public void setHasMine(boolean hasMine) {
        this.hasMine = hasMine;
    }
}
