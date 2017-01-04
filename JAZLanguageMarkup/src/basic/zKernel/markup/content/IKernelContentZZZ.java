package basic.zKernel.markup.content;

import org.jdom.Document;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;

public interface IKernelContentZZZ {
	public boolean setVar(String sVariableName, String sVariableValue) throws ExceptionZZZ;
	public boolean setVar(String sVariableName, HashMapMultiZZZ hmVariableValue) throws ExceptionZZZ;
	
	public HashMapMultiZZZ getVarHm(String sVariableName) throws ExceptionZZZ;
	public String getVarString(String sVariableName) throws ExceptionZZZ;
}
 