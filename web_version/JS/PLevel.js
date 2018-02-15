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
let level1 = new PLevel(7, 10, 9, null, null);

//let ob2 = [16,27,38, 46,47,48,49,50,51,52, 60,71,82];  // int[]
//let level2 = new PLevel(9, 11, 10, ob2, null);

//let ob3 = [1,3,5,7,9];
//let level3 =  new PLevel(11, 16, 14, ob3, 300);


