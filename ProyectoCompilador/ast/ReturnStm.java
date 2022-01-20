package ast;

import java.io.FileWriter;
import java.io.IOException;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;
import errors.ErroresTyping;

public class ReturnStm extends Node implements Statement{
	private final TokenValue value;
	private final Expression expression; // puede ser null
	private Function function;

	public ReturnStm(TokenValue value, Expression expression) {
		this.value = value;
		this.expression = expression;
		this.function = null;
	}

	public String toTree(int lvl) {
        StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl +
        		" RETURN STATEMENT\n");
		
		if(expression == null) sb.append(Nothing.toTree(lvl+1));
		else sb.append(expression.toTree(lvl+1));
		
		return sb.toString();
	}

	public void bind(Binder b) {
		if(expression != null)expression.bind(b);
		function = b.getCurrentFunction(); //nunca deberia ser null, por como hemos hecho el parsing
	}
	
	public boolean checkNumberConstructors() {
		return true;
	}
	
	public boolean typeCheckClause(Typer typer) throws Exception {
		if(expression == null) return true;
		
		Type t = expression.typeCheckExp(typer);
		
		if(function.getReturnType().equals(VoidType.VOID)) {
			ErroresTyping.returnVoid(value);
			return false;
		}
		else if(!t.compatibleWith((Type)function.getReturnType())) {
			ErroresTyping.expectedExpressionType(value, (Type)function.getReturnType(), "");
			return false;
		}
		
		return true;
	}
	
     public boolean searchEndReturnStm() {
    	 return true;
     }

     
     public void sizeAndDeltas(DeltasInfo DI) {
    	 //Nothing to do
     }
	
	public void codeStm(FileWriter writer, int breakLabel, int continueLabel) throws IOException {
		if(expression != null) expression.codeEvaluate(writer);
		writer.write("call $freeStack\n");
		writer.write("return\n");
	}
}
