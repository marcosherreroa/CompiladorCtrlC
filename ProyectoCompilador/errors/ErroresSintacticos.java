package errors;

import alex.TokenValue;
import alex.UnidadLexica;

public class ErroresSintacticos {
	private ErroresSintacticos() {};
	
	public static void errorSintactico(TokenValue value) {
	     System.out.println("**\nError sintactico : "+value.lexema+"\n FICHERO: "+value.fichero+"\n FILA: "+value.fila+ "\n COLUMNA: "+value.columna+"\n**\n\n");
	   }
}
