var endpoint = "./minesweeper"
var gameId;

$(document).on("click", "#newGameButton", function() {
    $("#newGameError").hide();
    $(".result").hide();
    $.ajax({
      url: endpoint + "/game",
      method: "POST",
      data: "{\"rows\":\""+$("#rows").val()+"\", \"cols\":\""+$("#cols").val()+"\", \"mines\":\""+$("#mines").val()+"\"}",
      contentType:"application/json; charset=utf-8",
      dataType:"json",
      cache: false,
    }).done(function( response ) {
      processResponse(response);
    }).fail(function(response) {
        $("#newGameError").html(response.responseJSON.message);
        $("#newGameError").show();
    });
})

$(document).on("click", ".cell.hidden", function() {
    $("#error").hide();
    $.ajax({
        url: endpoint + "/game/" + gameId + "/reveal/",
        method: "PUT",
        data: "{\"x\":\""+$(this).data("x")+"\", \"y\":\""+$(this).data("y")+"\"}",
        contentType:"application/json; charset=utf-8",
        dataType:"json",
        cache: false,
    }).done(function( response ) {
        processResponse(response);
    }).fail(function(response) {
        $("#error").show();
    });
});

$(document).on("contextmenu", ".cell.hidden, .cell.flag", function(e) {
    e.preventDefault();
    $("#error").hide();
    $.ajax({
        url: endpoint + "/game/" + gameId + "/flag/",
        method: "PUT",
        data: "{\"x\":\""+$(this).data("x")+"\", \"y\":\""+$(this).data("y")+"\"}",
        contentType:"application/json; charset=utf-8",
        dataType:"json",
        cache: false,
    }).done(function( response ) {
        processResponse(response);
    }).fail(function(response) {
        $("#error").show();
    });
    return false;
});

function processResponse(response){
    gameId = response.id;
    $("#gameId").html(gameId);
    printBoard(response.cells, response.lost, response.endGame, response.time);
}

function printBoard(rows, lost, endGame, time) {
    const board = $('#board');
    var content = "<table>";
    rows.forEach(function (cols, x) {
        content += "<tr>";
        cols.forEach(function (cell, y) {
            if (!cell.revealed && !endGame) {
                if (cell.flag) {
                    content += "<td class='cell flag' data-x="+ x +" data-y="+ y +"> ?";
                } else {
                    content += "<td class='cell hidden' data-x="+ x +" data-y="+ y +">";
                }
            } else {
                if (cell.mine) {
                    if (!cell.flag) {
                        content += "<td class='cell mine error'> <img src='bomb.png'>";
                    } else {
                        content += "<td class='cell mine correct'> <img src='bomb.png'>";
                    }
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
            $(".result").text("You lose");
            $(".result").addClass("lost");
        } else {
            $(".result").text("You win!");
            $(".result").addClass("win");
        }
        var d = new Date(time);
        $("#time").text("Time: "
            + ((d.getUTCHours() > 0) ? (d.getUTCHours() + ' hours, ') : "")
            + ((d.getUTCMinutes() > 0) ? (d.getUTCMinutes() + ' minutes, ') : "")
            + d.getUTCSeconds() + " seconds");
        $("#time").show();
        $(".result").show();
    }
}

$(document).on("click", "#loadGameById", function() {
    $("#gameNotFound").hide();
    $(".result").hide();
    $.ajax({
      url: endpoint + "/game/" + $("#gameIdInput").val(),
      cache: false
    }).done(function( response ) {
      processResponse(response);
    }).fail(function(response) {
        $("#gameNotFound").show();
    });
});