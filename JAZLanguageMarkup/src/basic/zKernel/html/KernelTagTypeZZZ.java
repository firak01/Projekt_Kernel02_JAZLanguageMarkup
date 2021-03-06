package basic.zKernel.html;


import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zKernel.KernelUseObjectZZZ;

public abstract class KernelTagTypeZZZ extends KernelUseObjectZZZ implements IKernelTagTypeZZZ {

	public KernelTagTypeZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
	}
	 
	//################## VOM INTERFACE GEERBT ########################
	public abstract String getTagKey(org.jdom.Element objElem) throws ExceptionZZZ;
	
	public abstract String getTagName();

	public abstract String readValue(org.jdom.Element objElem) throws ExceptionZZZ;
	
	public abstract String readName(org.jdom.Element objElem) throws ExceptionZZZ;
}
