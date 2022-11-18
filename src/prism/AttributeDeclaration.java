package prism;

import java.util.ArrayList;
import java.util.List;

public class AttributeDeclaration implements Declaration {
        private List<VariableDeclaration> attributes;
	private boolean executed = false;

        AttributeDeclaration() {
                this.attributes = new ArrayList<>();
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
                this.attributes.add(attribute);
        }

        public List<VariableDeclaration> getAttributes() {
                return this.attributes;
        }

        public void setAttributes(List<VariableDeclaration> attributes) {
                this.attributes = attributes;
        }

        @Override
        public String toString() {
                String returner = new String();
                for (VariableDeclaration attrs : attributes) {
                		returner += "\t";
                        returner += attrs.toString();
                }
                return returner;
        }
}
