// Generated from java-escape by ANTLR 4.11.1

package antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PrismParser}.
 */
public interface PrismListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code ProgramDecl}
	 * labeled alternative in {@link PrismParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProgramDecl(PrismParser.ProgramDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ProgramDecl}
	 * labeled alternative in {@link PrismParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProgramDecl(PrismParser.ProgramDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VariableDecl}
	 * labeled alternative in {@link PrismParser#variable_decl}.
	 * @param ctx the parse tree
	 */
	void enterVariableDecl(PrismParser.VariableDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VariableDecl}
	 * labeled alternative in {@link PrismParser#variable_decl}.
	 * @param ctx the parse tree
	 */
	void exitVariableDecl(PrismParser.VariableDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionDecl}
	 * labeled alternative in {@link PrismParser#function_decl}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDecl(PrismParser.FunctionDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionDecl}
	 * labeled alternative in {@link PrismParser#function_decl}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDecl(PrismParser.FunctionDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrismParser#function_body}.
	 * @param ctx the parse tree
	 */
	void enterFunction_body(PrismParser.Function_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrismParser#function_body}.
	 * @param ctx the parse tree
	 */
	void exitFunction_body(PrismParser.Function_bodyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ClassDecl}
	 * labeled alternative in {@link PrismParser#class_decl}.
	 * @param ctx the parse tree
	 */
	void enterClassDecl(PrismParser.ClassDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ClassDecl}
	 * labeled alternative in {@link PrismParser#class_decl}.
	 * @param ctx the parse tree
	 */
	void exitClassDecl(PrismParser.ClassDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ClassBodyDecl}
	 * labeled alternative in {@link PrismParser#class_body}.
	 * @param ctx the parse tree
	 */
	void enterClassBodyDecl(PrismParser.ClassBodyDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ClassBodyDecl}
	 * labeled alternative in {@link PrismParser#class_body}.
	 * @param ctx the parse tree
	 */
	void exitClassBodyDecl(PrismParser.ClassBodyDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrismParser#attributes_decl}.
	 * @param ctx the parse tree
	 */
	void enterAttributes_decl(PrismParser.Attributes_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrismParser#attributes_decl}.
	 * @param ctx the parse tree
	 */
	void exitAttributes_decl(PrismParser.Attributes_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrismParser#methods_decl}.
	 * @param ctx the parse tree
	 */
	void enterMethods_decl(PrismParser.Methods_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrismParser#methods_decl}.
	 * @param ctx the parse tree
	 */
	void exitMethods_decl(PrismParser.Methods_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrismParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(PrismParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrismParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(PrismParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrismParser#param_list}.
	 * @param ctx the parse tree
	 */
	void enterParam_list(PrismParser.Param_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrismParser#param_list}.
	 * @param ctx the parse tree
	 */
	void exitParam_list(PrismParser.Param_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrismParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(PrismParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrismParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(PrismParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StmtBlockStmt}
	 * labeled alternative in {@link PrismParser#stmt_block}.
	 * @param ctx the parse tree
	 */
	void enterStmtBlockStmt(PrismParser.StmtBlockStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StmtBlockStmt}
	 * labeled alternative in {@link PrismParser#stmt_block}.
	 * @param ctx the parse tree
	 */
	void exitStmtBlockStmt(PrismParser.StmtBlockStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BlockStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterBlockStmt(PrismParser.BlockStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BlockStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitBlockStmt(PrismParser.BlockStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VariableDeclStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclStmt(PrismParser.VariableDeclStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VariableDeclStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclStmt(PrismParser.VariableDeclStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfElseStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterIfElseStmt(PrismParser.IfElseStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfElseStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitIfElseStmt(PrismParser.IfElseStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ReturnStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(PrismParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ReturnStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(PrismParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignmentStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStmt(PrismParser.AssignmentStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignmentStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStmt(PrismParser.AssignmentStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(PrismParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprStmt}
	 * labeled alternative in {@link PrismParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(PrismParser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(PrismParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(PrismParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VariableAtomExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterVariableAtomExpr(PrismParser.VariableAtomExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VariableAtomExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitVariableAtomExpr(PrismParser.VariableAtomExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LessthanEqExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLessthanEqExpr(PrismParser.LessthanEqExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LessthanEqExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLessthanEqExpr(PrismParser.LessthanEqExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntAtomExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIntAtomExpr(PrismParser.IntAtomExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntAtomExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIntAtomExpr(PrismParser.IntAtomExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BoolAtomExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBoolAtomExpr(PrismParser.BoolAtomExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BoolAtomExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBoolAtomExpr(PrismParser.BoolAtomExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GreaterthanExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGreaterthanExpr(PrismParser.GreaterthanExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GreaterthanExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGreaterthanExpr(PrismParser.GreaterthanExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(PrismParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(PrismParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionCallExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpr(PrismParser.FunctionCallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionCallExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpr(PrismParser.FunctionCallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDivExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMulDivExpr(PrismParser.MulDivExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDivExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMulDivExpr(PrismParser.MulDivExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EqExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEqExpr(PrismParser.EqExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EqExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEqExpr(PrismParser.EqExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LessthanExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLessthanExpr(PrismParser.LessthanExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LessthanExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLessthanExpr(PrismParser.LessthanExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GreaterthanEqExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGreaterthanEqExpr(PrismParser.GreaterthanEqExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GreaterthanEqExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGreaterthanEqExpr(PrismParser.GreaterthanEqExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BracketExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBracketExpr(PrismParser.BracketExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BracketExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBracketExpr(PrismParser.BracketExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(PrismParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(PrismParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSubExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddSubExpr(PrismParser.AddSubExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSubExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddSubExpr(PrismParser.AddSubExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryMinusExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinusExpr(PrismParser.UnaryMinusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryMinusExpr}
	 * labeled alternative in {@link PrismParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinusExpr(PrismParser.UnaryMinusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionParamExpr}
	 * labeled alternative in {@link PrismParser#expr_list}.
	 * @param ctx the parse tree
	 */
	void enterFunctionParamExpr(PrismParser.FunctionParamExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionParamExpr}
	 * labeled alternative in {@link PrismParser#expr_list}.
	 * @param ctx the parse tree
	 */
	void exitFunctionParamExpr(PrismParser.FunctionParamExprContext ctx);
}