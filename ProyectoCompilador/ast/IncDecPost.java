package ast;

import java.io.FileWriter;
import java.io.IOException;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;
import errors.ErroresTyping;

public class IncDecPost extends Node implements Expression, CollateralOperation{
	private final TokenValue op;
    private final Lvalue opnd;

	public IncDecPost(TokenValue op, Lvalue opnd) {
		this.op = op;
		this.opnd = opnd;
	}

	public boolean isLiteral() {
		return false;
	}

	public boolean isLvalue() {
		return false;
	}

	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl);

		if(op.lexema.equals("++")) sb.append(" POSTFIX INCREMENT\n");
		else sb.append(" POSTFIX DECREMENT\n");

		sb.append(opnd.toTree(lvl+1));

		return sb.toString();
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

	public boolean searchEndReturnStm() {
		return false;
	}

	public void sizeAndDeltas(DeltasInfo DI) {
		//Nothing to do
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
		writer.write("set_local $prevVal\n");
		writer.write("get_local $prevVal\n");

		writer.write("i32.const 1\n");
		if(op.lexema.equals("++"))writer.write("i32.add\n");
		else writer.write("i32.sub\n");

		writer.write("i32.store\n");

		writer.write("get_local $prevVal\n");
	}

	public int codeWriteTo(FileWriter writer,int profundidad, int delta) throws IOException {

		if(profundidad == 0)writer.write("i32.const 8\n");
		else writer.write("get_local $localsStart\n");

		writer.write("i32.const "+(4*delta)+"\n");
		writer.write("i32.add\n");

		codeEvaluate(writer);
		writer.write("i32.store\n");
		return delta+1;
	}

	public int passByValue(FileWriter writer, int delta) throws IOException {
		writer.write("get_global $SP\n");
		writer.write("i32.const "+(4*(delta+2))+"\n");
		writer.write("i32.add\n");

		codeEvaluate(writer);
		writer.write("i32.store\n");

		return delta+1;
	}
}
