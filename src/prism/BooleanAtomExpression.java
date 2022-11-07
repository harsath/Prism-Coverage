package prism;

public class BooleanAtomExpression implements Expression {
	boolean value;

	public BooleanAtomExpression(boolean value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}
}
