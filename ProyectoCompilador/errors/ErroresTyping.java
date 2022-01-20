package errors;

import alex.TokenValue;
import ast.Expression;
import ast.MaybeType;
import ast.Type;

public class ErroresTyping {
    private ErroresTyping() {}

    public static void expectedLiteral(String expectedType, TokenValue value) {
    	System.out.println("**\nExpected literal of type "+expectedType+" in\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void expectedVariable(TokenValue value) {
    	System.out.println("**\nExpected variable in \n LEXEMA : "+value.lexema+"\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void expectedFunction(TokenValue value) {
    	System.out.println("**\nExpected function in\n LEXEMA : "+value.lexema+"\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void expectedClase(TokenValue value) {
    	System.out.println("**\nExpected clase in\n LEXEMA : "+value.lexema+"\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

//    public static void expectedArray(TokenValue value) {
//    	System.out.println("**\nExpected array in\n LEXEMA : "+value.lexema+"\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
//    }

    public static void expectedPointer(TokenValue value) {
    	System.out.println("**\nExpected pointer in\n LEXEMA : "+value.lexema+"\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void expectedArrayOrPointer(TokenValue value) {
    	System.out.println("**\nExpected array or pointer to array in\n LEXEMA : "+value.lexema+"\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void expectedType(TokenValue value) {
    	System.out.println("**\nExpected type in\n LEXEMA : "+value.lexema+"\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void expectedExpressionType(TokenValue value, Type t, String opnd) {
    	System.out.println("**\nExpected expression of type:");
    	System.out.println(t);
    	System.out.println("in "+opnd+" operand of:");
    	System.out.println(" FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void invalidReturnType(TokenValue value, MaybeType t) {
    	System.out.println("\n\n**\nInvalid return type :");
    	System.out.println(t);
    	System.out.println("in\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void returnVoid(TokenValue value) {
    	System.out.println("\n\n**\nCannot return an expression at a function labeled as void :");
    	System.out.println("in\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void usingVoid(TokenValue value) {
    	System.out.println("\n\n**\nCannot use in an expression a function call to a function returning void :");
    	System.out.println("in\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void incomparableExpressions(TokenValue value, Type t1, Type t2) {
    	System.out.println("**\nCannot compare expressions of types");
    	System.out.println(t1);
    	System.out.println("and");
    	System.out.println(t2);
    	System.out.println("in FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");

    }

    public static void collisionVarValue(TokenValue value, Type varType, Type expType ) {
    	System.out.println("**\nCannot convert expression of type:");
    	System.out.println(expType);
    	System.out.println("to type:");
    	System.out.println(varType);
    	System.out.println("in\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");

    }


    public static void collisionArgParam(TokenValue value, Type argType, Type paramType, int ind) {
    	System.out.println("**\nCannot convert expression of type:");
    	System.out.println(argType);
    	System.out.println("to type:");
    	System.out.println(paramType);
    	System.out.println("in argument "+ind+" of\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void differentNumberArgs(TokenValue value, int nargs, int nparams) {
    	System.out.println("**\nCannot call a function of "+nparams+" parameters with "+nargs+" arguments");
    	System.out.println("in\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void assigningConst(TokenValue value) {
    	System.out.println("**\nCannot assign to constant variable");
    	System.out.println("in\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void referenceNonLvalue(TokenValue value) {
    	System.out.println("**\nCannot take a reference & of something that is not an lvalue");
    	System.out.println("in\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void assigningArray(TokenValue value) {
    	System.out.println("**\nCannot assign to array beyond declaration :");
    	System.out.println("in\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }
    
    public static void initializingWithArray(TokenValue value) {
    	System.out.println("**\nArrays may only be initialized with an array literal:");
    	System.out.println("in\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }


    public static void positiveDimension(TokenValue value) {
    	System.out.println("**\nDimension of array must be positive");
    	System.out.println("in\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void requiresSize(TokenValue value) {
    	System.out.println("**\nRequired giving size to array");
    	System.out.println("in\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void differentTypesArray(TokenValue value) {
    	System.out.println("**\nAll elements of array must have compatible types");
    	System.out.println("FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void passByReference(TokenValue value, Expression e, int ind) {
    	System.out.println("**\nInvalid initialization of argument "+ind+" (passed by reference) from rvalue");
    	System.out.println(e);
    	System.out.println("FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void visibilityError(TokenValue value) {
    	System.out.println("**\nCannot access private attribute");
    	System.out.println("FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void thisOutsideClass(TokenValue value) {
    	System.out.println("**\nKeyword this cannot appear ouside of class");
    	System.out.println(" FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }

    public static void constructorBadName(TokenValue value) {
    	System.out.println("**\nA constructor must have the same name as its class");
    	System.out.println(" FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }
}
