// Ting Yin
// https://twyin.github.io/Pears/

// ============== Print statements to uncomment for debugging ==============
//	Pears addClick()
//	Pears match()
// 	GameBoard minTurns()


function newLevel() {
  // clear current table
  var boardElement = document.getElementById('gameboard');
  if (boardElement) {
    boardElement.parentNode.removeChild(boardElement);
    // must clear starting message if user finished level 1 before
    //   it's time for message to disappear
  }

  if (document.getElementById("starter_msg")) {
    document.getElementById("starter_msg").innerHTML = " ";
  }

  levelCounter++;
  currLevel = game[levelCounter];
  currGB = currLevel.gboard;

  totalUnsolvedPairs = currGB.getUnsolvedPairs();
  document.getElementById("score").innerHTML = "Pears matched: " + 
  	(totalUnsolvedPairs - currGB.getUnsolvedPairs()) + " / " + totalUnsolvedPairs;

  height = currLevel.gboard.getHeight();
  width = currLevel.gboard.getWidth();
  drawBoard();

}


function drawBoard() {
  //var body = document.getElementsByTagName('body')[0];
  var game = document.createElement("div");
  game.setAttribute("class", "board");
  game.setAttribute("id", "gameboard");
  game.setAttribute("alt", "Incompatible Browser?")

  // Cosmetic Top Row
  let topRow = document.createElement("div");
  topRow.setAttribute("class","row");

  // Top Left Corner
  var topLeft = document.createElement('div');
  topLeft.classList.add("cell", "cell--path", "corner");
  topRow.appendChild(topLeft);
  // Top T Cells
  for (var i=0; i<width; i++) {
    var td = document.createElement('div');
    td.classList.add("cell", "cell--path", "t");
    topRow.appendChild(td);
  }
  // Top Right Corner
  var topRight = document.createElement('div');
  topRight.classList.add("cell", "cell--path", "corner", "rotate90");
  topRow.appendChild(topRight);
  game.appendChild(topRow);

  for (var i=0; i<height; i++) {
    var row = document.createElement("div");
    row.setAttribute("class","row");

    // Cosmetic Left Column
    var leftTd = document.createElement('div');
    leftTd.classList.add("cell", "cell--path", "t", "rotate270");
    row.appendChild(leftTd);

    for (var j=0; j<width; j++) {
      // create row square

      var td = document.createElement('div');
      td.setAttribute("class", "cell");


      var id = (i+"_"+j);
      td.setAttribute("id",id);

      //var rowElement = document.createTextNode(currLevel.gboard.getValue(i,j));
      //var rowElement = document.createTextNode();

      var rowElementInner = document.createElement('span');
      var val = currLevel.gboard.getValue(i,j);
      // uncommet to display value on tile
      // var rowElementInnerContent = document.createTextNode(val);
      // rowElementInner.appendChild(rowElementInnerContent);
      td.appendChild(rowElementInner);

      if (currLevel.gboard.getValue(i,j) == -1) {
        td.classList.add('cell--obstacle');
        // maybe setting to x is unnecessary
        //rowElement = document.createTextNode(" ");
      } else {
        td.classList.add('cell--board');
        td.classList.add('cell--board-' + val);
        td.setAttribute("onclick", `addClick(${i},${j})`);
      }
      row.appendChild(td);
    }

    // Cosmetic Right Column
    var rightTd = document.createElement('div');
    rightTd.classList.add("cell", "cell--path", "t", "rotate90");
    row.appendChild(rightTd);

    game.appendChild(row); 
  }

  // Cosmetic Bottom Row
  let bottomRow = document.createElement("div");
  bottomRow.setAttribute("class","row");
  // Bottom Left Corner
  var bottomLeft = document.createElement('div');
  bottomLeft.classList.add("cell", "cell--path", "corner", "rotate270");
  bottomRow.appendChild(bottomLeft);
  // Bottom T Cells
  for (var i=0; i<width; i++) {
    var td = document.createElement('div');
    td.classList.add("cell", "cell--path", "t", "rotate180");
    bottomRow.appendChild(td);
  }
  // Bottom Right Corner
  var bottomRight = document.createElement('div');
  bottomRight.classList.add("cell", "cell--path", "corner", "rotate180");
  bottomRow.appendChild(bottomRight);
  game.appendChild(bottomRow);

  document.body.appendChild(game);
}


