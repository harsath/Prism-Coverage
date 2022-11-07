package prism;

import java.util.List;

public class PrismProgram implements Declaration {
       private List<Declaration> program; 

       PrismProgram() {

       }

       public void addDeclaration(Declaration decl) {
                this.program.add(decl);
       }

        public List<Declaration> getProgram() {
                return this.program;
        }

        public void setProgram(List<Declaration> program) {
                this.program = program;
        }

}
