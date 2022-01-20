package alex;

public class TokenValue {
    public String lexema;
    public String fichero;
    public int fila;
    public int columna;
    public TokenValue(String lexema, String fichero, int fila, int columna) {
    	this.lexema = lexema;
      this.fichero = fichero;
    	this.fila = fila;
    	this.columna = columna;
    }

    public String toString() {
        return "FICHERO: "+ fichero +", FILA: "+fila +", COLUMNA: "+columna +" LEXEMA: "+lexema ;
      }

    public String toTree(int lvl) {
    	StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl +" TOKENVALUE: "+ toString() +"\n");
    	return sb.toString();
    }
}
