package prism;

public class MultiplicationDivisionExpression implements Expression {
	private Expression left;
	private Expression right;
	private boolean multiplication;
	private boolean executed = false;

	public MultiplicationDivisionExpression(Expression left, Expression right, boolean multiplication) {
		this.left = left;
		this.right = right;
		this.multiplication = multiplication;
	}

	@Override
        public void setIsExecuted(boolean value) {
                this.executed = value;
        }

        @Override
        public boolean getIsExecuted() {
                return this.executed;
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

	public boolean isMultiplication() {
		return multiplication;
	}

	public void setIsMultiplication(boolean multiplication) {
		this.multiplication = multiplication;
	}

	@Override
	public String toString() {
		return left.toString() + (multiplication ? " * " : " / ") + right.toString();
	}
}
