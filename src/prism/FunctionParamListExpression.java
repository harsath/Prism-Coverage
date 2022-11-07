package prism;

import java.util.ArrayList;
import java.util.List;

public class FunctionParamListExpression implements Expression {
        private List<Expression> paramList;

        FunctionParamListExpression() {
                paramList = new ArrayList<>();
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
}
