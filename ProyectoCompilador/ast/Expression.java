package ast;

import java.io.FileWriter;
import java.io.IOException;

import asem.Binder;
import asem.Typer;

public interface Expression {
	public String toTree(int lvl);
	public boolean isLiteral();
	public boolean isLvalue();
	public void bind(Binder b);
	public Type typeCheckExp(Typer typer) throws Exception;
	public void codeEvaluate(FileWriter writer) throws IOException;
	public int codeWriteTo(FileWriter writer,int profundidad, int delta) throws IOException;//devuelve el siguiente delta
	public int passByValue(FileWriter writer, int delta) throws IOException;
}
