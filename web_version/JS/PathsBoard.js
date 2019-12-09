class PathsBoard extends Board{
    
// constructor of empty board
    constructor(h, w, b) {
        super(h,w, b);
    }
    

// method to make new board into pure path and non-path squares, in
//   other words, everything besides 0's are non-path
// GameBoard gb, int y1, int x1, int y2, int x2 -> return void
// all values should be 0 at the time of call
    pave (gb, y1, x1, y2, x2) { 
        let height = gb.getHeight();
        let width = gb.getWidth();
        
        for (let i=0; i<height; i++) {
            for (let j=0; j<width; j++) {
                if (gb.getValue(i,j) != 0) {
                    this.setValue(i+1, j+1, -1);
                } else {
                    this.setValue(i+1, j+1, 0);
                }
            }
        }
        this.setValue(y1, x1, 1);
        this.setValue(y2+1,  x2+1,  0);  
        // ^ set A' to 0 to allow it to be changed to 1
    }
    
    
// helper function for addTurns that will extend 1 UPwards until it 
//   either hits the edge or runs into an occupied coordinate
// int i, int j, int turnMarker -> return void
    extendUP(i, j, turnMarker) {
//      console.log("^ @ " + i + " " + j);
        let extend = 1;
        let UP = true;
        while (UP == true) {
            if (i-extend >= 0 && this.getValue(i-extend, j) === 0) {
                //this.setValue(i-extend, j, turnMarker);
                // if (this.getValue(i-extend, j) === 0) {
                //     this.setValue(i-extend, j, turnMarker);
                // } // else if it's not zero, don't change its value
                this.board[i-extend][j] = turnMarker;
                ++extend;
            } else {
                UP=false;
            }
        }
        
    }
        
// helper function for addTurns that will extend 1 DOWNwards until it 
//  either hits the edge or runs into an occupied coordinate
// int i, int j, int turnMarker -> return void
    extendDOWN(i, j, turnMarker) {
//      console.log("downs @ " + i + " " + j);
        let extend = 1;
        let height = this.getHeight();
        let DOWN = true;
        while (DOWN == true) {
            if (i+extend<height && this.getValue(i+extend, j) === 0) {
                //this.setValue(i+extend, j, turnMarker);
                // if (this.getValue(i+extend, j) === 0) {
                //    this.setValue(i+extend, j, turnMarker); 
                // }
                this.board[i+extend][j] = turnMarker;
                ++extend;
            } else {
                DOWN=false;
            }
        }
    }
    
    
// helper function for addTurns that will extend 1 LEFTwards until it 
//  either hits the edge or runs into an occupied coordinate
// int i, int j, int turnMarker -> return void
    extendLEFT(i, j, turnMarker) {
//      console.log("<- @ " + i + " " + j);
        let extend = 1;
        let LEFT = true;
        while (LEFT == true) {
            if (j-extend >=0 && this.getValue(i, j-extend) === 0) {
                // if (this.getValue(i, j-extend) === 0) {
                //     this.setValue(i, j-extend, turnMarker);
                // }
                this.board[i][j-extend] = turnMarker;
                ++extend;
            } 
            else {
                LEFT=false;
            }
        }
        
    }
    
    
// helper function for addTurns that will extend 1 RIGHTwards until it 
//  either hits the edge or runs into an occupied coordinate
// int i, int j, int turnMarker -> returns void
    extendRIGHT(i, j, turnMarker) {
//      console.log("-> @ " + i + " "+ j);
        let extend = 1;
        let width = this.getWidth();
        let RIGHT = true;
        while (RIGHT == true) {
            if ((j+extend) < width && this.getValue(i, j+extend) === 0) {
                // if (this.getValue(i, j+extend) === 0) {
                //     this.setValue(i, j+extend, turnMarker);
                // }
                this.board[i][j+extend] = turnMarker
                ++extend;
            } else {
                RIGHT=false;
            }
        }
    }
    
    
// helper function for minTurns that will take **ALL** existing 1's 
// and replace touching 0's sharing the same x or y value to a 1
// int turnMarker -> return void
    addTurn(turnMarker) {
        let height = this.getHeight();
        let width = this.getWidth();
        for (let i=0; i<height; i++) {
            for (let j=0; j<width; j++) {
                if (this.getValue(i, j) == turnMarker) {
//                  console.log("extend " + turnMarker + " at " + i + " " + j);
                    // extend 1 up/down/left/right until it hits edge or -1
                    this.extendUP(   i,j, turnMarker+1);
                    this.extendDOWN( i,j, turnMarker+1);
                    this.extendLEFT( i,j, turnMarker+1);
                    this.extendRIGHT(i,j, turnMarker+1); 
                }
            }
        }
        // console.log(this.toString());
    }
}
