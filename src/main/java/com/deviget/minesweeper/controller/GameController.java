package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.model.Board;
import com.deviget.minesweeper.model.GameConfiguration;
import com.deviget.minesweeper.model.Position;
import com.deviget.minesweeper.services.GameService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/minesweeper")
@CrossOrigin
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping(value = "/game", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Create a new game", notes = "Create a new game with the given parameters")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid parameters."),
            @ApiResponse(code = 500, message = "Configuration error.")
    })
    public Board newGame(
            @ApiParam(value = "Configuration to initialize the game (rows, cols and mines)")
            @RequestBody GameConfiguration gameConfiguration) {
        if (gameConfiguration.getRows() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid row value");
        }
        if (gameConfiguration.getCols() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid cols value");
        }
        if (gameConfiguration.getMines() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid mines value");
        }
        return gameService.newGame(gameConfiguration.getRows(), gameConfiguration.getCols(), gameConfiguration.getMines());
    }

    @RequestMapping(value = "/game/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a game", notes = "Get a game stored by id")
    @ApiResponse(code = 404, message = "Game not found.")
    public Board getGame(
            @ApiParam(value = "Id of a previous created game")
            @PathVariable String id) {
        Board game = gameService.getGame(id);
        if (game != null) {
            return game;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "game not found");
    }

    @RequestMapping(value = "/game/{id}/reveal", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Reveal a cell of a game")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Incorrect parameters."),
            @ApiResponse(code = 404, message = "Game not found.")
    })
    public Board reveal(
            @ApiParam(value = "Id of the game")
            @PathVariable String id,
            @ApiParam(value = "Position of a cell in the board to execute the operation")
            @RequestBody Position position) {
        try {
            Board game = gameService.reveal(id, position.getX(), position.getY());
            if (game != null) {
                return game;
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "game not found");
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @RequestMapping(value = "/game/{id}/flag", method = RequestMethod.PUT)
    @ApiOperation(value = "Flag a cell of a game")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Incorrect parameters."),
            @ApiResponse(code = 404, message = "Game not found.")
    })
    public Board flag(
            @ApiParam(value = "Id of the game")
            @PathVariable String id,
            @ApiParam(value = "Position of a cell in the board to execute the operation")
            @RequestBody Position position) {
        try {
            Board game = gameService.flag(id, position.getX(), position.getY());
            if (game != null) {
                return game;
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "game not found");
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
