package ast;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuilder;
import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import errors.ErroresTyping;

public class Identifier extends Lvalue implements Type {
	private final TokenValue value;
	private Definition def;
	private boolean constant;
	
	
	public Identifier(TokenValue value) {
		this.value = value;
		this.def = null;
		this.constant = false;
	}
	
	public TokenValue getValue() {
		return value;
	}
	
	public boolean isLiteral() {
		return false;
	}
	
	public Type getElementType() {
		System.out.println("Aqui no deber�amos llegar");
		return null;
	}
	
	public Type getPointeeType() { 
		System.out.println("Aqui no deber�amos llegar");
		return null;
	}
	
	public Clase getClase() {
		System.out.println("Aqui no deber�amos llegar");
		return null;
	}
	
	public int getDimension() {
		System.out.println("Aqui no deber�amos llegar");
		return -1;
	}
	
	public void setDimension(Type t) {
		System.out.println("Aqui no deber�amos llegar");
	}
	
	public boolean isConst() {
		System.out.println("Aqui no deber�amos llegar");
		return constant;
	}
	
	public void setConst() {
		this.constant = true;
	}
	
	public boolean isReturnable() {
		System.out.println("Aqui no deberiamos llegar");
		return true;
	}
	
	public boolean isVoid() {
		System.out.println("Aqui no deberiamos llegar");
		return false;
	}

	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " IDENTIFIER : ");
		
		sb.append(value.toString()+"\n");
		
		return sb.toString();
	}
	
	public boolean isAttributeFrom(Clase c) {
		return def.getClass() == Attribute.class;
	}
	
	public int size(){ 
		return def.getVariableType().size();
	}

	public void bind(Binder b) {
	    def = b.searchId(value);
	}
	
	public Type typeCheckCompleteType(){
		Type t = def.getType(constant);
		if(t == null) {
			ErroresTyping.expectedType(value);
			return null;
		}
		
		if(constant)t.setConst();
		
		return t;
	}
	
	public Type typeCheckIncompleteType(boolean recursive) {
		return typeCheckCompleteType();
	}
	
	public Type typeCheckExp(Typer typer) {
	    Type t = def.getVariableType();
		if(t == null) ErroresTyping.expectedVariable(value);
		
		return t;
	}
	

	public Function typeCheckFun(Typer typer) {
		Function f = def.getFunction();
		if(f == null) ErroresTyping.expectedFunction(value);
		return f;
	}


	public boolean compatibleWith(Type t)  {
		System.out.println("Error Identifier : no deberiamos llegar hasta aqui\n");
		return false;
	}

	public Type deepCopy(boolean constant) { 
		System.out.println("Error Identifier : no deberiamos llegar hasta aqui\n");
		return null;
	}
	
	public void codeAddress(FileWriter writer) throws IOException {
		def.codeAddressIden(writer);
	}

	
}
