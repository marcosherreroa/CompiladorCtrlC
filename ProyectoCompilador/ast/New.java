package ast;

import java.io.FileWriter;
import java.io.IOException;

import asem.Binder;
import asem.Typer;

public class New extends Node implements Expression{
	private Type type;

	public New(Type type) {
		this.type = type;
	}

	public boolean isLiteral() {
		return false;
	}

	public boolean isLvalue() {
		return false;
	}

	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl
				+ " UNARY EXPRESSION \n");

		sb.append(" ".repeat(2*(lvl+1)) + (lvl+1) + " OPERATOR: new \n");
		sb.append(type.toTree(lvl+1));

		return sb.toString();
	}

	public void bind(Binder b) {
		type.bind(b);
	}

	public Type typeCheckExp(Typer typer) throws Exception {
		type = type.typeCheckCompleteType();

		return new PointerType(type);
	}

	public void codeEvaluate(FileWriter writer) throws IOException {
		writer.write("i32.const "+(4*type.size())+"\n");
		writer.write("call $reserveHeap\n");
		writer.write("get_global $NP\n");
	}

	public int codeWriteTo(FileWriter writer,int profundidad, int delta) throws IOException {
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
