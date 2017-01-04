package zKernel.markup.content;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.markup.content.KernelContentXmlZZZ;

/** Weil KernelContentXmlZZZ eine abstracte Klasse ist, muss man f�r die konkreten Tests eine konkrete Klasse anbieten 
 * @author lindhaueradmin
 *
 */
public class ContentXmlDummyZZZ extends KernelContentXmlZZZ{
	public ContentXmlDummyZZZ(){
		super();
	}
	public ContentXmlDummyZZZ(KernelZZZ objKernel, String sRoot) throws ExceptionZZZ{
			super(objKernel, sRoot);
	}
	public boolean compute() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

}