function print_starter_msg() {
  var msg = document.createElement('p');
  msg.setAttribute("id", "starter_msg");
  msg.setAttribute("class", "start_msg");

  var linebreak = document.createElement('br');
  msg.appendChild(linebreak);
  // var message = document.createTextNode("S T O R Y \u00A0L I N E");
  // // msg.setAttribute("class", "start_msg_title");
  // msg.appendChild(message);
  // var linebreak = document.createElement('br');
  // msg.appendChild(linebreak);

  // message = document.createTextNode("Once upon a time in the " +
  //   "land of fruits, there was a dance where " + 
  //   "pears from many tribes attended. The dance lasted hours " + 
  //   "and hours, until eventually, it was time to go home. " + 
  //   "But to do so, they must venture through a dark forest where " + 
  //   "the big, bad wolf resides! The evil wolf tends to attack "+ 
  //   "only when his prey is alone, so this is where YOU come in. " + 
  //   "Please show each fruit the path to another of its kind.");
  // msg.appendChild(message);
  // linebreak = document.createElement('br');
  // msg.appendChild(linebreak);
  // linebreak = document.createElement('br');
  // msg.appendChild(linebreak);

  message = document.createTextNode("I N S T R U C T I O N S ");
  msg.appendChild(message);
  linebreak = document.createElement('br');
  msg.appendChild(linebreak);

  
  
  message = document.createTextNode("Pair two matching fruits together by clicking on them with your mouse. Two fruits can be paired together if there is a path joining them that:");
  msg.appendChild(message);
  linebreak = document.createElement('br');
  msg.appendChild(linebreak);

  var unordered_list = document.createElement('ul');
  var list = document.createElement('li');
  var list_item = document.createTextNode("does not " + 
    "cross through other fruits or obstacles, and");
  list.appendChild(list_item);
  unordered_list.appendChild(list);

  list = document.createElement('li');
  list_item = document.createTextNode("requires at most 3 turns");
  list.appendChild(list_item);
  unordered_list.appendChild(list);

  msg.appendChild(unordered_list);

  message = document.createTextNode("H I N T");
  msg.appendChild(message);
  linebreak = document.createElement('br');
  msg.appendChild(linebreak);

  message = document.createTextNode("Start by matching " + 
    "pears that are horizontally or vertically next to each " + 
    "other, then try to find pairs around the border.");
  msg.appendChild(message);
  linebreak = document.createElement('br');
  msg.appendChild(linebreak);
  linebreak = document.createElement('br');
  msg.appendChild(linebreak);

  document.body.appendChild(msg);

  // set to starter message to disappear after 4 minutes
  setTimeout(`clear_starter_msg()`, 240000);  

}


function clear_starter_msg() {
  document.getElementById("starter_msg").innerHTML = " ";
}


function addClick(i,j) {
  if (x1==null && y1==null) {
    y1 = i;
    x1 = j;

    let element1 = document.getElementById(`${y1}_${x1}`);
    if (currGB.getValue(y1,x1)) {
      element1.classList.add('cell--emphasis');
    }
    // console.log("yx: " + y1+","+x1);
  } else {
    // console.log("ji: " + i+","+j);

    //match(++y1, ++x1, ++y2, ++x2);  //accomodate paths

    if (x1!=j || y1!=i) {
      match(y1, x1, i, j);
    }
    let element1 = document.getElementById(`${y1}_${x1}`);
    if (element1) {
      element1.classList.remove('cell--emphasis');
    }
    x1=null;
    y1=null;
  }
}


function match(y1, x1, y2, x2) {
  // console.log("y1 x1 y2 x2: " + y1+" "+x1+" "+y2+" "+x2);

//    console.log("To quit, enter -1");
//    console.log("Please enter coordinate separated by a space");
  let intA; // int
  let intB; // int
  let maxTurns = GameBoard.maxTurns; // int, should be 3

  intA = currGB.getValue(y1, x1);

  intB = currGB.getValue(y2, x2);
  
  if (intA!=intB || intA==0 || intB==0) {
    // console.log("Invalid pear");
  } else {

    var i = currGB.minTurns(y1, x1, y2, x2);
    // console.log("minTurns: " + i);
   
    if (i > maxTurns) {
      // console.log("Invalid: must be within " + maxTurns + " turns");
    } else {
      // valid number of turns
      console.log("Valid: " + i + " turns needed ");
      currGB.setToZero(y1, x1);
      currGB.setToZero(y2, x2);
      //console.log(currGB.toString());

      let element1 = document.getElementById(`${y1}_${x1}`);
      element1.classList.remove('cell--board', 'cell--board-' + intA);
      element1.classList.add('cell--remove', 'cell--path', 'cross');

      //element1.textContent = "";

      let element2 = document.getElementById(`${y2}_${x2}`);
      element2.classList.remove('cell--board', 'cell--board-' + intB);
      element2.classList.add('cell--remove', 'cell--path', 'cross');
      //element2.textContent = "";

      updateScore();
    }
  }
}


function updateScore() {
  var pearsLeft = currGB.solvePair();
  document.getElementById("score").innerHTML = "Pears matched: " + 
    (totalUnsolvedPairs - pearsLeft) + " / " + totalUnsolvedPairs;

  if (pearsLeft == 0 && levelCounter<(game.length-1)) {
    //  will make level completion pop up more esthetically pleasing later
    endOfLevelAlert();
    newLevel();
  } else if (pearsLeft == 0 && levelCounter>=(game.length-1)){ // end of the game
    endOfGameAlert();
    endOfGame();
  }
}


function endOfLevelAlert() {
  alert("You have successfully completed this level!");
}

function endOfGameAlert() {
  alert("Congratulations on finishing Pears! " + 
    "Thanks to you, all Pears have safely made it home. " +
    "Hope you enjoyed playing! - T.Y.");
}

function endOfGame() {
  // erase score
  document.getElementById("score").innerHTML = "Pears matched: - / -";
  
  // change Hello to Goodbye
  document.getElementById("greetMessage").innerHTML = "Goodbye Pears";
  
  // clear the table
  var boardElement = document.getElementById('gameboard');
  if (boardElement) {
    boardElement.parentNode.removeChild(boardElement);
    // must clear starting message if user finished level 1 before
    //   it's time for message to disappear
  }
  if (document.getElementById("starter_msg")) {
    document.getElementById("starter_msg").innerHTML = " ";
  }
}

