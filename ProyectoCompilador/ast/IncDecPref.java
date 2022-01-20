package ast;

import java.io.FileWriter;
import java.io.IOException;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;
import errors.ErroresTyping;

public class IncDecPref extends Lvalue implements CollateralOperation{
	private final TokenValue op;
    private final Lvalue opnd;
    
	public IncDecPref(TokenValue op, Lvalue opnd) {
		this.op = op;
		this.opnd = opnd;
	}
    
	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl);
		
		if(op.lexema.equals("++")) sb.append(" PREFIX INCREMENT\n");
		else sb.append(" PREFIX DECREMENT\n");
		
		sb.append(opnd.toTree(lvl+1));
		
		return sb.toString();
	}
	
	public boolean isLiteral() {
		return false;
	}
	
	public int size() {
		return 1;
	}
	
	public boolean isAttributeFrom(Clase c) {
		return false;
	}
	
	public void sizeAndDeltas(DeltasInfo DI) {
		//Nothing to do
	}

	public void bind(Binder b) {
		opnd.bind(b);
	}
	
	public boolean checkNumberConstructors() {
		return true;
	}
 
	public boolean typeCheckClause(Typer typer) throws Exception {
        Type t1 = opnd.typeCheckExp(typer);
		
		if(t1 == null) return false;
		else if(!t1.compatibleWith(BasicType.INT)) {
			ErroresTyping.expectedExpressionType(op, BasicType.INT, "");
			return false;
		}
		else return true;
	}
	
	public Type typeCheckExp(Typer typer) throws Exception {
		Type t1 = opnd.typeCheckExp(typer);
		
		if(t1 == null) return null;
		else if(!t1.compatibleWith(BasicType.INT)) {
			ErroresTyping.expectedExpressionType(op, BasicType.INT, "");
			return null;
		}
		else return t1;
	}
	
	public Function typeCheckFun(Typer typer) { // no es una funcion
		return null;
	}
	
	public boolean searchEndReturnStm() {
		return false;
	}

	public void codeStm(FileWriter writer, int breakLabel, int continueLabel) throws IOException {
		opnd.codeAddress(writer);
		writer.write("set_local $lastAddr\n");
		writer.write("get_local $lastAddr\n");
		writer.write("get_local $lastAddr\n");
		writer.write("i32.load\n");
		
		writer.write("i32.const 1\n");
		if(op.lexema.equals("++"))writer.write("i32.add\n");
		else writer.write("i32.sub\n");
		
		writer.write("i32.store\n");
	}
	
	public void codeEvaluate(FileWriter writer) throws IOException {
		opnd.codeAddress(writer);
		writer.write("set_local $lastAddr\n");
		writer.write("get_local $lastAddr\n");
		writer.write("get_local $lastAddr\n");
		writer.write("i32.load\n");
		
		writer.write("i32.const 1\n");
		if(op.lexema.equals("++"))writer.write("i32.add\n");
		else writer.write("i32.sub\n");
		writer.write("set_local $resultIncDec\n");
		writer.write("get_local $resultIncDec\n");
		
		codeEvaluate(writer);
		writer.write("i32.store\n");
		
		writer.write("get_local $resultIncDec\n");
	}
	
	public void codeAddress(FileWriter writer) throws IOException {
		opnd.codeAddress(writer);
		writer.write("set_local $lastAddr\n");
		writer.write("get_local $lastAddr\n");
		writer.write("get_local $lastAddr\n");
		writer.write("i32.load\n");
		
		writer.write("i32.const 1\n");
		writer.write("i32.add\n");
		writer.write("i32.store\n");
		
		writer.write("get_local $lastAddr\n");
	}
	
}
