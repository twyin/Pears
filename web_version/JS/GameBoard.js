class GameBoard extends Board {
    // max amount of turns allowed is set to 3
    static get maxTurns() {
        const max = 3;
        return max;
    }


// constructor of gameboard
    constructor(h, w, p, ob) {
        super(h, w);
        this.patterns = p;                          // amount of patterns
/* removing unsolvedPairs from gameboards. Although the number of 
*  unsolvedPair belongs to an instance of gameboard, it would be 
*  faster to calculate and keep track of it in Pears instead as the
*  number of unsolved pairs left will determine when to end the
*  level/game. getUnsolvedPairs() commented out as well
*/
        
        this.obstacles = ob;                        // relative position of each obstacle
        if (ob!=null) {
            for (let i=0; i<ob.length; i++) {
                super.setValue(Math.floor(ob[i]/w), ob[i]%w, -1);
            }
            this.unsolvedPairs = (h*w - ob.length)/2;     // number of unsolved pairs left
        } else {
            this.unsolvedPairs = h*w/2;
        }
        
        
    }
    
// getter's
    
    getPatterns() {
        return this.patterns;
    }
    
    getUnsolvedPairs() {
       return this.unsolvedPairs;
    }

    solvePair() {
//        console.log("gb: " + ;
        return --this.unsolvedPairs;
    }
    
/* Obstacles is the "linear" INDEX at which they occur
 * For example, if desired obstacle is like:
 *   [ 0 0 -1 ]
 *   [ 0 0 -1 ]
 *   [ 0 0 -1 ]
 *   Then the desired boardArray[] used later in fill method should be:
 *   {0, 0, -1, 0, 0, -1, 0, 0, -1}
 *   Thus obstacles[] is at indices:
 *   {2,4,6}
 */
    getObstacle() {
        return this.obstacles;
    }

// setter's
// the number of unsolved pairs is dependent on the state of the board;
//   it's calculated, not set to some number, therefore no setter for it
    setPatterns(p) {
        this.patterns = p;
    }
    
// setObstalces() has been removed
    
// removed toString since it is in parent Board class
//   and does (almost) the same thing

// removed fill-1 as it is slower than fill-2; no need to have both

// ============================================================================
// ============================================================================
/* fill-2 that can handle obstacles
 * Visual example of fill-2
 *   given obstacles[] = [2,4,6] on a 3x3 board
 *   [ 0 0 -1 ]
 *   [ 0 0 -1 ] + obstacles={2,4,6}  --> [0,0,-1,0,0,-1,0,0,-1]
 *   [ 0 0 -1 ]
 *   for every obstacle, put -1 in boardArray where it should go,
 *   which would be boardArray[2], [4], and [6] in this example.
 *   put a pattern in boardArry[i] based on j, reason for using
 *   (j/2%patterns+1) explained : "+ 1" is so that the patterns would 
 *   start at 1 and not 0
 *   AS LONG AS THE AMOUNT OF EMPTY SPACES ON BOARD IS EVEN,
 *   this formula also assures that they would appear in pairs!
 *   That's because since i/2 = (i+1)/2 if i is even and the first
 *   index is 0 which is even
 *   (if it was only i%patterns, program wouldn't be able to confirm 
 *   they're in even amounts)
 *   {0,0,-1,0,0,-1,0,0,-1} -> {1,1,-1,2,2,-1,3,3,-1}
 *   afterwards, swap every pattern with another and put into board
 * fill2 has a loop at the end that swaps elements of boardArray before
 *   putting it in the board. Therefore it is shuffle before board is
 *   filled. 
 *   
 */

    fill() {
        let h = super.getHeight();
        let w = super.getWidth();
        let total = h*w;
        let boardArray = new Array(total);
        
        let i;
        let j = 0;             // counter that excluds empty spaces in boardArray
        let pos;
        let temp;
        let swapped;

        let obsLen=0;
        
        // JS GameBoard constructor requires obstacles[] to be 
        //   entered, therefore it must have an existing length
        if (this.obstacles == null) {
            obsLen = 0;
        } else {
            obsLen = this.obstacles.length;
        }
        
        for (i=0; i<obsLen; i++) {
        // obstacles holds the indexes in boardArray that will be -1
            boardArray[this.obstacles[i]] = -1;
        }

        

        for (i=0; i<total; i++) {
        // reason for two counters, j to go down boardArray, j to count
        //   empty spaces. This is so no new value is assigned to obstacle
            if (boardArray[i] != -1) {
                boardArray[i] = Math.floor(j/2)%this.patterns+1;
                j++;
            }
        }
        // for a 5x4 board with 4 obstacles, j=16 by end of ^ loop
        
        for (let i=0; i<total; i++) {
            // swap boardArray[counter] and [pos]
            swapped=false;
            if (boardArray[i]!=-1) {
                while (swapped===false) {
                    pos = Math.floor(Math.random()*total);  // generate 0-19 if total=20
                    if (boardArray[pos]!=-1) {
                        temp = boardArray[i];
                        boardArray[i] = boardArray[pos];
                        boardArray[pos] = temp;
                        swapped=true;
                    }
                }
            }
        }
        
        for (let i=0; i<h; i++) {
            for (let j=0; j<w; j++) {
                super.setValue(i, j, boardArray[i*w+j]);
            }
        }
    }
// ============================================================================
// ============================================================================

    
// ============================================================================
// ============================================================================
// shuffle the integers in board[][]
/* Note: if for some reason there are 0's on the board, current 
 *   shuffle is allow to swap pattern with a 0
 */
    shuffle() {     // changed from voi to returning a string 'shuffled'
        let h = super.getHeight();
        let w = super.getWidth();
        
        let posH;
        let posW;
        let temp;
        let swapped;

        for (let i=0; i<h; i++) {
            for (let j=0; j<w; j++) {
                swapped = false;
                // swap with a random if neither are obstacles, else
                //   pick another place to swap the original with
                
                if (super.getValue(i,j) != -1) {
//                  if (board[i][j]!=-1) {
                //if (board[i][j]>0) {
                // ^ switch to this if condition if shuffle must keep
                //  the "shape" of occupied spaces!
                // with (board[i][j]!=-1), shuffle is allowed to swap
                // patterns with 0's
                    while (swapped===false) {
                        posW = Math.floor(Math.random() * w);
                        posH = Math.floor(Math.random() * h)
                        
                        if (super.getValue(posH, posW) != -1) {
//                              if (board[posH][posW]!= -1 ) {
                        //if (board[posH][posW] > 0 ) {
                        // ^ switch to this if condition if shuffle must keep
                        //  the "shape" of occupied spaces
                            temp = super.getValue(i, j);
//                                  temp = board[i][j];
                            super.setValue(i, j, super.getValue(posH, posW));
//                                  board[i][j] = board[posH][posW];
                            super.setValue(posH, posW, temp);
//                                  board[posH][posW] = temp;
                            swapped=true;
                        }
                    }
                    
                }
            }
        }
        return 'shuffled';
    }
// ============================================================================
// ============================================================================

        

        

// minTurns should be a method of GameBoard that return minimum
//   number of turns from A(y1,x1) to A'(y2,x2)
    minTurns(y1, x1, y2, x2) {

    	// ====================== display coordinates ======================
        // console.log("min turns between y1 x1 y2 x2: " + y1+" "+x1+" "+y2+" "+x2);


        // (y1,x1) is A, (y2,x2) is A'
        let height = this.getHeight() + 2;
        let width = this.getWidth() + 2;
        let posH = y1++;        // correct offset from outside border
        let posW = x1++;
        let minTurn = 0;
        let addedTurns = 0;
        let found = false;
// make an empty board with extra zero's around first, and -1 
//   in place of patterns



        let pb = new PathsBoard(height, width); 
//        console.log(pb.toString());
        
        for (let i = 0; i < height; i++) {
          for (let j = 0; j < width; j++) {
            pb.setToZero(i, j);
          }
        }

        pb.pave(this, y1, x1, y2, x2);
 //       console.log(pb.toString());
        
        // shouldn't have anything other than -1/0/1's on it so 
        //   not necessary to make it a new Game board
        

// index for paths board is different than that of gameboard:
// -1 is obstacles and patterns, things that a path can't travel thru
// 0 is open for travel
// 1 marks those within reach of x turns
        
// first mark board with coordinates that are reachable within 0 turn,
//   then check if there's a 1 at (y2, x2)
// repeat 1 turn
// repeat 2 
// repeat 3 turns
        
//      pb.addTurn();
        
        let turnMarker = 1;  
        // turnMarker marks turns reachable within 0 turn with 1, 1 turn with 2, etc
        let maxT = GameBoard.maxTurns;
        //debugger;


// remove comment indicator when addTurn() works properly;
// SHOULD be able to actual minimum of turns needed
        while ((found==false) && (addedTurns <= maxT)) {
            //console.log("turnMarker = " + turnMarker);
            pb.addTurn(turnMarker);


            // ====================== display each paths board ======================
            // console.log(pb.toString());

            //System.out.println("With " + addedTurns + " turns");
            //System.out.println(pb);
// !!! yikes
            if (pb.getValue(y2+1, x2+1) > 0 ) {
                found=true;
            } else {
                ++addedTurns;
            }
            turnMarker++;
        }

        if (found==true) {
            return addedTurns;
        }
        // else
        return maxT+1;
    
    }
}

