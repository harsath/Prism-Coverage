package prism;

public class BooleanAtomExpression extends BooleanType implements Expression {
	private boolean executed = false;

	public BooleanAtomExpression(boolean value) {
		super(value);
	}

	@Override
	public void setIsExecuted(boolean value) {
		this.executed = value;
	}

	@Override
	public boolean getIsExecuted() {
		return this.executed;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
