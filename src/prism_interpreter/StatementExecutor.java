package prism_interpreter;

import java.util.*;

import prism.*;

public class StatementExecutor {
	private Map<String, FunctionDeclaration> functionDeclarationSymbolTable;
	private Pair<Map<String, Map<String, FunctionDeclaration>>, Map<String, Map<String, AtomType>>> classSymbolTable;
	private ExpressionExecutor expressionExecutor;

	// Execution of functions with parameters. Parameters will be passed in as a
	// pre-defined scope
	// that is readily available for statement execution. Global variables are also
	// passed passed this mannar
	// since our grammar supports variable declaration in `Declaration` block.
	public StatementExecutor(Map<String, FunctionDeclaration> functionDeclarationSymbolTable,
			Pair<Map<String, Map<String, FunctionDeclaration>>, Map<String, Map<String, AtomType>>> classSymbolTable) {
		this.functionDeclarationSymbolTable = functionDeclarationSymbolTable;
		this.classSymbolTable = classSymbolTable;
		this.expressionExecutor = new ExpressionExecutor(this.functionDeclarationSymbolTable,
				this.classSymbolTable);
	}

	// Execution functions returns a value, we return that as AtomType
	// `AtomType` is actual value of `executeStatements()`, and `Boolean` says if we
	// received `AtomType` as a result of a `RETURN` statement or not.
	public Pair<AtomType, Boolean> executeStatements(Map<String, AtomType> globalIdentifiers,
			Map<String, AtomType> scopeIdentifiers, List<Statement> statements) throws Exception {
		// First
		Pair<AtomType, Boolean> returner = new Pair<AtomType, Boolean>(new VoidType(), false);
		for (Statement statement : statements) {
			if (returner.b) {
				return returner;
			}
			if (statement instanceof VariableDeclarationStatement) {
				variableDeclarationStatementHandler(statement, globalIdentifiers, scopeIdentifiers);
			} else if (statement instanceof AssignmentStatement) {
				assignmentStatementHandler(statement, globalIdentifiers, scopeIdentifiers);
			} else if (statement instanceof ReturnStatement) {
				returnStatementHandler(statement, globalIdentifiers, scopeIdentifiers, returner);
				break;
			} else if (statement instanceof IfElseStatement) {
				ifElseStatementHandler(statement, globalIdentifiers, scopeIdentifiers, returner);
				if (returner.b) {
					return returner;
				}
			} else if (statement instanceof BlockStatement) {
				blockStatementHandler(statement, globalIdentifiers, scopeIdentifiers);
			} else if (statement instanceof ExpressionStatement) {
				expressionStatementHandler(statement, globalIdentifiers, scopeIdentifiers);
			} else if (statement instanceof ForLoopStatement) {
				forLoopStatementHandler(statement, globalIdentifiers, scopeIdentifiers, returner);
				if (returner.b) {
					return returner;
				}
			} else if (statement instanceof WhileLoopStatement) {
				statement.setIsExecuted(true);
				whileLoopStatementHandler(statement, globalIdentifiers, scopeIdentifiers, returner);
				if (returner.b) {
					return returner;
				}
			} else {
				throw new RuntimeException("Undefined statement");
			}
		}
		return returner;
	}

	public void setIdentifier(String id, AtomType value, Map<String, AtomType> globalIdentifiers,
			Map<String, AtomType> scopeIdentifiers) throws Exception {
		if (scopeIdentifiers.containsKey(id)) {
			scopeIdentifiers.put(id, value);
		} else if (globalIdentifiers.containsKey(id)) {
			globalIdentifiers.put(id, value);
		} else {
			throw new RuntimeException("Assignment to undefined variable");
		}
	}

	private void variableDeclarationStatementHandler(Statement statement, Map<String, AtomType> globalIdentifiers,
			Map<String, AtomType> scopeIdentifiers) throws Exception {
		VariableDeclarationStatement var_decl_stmt = (VariableDeclarationStatement) statement;
		var_decl_stmt.setIsExecuted(true);
		if (globalIdentifiers.containsKey(var_decl_stmt.getId())
				|| scopeIdentifiers.containsKey(var_decl_stmt.getId())) {
			throw new RuntimeException("Variable already declared");
		}
		Expression expr = expressionExecutor.executeExpression(globalIdentifiers, scopeIdentifiers,
				var_decl_stmt.getExpression());
		scopeIdentifiers.put(var_decl_stmt.getId(), ExecutorHelpers.getAtomTypeFromAtomExpression(expr,
				"Invalid Expression type in variable declaration statement"));
	}

