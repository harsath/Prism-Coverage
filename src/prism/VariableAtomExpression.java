package prism;

public class VariableAtomExpression implements Expression {
	private String id;

	public VariableAtomExpression(String id) {
		this.id = id;
	}

	public VariableAtomExpression() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}
}
