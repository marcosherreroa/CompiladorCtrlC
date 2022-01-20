package errors;

import alex.TokenValue;

public class ErroresBinding {
    private ErroresBinding() {}
    
    public static void alreadyDefined(TokenValue oldid , TokenValue newid) {
    	System.out.println("\n\n**\nError defining "+newid.lexema+"\n FICHERO: "+newid.fichero+"\n FILA: "+newid.fila+ "\n COLUMNA: "+newid.columna);
    	System.out.println("already defined in \n FICHERO: "+ oldid.fichero+"\n FILA: "+oldid.fila+ "\n COLUMNA: "+oldid.columna +"\n**\n\n");
    }
    
    public static void notDeclared(TokenValue id) {
    	System.out.println("\n\n**\nUndeclared identifier "+ id.lexema +"\n FICHERO: "+id.fichero+"\n FILA: "+id.fila+ "\n COLUMNA: "+id.columna+"\n**\n\n");
    }
    
    public static void invalidOutsideLoop(TokenValue id) {
    	System.out.println("\n\n**\nInvalid statement outside loop : "+id.lexema);
    	System.out.println(" FICHERO: "+id.fichero+"\n FILA: "+id.fila+ "\n COLUMNA: "+id.columna+"\n**\n\n");
    }
    
}
