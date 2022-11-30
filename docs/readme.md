## The Prism Programming Language

### How to use the interpreter? 
#### Running from IDE
Once the project has been imported into an IDE, run the `PrismSourcefileInterpreter.java` file found in `src/app`. A file prompt will be opened for an input Prism source file.
#### Running from JAR	
Once a runnable JAR file has been created with `PrismSourcefileInterpreter.java` as `main()`, use the interpreter using the following syntax:
```
$ java -jar prism-interpreter.jar input-source-file.prism
```

### Syntax Overview
A Prism source file must contain a `main()` method which marks the start of execution. For sample Prism programs, please look into `src/prism_source_files/robust_tests` directory.
#### Variable declaration and assignments
Supported types: `INT`, `BOOL`, `STRING`, `VOID`, and CLASS types.
Syntax is as follows:
```
<TYPE> <VARIABLE NAME> = <EXPRESSION>;

INT a = 1 * some_function();
STRING b = "Hello world";
BOOL c = false &&  some_function();
ClassType d = NEW ClassType();
```

Variable assignment syntax is as follows:
```
<VARIABLE NAME> = <EXPRESSION>;

a = a * 10;
```

### IF statements
`IF` statement syntax is as follows:
```
IF (<CONDITION>) {
	<BODY>
} ELES {
	<BODY>
}

IF ((variable_one > 15) && (some_function() != 15)) {
	RETURN true;
} ELSE {
	RETURN false;
}
```
The `ELSE` block is optional.
The `<CONDITION>` block must be a logical expression that returns a `BOOL` result. Some examples of valid conditions:
* `true`, `false`
* `12 == some_function()` (may return `false` or `true`)
* `some_function() && (boolean_variable == true)` (returns `false`)
* 12 >= 12 (returns `true`)

### Functions
Function declaration syntax is as follows:
```
FUNCTION <RETURN TYPE> <NAME> (<ARGUMENTS>) {
	<BODY>
}
```
The `<ARGUMENTS>` is declared as `<TYPE> <ARGUMENT NAME>`, example, `INT argument_one, STRING argument_two`.

#### Function calls
Functions can be called as follows:
```
<RETURN TYPE> <VARIABLE NAME> = <FUNCTION NAME>(<ARGUMENTS>);
<FUNCTION NAME>(<ARGUMENTS>)

INT value_one = some_function("Hello world", false);
some_function(100, "Hello world")
```
Prism language also supports recursion!

### Loops
Both `WHILE` and `FOR` loops support `BREAK` and `CONTINUE` statements. `BREAK` breaks the loop and goes outside, and `CONTINUE` forces the next iteration of the loop to take place.
#### WHILE loop
`WHILE` loops can be declared using the following syntax:
```
WHILE (<CONDITION>) {
	<BODY>
}

INT i = 1;
WHILE (i > 10) {
	PRINTLN(i);
	i = i + 1;
}
```
The while block's `<BODY>` executes as long as the `<CONDITION>` results in `true`. Updations to conditional variables should be placed within the `<BODY>`.

#### FOR Loop
`FOR` loops can be declared using the following syntax:
```
FOR (<INITIALIZATION BLOCK>; <CONDITIONAL BLOCK>; <UPDATION BLOCK>;) {
	<BODY>
}

FOR (INT i = 0; i < 10; i = i + 1;) {
	PRINTLN(i);
}
```

### Class
Prism language also supports classes, also called as user defined data types. The syntax is as follows:
```
CLASS <NAME> {
	ATTRIBUTES
	<ATTRIBUTES>
	METHODS
	<METHODS>
}

CLASS Rectangle {
	ATTRIBUTES
	INT length = 0;
	INT width = 0;
	METHODS
	FUNCTION VOID setHeight(INT input_height) {
		length = input_height;	
	}
	FUNCTION VOID setWidth(INT input_width) {
		width = input_width;
	}
	FUNCTION INT getArea() {
		RETURN (length * width);
	}
}
```
Class body consist of two distinct sections: `ATTRIBUTES` and `METHODS`
* In `ATTRIBUTES` section we declare the attributes of a class.
* In `METHODS` section we declare the member functions corresponding to the class.

Attributes and member functions can be invoked from `main()` or other functions is as follows:
```
Rectangle rectangle_class = NEW Rectangle();
rectangle_class.setHeight(10); // also be set using rectangle_class.height = 10
rectangle_class.setWidth(50); // also be set using rectangle_class.width = 50
INT area = rectangle_class.getArea();
```

### Builtin functions
Prism language supports these builtin functions:
* `PRINT(<EXPRESSION>)` - prints `<EXPRESSION>` (such as a literal value or a variable) to standard out.
* `PRINTLN(<EXPRESSION>)` - prints `<EXPRESSION>` into standard out with a new line character.
* `MIN(<EXPRESSION ONE>, <EXPRESSION TWO>)` - returns the minimum of the two expressions passed in.
* `MAX(<EXPRESSION ONE>, <EXPRESSION TWO>)` - returns the maximum of the two expressions passed in.
* `POW(<EXPRESSION ONE>, <EXPRESSION TWO>)` - returns the value of `<EXPRESSION ONE>` raised to the power of `<EXPRESSION TWO>` (`EXPR ONE` ^ `EXPR TWO`).
