$(document).ready(function() {
    addButtonHandlers();
});

function addButtonHandlers() {
    seatHandlers();
    registerWindowCloseEvent();
}

function seatHandlers() {
    $('button#wbutton').click(function(event) {
        if (!$(this).is('.disabled')) {
            requestSeat('WHITE');
        } else {
            alert('No need to click, it\'s disabled.');
        }
    });

    $('button#bbutton').click(function(event) {
        if (!$(this).is('.disabled')) {
            requestSeat('BLACK');
        } else {
            alert('No need to click, it\'s disabled.');
        }
    });
}

function registerWindowCloseEvent() {
    $(window).bind("beforeunload", function() {
        var result = confirm("Do you really want to close?");
        if (result === true) {
            window.opener.deleteWindowHandler(window);
        }
    });
}

function requestSeat(color) {
    var requestSeat = createMessage('SIT', color);
    var jsonString = JSON.stringify(requestSeat);
    window.opener.sendMessage(jsonString);
}

function requestCurrentRoomState() {
    var requestPositions = createMessage('ROOM_STATE', '');
    var jsonString = JSON.stringify(requestPositions);
    window.opener.sendMessage(jsonString);
}

function requestCurrentChessmenPositions() {
    var requestPositions = createMessage('CHESSBOARD_STATE', '');
    var jsonString = JSON.stringify(requestPositions);
    window.opener.sendMessage(jsonString);
}

/**
 * Creates message that will be sent to the server
 * 
 * @param {String} command
 * @param {String} content
 * @returns {createMessage.result}
 */
function createMessage(command, content) {
    var pageParam = window.location.search.substring(1);
    var pair = pageParam.split("=");
    var tableId = parseInt(pair[1]);
    var result = {TABLEID: tableId, COMMAND: command, CONTENT: content};
    return result;
}

function handleIncomingServerMessage(msg, username) {
    console.log(msg);
    if (msg.COMMAND === 'ROOM_STATE') {
        var wPlayer = msg['PLAYERS']['WPLAYER'];
        var bPlayer = msg['PLAYERS']['BPLAYER'];
        var wpTime = msg['TIME']['WPLAYER'];
        var bpTime = msg['TIME']['BPLAYER'];
        var spectators = msg['SPECTATORS'];
        var options = msg['OPTIONS']; // TBD
        var gameInProgress = msg['IN_PROGRESS'];
        chessboard = msg['CHESSBOARD_STATE'];
        // Temporary variables that are used to check whether both player are ready
        var wplayerIsReady = false;
        var bplayerIsReady = false;
        // Update page according to received data
        if (wPlayer.length !== 0) {
            $('#wbutton').text(wPlayer);
            $('#wbutton').toggleClass('disabled');
            wplayerIsReady = (username === wPlayer) ? true : false;
        } else {
            $('#wbutton').toggleClass('active');
        }
        if (bPlayer.length !== 0) {
            $('#bbutton').text(wPlayer);
            $('#bbutton').toggleClass('disabled');
            bplayerIsReady = true;
        } else {
            $('#bbutton').toggleClass('active');
        }
        // Set current player's game time
        $('wplayerTime').text(wpTime);
        $('bplayerTime').text(bpTime);
        // Clear spectator list before appending new items
        $('#myTabContent > #spectators').empty();
        for (var i = 0; i < spectators; i++) {
            var spec = '<p>' + spectators[i] + '</p>';
            $('#myTabContent > #spectators').append(spec);
        }
        // Update canvases
        drawPieces();
        drawCurtains(wplayerIsReady, bplayerIsReady, gameInProgress);
    } else if (msg.COMMAND === 'CHESSBOARD_STATE') {
        var wpTime = msg['TIME']['WPLAYER'];
        var bpTime = msg['TIME']['BPLAYER'];
        chessboard = msg['CHESSBOARD_STATE'];
        // Update page according to received data
        $('wplayerTime').text(wpTime);
        $('bplayerTime').text(bpTime);
        drawPieces();
    } else if (msg.COMMAND === 'SIT') {
        var wPlayer = msg['PLAYERS']['WPLAYER'];
        var bPlayer = msg['PLAYERS']['BPLAYER'];
        // Temporary variables that are used to check whether both player are ready
        var wplayerIsReady = false;
        var bplayerIsReady = false;
        // Update page according to received data
        if (wPlayer.length !== 0) {
            $('#wbutton').text(wPlayer);
            $('#wbutton').addClass('disabled');
            wplayerIsReady = true;
            wplayerIsReady = (username === wPlayer) ? true : false;
        } else {
            $('#wbutton').addClass('active');
        }
        if (bPlayer.length !== 0) {
            $('#bbutton').text(bPlayer);
            $('#bbutton').addClass('disabled');
            bplayerIsReady = true;
        } else {
            $('#bbutton').addClass('active');
        }
        drawCurtains(wplayerIsReady, bplayerIsReady, false);
    }
}
/**
 * If both players are sitting and game hasnt started then allow white to start game.
 * If players are not ready then show 'Waiting for players' message.
 * 
 * @param {Boolean} wplayerIsReady
 * @param {Boolean} bplayerIsReady
 * @param {Boolean} gameInProgress
 */
function drawCurtains(wplayerIsReady, bplayerIsReady, gameInProgress) {
    if (!wplayerIsReady || !bplayerIsReady) {
        //  Draw blue curtain
        drawWaitingForPlayersCurtain(curtainCanvasCtx);
    } else if (wplayerIsReady && bplayerIsReady && !gameInProgress) {
        // Draw green curtain
        drawPressStartButtonCurtain(curtainCanvasCtx);
    }
    // Else do not draw anything because game is in progress
}



