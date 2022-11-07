package prism;

public class EqualityExpression implements Expression {
	Expression left;
	Expression right;

	public EqualityExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return left.toString() + " == " + right.toString();
	}
}
