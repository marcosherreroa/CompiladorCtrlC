package ast;

import java.io.FileWriter;
import java.io.IOException;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;
import errors.ErroresTyping;

public class Declaration implements ProgramClause , Statement,  Definition{
	protected final Identifier identifier;
	private Type type;
	private final TokenValue op;
	private Expression expression;

	private int delta;
	private int profundidad; //0: programa principal, 1: funcion

	public Declaration(Identifier identifier, Type type, TokenValue op, Expression expression) {
		this.identifier = identifier;
		this.type = type;
		this.op = op;
		this.expression = expression;

		this.delta = -1;
		this.profundidad = -1;
	}

	public Type getVariableType() {
		return type;
	}

	public Function getFunction() { // no es una funcion
		return null;
	}

	public Type getType(boolean constant) { // si llegamos aqui es que un identificador deberia ser un tipo pero ha sido declarado como variable
		return null;
	}


	public TokenValue getValue() {
		return identifier.getValue();
	}

	public int getDelta() {
		return delta;
	}

	public int getProfundidad() {
		return profundidad;
	}

	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl +
				" DECLARATION\n");
		sb.append(type.toTree(lvl+1));
		sb.append(identifier.toTree(lvl+1));
		if(expression != null)sb.append(expression.toTree(lvl+1));

		return sb.toString();
	}


	public void bind(Binder b) {
		type.bind(b);
		if(expression != null)expression.bind(b);

		b.insertId(identifier.getValue(), this);

		if(b.getCurrentFunction() == null)profundidad = 0;
		else profundidad = 1;
	}

	public boolean checkNumberConstructors() {
		return true;
	}

	public boolean typeCheckClause(Typer typer) throws Exception {
		if(expression == null) {
			type = type.typeCheckCompleteType();

			if(type == null) return false;
			else return true;
		}

		else {
			type = type.typeCheckIncompleteType(true);
			Type tExp = expression.typeCheckExp(typer);

			if(type == null || tExp == null) return false;
			else if(! tExp.compatibleWith(type)) {
				ErroresTyping.collisionVarValue(op, type, tExp);
				return false;
			}
			else if(type.getClass() == ArrayType.class) {
				if(!expression.isLiteral()) {
					ErroresTyping.initializingWithArray(op);
					return false;
				}
				else {
					type.setDimension(tExp); //si es un array declarado sin dimensi�n, habra que cogerla del literal
					return true;
				}
			}
			else return true;
		}
	}

	public boolean checkReturns() {
		return true;
	}

	public boolean searchEndReturnStm() {
		return false;
	}

	public Function checkMain() {
		return null;
	}

	public void sizeAndDeltas(DeltasInfo DI) {
		delta = DI.getC();

		DI.setC(DI.getC()+type.size());
		if(DI.getC() > DI.getMax()) DI.setMax(DI.getC());

		System.out.println("DECLARATION :: Lexema: "+ identifier.getValue().lexema+ " || Delta : "+ delta);
	}


	public void codeInitialization(FileWriter writer) throws IOException{
		if(expression != null) {
			expression.codeWriteTo(writer, profundidad,delta);
		}
	}

	public void codeFunctions(FileWriter writer) {
		//Nothing to do
	}

	public void code(FileWriter writer) throws IOException{
		if(expression != null) {
			expression.codeWriteTo(writer, profundidad,delta);
		}
	}

	public void codeStm(FileWriter writer, int breakLabel, int continueLabel) throws IOException{
		if(expression != null) {
			expression.codeWriteTo(writer, profundidad,delta);
		}
	}

	public void codeAddressIden(FileWriter writer) throws IOException {
		writer.write("i32.const "+(4*delta)+"\n");

		if(profundidad == 0) writer.write("i32.const 8\n");
		else writer.write("get_local $localsStart\n");

		writer.write("i32.add\n");
	}

}
