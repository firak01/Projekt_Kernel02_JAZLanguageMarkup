package basic.zKernel.html;

import org.jdom.Element;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;

/**This tag describes a input-tag in html.
 * @author 0823
 *
 */
public class TagTypeInputZZZ extends AbstractKernelTagTypeZZZ{
	public final static String sTAGNAME = "input";
	
	//###### Constructor
	public TagTypeInputZZZ() throws ExceptionZZZ {
		super("");
	}
	
	public TagTypeInputZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
	}

//######### gerbt aus dem Interface
	public String getTagKey(Element objElem) throws ExceptionZZZ{
		return this.readTagKey(objElem);
	}
	
	public String getTagName() {
		return TagTypeInputZZZ.sTAGNAME;
	}
	
	@Override
	public String readTagKey(Element objElem) throws ExceptionZZZ {
		return this.readName(objElem);
	}
	
}
