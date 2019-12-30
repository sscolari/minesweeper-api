# minesweeper-api

## URLs
 Game: https://minesweeper-santiago-scolari.herokuapp.com/minesweeper.html
 Documentation: https://minesweeper-santiago-scolari.herokuapp.com/swagger-ui.html
 
## Design desitions
 
 The API was developed using Java and Springboot. There is only one resource **/game** with 4 operations:
  - POST -> create a new game
  - GET -> get a game
  - PUT /reveal -> reveal a cell
  - PUT /flag -> flag a cell
 
 The game logic is implemented in the Board class. I focused on complete all steps of the exercise (server, client, doc, deploy) more than in the performance. For example to calculate the end of the game I transverse all cells. A better option could be to store the mines in a different collection and check based on that. It could be an improvement for the next version.
 
 The client is implemented in a basic HTML using JS and JQuery. It is a very simple solution, but it works and it's enough to test the API and play the game. It is included in the same artifact as the server.
 
 The documentation was created using Swagger and it is available in the same server that the game.
 
 The application is deployed into Heroku.
 
 ## Task resume:
 - Design and implement a documented RESTful API for the game (think of a mobile app for your API) - **DONE**
 - Implement an API client library for the API designed above. Ideally, in a different language, of your preference, to the one used for the API **DONE**
 - When a cell with no adjacent mines is revealed, all adjacent squares will be revealed (and repeat) **DONE**
 - Ability to 'flag' a cell with a question mark or red flag **DONE in the server and client (rigth button)**
 - Detect when game is over **DONE win and lose cases** 
 - Persistence **DONE. The games are stored in a Map in memory.**
 - Time tracking **DONE**
 - Ability to start a new game and preserve/resume the old ones **DONE**
 - Ability to select the game parameters: number of rows, columns, and mines **DONE**
 - Ability to support multiple users/accounts **PENDING. Multiple games can be created from different clients. The user/account concept is not implemented.**
