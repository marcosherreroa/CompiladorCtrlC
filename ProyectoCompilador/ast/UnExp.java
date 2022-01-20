package ast;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuilder;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import errors.ErroresTyping;

public class UnExp extends Node implements Expression {
	private final String op;
	private final Expression opnd;
	private final TokenValue v;

	private Type opndType;

	public UnExp(String op, Expression opnd, TokenValue v) {
		this.op = op;
		this.opnd = opnd;
		this.v = v;
		this.opndType = null;
	}

	public boolean isLiteral() {
		return false;
	}

	public boolean isLvalue() {
		return false;
	}

	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl
				+ " UNARY EXPRESSION \n");

		sb.append(" ".repeat(2*(lvl+1)) + (lvl+1) + " OPERATOR: " + op + "\n");
		sb.append(opnd.toTree(lvl+1));

		return sb.toString();
	}

	public void bind(Binder b) {
		opnd.bind(b);
	}

	public Type typeCheckExp(Typer typer) throws Exception {
		opndType = opnd.typeCheckExp(typer);

		Type tRes = null;

		if(opndType == null) return null;
		else if(op.equals("-")) {
			tRes = opndType;
			if(! opndType.compatibleWith(BasicType.INT)) {
				ErroresTyping.expectedExpressionType(v, tRes, "");
				tRes = null;
			}
		}
		else if(op.equals("!")) {
			tRes = BasicType.BOOL;
			if(! opndType.compatibleWith(tRes)) {
				ErroresTyping.expectedExpressionType(v, tRes, "");
				tRes = null;
			}
		}
		else if (op.equals("sizeof")) {
			tRes = BasicType.INT;
		}
		else if(op.equals("&")) {
			if(! opnd.isLvalue()) {
				ErroresTyping.referenceNonLvalue(v);
				tRes = null;
			}
			else tRes = new PointerType(opndType);
		}
		else System.out.println("Unknown unary operator");


		return tRes;
	}

	public void codeEvaluate(FileWriter writer) throws IOException {
		if(op.equals("sizeof")) {
			writer.write("i32.const "+opndType.size()+"\n");
		}
		else if(op.equals("&")) {
			Lvalue lv = (Lvalue) opnd;
			lv.codeAddress(writer);
		}
		else {
			opnd.codeEvaluate(writer);
			switch(op) {
			case "-":
				writer.write("i32.const -1\n");
				writer.write("i32.mul\n");
				break;
			case "!":
				writer.write("i32.eqz\n");
				break;
			}
		}
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
