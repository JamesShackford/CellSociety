package rule;

import java.util.ArrayList;
import java.util.List;

public class PredatorPreyTimeGrid
{

	private PredatorPreyData[][] myGrid;

	public PredatorPreyTimeGrid(int width, int height)
	{
		myGrid = new PredatorPreyData[width][height];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				myGrid[i][j] = new PredatorPreyData(0, 0);
			}
		}
	}

	private class PredatorPreyData
	{
		int breedTime;
		int starveTime;

		private PredatorPreyData(int breedTime, int starveTime)
		{
			this.breedTime = breedTime;
			this.starveTime = starveTime;
		}

		private int getBreedTime()
		{
			return breedTime;
		}

		private int getStarveTime()
		{
			return starveTime;
		}

		private void setStarveTime(int newStarveTime)
		{
			this.starveTime = newStarveTime;
		}

		private void setBreedTime(int newBreedTime)
		{
			this.breedTime = newBreedTime;
		}
	}

	public int getBreedTime(int x, int y)
	{
		return myGrid[x][y].getBreedTime();
	}

	public int getStarveTime(int x, int y)
	{
		return myGrid[x][y].getStarveTime();
	}

	public void setBreedTime(int x, int y, int newTime)
	{
		myGrid[x][y].setBreedTime(newTime);
	}

	public void setStarveTime(int x, int y, int newTime)
	{
		myGrid[x][y].setStarveTime(newTime);
	}

	public void incrementBreedTime(int x, int y)
	{
		myGrid[x][y].setBreedTime(myGrid[x][y].getBreedTime() + 1);
	}
	
	public void incrementStarveTime(int x, int y)
	{
		myGrid[x][y].setStarveTime(myGrid[x][y].getStarveTime() + 1);
	}
	
	
	public void print(){
		for (int i = 0; i < myGrid.length; i++) {
			for (int j = 0; j < myGrid[0].length; j++) {
				System.out.print(myGrid[i][j].getBreedTime());
			}
			System.out.println();
		}
	}
}
