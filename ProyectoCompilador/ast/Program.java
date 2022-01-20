package ast;

import java.util.ArrayList;
import java.util.List;

import alex.ALexico;
import alex.UnidadLexica;
import asem.Binder;
import asem.Typer;
import asint.ASintactico;
import asint.ClaseLexica;
import codeGeneration.DeltasInfo;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.StringBuilder;

public class Program extends Node {
	private List<ProgramClause> clauseList;
	
	private int maxStackSize;

	public Program(List<ProgramClause> clauseList) {
		this.clauseList = clauseList;
		this.maxStackSize = -1;
	}
	
	public String toTree(int lvl) {
        StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " PROGRAM\n");
        for(ProgramClause pc: clauseList) {
        	sb.append(pc.toTree(lvl+1));
        }
        
        return sb.toString();
	}
	
	public static List<ProgramClause> include (String path) throws Exception{
		Reader input = new InputStreamReader(new FileInputStream(path));
		ALexico alex = new ALexico(input);
		alex.fijaPath(path);

	   UnidadLexica unidad = null;
	     do {
	       unidad = (UnidadLexica) alex.next_token();
	       System.out.println(unidad);

	     }
	     while (unidad == null || unidad.clase() != ClaseLexica.EOF);

	     input = new InputStreamReader(new FileInputStream(path));
	     alex = new ALexico(input);
	     alex.fijaPath(path);
	     ASintactico asint = new ASintactico(alex);
	     Program ast = (Program)asint.parse().value;
	     
	     return ast.clauseList;
	}
	
	public void bind(Binder b) {
		b.openBlock();
		
		for(ProgramClause pc : clauseList) {
			pc.bind(b);
		}
		
		b.closeBlock();
	}
	
	public boolean checkNumberConstructors() {
		boolean OK = true;
		for(ProgramClause pc : clauseList) {
			OK = pc.checkNumberConstructors() && OK;
		}
		
		return OK;
	}
	
	public boolean typeCheck(Typer typer) throws Exception {
		boolean OK = true;
		for(ProgramClause pc : clauseList) {
			OK = pc.typeCheckClause(typer) && OK;
		}
		
		return OK;
	}

	public boolean checkReturns() {
		boolean OK = true;
		for(ProgramClause pc : clauseList) {
			OK = pc.checkReturns() && OK;
		}
		
		return OK;
	}
	
	public Function checkMain() {
		Function f = null;
		for(ProgramClause pc : clauseList) {
			f = pc.checkMain();
			if(f != null) return f;
		}
		
		return f;
	}
	
	public void sizeAndDeltas() {
		DeltasInfo DI = new DeltasInfo();
		
		for(ProgramClause pc : clauseList) {
			pc.sizeAndDeltas(DI);
		}
		
		maxStackSize = DI.getMax();
		System.out.println("Main program || MaxStackSize: "+maxStackSize);
	}
	
	public void code(FileWriter writer) throws IOException {
		writer.write("(module\n(type $_sig_i32i32i32 (func (param i32 i32 i32) ))\n"
				+ "(type $_sig_i32ri32 (func (param i32) (result i32)))\n"
				+ "(type $_sig_i32 (func (param i32)))\n(type $_sig_ri32 (func (result i32)))\n"
				+ "(type $_sig_void (func ))\n(import \"runtime\" \"exceptionHandler\" (func $exception"
				+ " (type $_sig_i32)))\n(import \"runtime\" \"print\" (func $print (type $_sig_i32)))\n"
				+ "(import \"runtime\" \"read\" (func $read (type $_sig_ri32)))\n(memory 2000)\n(global"
				+ " $SP (mut i32) (i32.const 0)) ;; start of stack\n(global $MP (mut i32) (i32.const 0))"
				+ "\n(global $NP (mut i32) (i32.const 131071996))\n"
				+ "\n(start $init)\n");
		
		writer.write("\n\n(func $reserveStack (param $size i32)\n   (result i32)\n   get_global $MP\n"
				+ "   get_global $SP\n   set_global $MP\n   get_global $SP\n   get_local $size\n"
				+ "   i32.add\n   set_global $SP\n   get_global $SP\n   get_global $NP\n   i32.gt_u\n"
				+ "   if\n   i32.const 3\n   call $exception\n   end\n)\n(func $freeStack (type $_sig_void)\n"
				+ "   get_global $MP\n   i32.load\n   i32.load offset=4\n   set_global $SP\n   get_global $MP\n"
				+ "   i32.load\n   set_global $MP   \n)\n(func $reserveHeap (type $_sig_i32)\n   (param $size i32)\n"
				+ "   get_global $NP\n   get_local $size\n   i32.sub\n   set_global $NP\n)\n"
				+ "(func $copyn (type $_sig_i32i32i32) ;; copy $n i32 slots from $src to $dest\n   (param $src i32)\n"
				+ "   (param $dest i32)\n   (param $n i32)\n   block\n     loop\n       get_local $n\n       i32.eqz\n"
				+ "       br_if 1\n       get_local $n\n       i32.const 1\n       i32.sub\n       set_local $n\n"
				+ "       get_local $dest\n       get_local $src\n       i32.load\n       i32.store\n "
				+ "      get_local $dest\n       i32.const 4\n       i32.add\n       set_local $dest\n"
				+ "       get_local $src\n       i32.const 4\n       i32.add\n       set_local $src\n       br 0\n"
				+ "     end\n   end\n)\n\n");
		
		writer.write("(func $init (type $_sig_void)\n");
		writer.write("(local $temp i32)\n"
				+ "(local $localsStart i32)\n"
				+ "(local $lastAddr i32)\n"
				+ "(local $prevVal i32)\n"
				+ "(local $resultIncDec i32)\n"
				+ "   i32.const "+(4*(maxStackSize+2))
				+ "  \n   call $reserveStack  \n"
				+ "   set_local $temp\n   get_global $MP\n   get_local $temp\n   i32.store\n   get_global $MP\n   get_global $SP\n"
				+ "   i32.store offset=4\n   get_global $MP\n   i32.const 8\n   i32.add\n   set_local $localsStart\n");
		
		for(ProgramClause pc : clauseList) {
			pc.codeInitialization(writer);
		}
		
		writer.write("call $main\n");
		writer.write("call $freeStack\n)\n");
		
		for(ProgramClause pc: clauseList) {
			pc.codeFunctions(writer);
		}
		
		writer.write(")");
	}
}
