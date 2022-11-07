package prism;

public class LessthanExpression implements Expression {
	Expression left;
	Expression right;

	public LessthanExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return left.toString() + " <  " + right.toString();
	}
}
