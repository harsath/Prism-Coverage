package prism;

public class LessthanEqExpression implements Expression {
	Expression left;
	Expression right;

	public LessthanEqExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return left.toString() + " <= " + right.toString();
	}
}
