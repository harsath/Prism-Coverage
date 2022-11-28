package prism_interpreter;

import java.util.*;

import prism.*;

public class DeclarationExecutor {
        private List<Declaration> declarations;
        private Map<String, FunctionDeclaration> functionDeclarationSymbolTable;
        // Class and class's method declaration (a) class and class's attributes
        // declaration (b)
        private Pair<Map<String, Map<String, FunctionDeclaration>>, Map<String, Map<String, AtomType>>> classSymbolTable;
        private Map<String, AtomType> globalIdentifiers;

        public DeclarationExecutor(List<Declaration> declarations) throws Exception {
                this.declarations = declarations;
                populateGlobalIdentifiers();
                populateClassSymbolTable();
                populateFunctionSymbolTable();
        }

        public AtomType executeMain() throws Exception {
                if (!functionDeclarationSymbolTable.containsKey("main")) {
                        throw new RuntimeException("Source file does not contain main() function");
                }
                FunctionDeclaration main_fn = functionDeclarationSymbolTable.get("main");
                main_fn.setIsExecuted(true);
                List<Statement> main_stmt = main_fn.getFunctionBody().getStatements();
                Map<String, AtomType> scopeIdentifiers = new HashMap<>();
                StatementExecutor stmt_executor = new StatementExecutor(functionDeclarationSymbolTable,
                                classSymbolTable);
                Pair<AtomType, Boolean> ret = stmt_executor.executeStatements(globalIdentifiers, scopeIdentifiers,
                                main_stmt);
                return ret.a;
        }

        private void populateClassSymbolTable() throws Exception {
                classSymbolTable = new Pair<Map<String, Map<String, FunctionDeclaration>>, Map<String, Map<String, AtomType>>>(
                                new HashMap<>(), new HashMap<>());
                for (Declaration declaration : declarations) {
                        if (declaration instanceof ClassDeclaration) {
                                // putting class and class's method declaration (a) start
                                ClassDeclaration class_decl = (ClassDeclaration) declaration;
                                Map<String, FunctionDeclaration> methods_decl = new HashMap<>();
                                for (FunctionDeclaration fn_decl : class_decl.getClassBody().getMethods()
                                                .getMethods()) {
                                        methods_decl.put(fn_decl.getFunctionName(), fn_decl);
                                }
                                classSymbolTable.a.put(class_decl.getClassName(), methods_decl);
                                // putting class and class's method declaration (a) end

                                // putting class and class's attribute declaration (b) start
                                Map<String, AtomType> attributes_decl = new HashMap<>();
                                for (VariableDeclaration var_decl : class_decl.getClassBody().getAttributes()
                                                .getAttributes()) {
                                        ExpressionExecutor expr_executor = new ExpressionExecutor(methods_decl,
                                                        classSymbolTable);
                                        Map<String, AtomType> scopeIdentifiers = new HashMap<>();
                                        Expression expr = expr_executor.executeExpression(globalIdentifiers,
                                                        scopeIdentifiers, var_decl.getExpression());
                                        attributes_decl.put(var_decl.getId(),
                                                        ExecutorHelpers.getAtomTypeFromAtomExpression(expr,
                                                                        "Unsupported type in attribute declaration"));
                                }
                                classSymbolTable.b.put(class_decl.getClassName(), attributes_decl);
                                // putting class and class's attribute declaration (b) end
                        }
                }
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
                        // We're creating an object inside the loop everytime becase the
                        // `globalIdentifiers` gets modified within this
                        // loop. So that when one global variable references another global variable,
                        // ExpressionExecutor has the latest version
                        // We are also not copying the whole object, we are passing references, which is
                        // computationally cheap.
                        if (declaration instanceof VariableDeclaration) {
                                ExpressionExecutor expr_executor = new ExpressionExecutor(
                                                functionDeclarationSymbolTable, classSymbolTable);
                                VariableDeclaration var_decl = (VariableDeclaration) declaration;
                                // Since on global scope, we don't have any other scope identifiers other than
                                // global ones
                                // so we're just passing `globalIdentifiers` for both parameters.
                                Map<String, AtomType> scopeIdentifiers = new HashMap<>();
                                Expression expr = expr_executor.executeExpression(globalIdentifiers, scopeIdentifiers,
                                                var_decl.getExpression());
                                globalIdentifiers.put(var_decl.getId(), ExecutorHelpers.getAtomTypeFromAtomExpression(
                                                expr,
                                                "Undefined type in Declaration Executor populateGlobalIdentifiers()"));
                        }
                }
        }
}
