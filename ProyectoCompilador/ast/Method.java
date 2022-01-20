package ast;

import java.util.List;

public class Method extends Function implements ClassClause{
    protected final String visibility;

	public Method(Identifier identifier, MaybeType returnType, List<Parameter> paramList , DependentBlock body , String visibility) {
		super(identifier, returnType, paramList, body);
		this.visibility = visibility;
	}
    
	public String getVisibility() {
		return visibility;
	}
    
	public int size() {
		return 0;
	}
	
	public Attribute searchAttribute(String idLexema) { //not an Attribute;
		return null;
	}
	
	public Method searchMethod(String idLexema) {
		if(identifier.getValue().lexema.equals(idLexema)) return this;
		else return null;
	}
	
	public Constructor searchConstructor() {
		return null;
	}
	
	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl +
				" METHOD : " + visibility +"\n");
		
		sb.append(super.toTree(lvl+1));
		
		return sb.toString();
	}
}
