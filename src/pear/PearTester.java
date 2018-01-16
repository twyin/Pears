package pear;


import java.util.InputMismatchException;

/* Notes:
 *       -1 = obstacle
 *        0 = empty
 *     1-20 = pattern; becomes 0 after being paired up
 *       at least 1 of width and height must be even, or else WxH is odd
 *        
 */

/* Idea for Pear: 
 *       get random int[0:19], put them on board pair by pair
 *       give 3 shuffle chances, each will shuffle every icon left
 * 
 */


//compare two board filling methods and see which is faster
// fill1: get random int[0:19], put in boardArray[random1] and boardArray[random2], 
//		  then put into board
// fill2: given int[total], shuffle every icon, then put into board

/* Conclusions:
 * 		Nov 10, 2017 3am
 * 					on average fill-1 takes 0-10 millis, while fill-2 takes 0
 * 					  shuffling 10 times takes about 0 millis
 * 		Still need to do
 * 					assert input is valid, and in other forms besides 
 * 					  just 2 int's separated by space..
 * 					make path finder that limit turns to maximum of 3
 * 
 * 		Nov 10, 2017 7pm
 * 					realized that since paths can only be created over 0's,
 * 					  therefore need to create ring of 0's around board.
 * 					Adding obstacle-handling is easier to do for fill-1
 * 					  than fill-2 so fill-1 now has the new feature. But
 * 					  since fill-2 is proven to be faster, that will need
 * 					  to be done (will probably time them against each other
 * 					  gain after finishing obstacle-handling for both)
 * 					this also makes shuffling more complicated..
 * 					game ends when all the pairs have been found. Can make
 * 					  program check if all coord's are 0's and -1's after
 * 					  every valid move, or have number of pairs as 
 * 					  instance variable of board class
 * 					assert somewhere length of obstacles[] is even and no 
 *					  repeats (might be unnecessary?)
 * 					
 * 					NEEDS IMMEDIATE FIX:
 * 					how to stop game from running; right now posA_H 
 * 					  runs out of bound before while condition becomes
 * 					  false
 * 		
 * 		Nov 11, 2017 
 * 					shuffle is fixed - wont move -1's, and can throw/catch
 * 					  exception if it runs into error or is stuck in loop 
 * 		Nov 12, 2017
 * 					two possible ideas to find shortest path:
 * 					1) Breadth First Search
 * 						 - selected node
 * 						 - visited nodes
 * 						 - queue nodes
 * 					2) A* algorithm with
 * 						 a) Manhattan Distance Heuristic
 * 							h = |x_start - x_destination| + |y_s - y_d|
 * 						 b) Euclidean Distance Heuristic
 * 							h = sqrt((x_s - x_d)^2 + (y_s - y_d)^2)
 * 							slightly more accurate and favours straight lines,
 * 							  but slower due to larger area to explore
 * 					problem with both is that neither minimize the number
 * 					  of "turns" made when it's on a grid
 * 
 * 					current shuffle() is allowed to swap a pattern with 0.
 * 					with the end result in mind, this should be allowed in 
 * 					  order to open door for players if they are stuck
 * 
 * 					new fill-1 is slower than fill-2, this is evident when
 * 					  board dimensions are in the hundreds
 * 
 * 		Nov 14, 2017
 * 					make a method that finds the minimum number of turns
 * 					  from A to A'. With that information, utilize A* 
 * 					  algorithm such that h is some max number when program
 * 					  detects that it is making more turns than necessary
 * 
 * 					final version will probably need a Board as base class,
 * 					  and then GameBorard and PathBoard as derived classes.
 * 					  Additionally, unsure of what type of objects to use 
 * 					  to implement A* algorithm yet. This is because the 
 * 					  method to find minimum of turns from A to A' requires
 * 					  writing on a new board, since overwriting the 
 * 					  gameboard itself isn't suitable, new one must be 
 * 					  made. By nature it would have different purposes and 
 * 					  therefore methods, it would be better to made two 
 * 					  derived classes from one base Board class
 * 
 * 		Nov 27, 2017
 * 					idea for marking coordinates reachable within x amount
 * 					  of turns: from the gameboard, a new board will be
 * 					  initialized with 0's and -1's, with a single 1 at the
 * 					  starting position A. Then a method called addTurn will 
 * 					  take every 1 on the board, and replace every 0 with 
 * 					  same x or y position with 1. As a result, every 1 
 * 					  indicates which coordinates can be reached with turns 
 * 					  allowed so far
 * 
 * 		Nov 29, 2017	
 * pseudo code for when a player is playing the game:
 * TesterGameBoard gb = new TesterGameBoard(height, width, #ofPatterns, obstacles[]
 * player enters A:(x,y)
 * re-entering it cancels search
 * player enters new A: (x, y)
 * player enters new A':(x',y')
 * 		====================================================================================
 * 		|| EVERYTIME PLAYER CLICK ON A NEW PAIR OF COORDS, A NEW PATHS BOARD WILL BE MADE ||
 * 		====================================================================================
 * gb.travel(x, y, x', y');  
 * in gameboard:
 * 		gb.minTurns()
 * 			new pathboard pb
 * 			while found==false && addedTurns <= getMaxTurns()
 * 					pb.addTurn()
 * 					if A' has been conquered
 * 						found==true;
 * 					else
 * 						addedTurns++
 * 			if found==true;
 * 				return addedTurns
 * 			else
 * 				return getMaxTurns+1;
 * 
 * 		if minTurns > getMaxTurns
 * 			invalid path, don't run findPath
 * 		else it's valid
 * 			find actual path and return it as.. int array?
 * 					
 * 					addTurn() is a method that takes all existing 1s and 
 *                    spread them up, down, left, and right. Originally, the
 *                    plan was to take every 1, and change touching/surround
 *                    0s to 1. However, this creates a problem! For example,
 *                    say A = (0,0) and A' = (2,2), and the rest of the board 
 *                    is empty, then it should take exactly 1 turn. The 
 *                    original plan creates a problem where *new* 1s will be
 *                    treated like old/given 1s and spread in all 4 
 *                    directions as well. One quick and sufficient fix 
 *                    (instead of making another board to separately keep 
 *                    track of old and new 1s) is to make "new 1" into a 2 
 *                    instead. So after going through the board the first 
 *                    time to spread 1s, go through it again and change all
 *                    2s to 1s. 
 *                    old plan, thinks A' can be reached within 0 turns:
 *				[  0  0  0  0  0  ]  =>  [  0  1  0  0  0  ]  =>  [  1  1  1  1  1  ]
 * 				[  0  1  0  0  0  ]      [  1  1  1  1  1  ]      [  1  1  1  1  1  ]
 *   			[  0  0  0  0  0  ]      [  0  1  0  0  0  ]      [  1  1  1  1  1  ]
 * 				[  0  0  0  A' 0  ]      [  0  1  0  A' 0  ]      [  1  1  1  A' 1  ]
 * 				[  0  0  0  0  0  ]      [  0  1  0  0  0  ]      [  1  1  1  1  1  ]
 *                    new plan:
 *				[  0  0  0  0  0  ]  =>  [  0  2  0  0  0  ]  =>  [  0  1  0  0  0  ]
 * 				[  0  1  0  0  0  ]      [  2  1  2  2  2  ]      [  1  1  1  1  1  ]
 *   			[  0  0  0  0  0  ]      [  0  2  0  0  0  ]      [  0  1  0  0  0  ]
 * 				[  0  0  0  A' 0  ]      [  0  2  0  A' 0  ]      [  0  1  0  A' 0  ]
 * 				[  0  0  0  0  0  ]      [  0  2  0  0  0  ]      [  0  1  0  0  0  ]
 *                    Down side is that this might create too many variables,
 *                    since integers -1, 0, 1 ,2, all represent something 
 *                    different. This can get confusing or crowded later.
 *                    Additionally, this approach requires going through
 *                    the board twice when addTurn() is called. The method
 *                    itself will be called 4 times for every pair. Thus
 *                    minTurns() goes through the board a total of 8 times.
 *                    In the end it's still O(n) if n is the total number 
 *                    of elements on board
 *                    
 *                    Thought of decent solution while typing the above:
 *                    Get rid of for loops that change 2s back into 1s and
 *                    increment each time addTurns is called instead. Since
 *                    the end objective right now is just to check if it's
 *                    positive at coordinate A', it doesn't matter if it's
 *                    a 1 or positive integer. This requires new counter but
 *                    will only go through the board 4 times
 *                    New legend will be: 
 *                    -1: obstacles and patterns
 *                     0: open for path
 *                     1: A
 *                     2: reachable within 0 turn
 *                     3: reachable within 1 turn
 *                     4: reachable within 2 turns
 *                     5: reachable within 3 turns
 *                    (^doesn't feel intuitive, might change it later)
 *                    
 *					NOTE: would be nice to reuse this to create Hints later,
 *                    where system will random pick a random A (need to be
 *                    one on the outside and not buried deep in the centered)
 *                    and show user where its A' is at
 *                    
 *			Dec 6, 2017
 *					fill1 and fill2 swaps its elements before board is built.
 *                    So basically gameboards are made shuffled. Shuffle() is
 *                    just another method because the original plan was to 
 *                    give players 3 chances to shuffle
 *                    
 *                    
 */
		
	
