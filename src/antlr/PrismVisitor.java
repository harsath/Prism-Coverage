// Generated from java-escape by ANTLR 4.11.1

package antlr;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PrismParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PrismVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PrismParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(PrismParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VariableDecl}
	 * labeled alternative in {@link PrismParser#variable_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDecl(PrismParser.VariableDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionDecl}
	 * labeled alternative in {@link PrismParser#function_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDecl(PrismParser.FunctionDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ClassDecl}
	 * labeled alternative in {@link PrismParser#class_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDecl(PrismParser.ClassDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ClassBodyDecl}
	 * labeled alternative in {@link PrismParser#class_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBodyDecl(PrismParser.ClassBodyDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrismParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(PrismParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParamList}
	 * labeled alternative in {@link PrismParser#param_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(PrismParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrismParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(PrismParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrismParser#stmt_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt_block(PrismParser.Stmt_blockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BlockStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStmt(PrismParser.BlockStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VariableDeclStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclStmt(PrismParser.VariableDeclStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfElseStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfElseStmt(PrismParser.IfElseStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ReturnStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(PrismParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignmentStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStmt(PrismParser.AssignmentStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(PrismParser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(PrismParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VariableAtomExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableAtomExpr(PrismParser.VariableAtomExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LessthanEqExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessthanEqExpr(PrismParser.LessthanEqExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntAtomExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntAtomExpr(PrismParser.IntAtomExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolAtomExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolAtomExpr(PrismParser.BoolAtomExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(PrismParser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GreatherthanExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreatherthanExpr(PrismParser.GreatherthanExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionCallExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCallExpr(PrismParser.FunctionCallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulDivExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivExpr(PrismParser.MulDivExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqExpr(PrismParser.EqExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LessthanExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessthanExpr(PrismParser.LessthanExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GreatherthanEqExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreatherthanEqExpr(PrismParser.GreatherthanEqExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BracketExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketExpr(PrismParser.BracketExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(PrismParser.NotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSubExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSubExpr(PrismParser.AddSubExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryMinusExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryMinusExpr(PrismParser.UnaryMinusExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrismParser#expr_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_list(PrismParser.Expr_listContext ctx);
}