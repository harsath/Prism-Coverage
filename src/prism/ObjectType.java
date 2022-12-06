package prism;

import java.io.Serializable;
import java.util.*;

public class ObjectType implements AtomType, Serializable {
        private String className;
        private Map<String, AtomType> attributes;

        public ObjectType(String className, Map<String, AtomType> attributes) {
                this.className = className;
                this.attributes = attributes;
        }

        public String getClassName() {
                return this.className;
        }

        public Map<String, AtomType> getAttributes() {
                return this.attributes;
        }

        public void putAttribute(String id, AtomType value) {
                this.attributes.put(id, value);
        }

        public boolean containsAttribute(String id) {
                return this.attributes.containsKey(id);
        }

        public AtomType getAttribute(String id) {
                return this.attributes.get(id);
        }

        @Override
        public String toString() {
                return this.className;
        }
}
