package asint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import alex.ALexico;
import alex.UnidadLexica;
import asem.Binder;
import asem.Typer;
import ast.Function;
import ast.Program;
import java_cup.runtime.Symbol;

public class Main {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(args[0]));
	 ALexico alex = new ALexico(input);
   alex.fijaPath(args[0]);
   
   System.out.println("==============");
   System.out.println("LEXING");
   System.out.println("==============");
   System.out.println();
   
   
   UnidadLexica unidad = null;
     do {
       unidad = (UnidadLexica) alex.next_token();
       System.out.println(unidad);
     }
     while (unidad == null || unidad.clase() != ClaseLexica.EOF);
     System.out.println();
     
     int lexErrors = alex.getLexErrors();
     if(lexErrors > 0) {
    	 System.out.println("There were "+lexErrors+" errors in lexing phase. Aborting");
    	 return;
     }
     

     input = new InputStreamReader(new FileInputStream(args[0]));
     alex = new ALexico(input);
     alex.fijaPath(args[0]);
     
     System.out.println("==============");
     System.out.println("PARSING");
     System.out.println("==============");
     System.out.println();
     
     ASintactico asint = new ASintactico(alex);
     Program ast = (Program)asint.parse().value;
     
     System.out.println(ast); //COMENTAR ESTO PARA NO MOSTRAR EL AST
     System.out.println();
     
     int parseErrors = asint.getParseErrors();
     if(parseErrors > 0) {
    	 System.out.println("There were "+parseErrors+" errors in parsing phase. Aborting");
    	 return;
     }
     
     System.out.println("==============");
     System.out.println("BINDING");
     System.out.println("==============");
     System.out.println();

     Binder b = new Binder();
     ast.bind(b);
     if(b.getError()) {
    	 System.out.println("There were errors in binding phase. Aborting");
    	 return;
     }
     
     System.out.println();
     
     System.out.println("==============");
     System.out.println("TYPE-CHECKING");
     System.out.println("==============");
     System.out.println();

     boolean OK = ast.checkNumberConstructors();
     OK = ast.typeCheck(new Typer()) && OK;
     OK = ast.checkReturns() && OK;
     
     Function mainFunction = ast.checkMain();
     
     if(!OK || mainFunction == null) {
    	 System.out.println("There were errors in typing phase. Aborting");
    	 return;
     }
     
     System.out.println("Correctly typed\n");
     
     System.out.println("==============");
     System.out.println("CODE GENERATION");
     System.out.println("==============");
     System.out.println();

     System.out.println("Computing deltas and maxStackSize:");
     ast.sizeAndDeltas();
     System.out.println();
     
     System.out.println("Generating wat code");
     FileWriter writer = new FileWriter(new File("main.wat"));
     ast.code(writer);
     writer.close();
     
     
     System.out.println("Code generated to main.wat\n");
     
 }
}
