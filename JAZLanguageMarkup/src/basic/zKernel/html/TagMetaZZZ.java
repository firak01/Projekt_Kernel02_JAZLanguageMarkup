package basic.zKernel.html;

import org.jdom.Element;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;

public class TagMetaZZZ extends AbstractKernelTagZZZ{

	public TagMetaZZZ(IKernelZZZ objKernel, Element objElem) throws ExceptionZZZ {
		super(objKernel, new TagTypeMetaZZZ(), objElem);		
	}
	
	public String readCharset() throws ExceptionZZZ{
		String sReturn=null;
		main:{
			org.jdom.Element elemTag = this.getElement();
			if(elemTag==null){
				ExceptionZZZ ez = new ExceptionZZZ("org.jdom.Element", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			TagTypeMetaZZZ objType = (TagTypeMetaZZZ) this.getTagType();
			sReturn = objType.readCharset(elemTag);
		}//end main:
		return sReturn;
	}
}
