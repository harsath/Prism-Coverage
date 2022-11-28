package prism;

import java.io.Serializable;

public class IntegerType implements AtomType, Serializable {
	private int value;
	private boolean isInitialized;

	public IntegerType(int value) {
		this.value = value;
		this.isInitialized = true;
	}

	public IntegerType() {
		this.isInitialized = false;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.isInitialized = true;
		this.value = value;
	}

	@Override
	public String toString() {
		if (isInitialized) {
			return Integer.toString(value);
		} else {
			return "INT";
		}
	}
}
