package ast;

import java.io.FileWriter;
import java.io.IOException;

import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;

public interface ProgramClause {
	public String toTree(int lvl);
	public void bind(Binder b);
	public boolean checkNumberConstructors();
	public boolean typeCheckClause(Typer typer) throws Exception;
	public boolean checkReturns(); //devuelve false solo si es una funcion y no tiene return en todas sus ramas
	public Function checkMain();   // devuelve false si no es la función main
	public void sizeAndDeltas(DeltasInfo DI);
	public void codeInitialization(FileWriter writer) throws IOException;
	public void codeFunctions(FileWriter write) throws IOException;
}
