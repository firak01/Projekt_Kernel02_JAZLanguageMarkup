package basic.zKernel.html;

import org.jdom.Element;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;

public class TagInputZZZ extends AbstractKernelTagZZZ{

	public TagInputZZZ(Element objElem) throws ExceptionZZZ {
		super(new TagTypeInputZZZ(), objElem);		
	}
	
	public TagInputZZZ(IKernelZZZ objKernel, Element objElem) throws ExceptionZZZ {
		super(objKernel, new TagTypeInputZZZ(), objElem);		
	}
}
