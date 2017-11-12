package pear;

import java.util.Random;

class TesterBoard {
	// initialized to zero as they are instance variables
	private int width;
	private int height;
	private int patterns;
	private int unsolvedPairs;
	private int[] obstacles; // initialized to null
	private int[][] board;
	
	
// constructor of empty board
	protected TesterBoard(int w, int h, int p) {
		this.width = w;
		this.height = h;
		this.patterns = p;
		this.unsolvedPairs = w*h/2;
		// obstacles is null
		board = new int[h][w];
	}
	
	protected TesterBoard(int w, int h, int p, int[] ob) {
		this.width = w;
		this.height = h;
		this.patterns = p;
		this.unsolvedPairs = w*h/2 - ob.length;
		this.obstacles = ob;
		board = new int[h][w];
	}
	
	
// getter's
	protected int getWidth() {
		return this.width;
	}
	
	protected int getHeight() {
		return this.height;
	}
	
	protected int getPatterns() {
		return this.patterns;
	}
	
	protected int getUnsolvedPairs() {
		return this.unsolvedPairs;
	}
	
	
/* Obstacles is the "linear" INDEX at which they which
 * For example, if desired obstacle is like:
 *   [ 0 0 -1 ]
 *   [ 0 0 -1 ]
 *   [ 0 0 -1 ]
 *   Then the desired boardArray[] used later in fill method should be:
 *   {0, 0, -1, 0, 0, -1, 0, 0, -1}
 *   Thus obstacles[] is:
 *   {2,4,6}
 */
	protected int[] getObstacle() {
		return this.obstacles;
	}
	
