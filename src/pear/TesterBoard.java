package pear;

import java.util.Random;

class TesterBoard {
	// initialized to zero as they are instance variables
	private int width;
	private int height;
	private int patterns;
	private int[][] board;
	
	
// constructor of empty board
	protected TesterBoard(int w, int h, int p) {
		this.width = w;
		this.height = h;
		this.patterns = p;
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
 */
	protected void fill1() {
		int w = width;
		int h = height;
		int total = w*h;
		int[] boardArray = new int[total];
		
		int p = patterns;
		Random rand = new Random();
		
		int r;  
		int pos1;
		int pos2;
		
		int counter = total/2;
		System.out.println("total = " + total);
		for (; counter>0; counter--) {
			r = rand.nextInt(p)+1;     // return 1-20 inclusive
			pos1 = rand.nextInt(total);
			
			if (boardArray[pos1]==0) {
				boardArray[pos1]=r;
			} else {                   // else boardArray[pos1] is filled
				while(boardArray[++pos1%total]!=0) { 
				// this will find the next empty and let it = pos1
				}
				boardArray[pos1%total] = r;
			}
			
			pos2 = rand.nextInt(total);
			if (boardArray[pos2]==0) {
				boardArray[pos2]=r;
			} else {
				while(boardArray[++pos2%total]!=0) { 
				// this will find the next empty and let it = pos1
				}
				boardArray[pos2%total] = r;
			}
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
			boardArray[i] = i/2%20+1;
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

