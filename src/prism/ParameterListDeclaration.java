package prism;

import java.util.*;

public class ParameterListDeclaration implements Declaration {
        private List<ParameterDeclaration> paramList;        
	private boolean executed = false;

        ParameterListDeclaration() {
                paramList = new ArrayList<>();
        }

        @Override
        public void setIsExecuted(boolean value) {
                this.executed = value;
        }

        @Override
        public boolean getIsExecuted() {
                return this.executed;
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
