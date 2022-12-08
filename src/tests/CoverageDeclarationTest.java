package tests;

import antlr.*;
import prism.*;
import prism_interpreter.*;
import prism_coverage.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;

public class CoverageDeclarationTest {
        private String test_root;

        public CoverageDeclarationTest(String test_root) {
                this.test_root = test_root;
        }

        public void test_1() throws Exception {
                String input_file = test_root + "test-1.prism";
                PrismCodeCoverage prism_coverage = codeCoverage(input_file);
                TestHelpers.assertTrue(
                                (prism_coverage.getTotalFunctionDeclarations() == 3)
                                                && (prism_coverage.getTotalCoveredFunctionDeclarations() == 2),
                                "test_1, getTotalFunctionDeclarations() and getTotalCoveredFunctionDeclarations()");
        }

        private PrismCodeCoverage codeCoverage(String file_path) throws Exception {
                InputStream is = new FileInputStream(file_path);
                ANTLRInputStream input = new ANTLRInputStream(is);
                PrismLexer lexer = new PrismLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                PrismParser parser = new PrismParser(tokens);
                parser.setBuildParseTree(true);
                ParseTree AST = parser.prog();

                PrismProgramVisitor prismVisitor = new PrismProgramVisitor();
                PrismProgram prism_program = (PrismProgram) prismVisitor.visit(AST);
                PrismInterpreter prism_interpreter = new PrismInterpreter(prism_program.getProgram());
                prism_interpreter.interpret();
                PrismCodeCoverage prism_coverage = new PrismCodeCoverage(prism_program);
                return prism_coverage;
        }
}
