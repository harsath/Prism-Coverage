package prism;

import java.util.ArrayList;
import java.util.List;

public class MethodDeclaration implements Declaration {
       private List<FunctionDeclaration> methods;  

       MethodDeclaration() {
                this.methods = new ArrayList<>();
       }

       public void addMethod(FunctionDeclaration method) {
                this.methods.add(method);
       }

        public List<FunctionDeclaration> getMethods() {
                return this.methods;
        }

        public void setMethods(List<FunctionDeclaration> methods) {
                this.methods = methods;
        }

        @Override
        public String toString() {
                String returner = new String();
                for (FunctionDeclaration fn_decl : methods) {
                        returner += fn_decl.toString();
                        returner += "\n";
                }
                return returner;
        }
}
