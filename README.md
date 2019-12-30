# minesweeper-api

## URLs
 Game: https://minesweeper-santiago-scolari.herokuapp.com/minesweeper.html
 Documentation: https://minesweeper-santiago-scolari.herokuapp.com/swagger-ui.html
 
##Design desitions
 
 The API was developed using Java and Springboot. There is only one resource **/game** with 4 operations:
 POST -> to create a new game
 GET -> to get a game
 PUT /reveal -> to reveal a cell
 PUT /flag -> to flag a cell
 
 The game logic is implemented in the Board class. I focused on complete all steps of the exercise (server, client, doc, deploy) more than in the performance. For example to calculate the end of the game I transverse all cells. A better option could be to store the mines in a different collection and check based on that. It could be an improvement for the next version.
 
 The client is implemented in a basic HTML using JS and JQuery. It is a very simple solution, but it works and it's enough to test the API and play the game. It is included in the same artifact as the server.
 
 The documentation was created using Swagger and it is available in the same server that the game.
 
 The application is deployed into Heroku.
 