	protected int getValue(int h, int w) {
		return board[h][w];
	}
	
// setter's
	protected void setToEmpty(int h, int w) {
		board[h][w]=0;
	}
	
	
// ============================================================================
// ============================================================================
	
	
// ============================================================================
// ============================================================================
/* fill-1 that can handle obstacles
 * First it makes an int array called boardArray with size equal to
 *   the total amount of spaces in the board INCLUDING the obstacles.
 *   For example, let a 3x3 board have obstacles[] of {2,5,8}
 *   Then boardArray[] will be {0,0,-1,0,0,-1,0,0,-1}
 *   Then the -1's from obstacle[] will be put into their respective
 *   spot in boardArray. Afterwards, program will generate a random 
 *   pattern and put it into two random but available locations in 
 *   boardArray[]. After boardArray[] is filled, its contents are 
 *   copied over to board[][] wherever there is no obstacle
 *   if 1st and 2nd random pattern generated is 2 then 1, and their 
 *   two random locations generated are 0,2 and 1,2 respectively
 *   -> {2,1,-1,2,1,-1,0,0,-1} 2 @ 0,3 and 1 @ 1,4
 *   
 *   !! Maybe assert that both board[][] boardArray[] reach their ends
 *   
 * 
 */
	protected void fill1() {
		int w = width;
//
//		System.out.println("width: " + width);
		int h = height;
		int total = w*h;       // let w=5 h=4, total=20
		// total is the number of spaces on the board INCLUDING OBSTACLES
		int[] boardArray = new int[total];
//
//		System.out.println("total = " + total);
		
		int obsLen;
		if (obstacles==null) {
			obsLen=0;
		} else {
			obsLen = obstacles.length;
		}

		// add obstacles into boardArray
		// should work even if obstacle[] isn't in ascending order
		for (int i=0; i<obsLen; i++) {
			boardArray[obstacles[i]]=-1;
		}
		
//
/*
		System.out.print("empty boardArray: ");
		for (int i=0; i<total; i++) {
			System.out.print(boardArray[i] + ", ");
		}
		System.out.println("");
*/
		
		int p = patterns;
		Random rand = new Random();
		
		int r;  
		int pos1;
		int pos2;
		
		int counter = (total-obsLen)/2;
//
//		System.out.println("counter: " + counter);
		
		for (; counter>0; counter--) {
			r = rand.nextInt(p)+1;        // return 1-5 inclusive
//			
//			System.out.print("r: " + r);
			pos1 = rand.nextInt(total);   // generate[0:19]
//
//			System.out.print(" i1@" + pos1);
			if (boardArray[pos1]==0) {
				boardArray[pos1]=r;
			} else {                      // else boardArray[pos1] is filled
				while(boardArray[++pos1%total]!=0) {
				// pos1%total is because ++pos1 might eventually
				//   increment it to bigger than total
				//   (imagine the last few are filled, would 
				//   need pos1 to re-route back to beginning)
				// this while loop only stops when it finds a 0, which
				//   is the next empty and let it = r
				}
				boardArray[pos1%total] = r;
			}
//
//			System.out.print(" f1@" + pos1);
			
			pos2 = rand.nextInt(total);
//
//			System.out.print(" i2@" + pos2);
			if (boardArray[pos2]==0) {
				boardArray[pos2]=r;
			} else {
				while(boardArray[++pos2%total]!=0) {
				// this will find the next empty (which is 0)
				}
				boardArray[pos2%total] = r;
			}
//			System.out.println(" f2@" + pos2);
		}
		
//
/*
		for (int i=0; i<total; i++) {
			System.out.print(boardArray[i] + ", ");
		}
*/
		
		
//
//		System.out.println("\nh, w: " + h + ", " + w);
		for (int i=0; i<h; i++) {
			for (int j=0; j<w; j++) {
				board[i][j]=boardArray[i*w+j];
			}
		}
	}
// ============================================================================
// ============================================================================


// ============================================================================
// ============================================================================
/* fill-2 that can handle obstacles
 * Logically it's still the same as before, visual example of new fill-2
 *   given obstacles[] = {2,4,6} on a 3x3 board
 *   [ 0 0 -1 ]
 *   [ 0 0 -1 ] + obstacles={2,4,6}  --> {0,0,-1,0,0,-1,0,0,-1}
 *   [ 0 0 -1 ]
 *   create array same size as empty spaces; so boardArray[6]
 *   for every obstacle, put -1 in boardArray where it should go,
 *   which would be boardArray[2], [4], and [6] for the above example
 *   put a pattern in boardArry[i] based on j; what they are is 
 *   explained where they are declared below
 *   j/2%patterns+1 explained : "+ 1" is so that the patterns would 
 *   start at 1 and not 0
 *   AS LONG AS THE AMOUNT OF EMPTY SPACES ON BOARD IS EVEN,
 *   this formula also assures that they would appear in pairs!
 *   That's because since i/2 = (i+1)/2 if i is even and the first
 *   index is 0 which is even
 *   (if it was only i%patterns, program wouldn't be able to confirm 
 *   they're in even amounts)
 *   {0,0,-1,0,0,-1,0,0,-1} -> {1,1,-1,2,2,-1,3,3,-1}
 *   afterwards, swap every pattern with another and put into board
 *   
 */
	protected void fill2() {
		int w = width;
		int h = height;
		int total = w*h;
		int[] boardArray = new int[total];
		
		int i;               // general variable used in for loops
		int j=0;             // counts the empty spaces in boardArray
		int obsLen;          // length of obstacle[]
		int pos;
		int temp;
		boolean swapped;
		Random rand = new Random();
		
		if (this.obstacles==null) {
		// if obstacles[] = null, its length is undefined -> program crashes
			obsLen=0;
		} else {
			obsLen = obstacles.length;
		}
		
		for (i=0; i<obsLen; i++) {
		// obstacles holds the indexes in boardArray that will be -1
			boardArray[obstacles[i]] = -1;
//
			System.out.println(boardArray[obstacles[i]]);
		}
		
		for (i=0; i<total; i++) {
		// reason for two counters, j to go down boardArray, j to count
		//   empty spaces. This is so no new value is assigned to obstacle
			if (boardArray[i] != -1) {
				boardArray[i] = j/2%patterns+1;
				j++;
			}
		}
		// for a 5x4 board with 4 obstacles, j=16 by end of ^ loop
		
		for (i=0; i<total; i++) {
			// swap boardArray[counter] and [pos]
			swapped=false;
			if (boardArray[i]!=-1) {
				while (swapped==false) {
					pos = rand.nextInt(total);
					if (boardArray[pos]!=-1) {
						temp = boardArray[i];
						boardArray[i] = boardArray[pos];
						boardArray[pos] = temp;
						swapped=true;
					}
				}
			}
		}
		
		for (i=0; i<h; i++) {
			for (j=0; j<w; j++) {
				board[i][j]=boardArray[i*w+j];
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
		protected void shuffle() {
			int w = width;
			int h = height;
			
			int posW;
			int posH;
			int temp;
			Random rand = new Random();
			System.out.println("\nfrom shuffle()");
			
			boolean swapped;
			for (int i=0; i<h; i++) {
				for (int j=0; j<w; j++) {
					swapped = false;
					// swap with a random if neither are obstacles, else
					//   pick another place to swap the original with
					
					if (board[i][j]!=-1) {
					//if (board[i][j]>0) {
					// ^ switch to this if condition if shuffle must keep
					//  the "shape" of occupied spaces!
					// with (board[i][j]!=-1), shuffle is allowed to swap
					// patterns with 0's
						try {
							while (swapped==false) {
								posW = rand.nextInt(w);
								posH = rand.nextInt(h);
								
								if (board[posH][posW]!= -1 ) {
								//if (board[posH][posW] > 0 ) {
								// ^ switch to this if condition if shuffle must keep
								//  the "shape" of occupied spaces
									temp = board[i][j];
									board[i][j] = board[posH][posW];
									board[posH][posW] = temp;
									swapped=true;
								}
							}
						} catch (Exception e) {
							System.out.println(e.getMessage());
							System.out.println("Error from shuffle while "
									+ "attempting to swap at (i,j): "
									+ "(" + i + "," + j + ")");
						}
					}
				}
			}		
		}
// ============================================================================
// ============================================================================
		
	
// ============================================================================
// ============================================================================
	public String toString() {
		int w = this.width;
		int h = this.height;
		String drawBoard = "";
		for (int i=0; i<h; i++) {
			drawBoard += ("[ ");
			for (int j=0; j<w; j++) {
				drawBoard += (board[i][j] + " ");
			}
			drawBoard+="]\n";
		}
		return drawBoard;
	}
// ============================================================================
// ============================================================================
	
		
}

