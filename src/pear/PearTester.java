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
 * 		Nov 10, 2017
 * 					On average fill-1 takes 0-10 millis, while fill-2 takes 0.
 * 					Shuffling 10 times takes about 0 millis
 * 		Still need to do
 * 					assert input is valid, and in other forms besides 
 * 					  just 2 int's separated by space..
 * 					make path finder that limit turns to maximum of 3
 * 
 */
		
	
import java.util.Scanner;

// protected
class PearTester {
	
	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		
		long time1, time2;
		
		time1 = System.currentTimeMillis();
		
		TesterBoard tb1 = new TesterBoard(16, 12, 20);  
		// 192 squares, 96 pairs, 20 patterns named 1-20		
		tb1.fill1();
		time2 = System.currentTimeMillis();
		System.out.println("time from fill-1: " + (time2-time1));

		//System.out.println(tb1);
		
		
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
		
		
		TesterBoard tbSmall = new TesterBoard(5, 4, 5);
		tbSmall.fill2();
		tbSmall.shuffle();
		System.out.println(tbSmall);
// ======================================================================= PLAY
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
		
		

		
		
		
		
		keyboard.close();
	}

}
