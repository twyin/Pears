class PathsBoard extends Board{
    
// constructor of empty board
    constructor(h, w, b) {
        super(h,w, b);
    }
    
    
// ============================================================================
// ============================================================================


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
            if (i-extend >= 0 && this.getValue(i-extend, j) != -1) {
                //this.setValue(i-extend, j, turnMarker);
                if (this.getValue(i-extend, j)==0) {
                    this.setValue(i-extend, j, turnMarker);
                } // else if it's not zero, don't change its value
                ++extend;
//              System.out.print("1 up, ");
            } else {
//              System.out.println(" false ^");
                UP=false;
            }
        }
//      System.out.print(" up by " + (extend-1));
        
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
            if (i+extend<height && this.getValue(i+extend, j) != -1) {
                //this.setValue(i+extend, j, turnMarker);
                if (this.getValue(i+extend, j)==0) {
                   this.setValue(i+extend, j, turnMarker); 
                }
                ++extend;
//              System.out.print(" 1 down, ");
            } else {
//              System.out.println(" false DOWNS");
                DOWN=false;
            }
        }
//      System.out.print(" down by " + (extend-1));
    }
    
    
// helper function for addTurns that will extend 1 LEFTwards until it 
//  either hits the edge or runs into an occupied coordinate
// int i, int j, int turnMarker -> return void
    extendLEFT(i, j, turnMarker) {
//      console.log("<- @ " + i + " " + j);
        let extend = 1;
        let LEFT = true;
        while (LEFT == true) {
            if (j-extend >=0 && this.getValue(i, j-extend) != -1) {
                if (this.getValue(i, j-extend)==0) {
                    this.setValue(i, j-extend, turnMarker);
                }
                ++extend;
//              System.out.print("1 left, ");
            } else {
                LEFT=false;
//              System.out.println("false <-");
            }
        }
//      System.out.print(" left by " + (extend-1));
        
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
            if ((j+extend) < width && this.getValue(i, j+extend) != -1) {
                if (this.getValue(i, j+extend)==0) {
                    this.setValue(i, j+extend, turnMarker);
                }
//              System.out.print(" 1 right, ");
                ++extend;
            } else {
                RIGHT=false;
//              System.out.println("false ->");
            }
        }
//      System.out.print(" right by " + (extend-1));
    }
    
    
// helper function for minTurns that will take **ALL** existing 1's 
// and replace touching 0's sharing the same x or y value to a 1
// int turnMarker -> return void
    addTurn(turnMarker) {
        let height = this.getHeight();
        let width = this.getWidth();
//      int extend;
//      boolean UP, DOWN, LEFT, RIGHT;
        for (let i=0; i<height; i++) {
            for (let j=0; j<width; j++) {
                if (this.getValue(i, j) == turnMarker) {
//                  System.out.println("extend " + turnMarker + " at " + i + " " + j);
                    // extend 1 up/down/left/right until it hits edge or -1
                    // DO NOT INCREMENT TURNMARKER UNTIL ENTIRE BOARD HAS BEEN GONE THRU
                    // OR ELSE REST OF BOARD WILL LOOK FOR DIFFERNT VALUE AND INCREMENT *THAT*
//                  System.out.println(turnMarker);
                    this.extendUP(   i,j, turnMarker+1);
                    this.extendDOWN( i,j, turnMarker+1);
                    this.extendLEFT( i,j, turnMarker+1);
                    this.extendRIGHT(i,j, turnMarker+1); 
                }
            }
        }
    }

}


/////////////////// Testing ///////////////////////
/*

let justinsBoard = new Board(3, 5);
for (let i = 0; i < 3; i++) {
  for (let j = 0; j < 5; j++) {
    justinsBoard.setToZero(i, j);
  }
}
console.log(justinsBoard.toString());

let gb1 = new GameBoard(5,7,4,[4,6,13,20]);
for (let i = 0; i < 5; i++) {
  for (let j = 0; j < 7; j++) {
    if (gb1.getValue(i,j)!=-1) {
      gb1.setToZero(i, j);  
    }
  }
}

gb1.fill();

console.log(gb1.toString());

console.log(gb1.shuffle());
console.log(gb1.toString());
console.log(gb1.minTurns(0,0,1,0));

*/

/* 
temporary comment for a board that brought up a path related problem
the two 8's could not be matched up because function said it took 4 turns
fixed it by changing if statements in extendsUP/DOWN/LEFT/RIGHT from 
if (current_value == 0) {change to TURNMARKER} to
if (current_value != -1) {change}
"=========== GameBoard ============
[  0  0  0  0  0  0  0  0  0  0  ]
[  0  0  0  0  0  0  0  0  0  0  ]
[ 11  9  0  6  0  0  0  8  0  0  ]
[  0  4  2  6  5  6  7  0  0  0  ]
[  3  8  0  1  4  2  3  5  0  0  ]
[  0  0  0  7  0  6  9  0  0  0  ]
[  0  0  0  0  0  1  0 11  0  0  ]
"
*/