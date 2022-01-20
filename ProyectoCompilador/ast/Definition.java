package ast;

import java.io.FileWriter;
import java.io.IOException;

import alex.TokenValue;

public interface Definition {
   public String toTree(int lvl);
   public TokenValue getValue();
   public Type getVariableType(); //only for variable declarations
   public Function getFunction(); //only for function definitions
   public Type getType(boolean constant); //only for type declaration
   public void codeAddressIden(FileWriter writer) throws IOException;
}
