class Board {
// constructor of empty board
  constructor(h, w) {
    this.height = h;
    this.width = w;
    this.board = []; // value at each [i][j] is undefined
    for (let i=0; i<h; i++) {
        this.board.push(new Array(w)); //undefined
    }
  }
  
// getter's
  getHeight() {
    return this.height;
  }
  
  getWidth() {
    return this.width;
  }
  
  getBoard() {
    return this.board;
  }
  getValue(h, w) {
    return this.board[h][w];
  }
   
// setter's
// height and width should most definitely not change throughout
//   the game
  setBoard(b) {
    this.board = b;
  }
  
  setValue(h, w, val) {
    this.board[h][w] = val;
  }
  
  setToNull(h, w) {
    this.board[h][w]=null;
  }
  
  setToZero(h,w) {
    this.board[h][w]=0;
  }
  
  toString() { 
    let h = this.height;
    let w = this.width; 
    let headerLength = w*3+4;
    let boardName = this.constructor.name; // string
    let leftPadding = Math.floor((headerLength - boardName.length - 2)/2);
    let rightPadding = Math.ceil((headerLength - boardName.length - 2)/2);
    
    //let numLeft = (w*3-3)/2+7;
    //let numRight = w*3+4;
    let finalHeader = '='.repeat(leftPadding) + ' ' + boardName + 
                      ' ' + '='.repeat(rightPadding);
    let drawBoard = '';
    for (let i=0; i<h; i++) {
      drawBoard += "[";
      for (let j=0; j<w; j++) {
        if (this.board[i][j]==null) {
          //System.out.print("null");
          drawBoard += 'n'.padStart(3);
        } 
        else if (this.board[i][j] === -1) {
          drawBoard += '|'.padStart(3);
        }
        else {
          drawBoard += this.board[i][j].toString().padStart(3);
        }
      }
      drawBoard+="  ]\n";
    }
    return finalHeader + '\n' + drawBoard;
  }
}
