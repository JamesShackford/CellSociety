package cell;

public class GameOfLifeCell extends Cell {
	
	private boolean isAlive;
	private CellGrid myGrid;
	private int xPos;
	private int yPos;
	private boolean nextState;
	
	public GameOfLifeCell(){
		
	}
	
	
	public void getNextState() {
		Map<String, Cell> map = myGrid.getNeighbors(xPos, yPos);
		int numAlive = 0;
		for (GameOfLifeCell value : map.values()) {
		    if(value.isAlive()){
		    	numAlive++;
		    }
		}
		if(isAlive == true){
			if(numAlive < 2){
				nextState = false;
			}
			
		}else if(numAlive==3){
			nextState = true;
		}
		
		
	}
	
	public void updateCell(){
		
	}
	
	
	public boolean isAlive(){
		return isAlive;
	}

}
