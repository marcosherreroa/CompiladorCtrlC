package ast;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;
import errors.ErroresTyping;

public class FunctionCall  extends Node implements Expression, CollateralOperation{
    private Lvalue lv;
    private List<Expression> argList;
    private TokenValue v;

    private Function function;

	public FunctionCall(Lvalue lv, List<Expression> argList, TokenValue v) {
		this.lv = lv;
		this.argList = argList;
		this.v = v;
		this.function = null;
	}

	public boolean isLiteral() {
		return false;
	}

	public boolean isLvalue() {
		return false;
	}

	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " FUNCTION CALL\n");

		sb.append(lv.toTree(lvl+1));
		for(Expression e: argList) {
			sb.append(e.toTree(lvl+1));
		}

		return sb.toString();
	}

	public void bind(Binder b) {
		lv.bind(b);

		for(Expression e : argList)e.bind(b);
	}

	public boolean checkNumberConstructors() {
		return true;
	}

	public boolean typeCheckClause(Typer typer) throws Exception {
		boolean OK = true;
		function = lv.typeCheckFun(typer);
		if(function == null) return false;

		List<Parameter> paramList = function.getParamList();

		if(paramList.size() != argList.size()) {
			ErroresTyping.differentNumberArgs(v,argList.size(), paramList.size());;
			OK = false;
		}

		Iterator<Expression> argListIterator = argList.iterator();
		Iterator<Parameter> paramListIterator = paramList.iterator();
		int ind = 0;
		   while (argListIterator.hasNext() ) {
		            Expression e = argListIterator.next();
		            Type tArg = e.typeCheckExp(typer);

		            if(paramListIterator.hasNext()){
		            	Parameter p = paramListIterator.next();

		            	if(tArg == null || p.getVariableType() == null) OK = false;
		            	else if(p.isByReference() && !e.isLvalue()) ErroresTyping.passByReference(v, e, ind);
		            	else if(!tArg.compatibleWith(p.getVariableType())) {
		    				ErroresTyping.collisionArgParam(v, tArg, p.getVariableType(), ind);
		    				OK = false;
		    			}
		            }

		            ++ind;
		   }

		return OK;
	}

	public Type typeCheckExp(Typer typer) throws Exception {
		boolean OK = true;
		function = lv.typeCheckFun(typer);
		if(function == null) return null;

		List<Parameter> paramList = function.getParamList();

		if(paramList.size() != argList.size()) {
			ErroresTyping.differentNumberArgs(v,argList.size(), paramList.size());;
			OK = false;
		}

		Iterator<Expression> argListIterator = argList.iterator();
		Iterator<Parameter> paramListIterator = paramList.iterator();
		int ind = 0;
	   while (argListIterator.hasNext() ) {
	            Expression e = argListIterator.next();
	            Type tArg = e.typeCheckExp(typer);

	            if(paramListIterator.hasNext()){
	            	Parameter p = paramListIterator.next();

	            	if(tArg == null || p.getVariableType() == null) OK = false;
	            	else if(p.isByReference() && !e.isLvalue()) ErroresTyping.passByReference(v, e, ind);
	            	else if(!tArg.compatibleWith(p.getVariableType())) {
	    				ErroresTyping.collisionArgParam(v, tArg, p.getVariableType(), ind);
	    				OK = false;
	    			}
	            }

	            ++ind;
	   }

	    if(function.getReturnType().equals(VoidType.VOID)) {
	    	ErroresTyping.usingVoid(v);
	    	OK = false;
	    }

		if(OK) return (Type)function.getReturnType();
		else return null;
	}

	public boolean searchEndReturnStm() {
		return false;
	}

	public void sizeAndDeltas(DeltasInfo DI) {
		//Nothing to do
	}

	public void codeStm(FileWriter writer, int breakLabel, int continueLabel) throws IOException {
		List<Parameter> paramList = function.getParamList();

		Iterator<Expression> argListIterator = argList.iterator();
		Iterator<Parameter> paramListIterator = paramList.iterator();

	   while (argListIterator.hasNext() ) {
	            Expression e = argListIterator.next();
	            Parameter p = paramListIterator.next();

	            if(!p.isByReference()) e.passByValue(writer, p.getDelta());
	            else{
	            	Lvalue arg = (Lvalue) e;
	            	arg.passByReference(writer,p.getDelta());
	            }

	   }

	   Identifier name = (Identifier) lv; //esto es porque no generamos c�digo para los metodos de clases
	   writer.write("call $"+name.getValue().lexema+"\n");
	   if(! function.getReturnType().isVoid()) writer.write("drop\n");
	}

	public void codeEvaluate(FileWriter writer) throws IOException {
        List<Parameter> paramList = function.getParamList();

		Iterator<Expression> argListIterator = argList.iterator();
		Iterator<Parameter> paramListIterator = paramList.iterator();

	    while (argListIterator.hasNext() ) {
	            Expression e = argListIterator.next();
	            Parameter p = paramListIterator.next();

	            if(!p.isByReference()) e.passByValue(writer, p.getDelta());
	            else{
	            	Lvalue lv = (Lvalue) e;
	            	lv.passByReference(writer,p.getDelta());
	            }

	   }

	    Identifier name = (Identifier) lv; //esto es porque no generamos c�digo para los metodos de clases
		writer.write("call $"+name.getValue().lexema+"\n");
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
