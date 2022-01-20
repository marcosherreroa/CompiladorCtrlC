package ast;

import java.io.FileWriter;
import java.io.IOException;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import errors.ErroresTyping;

public class ClassAccess extends Lvalue{
   private final Lvalue lv;
   private final Identifier field;
   private final TokenValue value;
   
   private Clase clase;
   private ClassClause classClause;
   
	public ClassAccess(Lvalue lv, Identifier field, TokenValue value) {
		this.lv = lv;
		this.field = field;
		this.value = value;
		this.clase = null;
		this.classClause = null;
	}
	   
   public String toTree(int lvl) {
	   StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " CLASS ACCESS\n");
		
		sb.append(lv.toTree(lvl+1));
		sb.append(field.toTree(lvl+1));
		
		return sb.toString();
   }
   
   public boolean isLiteral() {
	   return false;
   }
   
   public int size() {
	   Attribute a = (Attribute) classClause; 
	   return a.getVariableType().size();
   }
   
   public boolean isAttributeFrom(Clase c) { //si llegamos aqui ya sabemos que es un atributo
	   return clase.equals(c);
   }

	public void bind(Binder b) {
		lv.bind(b);
	}
	
	public Type typeCheckExp(Typer typer) throws Exception {
		Type t = lv.typeCheckExp(typer);
		if(t == null) return null;
		
		clase = t.getClase();
		if(clase == null) {
			ErroresTyping.expectedClase(value);
			return null;
		}
		
		Attribute a = clase.getAttribute(field.getValue().lexema);
		classClause = a;
		
		if(a == null) {
			ErroresTyping.expectedVariable(value);
			return null;
		}
		
		if(a.getVisibility()== "private" && !typer.insideClass(clase.getValue().lexema)) {
			ErroresTyping.visibilityError(value);
			return null;
		}
		
		Type tFinal = a.getVariableType();
		if(t.isConst()) tFinal.setConst();
		
		return tFinal;
	}
	
	public Function typeCheckFun(Typer typer) throws Exception {
		Type t = lv.typeCheckExp(typer);
        if(t == null) return null;
		
		clase = t.getClase();
		if(clase == null) {
			ErroresTyping.expectedClase(value);
			return null;
		}
		
		Method m = clase.getMethod(field.getValue().lexema);
		classClause = m;
		if(m == null) {
			ErroresTyping.expectedFunction(value);
			return null;
		}
		
		if(m.getVisibility()== "private" && !typer.insideClass(clase.getValue().lexema)) {
			ErroresTyping.visibilityError(value);
			return null;
		}
		
		return m;
	}

	
	public void codeAddress(FileWriter writer) throws IOException {
		lv.codeAddress(writer);
		
		Attribute a = (Attribute) classClause;
		writer.write("i32.const "+(4*a.getDelta())+"\n");
		writer.write("i32.add\n");
	}
}
