package prism;

public class PrintlnFunctionCallExpression implements Expression { 
	private Expression expr;

	public PrintlnFunctionCallExpression(Expression expr) {
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
		return "PRINTLN(" + expr.toString() + ");";
	}
	
	
}
