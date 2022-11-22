package prism;

public class AndExpression implements Expression {
	private Expression left;
	private Expression right;
	private boolean executed = false;

	public AndExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean getIsExecuted() {
		return this.executed;
	}

	@Override
	public void setIsExecuted(boolean value) {
		this.executed = value;
	}

	public Expression getLeft() {
		return left;
	}

	public void setLeft(Expression left) {
		this.left = left;
	}

	public Expression getRight() {
		return right;
	}

	public void setRight(Expression right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return left.toString() + " and " + right.toString();
	}
}
