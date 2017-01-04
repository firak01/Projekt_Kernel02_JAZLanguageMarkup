package basic.zKernel.html;

import org.jdom.Element;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.KernelZZZ;

public class TagTypeHeadZZZ extends KernelTagTypeZZZ {	
	public final static String TagName = "head";
	 
	//######## Constructor
	public TagTypeHeadZZZ(KernelZZZ objKernel){
		super(objKernel);
	}
	
	//### gerbt aus dem Interface
	public String getTagName() {
		return TagTypeHeadZZZ.TagName;
	}

	public String getTagKey(Element objElem) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

	public String readValue(Element objElem) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

	public String readName(Element objElem) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}
}