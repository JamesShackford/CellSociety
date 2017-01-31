package cell;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class Cell {

	private boolean isEdge;
	private boolean isCorner;
	private Map<Integer, Paint> stateColorMap;
	private int state;
	private int nextState;

	public Cell(int s) {
		state = s;
		stateColorMap = getMap();
	}

	public abstract int getNextState();
	
	public abstract Map<Integer, Paint> getMap();

	public void updateCell() {
		this.setState(this.getNextState());
	}

	public int getState() {
		return state;
	}

	public void setState(int s) {
		state = s;
	}

	public void setNextState(int s) {
		nextState = s;
	}

	public boolean getIsEdge() {
		return isEdge;
	}

	public void setEdge(boolean isEdge) {
		this.isEdge = isEdge;
	}

}
