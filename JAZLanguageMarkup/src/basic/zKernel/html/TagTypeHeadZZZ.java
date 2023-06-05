package basic.zKernel.html;

import org.jdom.Element;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;

public class TagTypeHeadZZZ extends AbstractKernelTagTypeZZZ {	
	public final static String sTAGNAME = "head";
	 
	//######## Constructor
	public TagTypeHeadZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
	}
	
	//### gerbt aus dem Interface
	public String getTagName() {
		return TagTypeHeadZZZ.sTAGNAME;
	}

	public String getTagKey(Element objElem) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readTagKey(Element objElem) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String readName(Element objElem) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}
}