	private void assignmentStatementHandler(Statement statement, Map<String, AtomType> globalIdentifiers,
			Map<String, AtomType> scopeIdentifiers) throws Exception {
		AssignmentStatement assign_stmt = (AssignmentStatement) statement;
		assign_stmt.setIsExecuted(true);
		if (!(assign_stmt.getLhs() instanceof VariableAtomExpression)) {
			throw new RuntimeException("LHS of an assignment statement must be a variable");
		}
		VariableAtomExpression lhs = (VariableAtomExpression) assign_stmt.getLhs();
		Expression rhs = expressionExecutor.executeExpression(globalIdentifiers, scopeIdentifiers,
				assign_stmt.getRhs());
		if (rhs instanceof IntegerAtomExpression) {
			setIdentifier(lhs.getId(), new IntegerType(((IntegerAtomExpression) rhs).getValue()),
					globalIdentifiers, scopeIdentifiers);
		} else if (rhs instanceof BooleanAtomExpression) {
			setIdentifier(lhs.getId(), new BooleanType(((BooleanAtomExpression) rhs).getValue()),
					globalIdentifiers, scopeIdentifiers);
		} else if (rhs instanceof StringAtomExpression) {
			setIdentifier(lhs.getId(), new StringType(((StringAtomExpression) rhs).getValue()),
					globalIdentifiers, scopeIdentifiers);
		} else {
			throw new RuntimeException("Undefined type variable assignment");
		}
	}

	private void returnStatementHandler(Statement statement, Map<String, AtomType> globalIdentifiers,
			Map<String, AtomType> scopeIdentifiers, Pair<AtomType, Boolean> returner) throws Exception {
		ReturnStatement return_stmt = (ReturnStatement) statement;
		return_stmt.setIsExecuted(true);
		if (return_stmt.getExpression() == null) {
			returner.a = new VoidType();
			returner.b = true;
			return;
		}
		Expression expression = expressionExecutor.executeExpression(globalIdentifiers, scopeIdentifiers,
				return_stmt.getExpression());
		returner.a = ExecutorHelpers.getAtomTypeFromAtomExpression(expression,
				"Invalid expression type in return statement");
		returner.b = true;
	}

	private void ifElseStatementHandler(Statement statement, Map<String, AtomType> globalIdentifiers,
			Map<String, AtomType> scopeIdentifiers, Pair<AtomType, Boolean> returner) throws Exception {
		IfElseStatement if_else_stmt = (IfElseStatement) statement;
		if_else_stmt.setIsExecuted(true);
		Expression expr_condition = expressionExecutor.executeExpression(globalIdentifiers, scopeIdentifiers,
				if_else_stmt.getExpr_condition());
		if (!(expr_condition instanceof BooleanAtomExpression)) {
			throw new RuntimeException(
					"Expression condition of If-Else statement should be a logical expression");
		}
		if (!(if_else_stmt.getIf_statement_block() instanceof BlockStatement)) {
			throw new RuntimeException("If statement must be enclosed within {}");
		}
		if ((if_else_stmt.getElse_statement_block() != null)
				&& !(if_else_stmt.getElse_statement_block() instanceof BlockStatement)) {
			throw new RuntimeException("Else statement must be enclosed within {}");
		}
		BooleanType expr_condition_cast = (BooleanType) expr_condition;
		if (expr_condition_cast.getValue()) {
			BlockStatement block_stmt = (BlockStatement) if_else_stmt.getIf_statement_block();
			block_stmt.setIsExecuted(true);
			StatementExecutor stmt_exec = new StatementExecutor(functionDeclarationSymbolTable,
					classSymbolTable);
			Pair<AtomType, Boolean> stmt_return = stmt_exec.executeStatements(globalIdentifiers,
					scopeIdentifiers, block_stmt.getStatements());
			returner.a = stmt_return.a;
			returner.b = stmt_return.b;
		} else if (if_else_stmt.getElse_statement_block() != null) {
			BlockStatement block_stmt = (BlockStatement) if_else_stmt.getElse_statement_block();
			block_stmt.setIsExecuted(true);
			StatementExecutor stmt_exec = new StatementExecutor(functionDeclarationSymbolTable,
					classSymbolTable);
			Pair<AtomType, Boolean> stmt_return = stmt_exec.executeStatements(globalIdentifiers,
					scopeIdentifiers, block_stmt.getStatements());
			returner.a = stmt_return.a;
			returner.b = stmt_return.b;
		} else {
			return;
		}
	}

