package prism;

public class EqualityExpression implements Expression {
	private Expression left;
	private Expression right;
	private boolean executed = false;

	public EqualityExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
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
		return this.left;
	}

	public void setLeft(Expression left) {
		this.left = left;
	}

	public Expression getRight() {
		return this.right;
	}

	public void setRight(Expression right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return left.toString() + " == " + right.toString();
	}
}
