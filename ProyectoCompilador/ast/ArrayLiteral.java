package ast;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import errors.ErroresTyping;

public class ArrayLiteral extends Node implements Literal {
	 private final TokenValue value;
	 private Type type;
     private final List<Literal> elements;
     
     public ArrayLiteral(TokenValue value, List<Literal> elements) {
    	 this.value = value;
    	 type = null;
    	 this.elements = elements;
     }
     
     public boolean isLiteral() {
    	 return true;
     }
     
     public boolean isLvalue() {
    	 return false;
     }
     
     public String toTree(int lvl) {
 		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " ARRAYLITERAL\n");
 		
 		return sb.toString();
 	}
     
     public void bind(Binder b) {
 		// Nothing to do
 	}
     
     public Type typeCheckExp(Typer typer) throws Exception {
    	boolean OK = true;
    	Type taux = null; 
    	 
 		for(Literal e : elements) {
 			taux = e.typeCheckExp(typer);
 			if(type != null && !type.compatibleWith(taux)) {
 				ErroresTyping.differentTypesArray(value);
 				OK = false;
 			}		
 			type = taux;
 		}
 		
 		if(OK) return new ArrayType(type, elements.size(), value);
 		else return null;
 	}

	public void codeEvaluate(FileWriter writer) throws IOException {
		System.out.println("Error : No deberiamos llegar aqui\n");
	}
	
	public int codeWriteTo(FileWriter writer, int profundidad, int delta) throws IOException {
		for(Literal e: elements) {
			delta = e.codeWriteTo(writer,profundidad,delta);
		}
		
		return delta;
	}
	
	public int passByValue(FileWriter writer, int delta) throws IOException {
		for(Literal e : elements) {
			delta = e.passByValue(writer, delta);
		}
		
		return delta;
	}
}
