package basic.zKernel.html;

import org.jdom.Element;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;

/**This class describes a body-tag in html
 * @author 0823
 *
 */
public class TagTypeBodyZZZ extends KernelTagTypeZZZ {	
	public final static String TagName = "body";
	 
	//######## Constructor
	public TagTypeBodyZZZ(IKernelZZZ objKernel){
		super(objKernel);
	}
	
	//### gerbt aus dem Interface
	public String getTagName() {
		return TagTypeBodyZZZ.TagName;
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
