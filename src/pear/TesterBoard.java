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
/* This first filling method picks a random pattern,
 *   then places it into two random locations in the boardArray[].
 *   BoardArray[] is basically the linear representation of 
 *   board[][], where board[i][j] = boardArray[i*height+width].
 *   After boardArray[] is filled, its contents are copied over
 *   to board[][].
 *   
 * new fill-1 that can handle obstacles (Nov 11, 2017)
 * First it makes an int array called boardArray with size equal to
 *   the total amount of spaces in the board INCLUDING the obstacles.
 *   For example, let a 3x3 board have obstacles[] of {2,5,8}
 *   Then boardArray[] will be {0,0,-1,0,0,-1,0,0,-1}
 *   Then the -1's from obstacle[] will be put into their respective
 *   spot in boardArray. Afterwards, program will generate a random 
 *   pattern and put it into two random but available locations in 
 *   boardArray[]. After boardArray[] is filled, its contents are 
 *   copied over to board[][] wherever there is no obstacle
 *   
 *   
 *   !! Should assert that both board[][] boardArray[] reach their ends
 *   
 * 
 */
	protected void fill1() {
		int w = width;
//
		System.out.println("width: " + width);
		int h = height;
		int total = w*h;       // let w=5 h=4, total=20
		// total is the number of spaces on the board INCLUDING OBSTACLES
		int[] boardArray = new int[total];
//
		System.out.println("total = " + total);
		
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
		System.out.print("empty boardArray: ");
		for (int i=0; i<total; i++) {
			System.out.print(boardArray[i] + ", ");
		}
		System.out.println("");
		
		int p = patterns;
		Random rand = new Random();
		
		int r;  
		int pos1;
		int pos2;
		
		int counter = (total-obsLen)/2;
//
		System.out.println("counter: " + counter);
		
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
		
		
		for (int i=0; i<total; i++) {
			System.out.print(boardArray[i] + ", ");
		}
		
		
//
		System.out.println("\nh, w: " + h + ", " + w);
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
/* Given an int[] array of size (width*height), cleverly change
 *   each of them into an integer between [1:20]. Then go 
 *   through each one and swap with a random
 * 
 */
	protected void fill2() {
		int w = width;
		int h = height;
		int total = w*h;
		int[] boardArray = new int[total];
		
		int counter = 0;
		int pos;
		int temp;
		
		for (int i=0; i<total; i++) {
			boardArray[i] = i/2%patterns+1;
		}
		
		Random rand = new Random();
		for (; counter<total; counter++) {
			// swap boardArray[counter] and [r]
			pos = rand.nextInt(total);
			temp = boardArray[counter];
			boardArray[counter] = boardArray[pos];
			boardArray[pos] = temp;
		}
		
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
// shuffle the integers in board[][]
		protected void shuffle() {
			int w = width;
			int h = height;
			
			int posW;
			int posH;
			int temp;
			Random rand = new Random();
			System.out.println("\nfrom shuffle()");
			
			for (int i=0; i<h; i++) {
				for (int j=0; j<w; j++) {
					posW = rand.nextInt(w);
					posH = rand.nextInt(h);
					temp = board[i][j];
					board[i][j] = board[posH][posW];
					board[posH][posW] = temp;
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

