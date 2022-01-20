package ast;

import java.io.FileWriter;
import java.io.IOException;

import alex.TokenValue;
import asem.Binder;
import codeGeneration.DeltasInfo;

public class Parameter implements Definition{
	private final Identifier identifier;
	private final boolean byReference;
	private Type type;

	private int delta;


	public Parameter(Identifier identifier, boolean byReference, Type type) {
		this.identifier = identifier;
		this.byReference = byReference;
		this.type = type;

		this.delta = -1;
	}

	public TokenValue getValue() {
		return identifier.getValue();
	}

	public int getDelta() {
		return delta;
	}

	public boolean isByReference() {
		return byReference;
	}

	public Type getType(boolean constant) {// no es un tipo
		return null;
	}

	public Type getVariableType() {
		return type;
	}

	public Function getFunction() {// no es una funcion
		return null;
	}

	public String toTree(int lvl) {
		StringBuilder sb;

		if(byReference) sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " PARAMETER BY REFERENCE\n");
		else sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " PARAMETER BY VALUE\n");

		sb.append(type.toTree(lvl+1)+"\n");
		sb.append(identifier.toTree(lvl+1));

		return sb.toString();
	}

	public void bind(Binder b) {
		type.bind(b);

		b.insertId(identifier.getValue(), this);
	}

	public boolean typeCheckParameter() throws Exception {
		//solo permitimos arrays declarados sin dimension como parametros si se pasan por referencia
		//ademas , para arrays multidimensionales, la unica dimensión que podrá faltar será la primera, como ocurre en C
		if(byReference) type = type.typeCheckIncompleteType(false);
		else type = type.typeCheckCompleteType();

		if(type == null) return false;
		else return true;
	}

	public void sizeAndDeltas(DeltasInfo DI) {
		delta = DI.getC();
		
		if(byReference) DI.setC(DI.getC()+1);
		else DI.setC(DI.getC()+ type.size());
		
		if(DI.getC() > DI.getMax())DI.setMax(DI.getC());
		
		System.out.println("DECLARATION :: Lexema: "+ identifier.getValue().lexema+ " || Delta : "+ delta);
	}

	public void codeAddressIden(FileWriter writer) throws IOException {
		writer.write("i32.const "+(4*delta)+"\n");
		writer.write("get_local $localsStart\n");
		writer.write("i32.add\n");
		if(byReference) writer.write("i32.load\n");
	}
}
