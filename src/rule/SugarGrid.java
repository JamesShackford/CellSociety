package rule;

import java.util.ArrayList;

public class SugarGrid {

	private ArrayList<ArrayList<SugarData>> myGrid;

	public SugarGrid(int width, int height){
		myGrid = new ArrayList<ArrayList<SugarData>>();
		for loop
			for loop
				populate grid w/ initial sugar data
	}

	public int getSugarLevel(int x, int y) {
		return myGrid.get(x).get(y).getSugar();
	}

	public void setSugarLevel(int x, int y, int sugarLevel) {
		myGrid.get(x).get(y).setSugar(sugarLevel);
	}

	public int getMaxSugarLevel(int x, int y) {
		return myGrid.get(x).get(y).getMaxSugar();
	}

	public void setMaxSugarLevel(int x, int y, int maxSugarLevel) {
		myGrid.get(x).get(y).setSugar(maxSugarLevel);
	}

	public int getAgentSugar(int x, int y) {
		return myGrid.get(x).get(y).getAgentSugar();
	}

	public void setAgentSugar(int x, int y, int sugarLevel) {
		myGrid.get(x).get(y).setAgentSugar(sugarLevel);
	}

	public int getAgentMetabolism(int x, int y) {
		return myGrid.get(x).get(y).getAgentSugarMetabolism();
	}

	public void setAgentMetabolism(int x, int y, int metabolism) {
		myGrid.get(x).get(y).setAgentSugarMetabolism(metabolism);
	}

	public int getAgentVision(int x, int y) {
		return myGrid.get(x).get(y).getVision();
	}

	public void setAgentVision(int x, int y, int vision) {
		myGrid.get(x).get(y).setVision(vision);
	}

	public boolean atMaxSugar(int x, int y) {
		return getSugarLevel(x, y) >= getMaxSugarLevel(x, y);
	}

	private class SugarData {

		int sugar;
		int maxSugar;
		int agentSugar;
		int agentSugarMetabolism;
		int vision;

		private SugarData(int initialSugar, int max) {
			sugar = initialSugar;
			maxSugar = max;
		}

		private int getAgentSugar() {
			return agentSugar;
		}

		private void setAgentSugar(int agentSugar) {
			this.agentSugar = agentSugar;
		}

		private int getAgentSugarMetabolism() {
			return agentSugarMetabolism;
		}

		private void setAgentSugarMetabolism(int agentSugarMetabolism) {
			this.agentSugarMetabolism = agentSugarMetabolism;
		}

		private int getVision() {
			return vision;
		}

		private void setVision(int vision) {
			this.vision = vision;
		}

		private int getSugar() {
			return sugar;
		}

		private void setSugar(int sugar) {
			this.sugar = sugar;
		}

		private int getMaxSugar() {
			return maxSugar;
		}

		private void setMaxSugar(int maxSugar) {
			this.maxSugar = maxSugar;
		}

	}

}
