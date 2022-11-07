package prism;

public class VariableAtomExpression implements Expression {
	String id;

	public VariableAtomExpression(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}
}
