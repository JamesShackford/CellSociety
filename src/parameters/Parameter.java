package parameters;

import java.util.ArrayList;
import java.util.List;

public class Parameter {
	List<Integer> myParameters;

	public Parameter() {
		myParameters = new ArrayList<Integer>();
	}

	public Parameter(List<Integer> parameters) {
		myParameters = parameters;
	}

	public List<Integer> getParameters() {
		return myParameters;
	}

	protected void add(int index, int element) {
		myParameters.add(index, element);
	}

	public int get(int index) {
		return myParameters.get(index);
	}

	public void set(int index, int element) {
		myParameters.set(index, element);
	}

	public void incrementParameter(int incrementIndex, int referenceIndex) {
		set(incrementIndex, get(referenceIndex) + 1);
	}
	
	
	/**
	 * generic decrement method that can be called to increment any parameter
	 * @param incrementIndex
	 * @param referenceIndex is reference index
	 */
	public void decrementParameter(int incrementIndex, int referenceIndex) {
		set(incrementIndex, get(referenceIndex) - 1);
	}

	public void updateParameters(){

	}

	public List<Integer> getParameterClone() {
		List<Integer> newParameters = new ArrayList<Integer>();
		for (Integer parameter : myParameters) {
			newParameters.add(parameter);
		}
		return newParameters;
	}

	public void replaceParameters(List<Integer> newParameters) {
		myParameters = newParameters;
	}

}