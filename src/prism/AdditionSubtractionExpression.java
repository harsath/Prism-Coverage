package prism;

public class AdditionSubtractionExpression implements Expression {
	Expression left;
	Expression right;
	boolean addition;

	public AdditionSubtractionExpression(Expression left, Expression right, boolean addition) {
		this.left = left;
		this.right = right;
		this.addition = addition;
	}

	@Override
	public String toString() {
		return left.toString() + (addition ? " + " : " - ") + right.toString();
	}
}
