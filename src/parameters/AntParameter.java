package parameters;

import rule.AntRule;

public class AntParameter extends Parameter {
	public static final int FOOD_LEVEL = 0;
	public static final int NEXT_FOOD_LEVEL = 1;
	public static final int NUM_ANTS = 2;
	public static final int NEXT_NUM_ANTS = 3;
	public static final int FOOD_PHER = 4;
	public static final int NEXT_FOOD_PHER = 5;
	public static final int NEST_PHER = 6;
	public static final int NEXT_NEST_PHER = 7;
	public static final int ANTS_WITH_FOOD = 8;
	public static final int NEXT_ANTS_WITH_FOOD = 9;
	
	public AntParameter(int state, int startFoodLevel, int startingAnts, int maxPher){
		int foodLevel = determineLevel(state, startFoodLevel, AntRule.FOOD);
		add(FOOD_LEVEL, foodLevel);
		add(NEXT_FOOD_LEVEL, foodLevel);
		int antLevel = determineLevel(state, startingAnts, AntRule.NEST);
		add(NUM_ANTS, antLevel);
		add(NEXT_NUM_ANTS, antLevel);
		int foodPherLevel = determineLevel(state, maxPher, AntRule.FOOD);
		add(FOOD_PHER, foodPherLevel);
		add(NEXT_FOOD_PHER, foodPherLevel);
		int nestPherLevel = determineLevel(state, maxPher, AntRule.NEST);
		add(NEST_PHER, nestPherLevel);
		add(NEXT_NEST_PHER, nestPherLevel);
		add(ANTS_WITH_FOOD, 0);
		add(NEXT_ANTS_WITH_FOOD, 0);
		
		
	}
	
	@Override
	public void updateParameters() {
		set(FOOD_LEVEL, get(NEXT_FOOD_LEVEL));
		set(NUM_ANTS, get(NEXT_NUM_ANTS));
		set(FOOD_PHER, get(NEXT_FOOD_PHER));
		set(NEST_PHER, get(NEXT_NEST_PHER));
		set(ANTS_WITH_FOOD, get(NEXT_ANTS_WITH_FOOD));
	}
	
	private int determineLevel(int state, int returnNum, int desiredState){
		if (state == desiredState){
			return returnNum;
		}
		return 0;
	}

}
