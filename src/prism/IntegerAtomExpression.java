package prism;

public class IntegerAtomExpression implements Expression {
	int value;

	public IntegerAtomExpression(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}
}
