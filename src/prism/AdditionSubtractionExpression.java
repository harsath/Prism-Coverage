package prism;

public class AdditionSubtractionExpression implements Expression {
	private Expression left;
	private Expression right;
	private boolean addition;
	private boolean executed = false;

	public AdditionSubtractionExpression(Expression left, Expression right, boolean addition) {
		this.left = left;
		this.right = right;
		this.addition = addition;
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
	
	public Expression getRight() {
		return right;
	}
	
	public void setLeft(Expression left) {
		this.left = left;
	}
	
	public void setRight(Expression right) {
		this.right = right;
	}
	
	public boolean isAddition() {
		return addition;
	}
	
	public void setIsAddition(boolean addition) {
		this.addition = addition;
	}

	@Override
	public String toString() {
		return left.toString() + (addition ? " + " : " - ") + right.toString();
	}
}
