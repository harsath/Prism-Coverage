package prism;

import java.util.List;

public class MethodDeclaration implements Declaration {
       private List<FunctionDeclaration> methods;  

       MethodDeclaration() {

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
}
