package cell;

public abstract class Cell {
	
	private boolean isEdge;
	private boolean isCorner;
	
	public Cell(){
		
	}
	
	public abstract void updateCell();
	
	public void getState(){
		
	}
	
	public void setState(){
		
	}
	

	public boolean getIsEdge() {
		return isEdge;
	}

	public void setEdge(boolean isEdge) {
		this.isEdge = isEdge;
	}
	
	

}
