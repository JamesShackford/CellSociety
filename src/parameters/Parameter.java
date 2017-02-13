package parameters;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic parameter class that can be used to store parameters in a cell that are specific to a simulation.
 * For simulations that need additional parameters, a subclass of this class must be created.
 * 
 * Stores additional parameters in a List, instance of Parameter object is held in each cell.
 * 
 * @author Derek
 *
 */
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

	/**
	 * Add an integer parameter to the specified index, used by constructor of subclasses to populate list with initial parameters
	 * 
	 * @param index
	 * @param element
	 */
	protected void add(int index, int element) {
		myParameters.add(index, element);
	}

	/**
	 * used to get the value of the parameter at index
	 * 
	 * @param index
	 * @return return value of parameter at index
	 */
	public int get(int index) {
		return myParameters.get(index);
	}

	/**
	 * used to set the value of parameter at index
	 * 
	 * @param index
	 * @param element
	 */
	public void set(int index, int element) {
		myParameters.set(index, element);
	}

	/**
	 * generic increment method that can be called to increment any parameter
	 * 
	 * @param incrementIndex  index of parameter to be incremented
	 * @param referenceIndex   index of value to increment off of
	 */
	public void incrementParameter(int incrementIndex, int referenceIndex) {
		set(incrementIndex, get(referenceIndex) + 1);
	}
	
	
	/**
	 * generic decrement method that can be called to increment any parameter
	 * @param incrementIndex
	 * @param referenceIndex
	 */
	public void decrementParameter(int incrementIndex, int referenceIndex) {
		set(incrementIndex, get(referenceIndex) - 1);
	}

	/**
	 * Generic update parameters method, for simulations without a parameter subclass this method should do nothing as shown,
	 * for simulations with a parameter subclass this method should be overridden to perform the specific implementation of updating parameters
	 */
	public void updateParameters(){

	}

	/**
	 * can be used if the parameters of one cell need to be duplicated to another cell
	 * 
	 * @return cloned list of parameters
	 */
	public List<Integer> getParameterClone() {
		List<Integer> newParameters = new ArrayList<Integer>();
		for (Integer parameter : myParameters) {
			newParameters.add(parameter);
		}
		return newParameters;
	}

	/**
	 * used in duplication method to replace old parameters with copy of parameters of another cell
	 * 
	 * @param newParameters
	 */
	public void replaceParameters(List<Integer> newParameters) {
		myParameters = newParameters;
	}

}
