package ast;

import java.io.FileWriter;
import java.io.IOException;

import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;

public interface Statement {
	public String toTree(int lvl);
	public void bind(Binder b);
	public boolean checkNumberConstructors();
	public boolean typeCheckClause(Typer typer) throws Exception;
	public boolean searchEndReturnStm(); // devuelve true si esta instrucción está correctamente finalizada en todas
	                                     // sus ramas con un return
	public void sizeAndDeltas(DeltasInfo DI);
	public void codeStm(FileWriter writer, int breakLabel, int continueLabel) throws IOException;
}
