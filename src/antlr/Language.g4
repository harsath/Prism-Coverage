grammar Language;


@header {
package antlr;
}

/* Parser elements */

prog : (decl | expr)+ EOF                          #Program

decl : ID ':' INT_TYPE '=' NUM                      #IntVariableDeclaration
     | ID ':' BOOL_TYPE '=' BOOL                    #BoolVariableDeclaration
     | CLASS ID                                     #ClassDeclaration
     ;

expr : (arithmetic_expr | logical_expr | relational_expr)+;

arithmetic_expr : arithmetic_expr '*' arithmetic_expr    #MultiplicationExpression
                | arithmetic_expr '+' arithmetic_expr    #AdditionExpression
                | arithmetic_expr '+' arithmetic_expr    #SubtractionExpression
                | arithmetic_expr '/' arithmetic_expr    #DivisionExpression
                | ID                                     #IdentifierArithmeticExpression
                | NUM                                    #NumericArithemticExpression
                ;
                
logical_expr    : logical_expr '&&' logical_expr         #AndExpression
                | logical_expr '||' logical_expr         #OrExpression
                | '!' logical_expr                       #NotExpression
                | ID                                     #IdentifierLogicalExpression
                | NUM                                    #NumericArithemticExpression
                ;
                
relational_expr : relational_expr '>' relational_expr    #GTExpression
                | relational_expr '<' relational_expr    #LTExpression
                | relational_expr '==' relational_expr   #EQExpression
                | ID                                     #IdentifierRelationalExpression
                | NUM                                    #NumericRelationalExpression
                ;

/* Lexical elements */

ID          :       [a-z][a-zA-Z0-9_]*;
NUM         :       '0' | '-'?[1-9][0-9]*;
BOOL        :       'true' | 'false';
INT_TYPE    :       'INT';
BOOL_TYPE   :       'BOOL';
COMMENT     :       '//' ~[\r\n]* -> skip;
WS          :       [ \t\n]+ -> skip;
NEWLINE     :       ('\r'?'\n'|'\r')+ -> skip;