package basic.zKernel.html;

import org.jdom.Element;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;

/**This class describes a body-tag in html
 * @author 0823
 *
 */
public class TagTypeBodyZZZ extends AbstractKernelTagTypeZZZ {	
	public final static String sTAGNAME = "body";
	 
	//######## Constructor
	public TagTypeBodyZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
	}
	
	//### gerbt aus dem Interface
	
	@Override
	public String getTagName() {
		return TagTypeBodyZZZ.sTAGNAME;
	}

	@Override
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
	public String readValue(Element objElem) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

}
