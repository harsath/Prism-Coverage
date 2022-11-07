grammar Prism;

@header {
package antlr;
}

/* Lexical elements */

ID          :       [a-z][a-zA-Z0-9_]*;
INT         :       '0' | '-'?[1-9][0-9]*;
BOOL        :       'true' | 'false';
FUNCTION    :       'FUNCTION';
CLASS       :       'CLASS';
IF          :       'IF';
ELSE        :       'ELSE';
METHODS     :       'METHODS';
ATTRIBUTES  :       'ATTRIBUTES';
RETURN      :       'RETURN';
COMMENT     :       '//' ~[\r\n]* -> skip;
WS          :       [ \t\n]+ -> skip;
LCURLY      :       '{';
RCURLY      :       '}';
LPAREN      :       '(';
RPAREN      :       ')';
SEMICOLON   :       ';';

/* Parser elements */

prog : (function_decl | variable_decl | class_decl)+ EOF
     ;

variable_decl : type ID ('=' expr)? SEMICOLON                           #VariableDecl
              ;

function_decl : FUNCTION type ID LPAREN param_list? RPAREN stmt_block   #FunctionDecl
              ;

class_decl : CLASS ID LCURLY class_body RCURLY                          #ClassDecl
           ;

class_body : ATTRIBUTES attributes_decl METHODS methods_decl            #ClassBodyDecl
           ;

attributes_decl : variable_decl+
                ;

methods_decl : function_decl+
             ;

type : 'INT' | 'BOOL' | 'VOID'
     ;

param_list : param (',' param)*                                         #ParamList
           ;

param : type ID
      ;

stmt_block : LCURLY stmt* RCURLY                
           ;             

stmt : stmt_block                                       #BlockStmt
     | variable_decl                                    #VariableDeclStmt
     | IF LPAREN expr RPAREN stmt (ELSE stmt)?          #IfElseStmt
     | RETURN expr? SEMICOLON                           #ReturnStmt
     | expr '=' expr SEMICOLON                          #AssignmentStmt
     | expr SEMICOLON                                   #ExprStmt
     ;

expr : ID LPAREN expr_list? RPAREN #FunctionCallExpr // function invocation, fn(3, 2), fn(), fn(var1)
     | '-' expr                    #UnaryMinusExpr
     | '!' expr                    #NotExpr
     | expr op=('*' | '/') expr    #MulDivExpr
     | expr op=('+' | '-') expr    #AddSubExpr
     | expr '==' expr              #EqExpr
     | expr '||' expr              #OrExpr
     | expr '&&' expr              #AndExpr
     | expr '>' expr               #GreaterthanExpr
     | expr '<' expr               #LessthanExpr
     | expr '>=' expr              #GreaterthanEqExpr
     | expr '<=' expr              #LessthanEqExpr
     | ID                          #VariableAtomExpr
     | INT                         #IntAtomExpr
     | BOOL                        #BoolAtomExpr
     | LPAREN expr RPAREN          #BracketExpr
     ;

expr_list : expr (',' expr)*       #FunctionParamExpr // function argument list
          ;
