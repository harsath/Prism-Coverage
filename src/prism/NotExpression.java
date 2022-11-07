package prism;

public class NotExpression implements Expression {
	Expression expression;

	public NotExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		return expression.toString();
	}

}
