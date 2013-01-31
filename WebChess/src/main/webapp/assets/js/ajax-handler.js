// Variables
var jq = jQuery.noConflict();
var buttonInsideColumn = "<td>" + "<button class='btn btn-small' type='button'>take a sit</button>" + "</td>";
// Functions
function refresh() {
    // Avoid instant reloading page and getting json response too early
    jq(document).ready(function() {
    jq(function() {
        jq.get("/WebChess/allTables", function(data,status){
            //data contains the result list
            var listOfChessTables=" ";
            jq.each(data, function(i, v){
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
                listOfChessTables += "<td>"+table.gameTime+"</td>";
                listOfChessTables += "</tr>";
            });
            jq("#currentChessTables").html(listOfChessTables);
        });
    });
    setTimeout(function(){
        refresh()
    },5000);
    });
}
            
function createNewTable() {
    // send new request to the server
    var request = jq.ajax({
        url: "/WebChess/createTable",
        type: "post"
    });
    // callback handler that will be called on success
    request.done(function (response, textStatus, jqXHR){
        // response contains id of the newly created table
        var url = "http://localhost:8084/WebChess/table?chessTableId=" + response;
        myWindow=window.open(url,'','width=850,height=600');
        myWindow.focus();
    });

    // callback handler that will be called on failure
    request.fail(function (jqXHR, textStatus, errorThrown){
        // log the error to the console
        console.error("The following error occured: "+textStatus, errorThrown);
    });
}


