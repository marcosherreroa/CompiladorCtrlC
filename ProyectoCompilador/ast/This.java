package ast;

import java.io.FileWriter;
import java.io.IOException;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import errors.ErroresTyping;

public class This extends Lvalue {
	private final TokenValue v;
	private Clase def;
	
	public This(TokenValue v) {
		this.v = v;
		this.def = null;
	}
	
	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " THIS\n");
		
		return sb.toString();
	}
	
	public boolean isLiteral() {
		return false;
	}
	
	public int size() {
		return def.getSize();
	}
	
	public boolean isAttributeFrom(Clase c) {
		return false;
	}
	
	public void bind(Binder b) {
		//lo vinculamos en tipos mejor
	}
	
	public Function typeCheckFun(Typer typer) {
		return null;
	}

	public Type typeCheckExp(Typer typer) throws Exception {
		def  = typer.getCurrentClass();
		if(def == null) {
			ErroresTyping.thisOutsideClass(v);
			return null;
		}
		
		return new ClassType(def);
	}


	@Override
	public void codeAddress(FileWriter writer) throws IOException {
		//No generamos codigo para esto
	}

}
