package prism;

public class MultiplicationDivisionExpression implements Expression {
	Expression left;
	Expression right;
	boolean multiplication;

	public MultiplicationDivisionExpression(Expression left, Expression right, boolean multiplication) {
		this.left = left;
		this.right = right;
		this.multiplication = multiplication;
	}

	@Override
	public String toString() {
		return left.toString() + (multiplication ? " * " : " \\ ") + right.toString();
	}
}
