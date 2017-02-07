package rule;

import java.util.ArrayList;
import java.util.List;

public class PredatorPreyTimeGrid {

	private PredatorPreyData[][] myGrid;

	public PredatorPreyTimeGrid(int width, int height) {
		myGrid = new PredatorPreyData[width][height];
	}

	private class PredatorPreyData {
		private List<Integer> data;

		private PredatorPreyData(int breedTime, int starveTime) {
			data = new ArrayList<Integer>();
			data.add(breedTime);
			data.add(starveTime);
		}

		private int getBreedTime() {
			return data.get(0);
		}

		private int getStarveTime() {
			return data.get(1);
		}

		private void setStarveTime(int newStarveTime) {
			data.set(1, newStarveTime);
		}

		private void setBreedTime(int newBreedTime) {
			data.set(0, newBreedTime);
		}
	}

	public int getBreedTime(int x, int y) {
		return myGrid[x][y].getBreedTime();
	}

	public int getStarveTime(int x, int y) {
		return myGrid[x][y].getStarveTime();
	}

	public void setBreedTime(int x, int y, int newTime) {
		myGrid[x][y].setBreedTime(newTime);
	}

	public void setStarveTime(int x, int y, int newTime) {
		myGrid[x][y].setStarveTime(newTime);
	}

	public void incrementBreedTime(int x, int y) {
		myGrid[x][y].setBreedTime(myGrid[x][y].getBreedTime() + 1);
	}
}
