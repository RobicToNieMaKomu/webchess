var ws = null;
$(document).ready(function() {
    connect();
    addButtonHandlers();
});

function connect() {
    var target = 'ws://localhost:8084/WebChess/simple' + window.location.search;
    
    if ('WebSocket' in window) {
        ws = new WebSocket(target);
    } else if ('MozWebSocket' in window) {
        ws = new MozWebSocket(target);
    } else {
        alert('WebSocket is not supported by this browser.');
        return;
    }
    ws.onopen = function () {
        log('Info: WebSocket connection opened.');
        var requestState = createMessage('CHAT','Morning fellas!'); 
        var jsonString = JSON.stringify(requestState);
        sendMessage(jsonString);
    };
    ws.onmessage = function (event) {
        log(event.data);
        // tableId=<X>;<COMMAND>;<instruction(s) separated by coma>
    };
    ws.onclose = function () {
        window.alert("Connection with the server closed.");
        log('Info: WebSocket connection closed.');
    };
}
/**
 * Creates request to be to the server
 */
function createMessage(command,content) {
    var pageParam = window.location.search.substring(1);
    var pair = pageParam.split("=");
    var tableId = parseInt(pair[1]);
    var result = {TABLEID:tableId,COMMAND:command,CONTENT:content};
    return result;
}

function sendMessage(message) {
    ws.send(message);
}

function disconnect() {
    if (ws != null) {
        ws.close();
        ws = null;
    }
}

function echo() {
    if (ws != null) {
        var message = document.getElementById('message').value;
        log('Sent: ' + message);
        ws.send(message);
    } else {
        alert('WebSocket connection not established, please connect.');
    }
}

function log(message) {
    message = "<p>" + message + "</p>";
    $("#logs").append(message);
}

function addButtonHandlers() {
    $('button#wbutton').click(function(event) {
        $(this).disabled = 'disabled';
        $(this).text('Andrew Golota');
        $(this).addClass('disabled');
    });
    
    $('button#bbutton').click(function(event) {
        $(this).disabled = 'disabled';
        $(this).text('Riddick Bowe');
        $(this).addClass('disabled');
    });
}