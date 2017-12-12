
package pear;

class TesterBoard {
	// initialized to zero as they are instance variables
	private int height;
	private int width;
//	private int patterns;
//	private int unsolvedPairs;
//	private int[] obstacles;          // initialized to null
	private int[][] board;
	
	
// constructor of empty board
	protected TesterBoard(int h, int w) {
		this.height = h;
		this.width = w;
		board = new int[h][w]; // value at each [i][j] is zero
	}
	
	protected TesterBoard(int h, int w, int[][] b) {
		this.height = h;
		this.width = w;
		board = b;
	}
	
	
// getter's
	protected int getHeight() {
		return this.height;
	}
	
	protected int getWidth() {
		return this.width;
	}
	
	protected int[][] getBoard() {
		return this.board;
	}
	
	protected int getValue(int h, int w) {
		return board[h][w];
	}
	
	
// setter's
// height and width should most definitely not change throughout
//   the game
	protected void setBoard(int[][] b) {
		board = b;
	}
	
	protected void setValue(int h, int w, int val) {
		board[h][w] = val;
	}
	
	protected void setToEmpty(int h, int w) {
		board[h][w]=0;
	}
	
	
// ============================================================================
// ============================================================================
	
	
// ============================================================================
// ============================================================================
	public String toString() {
		int h = this.height;
		int w = this.width;
		
		// for personal esthetics
		// precise formula: (width*3+4 - " Board ".length()) in half, then + " Board ".length()
		String drawBoard = String.format("%"+ ((w*3-3)/2+7) + "s", 
				"xBoardx").replace(' ', '=');
		drawBoard = String.format("%-" + (w*3+4) + "s\n", 
				drawBoard).replace(' ', '=').replace('x',' ');
		
		for (int i=0; i<h; i++) {
			drawBoard += ("[ ");
			for (int j=0; j<w; j++) {
				drawBoard += (String.format("%2d ", board[i][j]));
				//drawBoard += (board[i][j] + " ");
			}
			drawBoard+=" ]\n";
		}
		return drawBoard;
	}
// ============================================================================
// ============================================================================
	
}