import java.util.Scanner;

// protected
class PearTester{
	
	
	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		
//		TesterBoard b1 = new TesterBoard(10, 12);
//		System.out.println(b1);
		
		//int[] ob1 = {4,9,14,19};
		//TesterGameBoard tbSmall = new TesterGameBoard(4, 5, 5, ob1);
		
		int[] ob2 = {16,27,38, 46,47,48,49,50,51,52, 60,71,82};
		TesterGameBoard tbBig = new TesterGameBoard(9, 11, 8, ob2);
		
		int maxTurns = tbBig.getMaxTurns();
		
// checking if new fill can add obstacles properly
		tbBig.fill2();
		System.out.println(tbBig);
		//tbBig.shuffle();
		//System.out.println(tbBig);

		
		int i; //  = tbSmall.minTurns(3, 2, 0, 0);
//		System.out.println("min turns needed: " + i);
// ======================================================================= PLAY

		System.out.println("To quit, enter -1");
		System.out.println("Please enter coordinate separated by a space");
		int intA, intB;
		int posA_H = 0;
		int posA_W, posB_H, posB_W;
		boolean quit = false;   // set quit to false if want to try game
		
		while (quit == false){
			try {
//				System.out.println("Enter coordinate pair =================");
				System.out.print("A: ");
				
				
				
				posA_H = keyboard.nextInt();
				
				if (posA_H == -1) {
					quit = true;
					System.out.println("Pear has exited");
					System.exit(0);
				}
				//posA_H = keyboard.nextInt();
				posA_W = keyboard.nextInt();
				intA = tbBig.getValue(posA_H, posA_W);
				System.out.print("A': ");
				posB_H = keyboard.nextInt();
				posB_W = keyboard.nextInt();
				intB = tbBig.getValue(posB_H, posB_W);
				
				if (intA==0 || intB==0) {
					System.out.println("Coordinate is empty");
				} else if (posA_H==posB_H && posA_W==posB_W) {
					System.out.println("They're the same");
				} else if (intA!=intB) {
					System.out.println("Invalid pear");
				} else {
					assert(posA_H!=posB_H && posA_W!=posB_W);
					assert(intA==intB);
					i = tbBig.minTurns(posA_H, posA_W,  posB_H, posB_W);
					if (i > maxTurns) {
						System.out.println("Invalid: must be within " + maxTurns + " turns");
					} else {
						// valid number of turns
						System.out.println("Valid: " + i + " turns needed ");
						tbBig.setToEmpty(posA_H, posA_W);
						tbBig.setToEmpty(posB_H, posB_W);
					}
				}
				System.out.print(tbBig);
			} catch (InputMismatchException e) {
				System.out.println("Input mismatch exception thrown");
				keyboard.nextLine();
				// so it's not stuck with same keyboard buffer
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Array index out of bounds exception thrown");
				//keyboard.nextLine();    not sure if needed
			}
			System.out.println("Exiting");
			quit = true; // will make game only run once
		}
		
		keyboard.close();
		
	}


}
