package prism_interpreter;

import prism.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressionExecutor {
        private Map<String, FunctionDeclaration> functionDeclarationSymbolTable;

        public ExpressionExecutor(Map<String, FunctionDeclaration> functionDeclarationSymbolTable) {
                this.functionDeclarationSymbolTable = functionDeclarationSymbolTable;
        }

        // Paramater takes an Expression and a reference to identifiers in current stack
        // We are passing `globalIdentifiers` everytime we call `executeExpression` because any given statement can modify
        // the global variable so we make sure we get the latest version everytime we execute an expression.
        public Expression executeExpression(Map<String, AtomType> globalIdentifiers, Map<String, AtomType> scopeIdentifiers, Expression expression) throws Exception {
                if (expression instanceof FunctionCallExpression){
                        // Initialize the call stack with parameter values
                        FunctionCallExpression fn_call_expr = (FunctionCallExpression) expression;
                        if (!functionDeclarationSymbolTable.containsKey(fn_call_expr.getFunctionName())) {
                                throw new RuntimeException("Call to undefined function");
                        }
                        Map<String, AtomType> fn_call_scope_identifiers = new HashMap<>();
                        if (fn_call_expr.getFunctionParamList() != null) {
                                String function_name = fn_call_expr.getFunctionName();
                                List<ParameterDeclaration> fn_decl_params = functionDeclarationSymbolTable.get(function_name).getFunctionParamDecl().getParamList();
                                List<Expression> params = fn_call_expr.getFunctionParamList().getParamList();
                                if (params.size() != fn_decl_params.size()) {
                                        throw new RuntimeException("Invalid number of parameters on function call");
                                }
                                for (int i = 0; i < params.size(); i++) {
                                        String param_name_in_fn_decl = fn_decl_params.get(i).getId();
                                        Expression expr_in_fn_call = executeExpression(globalIdentifiers, scopeIdentifiers, params.get(i));
                                        if (expr_in_fn_call instanceof IntegerAtomExpression) {
                                                fn_call_scope_identifiers.put(param_name_in_fn_decl, new IntegerType(((IntegerAtomExpression) expr_in_fn_call).getValue()));
                                        } else if (expr_in_fn_call instanceof BooleanAtomExpression) {
                                                fn_call_scope_identifiers.put(param_name_in_fn_decl, new BooleanType(((BooleanAtomExpression) expr_in_fn_call).getValue()));
                                        }
                                }
                                List<Statement> block_stmt = functionDeclarationSymbolTable.get(function_name).getFunctionBody().getStatements();
                                StatementExecutor statementExecutor = new StatementExecutor(globalIdentifiers, functionDeclarationSymbolTable, fn_call_scope_identifiers, block_stmt);
                                AtomType ret = statementExecutor.executeStatements();
                                return getAtomExpressionFromAtomType(ret, "Unsupported type in function call execution");
                        } else {
                                String function_name = fn_call_expr.getFunctionName();
                                List<Statement> block_stmt = functionDeclarationSymbolTable.get(function_name).getFunctionBody().getStatements();
                                StatementExecutor statementExecutor = new StatementExecutor(globalIdentifiers, functionDeclarationSymbolTable, fn_call_scope_identifiers, block_stmt);
                                AtomType ret = statementExecutor.executeStatements();
                                return getAtomExpressionFromAtomType(ret, "Unsupported type in function call execution");
                        }
                } else if (expression instanceof UnaryMinusExpression) {
                        UnaryMinusExpression expr = (UnaryMinusExpression) expression;
                        Expression expr_val = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getExpression());
                        if (expr_val instanceof BooleanAtomExpression) {
                                BooleanAtomExpression expr_val_cast = (BooleanAtomExpression) expr_val;
                                return new BooleanAtomExpression((!expr_val_cast.getValue()));
                        } else {
                                throw new RuntimeException("Unary not can only be used with Bool type");
                        }
                } else if (expression instanceof NotExpression) {
                        NotExpression expr = (NotExpression) expression;
                        Expression expr_val = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getExpression());
                        if (expr_val instanceof BooleanAtomExpression) {
                                BooleanAtomExpression expr_val_cast = (BooleanAtomExpression) expr_val;
                                return new BooleanAtomExpression((!expr_val_cast.getValue()));
                        } else {
                                throw new RuntimeException("Unary not can only be used with Bool type");
                        }
                } else if (expression instanceof MultiplicationDivisionExpression) {
                        MultiplicationDivisionExpression expr = (MultiplicationDivisionExpression) expression;
                        boolean is_multiplication = expr.isMultiplication();
                        Expression lhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getLeft());
                        Expression rhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getRight());
                        typeCheckRelationalExpression(lhs, rhs);
                        if (lhs instanceof IntegerAtomExpression) {
                                IntegerAtomExpression lhs_cast = (IntegerAtomExpression) lhs;
                                IntegerAtomExpression rhs_cast = (IntegerAtomExpression) rhs;
                                if (is_multiplication) {
                                        return new IntegerAtomExpression((lhs_cast.getValue() * rhs_cast.getValue()));
                                } else {
                                        return new IntegerAtomExpression((lhs_cast.getValue() / rhs_cast.getValue()));
                                }
                        } else {
                                throw new RuntimeException("Unsupported type in MultiplicationDivisionExpression");
                        }
                } else if (expression instanceof AdditionSubtractionExpression) {
                        AdditionSubtractionExpression expr = (AdditionSubtractionExpression) expression;
                        boolean is_addition = expr.isAddition();
                        Expression lhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getLeft());
                        Expression rhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getRight());
                        typeCheckRelationalExpression(lhs, rhs);
                        if (lhs instanceof IntegerAtomExpression) {
                                IntegerAtomExpression lhs_cast = (IntegerAtomExpression) lhs;
                                IntegerAtomExpression rhs_cast = (IntegerAtomExpression) rhs;
                                if (is_addition) {
                                        return new IntegerAtomExpression((lhs_cast.getValue() + rhs_cast.getValue()));
                                } else {
                                        return new IntegerAtomExpression((lhs_cast.getValue() - rhs_cast.getValue()));
                                }
                        } else {
                                throw new RuntimeException("Unsupported type in AdditionSubtractionExpression");
                        }
                } else if (expression instanceof EqualityExpression) {
                        EqualityExpression expr = (EqualityExpression) expression;
                        Expression lhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getLeft());
                        Expression rhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getRight());
                        typeCheckRelationalExpression(lhs, rhs);
                        if (lhs instanceof IntegerAtomExpression) {
                                IntegerAtomExpression lhs_cast = (IntegerAtomExpression) lhs;
                                IntegerAtomExpression rhs_cast = (IntegerAtomExpression) rhs;
                                return new BooleanAtomExpression((lhs_cast.getValue() == rhs_cast.getValue()));
                        } else {
                                throw new RuntimeException("Unsupported type in EqualityExpression");
                        }
                } else if (expression instanceof OrExpression) {
                        OrExpression expr = (OrExpression) expression;
                        Expression lhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getLeft());
                        Expression rhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getRight());
                        typeCheckLogicalExpression(lhs, rhs);
                        BooleanAtomExpression lhs_cast = (BooleanAtomExpression) lhs;
                        BooleanAtomExpression rhs_cast = (BooleanAtomExpression) rhs;
                        return new BooleanAtomExpression((lhs_cast.getValue() || rhs_cast.getValue()));
                } else if (expression instanceof AndExpression) {
                        AndExpression expr = (AndExpression) expression;
                        Expression lhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getLeft());
                        Expression rhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getRight());
                        typeCheckLogicalExpression(lhs, rhs);
                        BooleanAtomExpression lhs_cast = (BooleanAtomExpression) lhs;
                        BooleanAtomExpression rhs_cast = (BooleanAtomExpression) rhs;
                        return new BooleanAtomExpression((lhs_cast.getValue() && rhs_cast.getValue()));
                } else if (expression instanceof GreaterthanExpression) {
                        GreaterthanExpression expr = (GreaterthanExpression) expression;
                        Expression lhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getLeft());
                        Expression rhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getRight());
                        typeCheckRelationalExpression(lhs, rhs);
                        IntegerAtomExpression lhs_cast = (IntegerAtomExpression) lhs;
                        IntegerAtomExpression rhs_cast = (IntegerAtomExpression) rhs;
                        return new BooleanAtomExpression((lhs_cast.getValue() > rhs_cast.getValue()));
                } else if (expression instanceof LessthanExpression) {
                        LessthanExpression expr = (LessthanExpression) expression;
                        Expression lhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getLeft());
                        Expression rhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getRight());
                        typeCheckRelationalExpression(lhs, rhs);
                        IntegerAtomExpression lhs_cast = (IntegerAtomExpression) lhs;
                        IntegerAtomExpression rhs_cast = (IntegerAtomExpression) rhs;
                        return new BooleanAtomExpression((lhs_cast.getValue() < rhs_cast.getValue()));
                } else if (expression instanceof GreaterthanEqExpression) {
                        GreaterthanEqExpression expr = (GreaterthanEqExpression) expression;
                        Expression lhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getLeft());
                        Expression rhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getRight());
                        typeCheckRelationalExpression(lhs, rhs);
                        IntegerAtomExpression lhs_cast = (IntegerAtomExpression) lhs;
                        IntegerAtomExpression rhs_cast = (IntegerAtomExpression) rhs;
                        return new BooleanAtomExpression((lhs_cast.getValue() >= rhs_cast.getValue()));
                } else if (expression instanceof LessthanEqExpression) {
                        LessthanEqExpression expr = (LessthanEqExpression) expression;
                        Expression lhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getLeft());
                        Expression rhs = executeExpression(globalIdentifiers, scopeIdentifiers, expr.getRight());
                        typeCheckRelationalExpression(lhs, rhs);
                        IntegerAtomExpression lhs_cast = (IntegerAtomExpression) lhs;
                        IntegerAtomExpression rhs_cast = (IntegerAtomExpression) rhs;
                        return new BooleanAtomExpression((lhs_cast.getValue() <= rhs_cast.getValue()));
                } else if (expression instanceof VariableAtomExpression) {
                        VariableAtomExpression expr = (VariableAtomExpression) expression;
                        return lookupIdentifier(globalIdentifiers, scopeIdentifiers, expr.getId());
                } else if (expression instanceof IntegerAtomExpression) {
                        return (IntegerAtomExpression) expression;
                } else if (expression instanceof BooleanAtomExpression) {
                        return (BooleanAtomExpression) expression;
                } else if (expression instanceof BracketExprExpression) {
                        BracketExprExpression expr = (BracketExprExpression) expression;
                        return executeExpression(globalIdentifiers, scopeIdentifiers, expr.getExpression());
                } else {
                        throw new RuntimeException("Undefined Expression type");
                }
        }

        private Expression lookupIdentifier(Map<String, AtomType> globalIdentifiers, Map<String, AtomType> scopeIdentifiers, String id) throws Exception {
                // Local scope is given high presidence when looking up variables
                if (scopeIdentifiers.containsKey(id)) {
                        AtomType atom_value = scopeIdentifiers.get(id);
                        return getAtomExpressionFromAtomType(atom_value, "Undefined type on scope variable reference");
                } else if (globalIdentifiers.containsKey(id)) {
                        AtomType atom_value = globalIdentifiers.get(id);
                        return getAtomExpressionFromAtomType(atom_value, "Undefined type on global variable reference");
                } else {
                        throw new RuntimeException("Undefined variable");
                }
        }

        // Prism interpreter only allows relational expression (`>`, `<`, `>=`, `<=`, `==`), numeric computations (`*`, `/`, `+`, `-`) between two IntegerAtomExpression
        private void typeCheckRelationalExpression(Expression lhs, Expression rhs) throws Exception {
                if ((lhs instanceof IntegerAtomExpression) && (rhs instanceof IntegerAtomExpression)) {
                        return;
                } else {
                        throw new RuntimeException("Relational expressions can be executed between two Ints");
                }
        }

        // Prism interpreter only allows logical expression (`||`, `&&`) between two IntegerAtomExpression
        private void typeCheckLogicalExpression(Expression lhs, Expression rhs) throws Exception {
                if ((lhs instanceof BooleanAtomExpression) && (rhs instanceof BooleanAtomExpression)) {
                        return;
                } else {
                        throw new RuntimeException("Logical expressions can be executed between two Bools");
                }
        }

        private Expression getAtomExpressionFromAtomType(AtomType atom_type, String exception_str) {
                if (atom_type instanceof IntegerType) {
                        return new IntegerAtomExpression(((IntegerType) atom_type).getValue());
                } else if (atom_type instanceof BooleanType) {
                        return new BooleanAtomExpression(((BooleanType) atom_type).getValue());
                } else {
                        throw new RuntimeException(exception_str);
                }
        }

}
