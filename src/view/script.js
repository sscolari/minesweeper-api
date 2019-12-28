
var gameId;

$.ajax({
  url: "http://localhost:8080/game/new?rows=10&cols=10",
  cache: false
}).done(function( response ) {
  processResponse(response);
});

$(document).on("click", ".cell.hidden", function() {
    $.ajax({
      url: "http://localhost:8080/game/" + gameId + "/reveal/" + $(this).data("x") + "/" + $(this).data("y"),
      cache: false
    }).done(function( response ) {
      processResponse(response);
    });
});

$(document).on("contextmenu", ".cell.hidden, .cell.flag", function(e) {
    e.preventDefault();
    $.ajax({
      url: "http://localhost:8080/game/" + gameId + "/flag/" + $(this).data("x") + "/" + $(this).data("y"),
      cache: false
    }).done(function( response ) {
      processResponse(response);
    });
    return false;
});

function processResponse(response){
    gameId = response.id;
    $("#gameId").html(gameId);
    printBoard(response.cells, response.lost, response.endGame);
}

function printBoard(rows, lost, endGame) {
    const board = $('#board');
    var content = "<table>";
    rows.forEach(function (cols, x) {
        content += "<tr>";
        cols.forEach(function (cell, y) {
            if (!cell.revealed && !lost) {
                if (cell.flag) {
                    content += "<td class='cell flag' data-x="+ x +" data-y="+ y +"> ?";
                } else {
                    content += "<td class='cell hidden' data-x="+ x +" data-y="+ y +">";
                }
            } else {
                if (cell.mine) {
                    content += "<td class='cell mine'> <img src='bomb.png'>";
                } else {
                    content += "<td class='cell revealed'>";
                    if (cell.adjacentMinesCount == 0) {
                        content += " "
                    } else {
                        content += cell.adjacentMinesCount;
                    }
                }
            }
            content += "</td>";
        })
        content += "</tr>";
    })
    content += "</table>";
    board.html(content);

    if (endGame) {
        if (lost) {
            alert("You loose");
        } else {
            alert("You won!")
        }
    }

}