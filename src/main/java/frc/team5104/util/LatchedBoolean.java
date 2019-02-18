/*BreakerBots Robotics Team 2019*/
package frc.team5104.util;

/**
 * A latched boolean is a boolean that only returns true when the input changes values.
 * For example when a boolean changes from false->true or true->false.
 * This class needs to be called every loop to work properly.
 */
public class LatchedBoolean {
	/**
	 * always: False->True, and True->False activation,
	 * falseToTrue: only False->True activation,
	 * trueToFalse: only True->False activation
	 */
	public static enum LatchedBooleanMode {
		always,
		falseToTrue,
		trueToFalse
	}
	
	//Class Values
	private boolean lastValue = false;
	public LatchedBooleanMode mode;
	
	//Constructors
	/**
	 * Creates a latched boolean with the mode "Always" in which between False->True and True->False it will be activated.
	 */
	public LatchedBoolean() {
		this(LatchedBooleanMode.always);
	}
	/**
	 * Created a latch boolean with the specified mode.
	 * @param mode The specified mode.
	 */
	public LatchedBoolean(LatchedBooleanMode mode) {
		this.mode = mode;
	}
	
	//Main Getter Function
	/**
	 * 
	 * @param currentValue
	 * @return
	 */
	public boolean get(boolean currentValue) {
		if (currentValue != lastValue) {
			if (mode == LatchedBooleanMode.always)
				return true;
			if (mode == LatchedBooleanMode.falseToTrue && currentValue == true)
				return true;
			if (mode == LatchedBooleanMode.trueToFalse && currentValue == false)
				return true;
			lastValue = currentValue;
		}
		return false;
	}
}
