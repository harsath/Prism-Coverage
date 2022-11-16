package prism;

import java.util.*;

public class ParameterListDeclaration implements Declaration {
        private List<ParameterDeclaration> paramList;        

        ParameterListDeclaration() {
                paramList = new ArrayList<>();
        }

        public List<ParameterDeclaration> getParamList() {
                return paramList;
        }

        public void setParamList(List<ParameterDeclaration> paramList) {
                this.paramList = paramList;
        }

        public void addParam(ParameterDeclaration param) {
                paramList.add(param);
        }
        
        @Override
        public String toString() {
                String returner = new String();
                for (int i = 0; i < paramList.size(); i++) {
                        returner += paramList.get(i).toString();
                        if (!(i == paramList.size() - 1)){
                                returner += ", ";
                        }
                }
                return returner;
        }
}
