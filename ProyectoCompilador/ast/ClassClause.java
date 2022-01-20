package ast;

import codeGeneration.DeltasInfo;

public interface ClassClause extends ProgramClause {
	public void sizeAndDeltas(DeltasInfo DI);
	public Attribute searchAttribute(String idLexema);
	public Method searchMethod(String idLexema);
	public Constructor searchConstructor();
}
