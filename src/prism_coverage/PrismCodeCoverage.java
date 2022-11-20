package prism_coverage;

import java.util.*;

import prism.*;

public class PrismCodeCoverage {
        private PrismProgram prism_program;
        private Integer total_global_variabels = 0;
        private Integer total_covered_global_variables = 0;
        private Integer total_function_decls = 0;
        private Integer total_covered_function_decls = 0;
        private Integer total_class_decls = 0;
        private Integer total_covered_class_decls = 0;
        
        public PrismCodeCoverage(PrismProgram prism_program) {
                this.prism_program = prism_program;
                populateTotalAndCoveredDeclarations();
        }

        public Integer getTotalFunctionDeclarations() { return total_function_decls; }
        
        public Integer getTotalCoveredFunctionDeclarations() { return total_covered_function_decls; }

        public Integer getTotalGlobalVariableDeclarations() { return total_global_variabels; }

        public Integer getTotalCoveredGlobalVariableDeclarations() { return total_covered_global_variables; }

        public Integer getTotalClassDeclarations() { return total_class_decls; }

        public Integer getTotalCoveredClassDeclarations() { return total_covered_class_decls; }

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
                                total_global_variabels++;
                                if (decl.getIsExecuted()) {
                                        total_covered_global_variables++;
                                }
                        } else {
                                throw new RuntimeException("Unrecognized declaration");
                        }
                }
        }

}