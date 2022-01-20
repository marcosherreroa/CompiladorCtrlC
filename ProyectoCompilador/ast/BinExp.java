package ast;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuilder;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import errors.ErroresTyping;

public class BinExp extends Node implements Expression {
	private final Expression opnd1;
	private final String op;
	private final Expression opnd2;
	private final TokenValue v;

	public BinExp(Expression opnd1, String op, Expression opnd2, TokenValue v) {
		this.opnd1 = opnd1;
		this.op = op;
		this.opnd2 = opnd2;
		this.v = v;
	}

	public boolean isLiteral() {
		return false;
	}

	public boolean isLvalue() {
		return false;
	}

	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl
				+ " BINARY EXPRESSION\n");

		sb.append(" ".repeat(2*(lvl+1)) + (lvl+1) + " OPERATOR: " + op + "\n");
		sb.append(opnd1.toTree(lvl+1));
		sb.append(opnd2.toTree(lvl+1));

		return sb.toString();
	}

	public void bind(Binder b) {
		opnd1.bind(b);
		opnd2.bind(b);
	}

	public Type typeCheckExp(Typer typer) throws Exception {
		Type t1 = opnd1.typeCheckExp(typer);
		Type t2 = opnd2.typeCheckExp(typer);

		Type tRes = null, tOp = null;

		if(t1 == null || t2 == null) return null;
		else if(op.equals("||") || op.equals("&&") ) {
			tRes = BasicType.BOOL;
			if(!t1.compatibleWith(tRes)) {
				ErroresTyping.expectedExpressionType(v, tRes, "left");
				tRes = null;
			}
			if(!t2.compatibleWith(tRes)) {
				ErroresTyping.expectedExpressionType(v, tRes, "right");
				tRes = null;
			}
		}

		else if (op.equals("==") || op.equals("!=")) {
			tRes = BasicType.BOOL;
			if(!t1.compatibleWith(t2)) {
				ErroresTyping.incomparableExpressions(v,t1,t2);
				tRes = null;
			}
		}

		else if(op.equals("<") || op.equals(">") || op.equals("<=") || op.equals(">=")) {
			tRes = BasicType.BOOL;
			tOp = BasicType.INT;

			if(!t1.compatibleWith(tOp)){
				ErroresTyping.expectedExpressionType(v, tOp, "left");
				tRes = null;
			}

			if(!t2.compatibleWith(tOp)){
				ErroresTyping.expectedExpressionType(v, tOp, "right");
				tRes = null;
			}
		}

		else if(op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("%")) {
			if(t1 != BasicType.REAL && t2 != BasicType.REAL) tRes = BasicType.INT;
			else tRes = BasicType.REAL;

			if(!t1.compatibleWith(tRes)) {
				ErroresTyping.expectedExpressionType(v, tRes, "left");
				tRes = null;
			}

			if(!t2.compatibleWith(tRes)) {
				ErroresTyping.expectedExpressionType(v, tRes, "right");
				tRes = null;
			}
		}

		else System.out.println("Unknown binary operator");


		return tRes;
	}

	public void codeEvaluate(FileWriter writer) throws IOException {
		opnd1.codeEvaluate(writer);
		opnd2.codeEvaluate(writer);
		switch(op) {
		case "||":
			writer.write("i32.or\n");
			break;
		case "&&":
			writer.write("i32.and\n");
			break;
		case "==":
			writer.write("i32.eq\n");
			break;
		case "!=":
			writer.write("i32.ne\n");
			break;
		case "<":
			writer.write("i32.lt_s\n");
			break;
		case "<=":
			writer.write("i32.le_s\n");
			break;
		case ">":
			writer.write("i32.gt_s\n");
			break;
		case ">=":
			writer.write("i32.ge_s\n");
			break;
		case "+":
			writer.write("i32.add\n");
			break;
		case "-":
			writer.write("i32.sub\n");
			break;
		case "*":
			writer.write("i32.mul\n");
			break;
		case "/":
			writer.write("i32.div_s\n");
			break;
		case "%":
			writer.write("i32.rem_s\n");
			break;
		}
	}

	public int codeWriteTo(FileWriter writer, int profundidad, int delta) throws IOException {
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
