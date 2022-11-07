package prism;

public class GreaterthanEqExpression implements Expression {
	Expression left;
	Expression right;

	public GreaterthanEqExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return left.toString() + " >= " + right.toString();
	}
}
