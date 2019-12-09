class PLevel {

	constructor(h, w, p, ob, t) { // int, int, int, int[]
		var gb = new GameBoard(h, w, p, ob);
		gb.fill();
		this.gboard = gb;
		this.timer = t;
	}


// getters
	getGBoard() {
		return this.gboard;
	}


	getTimer() {
		return this.gboard;
	}
	
}

// no obstacles or timer for level 1
let level1 = new PLevel(4, 7, 9, null, null);  // 4 by 7, 10 patterns

let ob2 = [13, 27,29,31,33,35, 49];  // 13 obstacles within 99 squares
let level2 = new PLevel(7, 9, 15, ob2, null);

let ob3 = [59, 23,27, 4,12, 19,31, 52,66, 86,100, 121,133, 157,165, 176,180, 195];
let level3 =  new PLevel(12, 17, 20, ob3, 300);

let game = [level1, level2, level3];
