package alex;

import asint.ClaseLexica;

public class ALexOperations {
  private ALexico alex;
  public ALexOperations(ALexico alex) {
   this.alex = alex;
 }

  public UnidadLexica unidadInclude() {
    return new UnidadLexica(alex.path(), alex.fila(), alex.columna(),ClaseLexica.INCLUDE,"#include");
  }
  public UnidadLexica unidadPrint() {
    return new UnidadLexica(alex.path(), alex.fila(), alex.columna(),ClaseLexica.PRINT,"print");
  }
  public UnidadLexica unidadAlias() {
    return new UnidadLexica(alex.path(), alex.fila(), alex.columna(), ClaseLexica.ALIAS,"alias");
  }
  public UnidadLexica unidadConst() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.CONST,"const");
  }
  public UnidadLexica unidadReturn() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.RETURN,"return");
  }
  public UnidadLexica unidadClass() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.CLASS,"class");
  }
  public UnidadLexica unidadPublic() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.PUBLIC,"public");
  }
  public UnidadLexica unidadPrivate() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.PRIVATE,"private");
  }
  public UnidadLexica unidadThis() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.THIS,"this");
  }
  public UnidadLexica unidadIf() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.IF,"if");
  }
  public UnidadLexica unidadElse() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.ELSE,"else");
  }
  public UnidadLexica unidadWhile() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.WHILE,"while");
  }
  public UnidadLexica unidadFor() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.FOR,"for");
  }
  public UnidadLexica unidadBreak() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.BREAK,"break");
  }
  public UnidadLexica unidadContinue() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.CONTINUE,"continue");
  }
  public UnidadLexica unidadArray(){
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.ARRAY,"array");
  }
//------

  public UnidadLexica unidadVoidType() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.VOID,"void");
  }
  public UnidadLexica unidadIntType() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.INT,"int");
  }
  public UnidadLexica unidadRealType() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.REAL,"real");
  }
  public UnidadLexica unidadBoolType() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.BOOL,"bool");
  }
  public UnidadLexica unidadCharType() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.CHAR,"char");
  }

//------

  public UnidadLexica unidadPAp() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.PAP,"(");
  }
  public UnidadLexica unidadPCierre() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.PCIERRE,")");
  }
  public UnidadLexica unidadCAp() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.CAP,"[");
  }
  public UnidadLexica unidadCCierre() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.CCIERRE,"]");
  }
  public UnidadLexica unidadLAp() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.LAP,"{");
  }
  public UnidadLexica unidadLCierre() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.LCIERRE,"}");
  }

//------

  public UnidadLexica unidadComa() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.COMA,",");
  }
  public UnidadLexica unidadDquotes() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.DQUOTES,"\"");
  }
  public UnidadLexica unidadPuntoComa() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.PCOMA,";");
  }

//------

  public UnidadLexica unidadIntValue() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.INTLIT,alex.lexema());
  }
  public UnidadLexica unidadRealValue() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.REALLIT,alex.lexema());
  }
  public UnidadLexica unidadBoolValue() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.BOOLLIT,alex.lexema());
  }
  public UnidadLexica unidadCharValue() {
    String str = alex.lexema();
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.CHARLIT,str.substring(1,str.length()-1));
  }
  public UnidadLexica unidadRawChar() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.CHARLIT,alex.lexema());
  }
  public UnidadLexica unidadNumBin() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.INTLIT,
            Integer.toString(Integer.parseInt(alex.lexema().substring(2),2)));
  }
  public UnidadLexica unidadNumOct() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.INTLIT,
            Integer.toString(Integer.parseInt(alex.lexema().substring(1),8)));
  }
  public UnidadLexica unidadNumHex() {
	  return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.INTLIT,
             Integer.toString(Integer.parseInt(alex.lexema().substring(2),16)));
  }


//----

 public UnidadLexica unidadAsigna() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.ASIGNA,"=");
 }
 public UnidadLexica unidadMenor() {
   return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.MENOR,"<");
 }

 public UnidadLexica unidadMayor() {
   return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.MAYOR,">");
 }

 public UnidadLexica unidadIgual() {
   return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.IGUAL,"==");
 }

 public UnidadLexica unidadDistinto() {
   return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.DISTINTO,"!=");
 }

 public UnidadLexica unidadMenorIgual() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.MENORIGUAL,"<=");
 }
 public UnidadLexica unidadMayorIgual() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.MAYORIGUAL,">=");
 }

  public UnidadLexica unidadSuma() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.MAS,"+");
  }
  public UnidadLexica unidadResta() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.MENOS,"-");
  }
  public UnidadLexica unidadEstrella() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.ESTRELLA,"*");
  }
  public UnidadLexica unidadDiv() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.DIV,"/");
  }
  public UnidadLexica unidadMod() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.MOD,"%");
  }
  public UnidadLexica unidadPunto() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.PUNTO,".");
  }
  public UnidadLexica unidadFlecha() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.FLECHA,"->");
  }
  public UnidadLexica unidadIncr() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.INCR,"++");
  }
  public UnidadLexica unidadDecr() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.DECR,"--");
  }
  public UnidadLexica unidadSumaAsigna() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.SUMAASIGNA,"+=");
  }
  public UnidadLexica unidadRestaAsigna() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.RESTAASIGNA,"-=");
  }
  public UnidadLexica unidadMultAsigna() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.MULTASIGNA,"*=");
  }
  public UnidadLexica unidadDivAsigna() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.DIVASIGNA,"/=");
  }
  public UnidadLexica unidadModAsigna() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.MODASIGNA,"%=");
  }
  public UnidadLexica unidadNeg() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.NEG,"!");
  }
  public UnidadLexica unidadAnd() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.AND,"&&");
  }
  public UnidadLexica unidadOr() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.OR,"||");
  }
  public UnidadLexica unidadSizeof() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.SIZEOF,"sizeof");
  }
  public UnidadLexica unidadSizeoft() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.SIZEOFT,"sizeoft");
  }
  public UnidadLexica unidadNew() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.NEW,"new");
  }
  public UnidadLexica unidadInput() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.INPUT,"input");
  }
  public UnidadLexica unidadAmpersand() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.AMPERSAND,"&");
  }
// ---

  public UnidadLexica unidadIdentifier() {
    return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.IDEN,alex.lexema());
  }
  public UnidadLexica unidadPath() {
      String str = alex.lexema();
      return new UnidadLexica(alex.path(), alex.fila(), alex.columna(),ClaseLexica.PATH,str.substring(1,str.length()-1));
    }

// ---

  public UnidadLexica unidadEof() {
     return new UnidadLexica(alex.path(), alex.fila(),alex.columna(),ClaseLexica.EOF,"<EOF>");
  }

}
