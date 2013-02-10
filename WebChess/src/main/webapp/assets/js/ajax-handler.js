var sitButton = "<button class='btn btn-small' type='button'>take a sit</button>";
var buttonInsideColumn = "<td>" + sitButton + "</td>";
var noTableCreated = -1;

$(document).ready(function () {
    insertButtons();
    setRowListeners();
    formatTime();
    refresWrapper();
});

function refresWrapper() {
    setTimeout(function(){
        refreshContent();
        refresWrapper();
    },5000); 
}

function insertButtons() {
    $('tbody#currentChessTables tr td:empty').html(sitButton);
}

function setRowListeners() {
    $('tbody#currentChessTables tr').live('hover', function (event) {               
        $(this).toggleClass('info');
    });
}

function formatTime() {
    $('tbody#currentChessTables tr').each(function() {
        var unformattedTime = $(this).find('td:last').text();
        var formattedTime = remainingTime(unformattedTime);
        $(this).find('td:last').text(formattedTime);
    });
}

function refreshContent() {
    $.get("/WebChess/allTables", function(data,status){
        //data contains the result list
        var listOfChessTables=" ";
        $.each(data, function(i, v){
            var table = data[i]; 
            listOfChessTables += "<tr>";
            // Id and gameTime parameters should always be not null
            listOfChessTables += "<td>"+table.tableId+"</td>";
            // If sits are empty then add buttons instead of player names
            if (table.wplayer == null) {
                listOfChessTables += buttonInsideColumn;
            } else {
                listOfChessTables += "<td>"+table.wplayer+"</td>";
            }
            if (table.bplayer == null) {
                listOfChessTables += buttonInsideColumn;
            } else {
                listOfChessTables += "<td>"+table.bplayer+"</td>";
            }
            
            listOfChessTables += "<td>"+remainingTime(table.gameTime)+"</td>";
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
    request.done(function (response, textStatus, $XHR){
        // response contains id of the newly created table or 
        // null if table wasnt created
        if (response !== null && response !== noTableCreated) {
            var url = "http://localhost:8084/WebChess/table?chessTableId=" + response;
            myWindow=window.open(url,'','width=850,height=600');
            myWindow.focus();
        } else {
            alert("Could not create another table. Please join to an existing game or wait for a free table.");
        }
    });

    // callback handler that will be called on failure
    request.fail(function ($XHR, textStatus, errorThrown){
        // log the error to the console
        console.error("The following error occured: "+textStatus, errorThrown);
    });
}
