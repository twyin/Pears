package pear;

class TesterPathBoard extends TesterBoard{
	private final int maxTurns = 3;   // max amount of turns allowed is set to 3
	
// constructor of empty board
	protected TesterPathBoard(int h, int w) {
		super(h,w);
	}
	
	protected TesterPathBoard(int h, int w, int[][] b) {
		super(h,w,b);
	}
	
	protected int getMaxTurns() {
		return maxTurns;
	}
	
	
// ============================================================================
// ============================================================================
	public String toString() {
		int h = this.getHeight();
		int w = this.getWidth();
		
		// for personal esthetics
		// precise formula: (width*3+4 - " Paths ".length()) in half, then + " Board ".length()
		String drawBoard = String.format("%"+ ((w*3-1)/2+5) +"s", 
				"xPathsx").replace(' ', '=');
		drawBoard = String.format("%-" + (w*3+4) + "s\n", 
				drawBoard).replace(' ', '=').replace('x',' ');
		
		for (int i=0; i<h; i++) {
			drawBoard += ("[ ");
			for (int j=0; j<w; j++) {
				drawBoard += (String.format("%2d ", this.getValue(i, j)));
				//drawBoard += (board[i][j] + " ");
			}
			drawBoard+=" ]\n";
		}
		return drawBoard;
	}
// ============================================================================
// ============================================================================

	
	
// ============================================================================
// ============================================================================		


		
// method to make new board into pure path and non-path squares, in
//   other words, everything besides 0's are non-path
	protected void pave (TesterGameBoard gb, int y1, int x1, int y2, int x2) {

		int height = gb.getHeight();
		int width = gb.getWidth();
		
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
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
	protected void extendUP(int i, int j) {
		System.out.print("^ @ " + i + " " + j);
		int extend = 1;
		boolean UP = true;
		while (UP == true) {
			if (i-extend >=0 && this.getValue(i-extend, j) == 0) {
				this.setValue(i-extend, j, 2);
				++extend;
				System.out.print("1 up, ");
			} else {
				System.out.println(" false ^");
				UP=false;
			}
		}
		
	}

		
// helper function for addTurns that will extend 1 DOWNwards until it 
//  either hits the edge or runs into an occupied coordinate
	protected void extendDOWN(int i, int j) {
		System.out.print("downs @ " + i + " " + j);
		int extend = 1;
		int height = this.getHeight();
		boolean DOWN = true;
		while (DOWN == true) {
			if (i+extend<height && this.getValue(i+extend, j) == 0) {
				this.setValue(i+extend, j, 2);
				++extend;
				System.out.print(" 1 down, ");
			} else {
				System.out.println(" false DOWNS");
				DOWN=false;
			}
		}
	}
	
	
// helper function for addTurns that will extend 1 LEFTwards until it 
//  either hits the edge or runs into an occupied coordinate
	protected void extendLEFT(int i, int j) {
		System.out.println("<- @ " + i + " " + j);
		int extend = 1;
		boolean LEFT = true;
		while (LEFT == true) {
			if (j-extend >=0 && this.getValue(i, j-extend) == 0) {
				this.setValue(i, j-extend, 2);
				++extend;
				System.out.print("1 left, ");
			} else {
				LEFT=false;
				System.out.println("false <-");
			}
		}
		
	}
	
	
// helper function for addTurns that will extend 1 RIGHTwards until it 
//  either hits the edge or runs into an occupied coordinate
	protected void extendRIGHT(int i, int j) {
		System.out.print("-> @ " + i + " "+ j);
		int extend = 1;
		int width = this.getWidth();
		boolean RIGHT = true;
		while (RIGHT == true) {
			if (j+extend < width && this.getValue(i, j+extend) == 0) {
				this.setValue(i, j+extend, 2);
				System.out.print(" 1 right, ");
				++extend;
			} else {
				RIGHT=false;
				System.out.println("false ->");
			}
		}
	}
	
	
// helper function for minTurns that will take **ALL** existing 1's 
// and replace touching 0's sharing the same x or y value to a 1
	protected void addTurn() {
		int height = this.getHeight();
		int width = this.getWidth();
//		int extend;
		int i, j;
//		boolean UP, DOWN, LEFT, RIGHT;
		for (i=0; i<height; i++) {
			for (j=0; j<width; j++) {
				if (this.getValue(i, j) == 1) {
					
					// extend 1 up/down/left/right until it hits edge or -1
					// might look cleaner to make method to do so but feels excessive..?
					
					this.extendUP(i,j);
					this.extendDOWN(i,j);
					this.extendLEFT(i,j);
					this.extendRIGHT(i,j);
									
				}
			}
		}
		for (i=0; i<height; i++) {
			for (j=0; j<width; j++) {
				if (this.getValue(i, j)>1) {
					this.setValue(i, j, 1);
				}
			}
		}
	}

}
