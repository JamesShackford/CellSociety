package parameters;

/**
 * Stores relevant parameters for Wa Tor (Predator Prey) simulation
 * 
 * @author Derek
 *
 */
public class PredatorPreyParameter extends Parameter {

	public static final int BREED = 0;
	public static final int NEXT_BREED = 1;
	public static final int STARVE = 2;
	public static final int NEXT_STARVE = 3;

	public PredatorPreyParameter() {
		add(BREED, 0);
		add(NEXT_BREED, 0);
		add(STARVE, 0);
		add(NEXT_STARVE, 0);
	}

	@Override
	public void updateParameters() {
		set(BREED, get(NEXT_BREED));
		set(STARVE, get(NEXT_STARVE));
	}
}
