package gol;

import java.util.Random;

public class Game {

	private int width;
	private int height;
	private int sleepMs = 200;
	Random r = new Random();

	private Cell[][] map;

	Game() {
		this.width = 40;
		this.height = 20;

		this.map = this.getEmptyMap();

		
		// hier Startzellen festlegen...
		for (int x = 1; x < this.width - 1; x++) {
			for (int y = 1; y < this.height - 1; y++) {
				Cell c = new Cell();
				c.setState(this.flipCoin(0.5) ? State.ALIVE : State.DEAD);
				this.map[x][y] = c;
			}
		}

	}

	public void run() {
		while (true) {

			// neue Generation berechnen
			this.simulateGeneration();

			// zeichne frame
			this.draw();

			try {
				Thread.sleep(sleepMs);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private void simulateGeneration() {
		Cell[][] nextGeneration = this.getEmptyMap();

		for (int x = 1; x < this.width - 1; x++) {
			for (int y = 1; y < this.height - 1; y++) {
				Cell currentCell = this.map[x][y];
				int numAliveCells = 0;

				// benachbarte Zellen anschauen
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						if (!(i == 0 && j == 0)) {
							Cell c = this.map[x + i][y + j];
							if (c.getState() == State.ALIVE) {
								numAliveCells++;
							}
						}
					}
				}

				// lebendig
				if (currentCell.getState() == State.ALIVE) {
					if (numAliveCells < 2) {
						currentCell.setState(State.DEAD);
					} else if (numAliveCells == 2 || numAliveCells == 3) {
						currentCell.setState(State.ALIVE);
					} else if (numAliveCells > 3) {
						currentCell.setState(State.DEAD);
					}
				}
				// tot
				else {
					if (numAliveCells == 3) {
						currentCell.setState(State.ALIVE);
					}
				}

				currentCell.neighbours = numAliveCells;

				nextGeneration[x][y] = currentCell;
			}
		}
		// neue Generation übernehmen
		this.map = nextGeneration;
	}

	private void draw() {

		for (int i = 0; i < 20; i++) {
			System.out.println();
		}

		for (int y = 0; y < this.height; y++) {

			String line = new String();

			for (int x = 0; x < this.width; x++) {
				line += this.map[x][y].toString();
			}

			System.out.println(line);
		}
	}

	private Cell[][] getEmptyMap() {
		Cell[][] map = new Cell[this.width][this.height];
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				map[x][y] = new Cell();
				if (x == 0 || x == (this.width - 1) || y == 0 || y == (this.height - 1)) {
					map[x][y].setState(State.DEAD);
				}
			}
		}
		return map;
	}

	private boolean flipCoin(double d) {

		return this.r.nextDouble() <= d;
	}
}
