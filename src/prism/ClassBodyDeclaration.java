package prism;

public class ClassBodyDeclaration implements Declaration {
        private AttributeDeclaration attributes;
        private MethodDeclaration methods;
        private boolean executed = false;

        ClassBodyDeclaration() {
                this.attributes = new AttributeDeclaration();
                this.methods = new MethodDeclaration();
        }

        @Override
        public void setIsExecuted(boolean value) {
                this.executed = value;
        }

        @Override
        public boolean getIsExecuted() {
                return this.executed;
        }

        public void addAttribute(VariableDeclaration attribute) {
                this.attributes.addAttribute(attribute);
        }

        public void addMethod(FunctionDeclaration method) {
                this.methods.addMethod(method);
        }

        public AttributeDeclaration getAttributes() {
                return this.attributes;
        }

        public void setAttributes(AttributeDeclaration attributes) {
                this.attributes = attributes;
        }

        public MethodDeclaration getMethods() {
                return this.methods;
        }

        public void setMethods(MethodDeclaration methods) {
                this.methods = methods;
        }

        @Override
        public String toString() {
                String returner = new String();
                returner += "ATTRIBUTES \n" + attributes.toString() + "METHODS \n" + methods.toString();
                return returner;
        }
}
