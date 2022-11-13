package prism_interpreter;

import prism.*;
import java.util.*;

public class DeclarationExecutor {
        private List<Declaration> declarations;
        private Map<String, FunctionDeclaration> functionDeclarationSymbolTable;
        private Map<String, AtomType> globalIdentifiers;

        public DeclarationExecutor(List<Declaration> declarations) throws Exception { 
                this.declarations = declarations;
                populateFunctionSymbolTable();
                populateGlobalIdentifiers();
        }

        public AtomType executeMain() throws Exception {
                if (!functionDeclarationSymbolTable.containsKey("main")) {
                        throw new RuntimeException("Source file does not contain main() function");
                }
                List<Statement> main_stmt = functionDeclarationSymbolTable.get("main").getFunctionBody().getStatements();
                Map<String, AtomType> scopeIdentifiers = new HashMap<>();
                StatementExecutor stmt_executor = new StatementExecutor(functionDeclarationSymbolTable);
                Pair<AtomType, Boolean> ret = stmt_executor.executeStatements(globalIdentifiers, scopeIdentifiers, main_stmt);
                return ret.a;
        }

        private void populateFunctionSymbolTable() {
                functionDeclarationSymbolTable = new HashMap<>();
                for (Declaration declaration : declarations) {
                        if (declaration instanceof FunctionDeclaration) {
                                FunctionDeclaration fn_decl = (FunctionDeclaration) declaration;
                                functionDeclarationSymbolTable.put(fn_decl.getFunctionName(), fn_decl);
                        }
                }
        }

        private void populateGlobalIdentifiers() throws Exception {
                globalIdentifiers = new HashMap<>();
                for (Declaration declaration : declarations) {
                        // We're creating an object inside the loop everytime becase the `globalIdentifiers` gets modified within this
                        // loop. So that when one global variable references another global variable, ExpressionExecutor has the latest version
                        // We are also not copying the whole object, we are passing references, which is computationally cheap.
                        if (declaration instanceof VariableDeclaration) {
                                ExpressionExecutor expr_executor = new ExpressionExecutor(functionDeclarationSymbolTable);
                                VariableDeclaration fn_decl = (VariableDeclaration) declaration;
                                // Since on global scope, we don't have any other scope identifiers other than global ones
                                // so we're just passing `globalIdentifiers` for both parameters.
                                Map<String, AtomType> scopeIdentifiers = new HashMap<>();
                                Expression expr = expr_executor.executeExpression(globalIdentifiers, scopeIdentifiers, fn_decl.getExpression());
                                if (expr instanceof IntegerAtomExpression) {
                                        IntegerType int_type = new IntegerType(((IntegerAtomExpression) expr).getValue());
                                        globalIdentifiers.put(fn_decl.getId(), int_type);
                                } else if (expr instanceof BooleanAtomExpression) {
                                        BooleanAtomExpression bool_type = new BooleanAtomExpression(((BooleanAtomExpression) expr).getValue());
                                        globalIdentifiers.put(fn_decl.getId(), bool_type);
                                } else {
                                        throw new RuntimeException("Undefined type");
                                }
                        }
                }
        }
}
