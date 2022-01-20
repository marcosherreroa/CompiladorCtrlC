package asem;

import java.util.ArrayDeque;
import java.util.Deque;

import ast.Clase;

public class Typer { //el binding de ciertas cosas de clases conviene hacerlo en la fase de type-checking
	public boolean constructor;
    private final Deque<Clase> claseStack; 
    
    public Typer() {
    	this.constructor = false;
    	this.claseStack = new ArrayDeque<Clase>();
    }
    
    public void openConstructor() {
    	constructor = true;
    }
    
    public void closeConstructor() {
    	constructor = false;
    }
    
    public void openClase(Clase c) {
    	claseStack.push(c);
    }
    
    public void closeClase() {
    	claseStack.pop();
    }
    
    public Clase getCurrentClass() {
    	return claseStack.peekFirst();
    }
    
    public boolean insideClass(String idClase) {
    	boolean inside = false;
    	for(Clase c : claseStack) {
    		if(c.getValue().lexema.equals(idClase)) {
    			inside = true;
    			break;
    		}
    	}
    	
    	return inside;
    }
    
    public boolean inConstructor() {
    	return constructor;
    }
}
