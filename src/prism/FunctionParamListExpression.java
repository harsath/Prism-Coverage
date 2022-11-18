package prism;

import java.util.ArrayList;
import java.util.List;

public class FunctionParamListExpression implements Expression {
        private List<Expression> paramList;
	private boolean executed = false;

        FunctionParamListExpression() {
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

        public List<Expression> getParamList() {
                return paramList;
        }

        public void setParamList(List<Expression> paramList) {
                this.paramList = paramList;
        }

        public void addExpression(Expression expr) {
                paramList.add(expr);
        }
        
        @Override
        public String toString() {
                String returner = new String();
                for (int i = 0; i < paramList.size(); i++) {
                        returner += paramList.get(i).toString();
                        if (i != paramList.size() - 1) {
                        	returner += ", ";
                        }
                }
                return returner;
        }
}
