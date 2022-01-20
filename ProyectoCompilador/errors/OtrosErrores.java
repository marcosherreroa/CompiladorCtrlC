package errors;

import alex.TokenValue;

public class OtrosErrores {
    private OtrosErrores() {}
    
    public static void missingReturn(TokenValue value) {
    	System.out.println("**\nFound a branch with no return");
    	System.out.println("in\n FUNCTION: "+value.lexema+"\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }
    
    public static void tooManyConstructors(TokenValue value) {
    	System.out.println("**\nA class can only have a constructor");
    	System.out.println("in\n CLASS: "+value.lexema+"\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
    }
    
}
