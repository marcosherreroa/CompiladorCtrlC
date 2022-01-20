package asem;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import alex.TokenValue;
import ast.Definition;
import ast.Function;
import ast.Loop;
import errors.ErroresBinding;

public class Binder {
	private boolean error;
    private final Deque<Map<String,Definition>> bindingStack;
    private Function currentFunction;
    private final Deque<Loop> loopStack;
    
    public Binder() {
    	this.error = false;
    	this.bindingStack = new ArrayDeque<Map<String,Definition>>();
    	this.currentFunction = null;
    	this.loopStack = new ArrayDeque<Loop>();
    }
    
    public boolean getError() {
    	return error;
    }
    
    
    public void openBlock() {
    	bindingStack.push(new HashMap<String,Definition>());
    }
    
    public void closeBlock() {
    	bindingStack.pop();
    }
    
    public void insertId(TokenValue id, Definition def) {
    	System.out.println("Declared : "+id);
    	
  
    	if(bindingStack.getFirst().containsKey(id.lexema)) {
    		ErroresBinding.alreadyDefined(bindingStack.getFirst().get(id.lexema).getValue(), id);
    		error = true;
    	}
    	else bindingStack.getFirst().put(id.lexema, def);
    }
    
    public Definition searchId(TokenValue id) {
    	System.out.println("Referenced :"+id);
    	
    	Definition def = null;
    	
    	for(Map<String,Definition> map : bindingStack) {
    		def = map.get(id.lexema);
    		if (def != null) break;
    	}
    	
    	if(def == null) {
    		ErroresBinding.notDeclared(id);
    		error = true;
    	}
    	return def;
    }
    
    public void openFunction(Function f) {
    	currentFunction = f;
    }
    
    public void closeFunction() {
    	currentFunction = null;
    }
    
    public Function getCurrentFunction() {
    	return currentFunction;
    }
    
    public void openLoop(Loop l) {
    	loopStack.push(l);
    }
    
    public void closeLoop() {
    	loopStack.pop();
    }
    
    public Loop getCurrentLoop(TokenValue stm) {
    	if(loopStack.isEmpty()) {
    		ErroresBinding.invalidOutsideLoop(stm);
    		error = true;
    		return null;
    	}
    	else return loopStack.getFirst();
    }
}
