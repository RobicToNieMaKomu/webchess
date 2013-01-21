var ws = null;

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('echo').disabled = !connected;
}

function connect() {
    // 'ws://' + window.location.host + target
    var target = 'ws://localhost:8084/WebChess/simple';

    if ('WebSocket' in window) {
        ws = new WebSocket(target);
    } else if ('MozWebSocket' in window) {
        ws = new MozWebSocket(target);
    } else {
        alert('WebSocket is not supported by this browser.');
        return;
    }
    ws.onopen = function () {
        setConnected(true);
        log('Info: WebSocket connection opened.');
    };
    ws.onmessage = function (event) {
        log('Received: ' + event.data);
    };
    ws.onclose = function () {
        setConnected(false);
        log('Info: WebSocket connection closed.');
    };
};

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

function updateTarget(target) {
    if (window.location.protocol == 'http:') {
        document.getElementById('target').value = 'ws://' + window.location.host + target;
    } else {
        document.getElementById('target').value = 'wss://' + window.location.host + target;
    }
}

function log(message) {
    var console = document.getElementById('logs');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(message));
    console.appendChild(p);
    while (console.childNodes.length > 25) {
        console.removeChild(console.firstChild);
    }
    console.scrollTop = console.scrollHeight;
}