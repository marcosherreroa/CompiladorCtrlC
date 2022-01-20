package ast;

import java.io.FileWriter;
import java.io.IOException;

import asem.Binder;
import asem.Typer;

public class Input implements Expression {
     private final Expression prompt;

	public Input(Expression prompt) {
		this.prompt = prompt;
	}

	public boolean isLiteral() {
		return false;
	}

	public boolean isLvalue() {
		return false;
	}

	public String toTree(int lvl) {
        StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " INPUT\n");

		if(prompt != null) sb.append(prompt.toTree(lvl+1));

		return sb.toString();
	}


	public void bind(Binder b) {
		if(prompt != null) prompt.bind(b);
	}

	public Type typeCheckExp(Typer typer) throws Exception {
		if(prompt != null)prompt.typeCheckExp(typer);
		return BasicType.INT;
	}

	public void codeEvaluate(FileWriter writer) throws IOException {
		if(prompt != null) {
			prompt.codeEvaluate(writer);
			writer.write("call $print\n");
		}

		writer.write("call $read\n");
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
