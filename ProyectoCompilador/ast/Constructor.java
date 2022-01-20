package ast;

import java.util.List;

import asem.Binder;
import asem.Typer;
import errors.ErroresTyping;

public class Constructor extends Method implements ClassClause {
	private Clase clasRef;

	public Constructor(Identifier id, List<Parameter> pL, DependentBlock b, String visibility) {
	    super(id,id,pL, b, visibility);
	    this.clasRef = null;
	}
    
	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl +
				" CONSTRUCTOR : " + visibility +"\n");
	
		sb.append(super.toTree(lvl+1));
		
		return sb.toString();
	}
	
	public Constructor searchConstructor() {
		return this;
	}
	
	public void bind(Binder b) {
    	b.openBlock();
    	
    	for(Parameter p : paramList) p.bind(b);
    	
    	b.openFunction(this);
    	
    	body.bind(b);
    	
    	b.closeFunction();
    	
    	b.closeBlock();
    }
	
	public boolean typeCheckClause(Typer typer) throws Exception {
    	boolean OK = true;
    	
    	typer.openConstructor();
    	
    	Clase c = typer.getCurrentClass(); //creo que nunca va a ser null
    	if(!c.getValue().lexema.equals(identifier.getValue().lexema)) {
    		ErroresTyping.constructorBadName(identifier.getValue());
    		OK = false;
    	}
    	else {
    		clasRef = c;
        	returnType = new ClassType(c);
    	}
    	
    	for(Parameter p : paramList) {
    		OK = p.typeCheckParameter() && OK;
    	}
    	
    	OK = body.typeCheckClause(typer) && OK;
    	
    	typer.closeConstructor();
    	
    	return OK;
    }
	
	public boolean checkReturns() {
		return true;
	}
	
}
