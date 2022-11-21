package prism_coverage;

import java.util.*;

import prism.*;

public class PrismCodeCoverage {
        private PrismProgram prism_program;
        private Integer total_global_variables = 0;
        private Integer total_covered_global_variables = 0;
        private Integer total_function_decls = 0;
        private Integer total_covered_function_decls = 0;
        private Integer total_class_decls = 0;
        private Integer total_covered_class_decls = 0;
        // declaration coverage + statement coverage
        private Integer total_source_code = 0;
        private Integer total_covered_source_code = 0;

        public PrismCodeCoverage(PrismProgram prism_program) {
                this.prism_program = prism_program;
                populateTotalAndCoveredDeclarations();
                populateSourceCodeTotalAndCovered();
                // TODO: we need same for classes once we support operations over classes
        }

        public Integer getTotalSourceCode() {
                return total_source_code;
        }

        public Integer getTotalCoveredSourceCode() {
                return total_covered_source_code;
        }

        public Integer getTotalFunctionDeclarations() {
                return total_function_decls;
        }

        public Integer getTotalCoveredFunctionDeclarations() {
                return total_covered_function_decls;
        }

        public Integer getTotalGlobalVariableDeclarations() {
                return total_global_variables;
        }

        public Integer getTotalCoveredGlobalVariableDeclarations() {
                return total_covered_global_variables;
        }

        public Integer getTotalClassDeclarations() {
                return total_class_decls;
        }

        public Integer getTotalCoveredClassDeclarations() {
                return total_covered_class_decls;
        }

        private Integer getTotalStatementsFromFunction(FunctionDeclaration fn_decl) {
                return fn_decl.getFunctionBody().getStatements().size();
        }

        private Integer getTotalCoveredStatementsFromFunction(FunctionDeclaration fn_decl) {
                Integer returner = 0;
                for (Statement stmt : fn_decl.getFunctionBody().getStatements()) {
                        if (stmt instanceof AssignmentStatement) {
                                if (stmt.getIsExecuted()) {
                                        returner++;
                                }
                        } else if (stmt instanceof IfElseStatement) {
                                IfElseStatement if_else_block = (IfElseStatement) stmt;
                                if (if_else_block.getIf_statement_block().getIsExecuted()) {
                                        returner++;
                                } else {
                                        if ((if_else_block.getElse_statement_block() != null)
                                                        && (if_else_block.getElse_statement_block().getIsExecuted())) {
                                                returner++;
                                        }
                                }
                        } else if (stmt instanceof ForLoopStatement) {
                                // A for-loop may or may nor execute. If the condition is false on the first
                                // iteration, the BlockStatement wouldn't
                                // get executed even once.
                                ForLoopStatement for_loop_stmt = (ForLoopStatement) stmt;
                                if (for_loop_stmt.getStatementBlock().getIsExecuted()) {
                                        returner++;
                                }
                        } else if (stmt instanceof WhileLoopStatement) {
                                WhileLoopStatement while_loop_stmt = (WhileLoopStatement) stmt;
                                if (while_loop_stmt.getStatementBlock().getIsExecuted()) {
                                        returner++;
                                }
                        } else if (stmt instanceof ReturnStatement) {
                                // If a function returns before hitting this particular ReturnStatement, it will
                                // not be executed
                                if (stmt.getIsExecuted()) {
                                        returner++;
                                }
                        } else if (stmt instanceof VariableDeclarationStatement) {
                                if (stmt.getIsExecuted()) {
                                        returner++;
                                }
                        }
                }
                return returner;
        }

        private void populateTotalAndCoveredDeclarations() throws RuntimeException {
                List<Declaration> decls = prism_program.getProgram();
                for (Declaration decl : decls) {
                        if (decl instanceof ClassDeclaration) {
                                total_class_decls++;
                                if (decl.getIsExecuted()) {
                                        total_covered_class_decls++;
                                }
                        } else if (decl instanceof FunctionDeclaration) {
                                total_function_decls++;
                                if (decl.getIsExecuted()) {
                                        total_covered_function_decls++;
                                }
                        } else if (decl instanceof VariableDeclaration) {
                                total_global_variables++;
                                if (decl.getIsExecuted()) {
                                        total_covered_global_variables++;
                                }
                        } else {
                                throw new RuntimeException("Unrecognized declaration");
                        }
                }
                total_source_code += (total_class_decls + total_function_decls + total_global_variables);
                total_covered_source_code += (total_covered_class_decls + total_covered_function_decls
                                + total_covered_global_variables);
        }

        private void populateSourceCodeTotalAndCovered() throws RuntimeException {
                List<Declaration> decls = prism_program.getProgram();
                for (Declaration decl : decls) {
                        if (decl instanceof FunctionDeclaration) {
                                total_source_code += getTotalStatementsFromFunction((FunctionDeclaration) decl);
                                if (decl.getIsExecuted()) {
                                        total_covered_source_code += getTotalCoveredStatementsFromFunction(
                                                        (FunctionDeclaration) decl);
                                }
                        }
                }
        }

}