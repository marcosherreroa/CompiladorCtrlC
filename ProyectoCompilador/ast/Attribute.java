package ast;

import asem.Typer;

public class Attribute extends Declaration implements ClassClause {
     private final String visibility;
     private Clase clase;

	public Attribute(Identifier identifier, Type type, String visibility) {
		super(identifier, type, null, null);
		this.visibility = visibility;
		this.clase = null;
	}
	
	public String getVisibility() {
		return visibility;
	}
	
	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl +
				" ATTRIBUTE : " + visibility +"\n");
		
		sb.append(super.toTree(lvl+1));
		
		return sb.toString();
	}
	
	public Attribute searchAttribute(String idLexema) {
		if(identifier.getValue().lexema.equals(idLexema)) return this;
		else return null;
	}
	
	public Method searchMethod(String idLexema) {// not a method
		return null;
	}
	
	public Constructor searchConstructor() { // not a constructor
		return null;
	}
	
	public boolean typeCheckClause(Typer typer) throws Exception {
		clase = typer.getCurrentClass();
		return super.typeCheckClause(typer);
	}
	
}
