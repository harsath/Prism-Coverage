package prism;

public class BooleanType implements AtomType {
	private boolean value;
	
	public BooleanType(boolean value) {
		this.value = value;
	}
	
	public boolean getValue() {
		return this.value;
	}
	
	public void setValue(boolean value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return Boolean.toString(value);
	}
	
}
