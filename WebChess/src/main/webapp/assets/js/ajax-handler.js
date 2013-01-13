var jq = jQuery.noConflict();
Window.alert("Hello js!");
function refresh() {
    jq(function() {
        jq.post("/WebChess/",
        {},
            function(data){
                // data contains the result list
                var listOfMatches=" ";
                jQuery.each(data, function(i, v){
                    var match = data[i]; 
                    listOfMatches=listOfMatches+"<tr>";
                    listOfMatches=listOfMatches+"<td>"+match.matchId+"</td>";
                    listOfMatches=listOfMatches+"<td>"+match.progress+"</td>";
                    listOfMatches=listOfMatches+"</tr>";
                });
                jQuery("#currentMatches").html(listOfMatches);
            });
    });
    setTimeout(function(){
        refresh()
        },5000);
}
            
function add() {
    jq(function() {
        jq.post("/WebChess/createTable",
        {});
    });
}


