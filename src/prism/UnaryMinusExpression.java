package prism;

public class UnaryMinusExpression implements Expression {
	Expression expression;

	public UnaryMinusExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "-" + expression.toString();
	}
}
