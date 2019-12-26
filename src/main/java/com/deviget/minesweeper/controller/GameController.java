package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.model.Board;
import com.deviget.minesweeper.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping("/new")
    public Board newGame(@RequestParam(defaultValue = "10") Integer rows,
                         @RequestParam(defaultValue = "10") Integer cols,
                         @RequestParam(defaultValue = "10") Integer mines) {
        return gameService.newGame(rows, cols, mines);
    }

    @RequestMapping("/{id}")
    public Board getGame(@PathVariable String id) {
        return gameService.getGame(id);
    }

}
