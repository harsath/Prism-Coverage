grammar Language;


@header {
package antlr;
}

/* Parser elements */

prog		:		(decl | expr)+ EOF			#Program

decl		:		ID ':' INT_TYPE '=' NUM		#IntVariableDeclaration
			|		ID ':' BOOL_TYPE '=' BOOL	#BoolVariableDeclaration
			|		CLASS ID					#ClassDeclaration
			;

expr		:		expr '*' expr				#MultiplicationExpression
			|		expr '+' expr				#AdditionExpression
			|		expr '>' expr 				#GreaterThanExpression
			|		expr '==' expr				#EqualsExpression
			|		'!' expr					#NotExpression
			|		ID							#IdentifierExpression
			|		NUM							#NumericExpression
			;

/* Lexical elements */

ID			:		[a-z][a-zA-Z0-9_]*;
NUM			:		'0' | '-'?[1-9][0-9]*;
BOOL		:		'true' | 'false';
INT_TYPE	:		'INT';
BOOL_TYPE	:		'BOOL';
COMMENT : '//' ~[\r\n]* -> skip;
WS : [ \t\n]+ -> skip;
NEWLINE : ('\r'?'\n'|'\r')+ -> skip;