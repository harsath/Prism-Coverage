package prism;

import antlr.PrismBaseVisitor;
import antlr.PrismParser;
import antlr.PrismParser.*;

public class ExpressionVisitor extends PrismBaseVisitor<Expression> {

	@Override
	public Expression visitFunctionParamExpr(FunctionParamExprContext ctx) {
		FunctionParamListExpression fn_param_list_expr = new FunctionParamListExpression();	
		// Interleave visits, because we don't want to visit ',' token.
		for (int i = 0; i < ctx.getChildCount(); i += 2) {
			fn_param_list_expr.addExpression(visit(ctx.getChild(i)));
		}
		return fn_param_list_expr;
	}

	@Override
	public Expression visitFunctionCallExpr(FunctionCallExprContext ctx) {
		FunctionCallExpression fn_call_expr = new FunctionCallExpression();
		fn_call_expr.setFunctionName(ctx.getChild(0).getText());
		// Invoking a function without parameter. Ex: fn()
		if (ctx.getChildCount() == 3) {
			return fn_call_expr;
		} 
		// Invoking function with parameters. Ex: fn(4), fn(foo, bar, 3)
		else {
			Expression fn_param_expr = visit(ctx.getChild(2));
			
			fn_call_expr.setFunctionParamList((FunctionParamListExpression) fn_param_expr);
		}

		return fn_call_expr;
	}

	@Override
	public Expression visitUnaryMinusExpr(UnaryMinusExprContext ctx) {
		Expression e = visit(ctx.getChild(1));
		return new UnaryMinusExpression(e);
	}

	@Override
	public Expression visitNotExpr(NotExprContext ctx) {
		Expression e = visit(ctx.getChild(1));
		return new NotExpression(e);
	}

	@Override
	public Expression visitMulDivExpr(MulDivExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		boolean multiplication = (ctx.op.getType() == PrismParser.MUL);
		return new MultiplicationDivisionExpression(left, right, multiplication);
	}

	@Override
	public Expression visitAddSubExpr(AddSubExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		boolean addition = (ctx.op.getType() == PrismParser.ADD);
		return new AdditionSubtractionExpression(left, right, addition);
	}

	@Override
	public Expression visitEqExpr(EqExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		return new EqualityExpression(left, right);
	}

	@Override
	public Expression visitOrExpr(OrExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		return new OrExpression(left, right);
	}

	@Override
	public Expression visitAndExpr(AndExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		return new AndExpression(left, right);
	}

	@Override
	public Expression visitGreaterthanExpr(GreaterthanExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		return new GreaterthanExpression(left, right);
	}

	@Override
	public Expression visitLessthanExpr(LessthanExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		return new LessthanExpression(left, right);
	}

	@Override
	public Expression visitGreaterthanEqExpr(GreaterthanEqExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		return new GreaterthanEqExpression(left, right);
	}

	@Override
	public Expression visitLessthanEqExpr(LessthanEqExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		return new LessthanEqExpression(left, right);
	}

	@Override
	public Expression visitVariableAtomExpr(VariableAtomExprContext ctx) {
		String id = ctx.getChild(0).getText();
		return new VariableAtomExpression(id);
	}

	@Override
	public Expression visitIntAtomExpr(IntAtomExprContext ctx) {
		String valueText = ctx.getChild(0).getText();
		int value = Integer.parseInt(valueText);
		return new IntegerAtomExpression(value);
	}

	@Override
	public Expression visitBoolAtomExpr(BoolAtomExprContext ctx) {
		String valueText = ctx.getChild(0).getText();
		boolean value = Boolean.parseBoolean(valueText);
		return new BooleanAtomExpression(value);
	}

	@Override
	public Expression visitBracketExpr(BracketExprContext ctx) {
		Expression e = visit(ctx.getChild(1));
		return new BracketExprExpression(e);
	}

	@Override
	public Expression visitMaxFunctionCallExpression(MaxFunctionCallExpressionContext ctx) {
		Expression left = visit(ctx.getChild(2));
		Expression right = visit(ctx.getChild(4));		
		return new MaxFunctionCallExpression(left,right);
	}

	@Override
	public Expression visitMinFunctionCallExpression(MinFunctionCallExpressionContext ctx) {
		Expression left = visit(ctx.getChild(2));
		Expression right = visit(ctx.getChild(4));		
		return new MinFunctionCallExpression(left,right);
	}

	@Override
	public Expression visitPowFunctionCallExpression(PowFunctionCallExpressionContext ctx) {
		Expression left = visit(ctx.getChild(2));
		Expression right = visit(ctx.getChild(4));		
		return new PowFunctionCallExpression(left,right);
		
	}
	
}
