package errors;

public class ErroresLexicos {
    private ErroresLexicos() {}
    
    public static void unmatchedInput(int fila, int columna, String lexema) {
    	System.out.println("** Unmatched input. FILA: "+fila+ ", COLUMNA: "+columna+", LEXEMA: "+lexema+" **");
    }
    
    public static void unterminatedString(int fila, int columna, String lexema) {
    	System.out.println("** Unterminated string. FILA: "+fila+ ", COLUMNA: "+columna+" **");
    }
    
    public static void forbidden(int fila, int columna, String lexema) {
    	System.out.println("** Forbidden character. FILA: "+fila+ ", COLUMNA: "+columna+", LEXEMA: "+lexema+" **");
    }
   
    public static void forbiddenInString(int fila, int columna, String lexema) {
    	System.out.println("** Forbidden character in string literal. FILA: "+fila+ ", COLUMNA: "+columna+", LEXEMA: "+lexema+" **");
    }
    
    public static void missingPath(int fila, int columna, String lexema) {
    	System.out.println("** Expecting a path between double quotes after #include. FILA: "+fila+", COLUMNA: "+columna+", LEXEMA: "+lexema+" **");
    }
    
}
