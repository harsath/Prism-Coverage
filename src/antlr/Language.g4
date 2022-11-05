grammar Language;


@header {
package antlr;
}

/* Parser elements */

prog : (stmt NEWLINE)+ EOF
     ;

stmt : variable_assignment
     | class_definition
     | function_declaration
     | if_statement
     | return_statement
     ;

variable_assignment : ID ':' decl_type '=' expr
                    ;

class_definition : CLASS_TYPE ID LCURLY class_def RCURLY
                 ;

function_declaration : FN_TYPE decl_type ID LPAREN param_list? RPAREN stmt_block
                     ;

function_invocation : ID LPAREN param_list RPAREN
                    ;

param_list : decl_type ID (',' decl_type ID)*
           ;

return_statement : RETURN expr
                 ;

if_statement : IF conditional_expr (ELSE IF conditional_expr)* (ELSE stmt_block)?
             ;

conditional_expr : LPAREN expr RPAREN stmt_block
                 ;

stmt_block : LCURLY stmt RCURLY
           ;

decl_type : INT_TYPE
          | BOOL_TYPE
          ;

class_def : ATTRIBUTES (attributes_def NEWLINE)+ METHODS (method_def NEWLINE)+
          ;

attributes_def : variable_assignment
               ;

method_def : function_declaration
           ;

expr : '-' expr                                   #UnaryMinusExpr
     | '!' expr                                   #NotExpr
     | expr '*' expr                              #MultiplicationExpr
     | expr '/' expr                              #DivisionExpr
     | expr '+' expr                              #AdditionExpr
     | expr '-' expr                              #SubtractionExpr
     | expr op=('<=' | '>=' | '>' | '<') expr     #RelationalExpr
     | expr op=('==' | '!=')                      #EqualityExpr
     | expr 'AND' expr                            #AndExpr
     | expr 'OR' expr                             #OrExpr
     | function_invocation                        #FnExpr
     | atom                                       #AtomExpr
     ;

atom : LPAREN expr RPAREN #ParExpr
     | INT_TYPE           #IntAtom
     | BOOL_TYPE          #BoolAtom
     | ID                 #IdAtom
     ;

/* Lexical elements */

ID          :       [a-z][a-zA-Z0-9_]*;
NUM         :       '0' | '-'?[1-9][0-9]*;
BOOL        :       'true' | 'false';
INT_TYPE    :       'INT';
BOOL_TYPE   :       'BOOL';
FN_TYPE     :       'FUNCTION';
CLASS_TYPE  :       'CLASS';
IF          :       'IF';
ELSE        :       'ELSE';
METHODS     :       'METHODS';
ATTRIBUTES  :       'ATTRIBUTES';
RETURN      :       'RETURN';
COMMENT     :       '//' ~[\r\n]* -> skip;
WS          :       [ \t\n]+ -> skip;
NEWLINE     :       ('\r'?'\n'|'\r')+ -> skip;
LCURLY      :       '{';
RCURLY      :       '}';
LPAREN      :       '(';
RPAREN      :       ')';