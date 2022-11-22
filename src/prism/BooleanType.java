package prism;

public class BooleanType implements AtomType {
	private boolean value;
	private boolean isInitialized;

	public BooleanType(boolean value) {
		this.isInitialized = true;
		this.value = value;
	}

	public BooleanType() {
		this.isInitialized = false;
	}

	public boolean getValue() {
		return this.value;
	}

	public void setValue(boolean value) {
		this.isInitialized = true;
		this.value = value;
	}

	@Override
	public String toString() {
		if (isInitialized) {
			return Boolean.toString(value);
		} else {
			return "BOOL";
		}
	}

}