	private void blockStatementHandler(Statement statement, Map<String, AtomType> globalIdentifers,
			Map<String, AtomType> scopeIdentifiers) throws Exception {
		BlockStatement block_stmt = (BlockStatement) statement;
		block_stmt.setIsExecuted(true);
		StatementExecutor stmt_exec = new StatementExecutor(functionDeclarationSymbolTable, classSymbolTable);
		stmt_exec.executeStatements(globalIdentifers, scopeIdentifiers, block_stmt.getStatements());
	}

	private void expressionStatementHandler(Statement statement, Map<String, AtomType> globalIdentifiers,
			Map<String, AtomType> scopeIdentifiers) throws Exception {
		ExpressionStatement expr_stmt = (ExpressionStatement) statement;
		expr_stmt.setIsExecuted(true);
		expressionExecutor.executeExpression(globalIdentifiers, scopeIdentifiers, expr_stmt.getExpression());
	}

	private void forLoopStatementHandler(Statement statement, Map<String, AtomType> globalIdentifiers,
			Map<String, AtomType> scopeIdentifiers, Pair<AtomType, Boolean> returner) throws Exception {
		ForLoopStatement for_stmt = (ForLoopStatement) statement;
		VariableDeclarationStatement for_decl_stmt = for_stmt.getInitBlock();
		Expression for_condition_expr = for_stmt.getConditionalBlock();
		AssignmentStatement for_updation_stmt = for_stmt.getUpdationBlock();
		List<Statement> for_block_stmt = for_stmt.getStatementBlock().getStatements();

		variableDeclarationStatementHandler(for_decl_stmt, globalIdentifiers, scopeIdentifiers);
		Expression for_condition_expr_exec = expressionExecutor.executeExpression(globalIdentifiers,
				scopeIdentifiers, for_condition_expr);
		if (!(for_condition_expr_exec instanceof BooleanAtomExpression)) {
			throw new RuntimeException(
					"Updation block of FOR loop must be a logical expression or relational expression that returns a boolean");
		}
		BooleanAtomExpression for_condition_expr_exec_cast = (BooleanAtomExpression) for_condition_expr_exec;
		StatementExecutor stmt_executor = new StatementExecutor(functionDeclarationSymbolTable,
				classSymbolTable);
		if (for_condition_expr_exec_cast.getValue()) {
			for_stmt.getStatementBlock().setIsExecuted(true);
		}
		while (for_condition_expr_exec_cast.getValue()) {
			Pair<AtomType, Boolean> ret = stmt_executor.executeStatements(globalIdentifiers,
					scopeIdentifiers, for_block_stmt);
			if (ret.b) {
				returner.a = ret.a;
				returner.b = ret.b;
				return;
			}
			assignmentStatementHandler(for_updation_stmt, globalIdentifiers, scopeIdentifiers);
			for_condition_expr_exec_cast = (BooleanAtomExpression) expressionExecutor
					.executeExpression(globalIdentifiers, scopeIdentifiers, for_condition_expr);
		}
	}

	private void whileLoopStatementHandler(Statement statement, Map<String, AtomType> globalIdentifiers,
			Map<String, AtomType> scopeIdentifiers, Pair<AtomType, Boolean> returner) throws Exception {
		WhileLoopStatement while_stmt = (WhileLoopStatement) statement;
		while_stmt.setIsExecuted(true);
		Expression while_expr = while_stmt.getExpressionBlock();
		List<Statement> while_block_stmt = while_stmt.getStatementBlock().getStatements();
		Expression while_condition_expr = expressionExecutor.executeExpression(globalIdentifiers,
				scopeIdentifiers, while_expr);
		if (!(while_condition_expr instanceof BooleanAtomExpression)) {
			throw new RuntimeException(
					"Condition block of WHILE loop must be a logical expression or relational expression that returns a boolean");
		}
		BooleanAtomExpression while_condition_expr_cast = (BooleanAtomExpression) while_condition_expr;
		StatementExecutor stmt_executor = new StatementExecutor(functionDeclarationSymbolTable,
				classSymbolTable);
		if (while_condition_expr_cast.getValue()) {
			while_stmt.getStatementBlock().setIsExecuted(true);
		}
		while (while_condition_expr_cast.getValue()) {
			Pair<AtomType, Boolean> ret = stmt_executor.executeStatements(globalIdentifiers,
					scopeIdentifiers, while_block_stmt);
			while_condition_expr_cast = (BooleanAtomExpression) expressionExecutor
					.executeExpression(globalIdentifiers, scopeIdentifiers, while_expr);
			if (ret.b) {
				returner.a = ret.a;
				returner.b = ret.b;
				return;
			}
		}
	}
}
