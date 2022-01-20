package ast;

import java.io.FileWriter;
import java.io.IOException;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import errors.ErroresTyping;

public class Dereference extends Lvalue {
   private final Lvalue lv;
   private final TokenValue v;
   
   private Type referencedType;

	public Dereference(Lvalue lv, TokenValue v) {
		this.lv = lv;
		this.v = v;
		this.referencedType = null;
	}
	
	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " DEREFERENCE\n");
		
		sb.append(lv.toTree(lvl+1));
		
		return sb.toString();
	}
	
	public boolean isLiteral() {
		return false;
	}

	public int size() {
		return referencedType.size();
	}
	
	public boolean isAttributeFrom(Clase c) {
		return false;
	}
	
	public void bind(Binder b) {
		lv.bind(b);
	}

	public Type typeCheckExp(Typer typer) throws Exception {
		Type t = lv.typeCheckExp(typer);
		
		if (t == null) return null;
		else {
			Type referencedType = t.getPointeeType();
			if(referencedType == null) {
				ErroresTyping.expectedPointer(v);
				return null;
			}
			else return referencedType;
		}

	}
	
	public Function typeCheckFun(Typer typer) { // no es una funcion
		return null;
	}
	
	public void codeEvaluate(FileWriter writer) throws IOException {
		codeAddress(writer);
		writer.write("i32.load\n");
	}

	public void codeAddress(FileWriter writer) throws IOException {
		lv.codeEvaluate(writer);
	}
	
}
