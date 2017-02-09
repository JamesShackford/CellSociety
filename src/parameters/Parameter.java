package parameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class Parameter {
	List<Integer> myParameters;
	
	public Parameter(){
		myParameters = new ArrayList<Integer>();
	}
	
	public Parameter(List<Integer> parameters){
		myParameters = parameters;
	}
	
	public List<Integer> getParameters(){
		return myParameters;
	}

	protected void add(int index, int element){
		myParameters.add(index, element);
	}
	
	public int get(int index){
		return myParameters.get(index);
	}
	
	public void set(int index, int element){
		myParameters.set(index, element);
	}
	
	
	public void incrementParameter(int index){
		set(index, get(index) + 1);
	}
	public abstract void updateParameters();
	
	public List<Integer> getParameterClone(){
		List<Integer> newParameters = new ArrayList<Integer>();
		Collections.copy(newParameters, myParameters);
		return newParameters;
	}
	
	public void replaceParameters(List<Integer> newParameters){
		myParameters = newParameters;
	}
	
}
