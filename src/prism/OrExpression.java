package prism;

public class OrExpression implements Expression {
	Expression left;
	Expression right;

	public OrExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return left.toString() + " or " + right.toString();
	}
}
