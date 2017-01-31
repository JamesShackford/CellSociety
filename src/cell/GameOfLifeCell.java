package cell;

import java.util.HashMap;
import java.util.Map;

import cellGrid.CellGrid;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GameOfLifeCell extends Cell {

	// 1 = alive, 0 = dead for state
	private CellGrid myGrid;
	private int xPos;
	private int yPos;

	public GameOfLifeCell(int s) {
		super(s);

	}
	
	public Map<Integer, Paint> getMap(){
		HashMap<Integer, Paint> stateColorMap = new HashMap<Integer, Paint>();
		stateColorMap.put(0, Color.RED);
		stateColorMap.put(1, Color.BLUE);
		return stateColorMap;
	}

	public int getNextState() {
		Map<String, Cell> map = myGrid.getNeighbors(xPos, yPos);
		int numAlive = 0;
		for (Cell neighborCell : map.values()) {
			if(neighborCell != null){
				if (neighborCell.getState() == 1) {
					numAlive++;
				}
			}
			
		}

		// need to simplify this if statement structure
		if (this.getState() == 1) {
			if (numAlive < 2 || numAlive > 3) {
				return 0;
			} else if (numAlive == 2 || numAlive == 3) {
				return 1;
			}

		} else if (numAlive == 3) {
			return 1;
		} 
		return this.getState();

	}
}
