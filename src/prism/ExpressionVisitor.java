package prism;

import antlr.PrismBaseVisitor;
import antlr.PrismParser.AddSubExprContext;
import antlr.PrismParser.AndExprContext;
import antlr.PrismParser.BoolAtomExprContext;
import antlr.PrismParser.BracketExprContext;
import antlr.PrismParser.EqExprContext;
import antlr.PrismParser.FunctionCallExprContext;
import antlr.PrismParser.GreatherthanEqExprContext;
import antlr.PrismParser.GreatherthanExprContext;
import antlr.PrismParser.IntAtomExprContext;
import antlr.PrismParser.LessthanEqExprContext;
import antlr.PrismParser.LessthanExprContext;
import antlr.PrismParser.MulDivExprContext;
import antlr.PrismParser.NotExprContext;
import antlr.PrismParser.OrExprContext;
import antlr.PrismParser.UnaryMinusExprContext;
import antlr.PrismParser.VariableAtomExprContext;

public class ExpressionVisitor extends PrismBaseVisitor<Expression> {

	@Override
	public Expression visitAndExpr(AndExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		return new AndExpression(left, right);
	}

	@Override
	public Expression visitVariableAtomExpr(VariableAtomExprContext ctx) {
		String id = ctx.getChild(0).getText();
		return new VariableAtomExpression(id);
	}

	@Override
	public Expression visitLessthanEqExpr(LessthanEqExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		return new LessthanEqExpression(left, right);
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
	public Expression visitOrExpr(OrExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		return new OrExpression(left, right);
	}

	@Override
	public Expression visitGreatherthanExpr(GreatherthanExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		return new GreaterthanExpression(left, right);
	}

	@Override
	public Expression visitFunctionCallExpr(FunctionCallExprContext ctx) {
		// TODO Auto-generated method stub
		return super.visitFunctionCallExpr(ctx);
	}

	@Override
	public Expression visitMulDivExpr(MulDivExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		boolean multiplication = ctx.getChild(1).getText() == "*";
		return new MultiplicationDivisionExpression(left, right, multiplication);
	}

	@Override
	public Expression visitEqExpr(EqExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		return new EqualityExpression(left, right);
	}

	@Override
	public Expression visitLessthanExpr(LessthanExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		return new LessthanExpression(left, right);
	}

	@Override
	public Expression visitGreatherthanEqExpr(GreatherthanEqExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		return new GreaterthanEqExpression(left, right);
	}

	@Override
	public Expression visitBracketExpr(BracketExprContext ctx) {
		Expression e = visit(ctx.getChild(1));
		return new BracketExprExpression(e);
	}

	@Override
	public Expression visitNotExpr(NotExprContext ctx) {
		Expression e = visit(ctx.getChild(1));
		return new NotExpression(e);
	}

	@Override
	public Expression visitAddSubExpr(AddSubExprContext ctx) {
		Expression left = visit(ctx.getChild(0));
		Expression right = visit(ctx.getChild(2));
		boolean addition = ctx.getChild(1).getText() == "+";
		return new AdditionSubtractionExpression(left, right, addition);
	}

	@Override
	public Expression visitUnaryMinusExpr(UnaryMinusExprContext ctx) {
		Expression e = visit(ctx.getChild(1));
		return new UnaryMinusExpression(e);
	}

}
