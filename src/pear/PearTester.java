package pear;


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

/* Conclusion:
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
 * 					assert somewhere length of obstacles[] is even and no repeats
 * 					  (might be unnecessary?)
 * 					
 * 					NEEDS IMMEDIATE FIX:
 * 					HOW TO CREATE OBSTACLES AND NOT LET FILL AND SHUFFLE
 * 					  CHANGE THEM
 * 					how to stop game from running; right now posA_H 
 * 					  runs out of bound before while condition becomes
 * 					  false
 * 					
 * 		
 * 			
 * 
 */
		
	
import java.util.Scanner;

// protected
class PearTester {
	
	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		
		
		
		
		int[] ob1 = {4,9,14,19};
		TesterBoard tbSmall = new TesterBoard(5, 4, 5, ob1);
		
		
// checking if new fill-1 can add obstacles properly
		tbSmall.fill1();
		System.out.println(tbSmall);
		tbSmall.shuffle();
		System.out.println(tbSmall);
		

		
		
		
		long time1, time2;
		
		time1 = System.currentTimeMillis();
		
		TesterBoard tb1 = new TesterBoard(16,12,20);
		
		// 192 squares, 96 pairs, 20 patterns named 1-20		
		tb1.fill1();
		time2 = System.currentTimeMillis();
		System.out.println("time from fill-1: " + (time2-time1));

		System.out.println(tb1);
		
		
		time1 = System.currentTimeMillis();
		TesterBoard tb2 = new TesterBoard(16,12,20);
		tb2.fill2();
		time2 = System.currentTimeMillis();
		System.out.println("time from fill-2: " + (time2-time1));
		//System.out.println(tb2);
		
		
/* looping shuffle()
		System.out.println("shuffle times? ");
		int shufTimes = keyboard.nextInt();
		
		time1 = System.currentTimeMillis();
		while (shufTimes>0) {
			tb2.shuffle();
			shufTimes--;
		}
		time2 = System.currentTimeMillis();
		System.out.println("time for shuffling " + shufTimes + 
				           " times: " + (time2-time1));
*/
		
//		System.out.println(tb1);
		

// ======================================================================= PLAY
/*
		System.out.println("Please enter integers separated by a space");
		System.out.println("Enter negative integer to quit");
		int intA, intB;
		int posA_H, posA_W, posB_H, posB_W;
		do {
			System.out.println("Enter coordinate pair =================");
			System.out.print("A: ");
			posA_H = keyboard.nextInt();
			posA_W = keyboard.nextInt();
			intA = tbSmall.getValue(posA_H, posA_W);
			
			System.out.print("B: ");
			posB_H = keyboard.nextInt();
			posB_W = keyboard.nextInt();
			intB = tbSmall.getValue(posB_H, posB_W);
			
			if(intA==intB) {
				tbSmall.setToEmpty(posA_H, posA_W);
				tbSmall.setToEmpty(posB_H, posB_W);
			}
			System.out.print(tbSmall);
		} while (posA_H>=0);
*/
		
		

		
		
		
		
		keyboard.close();
	}

}
