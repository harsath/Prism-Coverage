package prism;

import java.util.ArrayList;
import java.util.List;

public class PrismProgram implements Declaration {
       private List<Declaration> program; 
       private boolean executed = false;

       PrismProgram() {
                this.program = new ArrayList<>();
       }

       @Override
       public void setIsExecuted(boolean value) {
               this.executed = value;
       }

       @Override
       public boolean getIsExecuted() {
               return this.executed;
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
