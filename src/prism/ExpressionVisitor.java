package prism;

import antlr.*;
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
	public Expression visitObjectCreationExpr(ObjectCreationExprContext ctx) {
		return visit(ctx.getChild(0));
	}

	@Override
	public Expression visitObjectCreationExpression(ObjectCreationExpressionContext ctx) {
		String id = ctx.getChild(1).getText();
		Expression param_decl = visit(ctx.getChild(3));
		ObjectRHSExpression object_decl = new ObjectRHSExpression();
		object_decl.setParameterDeclaration((FunctionParamListExpression) param_decl);
		object_decl.setId(id);
		return object_decl;
	}

	@Override
	public Expression visitObjectInvocationExpr(ObjectInvocationExprContext ctx) {
		return visit(ctx.getChild(0));
	}

	@Override
	public Expression visitObjectInvocationExpression(ObjectInvocationExpressionContext ctx) {
		String id = ctx.getChild(0).getText();
		String member_id = ctx.getChild(2).getText();
		ObjectInvocationExpression object_invocation = new ObjectInvocationExpression();
		object_invocation.setId(id);
		object_invocation.setMember_Id(member_id);
		object_invocation.setIs_method(false);
		if (ctx.getChildCount() > 3) {
			object_invocation.setIs_method(true);
			if (ctx.getChildCount() == 6) {
				Expression param_decl = visit(ctx.getChild(4));
				object_invocation.setParam_decl((FunctionParamListExpression) param_decl);
			}
		}
		return object_invocation;
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
	public Expression visitNotEqExpr(NotEqExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		return new NotEqualExpression(left, right);
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
	public Expression visitStringAtomExpr(StringAtomExprContext ctx) {
		String value = ctx.getChild(0).getText().substring(1, ctx.getChild(0).getText().length() - 1);
		return new StringAtomExpression(value);
	}

	@Override
	public Expression visitBracketExpr(BracketExprContext ctx) {
		Expression e = visit(ctx.getChild(1));
		return new BracketExprExpression(e);
	}

	@Override
	public Expression visitPostfixAdditionExpr(PostfixAdditionExprContext ctx) {
		Expression e = visit(ctx.getChild(0));
		return new PostfixAdditionExpression(e);
	}

	@Override
	public Expression visitPostfixSubtractionExpr(PostfixSubtractionExprContext ctx) {
		Expression e = visit(ctx.getChild(0));
		return new PostfixSubtractionExpression(e);
	}

	@Override
	public Expression visitExitFunctionCallExpression(ExitFunctionCallExpressionContext ctx) {
		Expression expr = visit(ctx.getChild(2));
		return new ExitFunctionCallExpression(expr);
	}

	@Override
	public Expression visitMaxFunctionCallExpression(MaxFunctionCallExpressionContext ctx) {
		Expression left = visit(ctx.getChild(2));
		Expression right = visit(ctx.getChild(4));
		return new MaxFunctionCallExpression(left, right);
	}

	@Override
	public Expression visitMinFunctionCallExpression(MinFunctionCallExpressionContext ctx) {
		Expression left = visit(ctx.getChild(2));
		Expression right = visit(ctx.getChild(4));
		return new MinFunctionCallExpression(left, right);
	}

	@Override
	public Expression visitPowFunctionCallExpression(PowFunctionCallExpressionContext ctx) {
		Expression left = visit(ctx.getChild(2));
		Expression right = visit(ctx.getChild(4));
		return new PowFunctionCallExpression(left, right);

	}

	@Override
	public Expression visitPrintFunctionCallExpression(PrintFunctionCallExpressionContext ctx) {
		Expression expr = visit(ctx.getChild(2));
		return new PrintFunctionCallExpression(expr);
	}

	@Override
	public Expression visitArrayCreationExpr(ArrayCreationExprContext ctx) {
		return new ArrayCreationExpression(ctx.getChild(0).getChild(1).getText());
	}

	@Override
	public Expression visitArrayOperationExpr(ArrayOperationExprContext ctx) {
		return visit(ctx.getChild(0));
	}

	@Override
	public Expression visitArrayOperationExpression(ArrayOperationExpressionContext ctx) {
		String id = ctx.getChild(0).getText();
		String array_operation = ctx.getChild(2).getChild(0).getText();
		ArrayOperationExpression returner = new ArrayOperationExpression(id, array_operation);
		if (ctx.getChildCount() > 5) {
			returner.setExpression(visit(ctx.getChild(4)));
		}
		return returner;
	}

	@Override
	public Expression visitPrintlnFunctionCallExpression(PrintlnFunctionCallExpressionContext ctx) {
		Expression expr = visit(ctx.getChild(2));
		return new PrintlnFunctionCallExpression(expr);
	}

}
