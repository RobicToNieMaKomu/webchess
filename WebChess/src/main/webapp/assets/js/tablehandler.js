$(document).ready(function() {
    addButtonHandlers();
});

function addButtonHandlers() {
    seatHandlers();
}

function seatHandlers() {
    $('button#wbutton').click(function(event) {
        if (!$(this).is('.disabled')) {
            requestSeat('white');
        } else {
            alert('No need to click, it\'s disabled.');
        }
    });

    $('button#bbutton').click(function(event) {
        if (!$(this).is('.disabled')) {
            requestSeat('black');
        } else {
            alert('No need to click, it\'s disabled.');
        }
    });
}

function requestSeat(color) {
    var requestSeat = createMessage('SIT', color);
    var jsonString = JSON.stringify(requestSeat);
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

function incomingServerMessages(msg) {
    console.log(msg);
    if (msg.COMMAND === '') {
        
    } else if (msg.COMMAND === '') {
        
    } 
}



