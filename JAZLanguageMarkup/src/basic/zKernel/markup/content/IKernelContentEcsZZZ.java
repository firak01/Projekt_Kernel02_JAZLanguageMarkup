package basic.zKernel.markup.content;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;

public interface IKernelContentEcsZZZ {
	public boolean addElement(String sAlias, Object objECS) throws ExceptionZZZ;
	public String getAliasByIndex(int iIndex) throws ExceptionZZZ;
	public HashMap getElementList();
	public int NumberOfElement();
}
