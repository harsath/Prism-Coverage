package prism_interpreter;

import java.util.*;

import prism.*;

public class StatementExecutor {
        private List<Statement> statements;
        private Map<String, AtomType> scopeIdentifiers;
        private Map<String, AtomType> globalIdentifiers;
        private Map<String, FunctionDeclaration> functionDeclarationSymbolTable;
        private ExpressionExecutor expressionExecutor;

        // Execution of functions with parameters. Parameters will be passed in as a pre-defined scope
        // that is readily available for statement execution. Global variables are also passed passed this mannar
        // since our grammar supports variable declaration in `Declaration` block.
        public StatementExecutor(Map<String, AtomType> globalIdentifiers, Map<String, FunctionDeclaration> functionDeclarationSymbolTable, Map<String, AtomType> scopeIdentifiers, List<Statement> statements) {
                this.statements = statements;
                this.scopeIdentifiers = scopeIdentifiers;
                this.globalIdentifiers = globalIdentifiers;
                this.functionDeclarationSymbolTable = functionDeclarationSymbolTable;
                this.expressionExecutor = new ExpressionExecutor(this.functionDeclarationSymbolTable);
        }

        // Execution functions returns a value, we return that as AtomType
        public AtomType executeStatements() throws Exception {
                AtomType return_value = new VoidType();
                for (Statement statement : statements) {
                        if (statement instanceof VariableDeclarationStatement) {
                                variableDeclarationStatementHandler(statement);
                        } else if (statement instanceof AssignmentStatement) {
                                assignmentStatementHandler(statement);
                        } else if (statement instanceof ReturnStatement) {
                                return_value = returnStatementHandler(statement);
                                break;
                        } else if (statement instanceof IfElseStatement) {
                                return_value = ifElseStatementHandler(statement);
                        } else if (statement instanceof BlockStatement) {
                                blockStatementHandler(statement);
                        } else if (statement instanceof ExpressionStatement) {
                                expressionStatementHandler(statement);
                        } else {
                                throw new RuntimeException("Undefined statement");
                        }
                }
                return return_value;
        }

        public void setIdentifier(String id, AtomType value) throws Exception {
                if (scopeIdentifiers.containsKey(id)) {
                        scopeIdentifiers.put(id, value);
                } else if (globalIdentifiers.containsKey(id)) {
                        globalIdentifiers.put(id, value);
                } else {
                        throw new RuntimeException("Assignment to undefined variable");
                }
        }

        public AtomType getAtomTypeFromExpression(Expression expression) throws Exception {
                if (expression instanceof IntegerAtomExpression) {
                        return new IntegerType(((IntegerAtomExpression) expression).getValue());
                } else if (expression instanceof BooleanAtomExpression) {
                        return new BooleanType(((BooleanAtomExpression) expression).getValue());
                } else {
                        throw new RuntimeException("Undefined type in get atom from expession");
                }
        }

        private void variableDeclarationStatementHandler(Statement statement) throws Exception {
                VariableDeclarationStatement var_decl_stmt = (VariableDeclarationStatement) statement;
                if (globalIdentifiers.containsKey(var_decl_stmt.getId()) || scopeIdentifiers.containsKey(var_decl_stmt.getId())) {
                        throw new RuntimeException("Variable already declared");
                }
                Expression expr = expressionExecutor.executeExpression(globalIdentifiers, scopeIdentifiers, var_decl_stmt.getExpression());
                if (expr instanceof IntegerAtomExpression) {
                        scopeIdentifiers.put(var_decl_stmt.getId(), new IntegerType(((IntegerAtomExpression) expr).getValue()));
                } else if (expr instanceof BooleanAtomExpression) {
                        scopeIdentifiers.put(var_decl_stmt.getId(), new BooleanType(((BooleanAtomExpression) expr).getValue()));
                } else {
                        throw new RuntimeException("Unsupported type in variable declaration");
                }
        }

        private void assignmentStatementHandler(Statement statement) throws Exception {
                AssignmentStatement assign_stmt = (AssignmentStatement) statement;
                if (!(assign_stmt.getLhs() instanceof VariableAtomExpression)) {
                        throw new RuntimeException("LHS of an assignment statement must be a variable");
                }
                VariableAtomExpression lhs = (VariableAtomExpression) assign_stmt.getLhs();
                Expression rhs = expressionExecutor.executeExpression(globalIdentifiers, scopeIdentifiers, assign_stmt.getRhs());
                if (rhs instanceof IntegerAtomExpression) {
                        setIdentifier(lhs.getId(), new IntegerType(((IntegerAtomExpression) rhs).getValue()));
                } else if (rhs instanceof BooleanAtomExpression) {
                        setIdentifier(lhs.getId(), new BooleanType(((BooleanAtomExpression) rhs).getValue()));
                } else {
                        throw new RuntimeException("Undefined type variable assignment");
                }
        }

        private AtomType returnStatementHandler(Statement statement) throws Exception {
                ReturnStatement return_stmt = (ReturnStatement) statement;
                if (return_stmt.getExpression() == null) {
                        return new VoidType();
                }
                Expression expression = expressionExecutor.executeExpression(globalIdentifiers, scopeIdentifiers, return_stmt.getExpression());
                return getAtomTypeFromExpression(expression);
        }

        private AtomType ifElseStatementHandler(Statement statement) throws Exception {
                IfElseStatement if_else_stmt = (IfElseStatement) statement;
                Expression expr_condition = expressionExecutor.executeExpression(globalIdentifiers, scopeIdentifiers, if_else_stmt.getExpr_condition());
                if (!(expr_condition instanceof BooleanAtomExpression)) {
                        throw new RuntimeException("Expression condition of If-Else statement should be a logical expression");
                }
                if (!(if_else_stmt.getIf_statement_block() instanceof BlockStatement)) {
                        throw new RuntimeException("If statement must be enclosed within {}");
                }
                if ((if_else_stmt.getElse_statement_block() != null) && !(if_else_stmt.getElse_statement_block() instanceof BlockStatement)) {
                        throw new RuntimeException("Else statement must be enclosed within {}");
                }
                BooleanType expr_condition_cast = (BooleanType) expr_condition;
                if (expr_condition_cast.getValue()) {
                        BlockStatement block_stmt = (BlockStatement) if_else_stmt.getIf_statement_block();
                        StatementExecutor stmt_exec = new StatementExecutor(globalIdentifiers, functionDeclarationSymbolTable, scopeIdentifiers, block_stmt.getStatements());
                        return stmt_exec.executeStatements();
                } else if (if_else_stmt.getElse_statement_block() != null) {
                        BlockStatement block_stmt = (BlockStatement) if_else_stmt.getElse_statement_block();
                        StatementExecutor stmt_exec = new StatementExecutor(globalIdentifiers, functionDeclarationSymbolTable, scopeIdentifiers, block_stmt.getStatements());
                        return stmt_exec.executeStatements();
                } else {
                        throw new RuntimeException("Undefined type in if else block");
                }
        }

        private void blockStatementHandler(Statement statement) throws Exception {
                BlockStatement block_stmt = (BlockStatement) statement;
                StatementExecutor stmt_exec = new StatementExecutor(globalIdentifiers, functionDeclarationSymbolTable, scopeIdentifiers, block_stmt.getStatements());
                stmt_exec.executeStatements();
        }

        private void expressionStatementHandler(Statement statement) throws Exception {
                ExpressionStatement expr_stmt = (ExpressionStatement) statement;
                expressionExecutor.executeExpression(globalIdentifiers, scopeIdentifiers, expr_stmt.getExpression());
        }
}
