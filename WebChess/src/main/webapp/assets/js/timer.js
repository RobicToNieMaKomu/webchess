var jq = jQuery.noConflict();
var gameStarted = true;
var remainingWPlayerTime = 0;
var remainingBPlayerTime = 0;

// Remaining game time is being sent from server in seconds. 
// This function will translate seconds into MINUTES:SECONDS for displaying purposes.
function remainingTime(seconds) {
    var result = "";
    var minutes = "";
    var sec = "";
    if (!isNaN(seconds)) {
        minutes = "" + parseInt(seconds/60);
        if (seconds%60 === 0) {
            sec = "00";
        } else {
            var tmpS = seconds - parseInt(minutes)*60;
            if (tmpS < 10) {
                tmpS = "0" + tmpS;   
            }
            sec = "" + tmpS;
        }            
    }
    result = minutes + ":" + sec;
    return result;
}

function refreshRemainingTime(initialWPlayerTime, initialBPlayerTime) {
    if (gameStarted === true) {
        remainingWPlayerTime = initialWPlayerTime;
        remainingBPlayerTime = initialBPlayerTime;
        var wTime = remainingTime(remainingWPlayerTime);
        var bTime = remainingTime(remainingBPlayerTime);
        jq('#wplayerTime').text(wTime);
        jq('#bplayerTime').text(bTime);
        setTimeout(function(){
            remainingWPlayerTime = remainingWPlayerTime - 1;
            remainingBPlayerTime = remainingWPlayerTime - 1;
            refreshRemainingTime(remainingWPlayerTime, remainingBPlayerTime);
        },1000);
    }
}

