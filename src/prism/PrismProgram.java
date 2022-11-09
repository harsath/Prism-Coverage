package prism;

import java.util.ArrayList;
import java.util.List;

public class PrismProgram implements Declaration {
       private List<Declaration> program; 

       PrismProgram() {
                this.program = new ArrayList<>();
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

        @Override
        public String toString() {
                String returner = new String();
                for (Declaration decl : program) {
                        returner += decl.toString();
                        returner += "\n";
                }
                return returner;
        }

}
