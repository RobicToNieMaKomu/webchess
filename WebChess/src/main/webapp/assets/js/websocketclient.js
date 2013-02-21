var ws = null;

function connect() {
    var target = 'ws://localhost:8080/WebChess/socket';

    if ('WebSocket' in window) {
        ws = new WebSocket(target);
    } else if ('MozWebSocket' in window) {
        ws = new MozWebSocket(target);
    } else {
        alert('WebSocket is not supported by this browser.');
        return;
    }
    ws.onopen = function() {
        console.log('WebSocket connection opened.');
    };
    ws.onmessage = function(event) {
        console.log(event.data);
        myWindow.sayHello(event.data);
    };
    ws.onclose = function() {
        window.alert("Connection with the server closed.");
        log('Info: WebSocket connection closed.');
    };
}


function sendMessage(message) {
    ws.send(message);
}

function disconnect() {
    if (ws !== null) {
        ws.close();
        ws = null;
    }
}

function log(message) {
    message = "<p>" + message + "</p>";
    $("#logs").append(message);
}

/**
 * This function is called from children of this window
 * @param {type} myVal
 * @returns {undefined}
 */
function childCallback(myVal) {
    console.log(myVal);
}
