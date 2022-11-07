package prism;

public class GreaterthanExpression implements Expression {
	Expression left;
	Expression right;

	public GreaterthanExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return left.toString() + " > " + right.toString();
	}
}
