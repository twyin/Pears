class Pears {

	constructor() {
		this.timer;         // int
		this.curr_level;    // level object?
	}

	
	startNewLevel() {

		makeBoard(curr_level);
		updateTimer();
		updateUnsolvedPears();

		displayTimer();
		setInternal(1000, updateTimeLeft());

		curr_level.fill();

	}

// !!!!! match doesn't work yet; purely java code
	match (y1, x1, y2, x2) {     
//		console.log("To quit, enter -1");
//		console.log("Please enter coordinate separated by a space");
		let intA; // int
		let intB; // int
		let posA_H, posA_W, posB_H, posB_W; // int
		let maxTurns = curr_level.getMaxTurns(); // int
		let quit = false;   // boolean; set quit to false if want to try game
		
		while (quit == false){
			
			console.log("A: ");
			
			posA_H = keyboard.nextInt();
			
			if (posA_H == -1) {
				quit = true;
				console.log("Pears exiting...");
			} else {
				//posA_H = keyboard.nextInt();
				posA_W = keyboard.nextInt();
				intA = tb.getValue(posA_H, posA_W);
				console.log("A': ");
				posB_H = keyboard.nextInt();
				posB_W = keyboard.nextInt();
				intB = tb.getValue(posB_H, posB_W);
				
				if (intA==0 || intB==0) {
					console.log("Coordinate is empty");
				} else if (posA_H==posB_H && posA_W==posB_W) {
					console.log("They're the same");
				} else if (intA!=intB) {
					console.log("Invalid pear");
				} else {
//					assert(posA_H!=posB_H && posA_W!=posB_W);
//					assert(intA==intB);
					i = tb.minTurns(posA_H, posA_W,  posB_H, posB_W);
					if (i > maxTurns) {
						console.log("Invalid: must be within " + maxTurns + " turns");
					} else {
						// valid number of turns
						console.log("Valid: " + i + " turns needed ");
						tb.setToEmpty(posA_H, posA_W);
						tb.setToEmpty(posB_H, posB_W);
					}
				}
				console.log(tbBig.toString());
			}
			
			console.log("Exiting");
			quit = true; // will make game only run once
		}
	}

	endLevel() {
		if noMoreLevels {
			exit()
		} else {
			startNewLevel();
		}

	}
	
}


// pages load
// define level constants
// call the game

//	let level1;
//	let level2;
//	let level3;

let curr_level = 1;
let timer;
new Pears();
Pears.startNewLevel();

