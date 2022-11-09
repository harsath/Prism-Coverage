Prism Language

Authors: Anthony Iafrate
         Harsath Kaliappa Gounder Thangavel
         Uwais Kazi
         Robert Suwary

How to Use Our Language!:
java -jar prism.jar test1.prism

To run on eclipse:
    Once you import the zip file, run the PrismSourcefileParser.java found in app using a file prompt argument set in run configuration.
    When you are prompted for a file choose any of the three given test files found in the tests folder.



Syntax Overview:
    -- Variable Declarations --
    <TYPE> <NAME> = <EXPRESSION>;
    
    The types supported by our language are INT, BOOL, and VOID.
    
    Examples:
    INT  a = 1 + 2;
    BOOL b = true;
    INT  c = a;

    -- Variable Assignments --
    <NAME> = <EXPRESSION>;

    Similar to variable declarations except the name must refer to an existing declared variable.

    Example: Assuming we have already declared variable `a`.
    a = a + 1;
    
    -- IF Statements --
    IF (<CONDITION>) {
        <BODY>
    } ELSE {
        <BODY>
    }

    The given condition must be a logical expression which returns a boolean result.
    Some examples of a valid condition are:
        - true, false
        - 0 > 1 (returns false)
        - !true (returns false)
        - 12 >= 12 (returns true)
        - etc.
    
    Example:
    IF ( (1 > 0) && (false) ){
        RETURN true;
    } ELSE {
        RETURN false;
    }

    In this case we would return false.

    -- Functions --
    Functions are declared like so.
    FUNCTION <RETURN_TYPE> <NAME> (<ARGUMENTS>){
        <BODY>
    }

    The supported return types are INT, BOOL, and VOID.
    Your function can contain zero or more arguments.
    
    Example:
    FUNCTION BOOL ourFunc(BOOL x, BOOL y){
        RETURN x && y;
    }


    -- Classes --
    CLASS <NAME> {
        ATTRIBUTES
        <ATTRIBUTES>
        METHODS
        <METHODS>
    }

    The class body consists of two distinct sections.

    The attribute section is where you can declare zero or more variables.
    The methods section is where you can declare zero or more functions.

    Example:
    CLASS ourClass {
        ATTRIBUTES
        INT x = 4;
        INT y = 2;
        METHODS
        FUNCTION INT divide(INT a, INT b) {
            RETURN a / b;
        }
        FUNCTION INT main() {
            INT result = divide(x, y);
            RETURN result;
        }
    }
