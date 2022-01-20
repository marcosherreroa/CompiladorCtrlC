package alex;

import java_cup.runtime.Symbol;


public class UnidadLexica extends Symbol {

   public UnidadLexica(String fichero, int fila, int columna, int clase, String lexema) {
       super(clase,new TokenValue(lexema, fichero, fila, columna));
   }
   public int clase () {return sym;}
   public String lexema() {return ((TokenValue)value).lexema;}
   public String path() {return ((TokenValue)value).fichero;}
   public int fila() {return ((TokenValue)value).fila;}
   public int columna() {return ((TokenValue)value).columna;}

   public String toString() {
     return "FICHERO: "+path() + " FILA: "+fila() +", COLUMNA: "+columna() +" CLASE: "+clase()+",LEXEMA: "+lexema() ;
   }

}
