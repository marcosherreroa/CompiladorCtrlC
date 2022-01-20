package ast;

import java.io.FileWriter;
import java.io.IOException;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;

public class SimpleLiteral extends Node implements Literal{
	private final TokenValue value;
	private final Type type;

	public SimpleLiteral(TokenValue value, Type type) {
		this.value = value;
		this.type = type;
	}

	public boolean isLiteral() {
		return true;
	}

	public boolean isLvalue() {
		return false;
	}

	public TokenValue getValue() {
		return value;
	}

	public Type getType() {
		return type;
	}

	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " SIMPLELITERAL\n");

		sb.append(value.toTree(lvl+1));

		return sb.toString();
	}

	public void bind(Binder b) {
		// Nothing to do
	}

	public Type typeCheckExp(Typer typer) {
		return type;
	}

	public void codeEvaluate(FileWriter writer) throws IOException { //asumimos que solo hay enteros y valores de verdad. No generamos c�digo para reales
        if(value.lexema.equals("true")) writer.write("i32.const 1\n");
        else if(value.lexema.equals("false")) writer.write("i32.const 0\n");
        else writer.write("i32.const "+value.lexema+"\n");
	}

	public int codeWriteTo(FileWriter writer, int profundidad, int delta) throws IOException {
		if(profundidad == 0)writer.write("i32.const 8\n");
		else writer.write("get_local $localsStart\n");

		writer.write("i32.const "+(4*delta)+"\n");
		writer.write("i32.add\n");

		codeEvaluate(writer);
		writer.write("i32.store\n");

		return delta+1;
	}

	public int passByValue(FileWriter writer, int delta) throws IOException {
		writer.write("get_global $SP\n");
		writer.write("i32.const "+(4*(delta+2))+"\n");
		writer.write("i32.add\n");

		codeEvaluate(writer);
		writer.write("i32.store\n");

		return delta+1;
	}
}
