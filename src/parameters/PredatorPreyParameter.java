package parameters;

import java.util.List;

public class PredatorPreyParameter extends Parameter{

	public static final int BREED_INDEX = 0;
	public static final int NEXT_BREED_INDEX = 1;
	public static final int STARVE_INDEX = 2;
	public static final int NEXT_STARVE_INDEX = 3;
	
	public PredatorPreyParameter(){
		add(BREED_INDEX, 0);
		add(STARVE_INDEX, 0);
		add(NEXT_BREED_INDEX, 0);
		add(NEXT_STARVE_INDEX, 0);
	}

	@Override
	public void updateParameters() {
		set(BREED_INDEX, get(NEXT_BREED_INDEX));
		set(STARVE_INDEX, get(NEXT_STARVE_INDEX));
	}


	
	
}
