// Global variables
var SUFFIX = '.png';
var ctx = null;
var chessmenCanvasCtx = null;
var glassCanvasCtx = null;
var curtainCanvasCtx = null;
var width = 480;
var height = 480;
var piecesImg = null;
var chessboard = null;
var startX = -1;
var startY = -1;
var canvas = null;
var chessmenCanvas = null;
var glassCanvas = null;
var curtainCanvas = null;


$(document).ready(function() {
    // Initialize canvases
    canvas = document.getElementById("chessboardCanvas");
    chessmenCanvas = document.getElementById("chessmenCanvas");
    glassCanvas = document.getElementById("glassCanvas");
    curtainCanvas = document.getElementById("curtainCanvas");
    // Initialize contex for each canvas
    ctx = canvas.getContext("2d");
    glassCanvasCtx = glassCanvas.getContext("2d");
    chessmenCanvasCtx = chessmenCanvas.getContext("2d");
    curtainCanvasCtx = curtainCanvas.getContext("2d");
    // Add listeners, draw chessbord, load pieces, etc.
    drawWaitingForPlayersCurtain(curtainCanvasCtx);
    drawChessboard(ctx, canvas.width);
    loadPieces();
    addMouseHandlers(chessmenCanvas, curtainCanvas); // ChessmenCanvas because it is the foreground layer
    // Finally, request room status (chessboard status, players, game time, etc.)
    requestCurrentRoomState();
});

function addMouseHandlers(chessmenCanvas, curtainCanvas) {
    // Listeners bellow are responsible for handling user's basic interactions
    // concerning game - moving pieces, highlighting fields, etc
    chessmenCanvas.addEventListener('mousedown', canvasMouseDown, false);
    chessmenCanvas.addEventListener('mousemove', highlightSquare, false);
    chessmenCanvas.addEventListener('mouseup', canvasMouseUp, false);
    // Listeners bellow are reponsible for client interactions concerning 'Start' button/canvas
    curtainCanvas.addEventListener('mouseover', mouseOverCurtainHandler, false);
    curtainCanvas.addEventListener('mouseout', mouseOutCurtainHandler, false);
    curtainCanvas.addEventListener('click', curtainOnClick, false);
}

function canvasMouseUp() {
    startX = -1;
    staryY = -1;
}

function canvasMouseDown(event) {
    var xx = getAbsoluteMousePosition(event).x;
    var yy = getAbsoluteMousePosition(event).y;
    console.log('get dooown!' + xx + ' ' + yy);
    startX = xx;
    startY = yy;
    highlightField(startX, startY, width / 8);
}

function highlightSquare(event) {
    glassCanvasCtx.clearRect(0, 0, width, height);
    var squareWidth = width / 8;
    var x = Math.floor(getAbsoluteMousePosition(event).x / squareWidth);
    var y = Math.floor(getAbsoluteMousePosition(event).y / squareWidth);
    // current cursor position
    highlightField(x, y, squareWidth);
    // starting field
    if (startX !== -1 || startY !== -1) {
        x = Math.floor(startX / squareWidth);
        y = Math.floor(startY / squareWidth);
        highlightField(x, y, squareWidth, true);
    }
}

function getAbsoluteMousePosition(event) {
    var rect = canvas.getBoundingClientRect();
    var xx = event.clientX - rect.left;
    var yy = event.clientY - rect.top;
    return {x: xx, y: yy};
}


function highlightField(x, y, squareWidth, hasBackground) {
    glassCanvasCtx.fillStyle = 'blue';
    glassCanvasCtx.fillRect(x * squareWidth, y * squareWidth, squareWidth, squareWidth);
    if (!hasBackground) {
        glassCanvasCtx.fillStyle = 'white';
        var offset = 4;
        glassCanvasCtx.fillRect(x * squareWidth + offset, y * squareWidth + offset, squareWidth - 2 * offset, squareWidth - 2 * offset);
    }
}

function mouseOverCurtainHandler() {
    if (gameStatusHolder.readyToStart && !gameStatusHolder.isReadyButtonHighlighted) {
        curtainCanvasCtx.clearRect(curtainDim.innerX, curtainDim.innerY, curtainDim.innerWidth, curtainDim.innerHeight);
        curtainCanvasCtx.globalAlpha = 0.95;
        curtainCanvasCtx.fillStyle = '#00CC00';
        curtainCanvasCtx.fillRect(curtainDim.innerX, curtainDim.innerY, curtainDim.innerWidth, curtainDim.innerHeight);
        drawText(curtainCanvasCtx, 'START');
    }
}

function mouseOutCurtainHandler() {
    if (gameStatusHolder.readyToStart === true) {
        curtainCanvasCtx.clearRect(curtainDim.innerX, curtainDim.innerY, curtainDim.innerWidth, curtainDim.innerHeight);
        curtainCanvasCtx.globalAlpha = 0.95;
        curtainCanvasCtx.fillStyle = '#67E667';
        curtainCanvasCtx.fillRect(curtainDim.innerX, curtainDim.innerY, curtainDim.innerWidth, curtainDim.innerHeight);
        drawText(curtainCanvasCtx, 'Click to start', 'the game');
        gameStatusHolder.isReadyButtonHighlighted = false;
    }
}

