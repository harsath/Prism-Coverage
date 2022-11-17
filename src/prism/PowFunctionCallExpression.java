package prism;

public class PowFunctionCallExpression implements Expression {
	private Expression left;
	private Expression right;
	
	public PowFunctionCallExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
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
		return "POW(" + left + ", " + right + ")";
	}
}
