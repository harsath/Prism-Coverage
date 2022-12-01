package prism;

import java.util.Map;

public class ObjectAtomExpression extends ObjectType implements Expression {
       private boolean executed = false; 

       public ObjectAtomExpression(String className, Map<String, AtomType> attributes) {
                super(className, attributes);
       }

       @Override
       public void setIsExecuted(boolean value) {
               this.executed = value;
       }

       @Override
       public boolean getIsExecuted() {
               return this.executed;
       }
}
