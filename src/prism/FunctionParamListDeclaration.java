package prism;

import java.util.List;
import java.util.Map;

public class FunctionParamListDeclaration implements Declaration {
        //  ID    , type
        Map<String, AtomType> paramDecl;

        FunctionParamListDeclaration() {

        }

        public void addParam(String id, AtomType type) {
                this.paramDecl.put(id, type);
        }

        public Map<String,AtomType> getParamDecl() {
                return this.paramDecl;
        }

        public void setParamDecl(Map<String,AtomType> paramDecl) {
                this.paramDecl = paramDecl;
        }
}
