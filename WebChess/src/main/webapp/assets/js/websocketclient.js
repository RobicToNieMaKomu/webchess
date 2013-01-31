var ws = null;
var jq = jQuery.noConflict();

function setConnected(connected) {
    // jq("#indicator").html();
}

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
        // var currentTable = window.location.search;
        // sendMessage(currentTable)
        setConnected(true);
        log('Info: WebSocket connection opened.');
    };
    ws.onmessage = function (event) {
        log('Received: ' + event.data);
    };
    ws.onclose = function () {
        window.alert("Connection with the server closed.");
        log('Info: WebSocket connection closed.');
    };
    refreshRemainingTime(300, 300);
};

function sendMessage(message) {
    ws.send(message);
}

function disconnect() {
    if (ws != null) {
        ws.close();
        ws = null;
    }
    setConnected(false);
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
    jq("#logs").append(message);
}

function sit(side) {
    log('tried to sit:' + side);
}