/* this is the beauty of browser based things 
   just copy/paste this script and boom 
   you have game working in a browser */

/* setup viewport */

/* create canvas element to display things */
var canvas = document.createElement("Canvas");

/* insert element from memory to browser view */
document.body.appendChild(canvas);


/* make it fill the whole screen */
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;


/* get rendering context */
var ctx = canvas.getContext("2d");

