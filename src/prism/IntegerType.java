package prism;

public class IntegerType implements AtomType {
	private int value;
	
	public IntegerType(int value) {
		this.value = value;
	}

	public IntegerType() {

	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return Integer.toString(value);
	}
}
