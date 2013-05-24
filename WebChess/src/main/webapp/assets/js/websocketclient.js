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
    ws.onopen = function(event) {
        console.log('WebSocket connection opened');
    };
    ws.onmessage = function(event) {
        console.log(event.data);
        if (event.data !== null && event.data !== undefined) {
            var response = JSON.parse(event.data);
            var login = response['USERNAME'];
            if (login !== null && login !== undefined) {
                console.log('Welcome ' + login);
                username = login;
            } else {
                var tableHandler = findTableHandlerById(response['TABLEID']);
                if (tableHandler !== null && tableHandler !== undefined) {
                    tableHandler.handleIncomingServerMessage(response, username);
                }
            }
        }
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
 * This function is called by children of this window
 * @param {type} myVal
 * @returns {undefined}
 */
function childCallback(myVal) {
    console.log(myVal);
}