function curtainOnClick() {
    if (gameStatusHolder.readyToStart === true) {
        console.log('START req has been sent');
        requestStartOfTheGame();
    }
}

function drawChessboard(ctx, width) {
    ctx.rect(0, 0, width, height);
    ctx.beginPath();
    var squareWidth = width / 8;

    for (var i = 1; i < 8; i++) {
        // Horizontal lines
        ctx.moveTo(0, squareWidth * i);
        ctx.lineTo(width, squareWidth * i);
        // Vertical lines
        ctx.moveTo(squareWidth * i, 0);
        ctx.lineTo(squareWidth * i, width);
    }
    ctx.stroke();
    // Draw and fill squares
    ctx.fillStyle = '#888888';
    for (var i = 0; i < 8; i++) {
        if (i % 2 === 0) {
            for (var j = 0; j < 4; j++) {
                ctx.fillRect((i + 1) * squareWidth, (1 + 2 * j) * squareWidth, squareWidth, squareWidth);
            }
        } else {
            for (var j = 0; j < 4; j++) {
                ctx.fillRect((i - 1) * squareWidth, 2 * j * squareWidth, squareWidth, squareWidth);
            }
        }
    }
}

function loadPieces() {
    var img = new Image();
    img.onload = loadImage;
    //img.src = '../../assets/img/pieces.png'; // test env    
    img.src = 'http://localhost:8080/WebChess/img/pieces.png';

    function loadImage() {
        piecesImg = this;
        console.log('pieces loaded!');
    }
}
/**
 * Method draws current position on the chessboard.
 */
function drawPieces() {
    //var parsedChessboard = JSON.parse(chessboard);
    for (var key in chessboard) {
        // get information about position
        var column = key.charAt(0); //0
        var row = key.charAt(1);    //0
        var pieceName = chessboard[key];
        if (pieceName !== null && pieceName.length > 0) {
            var piece = pieceName + SUFFIX;
            // place piece on the chessboard
            var squareWidth = width / 8;
            //drawImage(image, sx, sy, sw, sh, dx, dy, dw, dh)
            var sprite = piecesData.frames[piece];
            var sw = sprite.frame.w;
            var sh = sprite.frame.h;
            var sx = sprite.frame.x;
            var sy = sprite.frame.y;
            var dx = column * squareWidth;
            var dy = (7 - row) * squareWidth;
            chessmenCanvasCtx.drawImage(piecesImg, sx, sy, sw, sh, dx, dy, squareWidth, squareWidth);
        }
    }
}

function drawWaitingForPlayersCurtain(ctx) {
    ctx.clearRect(120, 180, 240, 120);
    ctx.globalAlpha = 0.35;
    ctx.fillStyle = 'white';
    ctx.fillRect(0, 0, 480, 480);
    ctx.globalAlpha = 0.95;
    ctx.fillStyle = '#0772A1';
    ctx.fillRect(120, 180, 240, 120);
    ctx.lineWidth = '4';
    ctx.rect(120, 180, 240, 120);
    ctx.stroke();
    drawText(ctx, 'Waiting for players');
    gameStatusHolder.readyToStart = false;
}

function drawPressStartButtonCurtain(ctx) {
    ctx.clearRect(120, 180, 240, 120);
    ctx.globalAlpha = 0.35;
    ctx.fillStyle = 'white';
    ctx.fillRect(0, 0, 480, 480);
    ctx.globalAlpha = 0.95;
    ctx.fillStyle = '#67E667';
    ctx.fillRect(120, 180, 240, 120);
    ctx.lineWidth = '4';
    ctx.rect(120, 180, 240, 120);
    ctx.stroke();
    drawText(ctx, 'Click to start', 'the game');
    gameStatusHolder.readyToStart = true;
}

function drawText(ctx, txt1, txt2) {
    ctx.font = '25px Arial';
    ctx.fillStyle = 'white';
    if (txt1 === 'START') {
        ctx.fillText(txt1, curtainDim.txtStartButtonX, curtainDim.txtWaitForPlayersY);
    } else if (txt1 !== null && txt2 === undefined) {
        ctx.fillText(txt1, curtainDim.txtWaitForPlayersX, curtainDim.txtWaitForPlayersY);
    } else {
        ctx.fillText(txt1, curtainDim.txtLine1ReadyX, curtainDim.txtLine1ReadyY);
        ctx.fillText(txt2, curtainDim.txtLine2ReadyX, curtainDim.txtLine2ReadyY);
    }
}

var curtainDim = {
    x: 0,
    y: 0,
    width: 480,
    height: 480,
    innerX: 120,
    innerY: 180,
    innerWidth: 240,
    innerHeight: 120,
    txtWaitForPlayersX: 135,
    txtWaitForPlayersY: 245,
    txtLine1ReadyX: 165,
    txtLine1ReadyY: 230,
    txtLine2ReadyX: 185,
    txtLine2ReadyY: 260,
    txtStartButtonX: 200
};

var gameStatusHolder = {
    readyToStart: false,
    isReadyButtonHighlighted: false
};