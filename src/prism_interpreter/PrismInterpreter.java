package prism_interpreter;

import java.util.*;

import prism.*;

public class PrismInterpreter {
        private List<Declaration> programDeclarations; 

        public PrismInterpreter(List<Declaration> programDeclarations) {
                this.programDeclarations = programDeclarations;
        }
        
        // Method returns the return value of `main()` function of a prism program.
        public AtomType interpret() throws Exception {
                DeclarationExecutor declarationExecutor = new DeclarationExecutor(programDeclarations);
                return declarationExecutor.executeMain();
        }
}
