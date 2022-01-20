package ast;

import java.io.FileWriter;
import java.io.IOException;

import asem.Binder;
import asem.Typer;

public abstract class Lvalue implements Expression {
	//protected Type type;
	//protected int profundidad; //0 si es global, 1 si es local

//	public Lvalue() {
//		this.profundidad = -1;
//	}

	public boolean isLvalue() {
		return true;
	}

//	public Type getType() {
//		return type;
//	}

//	public int getProfundidad() {
//		return profundidad;
//	}

	public void codeEvaluate(FileWriter writer) throws IOException {
		codeAddress(writer);
		writer.write("i32.load\n");
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
		//src
		codeAddress(writer);

		//dest
		writer.write("get_global $SP\n");
		writer.write("i32.const "+(4*(delta+2))+"\n");
		writer.write("i32.add\n");

		//n
		int sz = size();
		writer.write("i32.const "+sz+"\n");

		//llamar a copyn
		writer.write("call $copyn\n");

		return delta+sz;
	}

	public void passByReference(FileWriter writer, int delta) throws IOException {
		writer.write("get_global $SP\n");
		writer.write("i32.const "+(4*(delta+2))+"\n");
		writer.write("i32.add\n");

		codeAddress(writer);
		writer.write("i32.store\n");
	}

	public abstract String toTree(int lvl);
	public abstract int size();
	public abstract boolean isAttributeFrom(Clase c);
	public abstract void bind(Binder b);
	public abstract Function typeCheckFun(Typer typer) throws Exception;
	public abstract void codeAddress(FileWriter writer) throws IOException;
}
