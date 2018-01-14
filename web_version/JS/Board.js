class Board {
// constructor of empty board
  constructor(h, w) {
    this.height = h;
    this.width = w;
    this.board = []; // value at each [i][j] is undefined
    for (let i=0; i<h; i++) {
        this.board.push(new Array(w));
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
  
  
// ============================================================================
// ============================================================================
  
  
// ============================================================================
// ============================================================================
  toString() {
    let h = this.height;
    let w = this.width; 
    let numLeft = (w*3-3)/2+7;
    let numRight = w*3+4;
    let finalHeader = '='.repeat(numLeft) + ' Board ' + '='.repeat(numRight);
    
    let drawBoard = '';
    for (let i=0; i<h; i++) {
      drawBoard += "[ ";
      for (let j=0; j<w; j++) {
        drawBoard += this.board[i][j].toString().padStart(2);
      }
      drawBoard+=" ]\n";
    }
    return finalHeader + '\n' + drawBoard;
  }
// ============================================================================
// ============================================================================
  
}

let justinsBoard = new Board(3, 4);
for (let i = 0; i < 3; i++) {
  for (let j = 0; j < 4; j++) {
    justinsBoard.setToZero(i, j);
  }
}
console.log(justinsBoard.toString());

