package parameters;

import rule.SugarRule;

public class SugarParameter extends Parameter {

	public static final int GROWTH = 0;
	public static final int NEXT_GROWTH = 1;
	public static final int AGENT_SUGAR = 2;
	public static final int NEXT_AGENT_SUGAR = 3;
	public static final int MAXSUGAR = 4;
	public static final int AGENT_METABOLISM = 5;
	public static final int VISION = 6;

	// pass in limits
	public SugarParameter(int state, int upperMetabolismLimit, int lowerMetabolismLimit,
			int upperVisionLimit, int lowerVisionLimit, int upperAgentStartSugar, int lowerAgentStartSugar) {
		add(GROWTH, 0);
		add(NEXT_GROWTH, 0);
		int agentSugar = determineAgentStat(state, upperAgentStartSugar, lowerAgentStartSugar);
		add(AGENT_SUGAR, agentSugar);
		add(NEXT_AGENT_SUGAR, agentSugar);
		int maxSugar = determineMaxSugar(state);
		add(MAXSUGAR, maxSugar);
		add(AGENT_METABOLISM, determineAgentStat(state, upperMetabolismLimit, lowerMetabolismLimit));
		add(VISION, determineAgentStat(state, upperVisionLimit, lowerVisionLimit));
	}

	@Override
	public void updateParameters() {
		set(GROWTH, get(NEXT_GROWTH));
	}

	private int determineAgentStat(int state, int high, int low) {
		if (state == SugarRule.AGENT) {
			return randInt(high, low);
		}
		return 0;
	}

	private int randInt(int high, int low) {
		return (int) (Math.random() * (high - low + 1)) + low;
	}

	private int determineMaxSugar(int state) {
		if (state == SugarRule.AGENT) {
			return randInt(7, 1);
		}
		return state;
	}

}
