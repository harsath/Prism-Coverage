package prism;

public class ParameterDeclaration implements Declaration {
        private String id;
        private AtomType type;
	private boolean executed = false;

        ParameterDeclaration(String id, AtomType type) {
                this.id = id;
                this.type = type;
        }

        @Override
        public void setIsExecuted(boolean value) {
                this.executed = value;
        }

        @Override
        public boolean getIsExecuted() {
                return this.executed;
        }

        public String getId() {
                return this.id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public AtomType getType() {
                return this.type;
        }

        public void setType(AtomType type) {
                this.type = type;
        }

        @Override
        public String toString() {
                return type.toString() + " " + id;
        }
}
