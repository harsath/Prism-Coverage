package prism;

public class AndExpression implements Expression {
	Expression left;
	Expression right;

	public AndExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return left.toString() + " and " + right.toString();
	}
}
