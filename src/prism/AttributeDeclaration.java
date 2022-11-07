package prism;

import java.security.KeyStore.Entry.Attribute;
import java.util.List;

public class AttributeDeclaration implements Declaration {
        private List<VariableDeclaration> attributes;

        AttributeDeclaration() {

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
}
