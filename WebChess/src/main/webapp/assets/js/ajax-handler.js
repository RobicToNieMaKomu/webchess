var sitButton = "<button class='btn btn-small' type='button'>take a sit</button>";
var buttonInsideColumn = "<td>" + sitButton + "</td>";
var noTableCreated = -1;
var tableHandlers = [];
function TableWrapper(id, handler) {
    this.id = id,
            this.handler = handler;
}

$(document).ready(function() {
    insertButtons();
    setRowListeners();
    setButtonHandlers();
    formatTime();
    refresWrapper();
    connect();
});

function refresWrapper() {
    setTimeout(function() {
        refreshContent();
        refresWrapper();
    }, 5000);
}

function insertButtons() {
    $('tbody#currentChessTables tr td:empty').html(sitButton);
}

function setRowListeners() {
    // Rows highlighting 
    $('tbody#currentChessTables tr').live('hover', function(event) {
        $(this).toggleClass('info');
    });
    // OnClick handlers
    $('tbody#currentChessTables tr').live('click', function(event) {
        var tableId = $(this).find('td:first').text();
        openTable(parseInt(tableId));
    });
}

function formatTime() {
    $('tbody#currentChessTables tr').each(function() {
        var unformattedTime = $(this).find('td:last').text();
        var formattedTime = remainingTime(unformattedTime);
        $(this).find('td:last').text(formattedTime);
    });
}

function setButtonHandlers() {
    $('tr td button.btn').live('click', function(event) {
        event.stopPropagation();
        var tableId = $(this).parent().parent().find('td:first').text();
        openTable(parseInt(tableId));
    });
}

function openTable(tableId, color) {
    var url = "http://localhost:8080/WebChess/table?chessTableId=" + tableId;
    var myWindow = window.open(url, '', 'width=850,height=600');
    myWindow.focus();
    if (null === findTableHandlerById(tableId)) {
        var windowWrapper = new TableWrapper(tableId, myWindow);
        tableHandlers.push(windowWrapper);
    }
}

function findTableHandlerById(id) {
    var result = null;
    for (var i = 0; i < tableHandlers.length; i++) {
        var windowWrapper = tableHandlers[i];
        if (windowWrapper.id === id) {
            result = windowWrapper.handler;
            break;
        }
    }
    return result;
}

function refreshContent() {
    $.get("/WebChess/allTables", function(data, status) {
        //data contains the result list
        var listOfChessTables = " ";
        $.each(data, function(i, v) {
            var table = data[i];
            listOfChessTables += "<tr>";
            // Id and gameTime parameters should always be not null
            listOfChessTables += "<td>" + table.tableId + "</td>";
            // If sits are empty then add buttons instead of player names
            if (table.wplayer === null) {
                listOfChessTables += buttonInsideColumn;
            } else {
                listOfChessTables += "<td>" + table.wplayer.login + "</td>";
            }
            if (table.bplayer === null) {
                listOfChessTables += buttonInsideColumn;
            } else {
                listOfChessTables += "<td>" + table.bplayer.login + "</td>";
            }

            listOfChessTables += "<td>" + remainingTime(table.gameTime) + "</td>";
            listOfChessTables += "</tr>";
        });
        $("#currentChessTables").html(listOfChessTables);
    });
}

function createNewTable() {
    // send new request to the server
    var request = $.ajax({
        url: "/WebChess/createTable",
        type: "post"
    });
    // callback handler that will be called on success
    request.done(function(response, textStatus, $XHR) {
        // response contains id of the newly created table or 
        // null if table wasnt created
        if (response !== null && response !== noTableCreated) {
            openTable(parseInt(response));
        } else {
            alert("Could not create another table. Please join to an existing game or wait for a free table.");
        }
    });

    // callback handler that will be called on failure
    request.fail(function($XHR, textStatus, errorThrown) {
        // log the error to the console
        console.error("The following error occured: " + textStatus, errorThrown);
    });
}
