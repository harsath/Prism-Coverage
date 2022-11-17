package prism;

public class PrintFunctionCallExpression implements Expression {
	private Expression expr;

	public PrintFunctionCallExpression(Expression expr) {
		this.expr = expr;
	}

	public Expression getExpr() {
		return expr;
	}

	public void setExpr(Expression expr) {
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "PRINT(" + expr.toString() + ");";
	}
}
