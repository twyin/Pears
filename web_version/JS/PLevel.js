class PLevel {

	constructor(h, w, p, ob, t) { // int, int, int, int[]
		this.gboard = new GameBoard(h, w, p, ob);
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
let level1 = new PLevel(7, 10, 7, null, null);

let int[] ob2 = {16,27,38, 46,47,48,49,50,51,52, 60,71,82};
let level2 = new PLevel(9, 11, 8, ob2, null);

let int[] ob3 = {1,3,5,7,9};
let level3 =  new PLevel(11, 16, 11, ob3, 300);


