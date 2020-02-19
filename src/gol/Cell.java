package gol;

enum State{
	DEAD,
	ALIVE
}

public class Cell {
	private State state;
	public int neighbours;
	
	// -------------------------------------
	
	public Cell(){
		this.state = State.DEAD;
	}
	
	public Cell(Cell c){
		this.state = c.getState();
	}
	
	// --------------------------------------
	
	public void setState(State s) {
		this.state = s;
	}
	
	public State getState() {
		return this.state;
	}
	
	public String toString() {
		return this.state == State.ALIVE ? "#" : " ";
	}
}
