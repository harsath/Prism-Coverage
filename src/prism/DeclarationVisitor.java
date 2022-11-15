package prism;

import antlr.PrismBaseVisitor;
import antlr.PrismParser.ClassBodyDeclContext;
import antlr.PrismParser.ClassDeclContext;
import antlr.PrismParser.FunctionDeclContext;
import antlr.PrismParser.FunctionParamDeclContext;
import antlr.PrismParser.VariableDeclContext;

public class DeclarationVisitor extends PrismBaseVisitor<Declaration> {
	private StatementVisitor stmtVisitor = new StatementVisitor();
	private ExpressionVisitor exprVisitor = new ExpressionVisitor();

	private static AtomType strTypeToAtomType(String str) throws RuntimeException {
		switch (str) {
		case "INT":
			return new IntegerType();
		case "BOOL":
			return new BooleanType();
		case "STRING":
			return new StringType();
		case "VOID":
			return new VoidType();
		default:
			throw new RuntimeException("Invalid type as function return type.");
		}
	}

	@Override
	public Declaration visitVariableDecl(VariableDeclContext ctx) {
		String var_id = ctx.getChild(1).getText();
		Expression expr = exprVisitor.visit(ctx.getChild(3));
		VariableDeclaration var_decl_stmt = new VariableDeclaration(var_id, expr);
		var_decl_stmt.setExpression((Expression) expr);
		return var_decl_stmt;
	}

	@Override
	public Declaration visitFunctionParamDecl(FunctionParamDeclContext ctx) {
		ParameterListDeclaration fn_param_list_decl = new ParameterListDeclaration();
		// Interleave visits, because we don't want to visit ',' token.
		for (int i = 0; i < ctx.getChildCount(); i += 2) {
			String type_str = ctx.getChild(i).getChild(0).getText();
			String type_id = ctx.getChild(i).getChild(1).getText();
			ParameterDeclaration param = new ParameterDeclaration(type_id, strTypeToAtomType(type_str));
			fn_param_list_decl.addParam(param);
		}
		return fn_param_list_decl;
	}

	@Override
	public Declaration visitFunctionDecl(FunctionDeclContext ctx) throws RuntimeException {
		FunctionDeclaration fn_decl = new FunctionDeclaration();
		String fn_id = ctx.getChild(2).getText();
		String fn_type_type_str = ctx.getChild(1).getChild(0).getText();
		AtomType fn_return_type = strTypeToAtomType(fn_type_type_str);
		fn_decl.setFunctionName(fn_id);
		fn_decl.setReturnType(fn_return_type);
		Statement stmt_body;
		// Function declaration has parameters
		if (ctx.getChildCount() == 7) {
			Declaration fn_param_list = visit(ctx.getChild(4));
			fn_decl.setFunctionParamDecl((ParameterListDeclaration) fn_param_list);
			stmt_body = stmtVisitor.visit(ctx.getChild(6).getChild(0));
		} else {
			stmt_body = stmtVisitor.visit(ctx.getChild(5).getChild(0));
		}
		fn_decl.setFunctionBody((BlockStatement) stmt_body);
		return fn_decl;
	}

	@Override
	public Declaration visitClassDecl(ClassDeclContext ctx) {
		ClassDeclaration class_decl = new ClassDeclaration();
		class_decl.setClassName(ctx.getChild(1).getText());
		Declaration class_body_decl = visit(ctx.getChild(3));
		class_decl.setClassBody((ClassBodyDeclaration) class_body_decl);
		return class_decl;
	}

	@Override
	public Declaration visitClassBodyDecl(ClassBodyDeclContext ctx) {
		ClassBodyDeclaration class_body_decl = new ClassBodyDeclaration();
		for (int i = 0; i < ctx.getChild(1).getChildCount(); i++) {
			Declaration attribute_decl = visit(ctx.getChild(1).getChild(i));
			class_body_decl.addAttribute((VariableDeclaration) attribute_decl);
		}
		for (int i = 0; i < ctx.getChild(3).getChildCount(); i++) {
			Declaration method_decl = visit(ctx.getChild(3).getChild(i));
			class_body_decl.addMethod((FunctionDeclaration) method_decl);
		}
		return class_body_decl;
	}
}
