package basic.zKernel.html;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;

public class TagTableCellZZZ extends AbstractKernelTagZZZ{
	private static final long serialVersionUID = -4171802821086651895L;

	public TagTableCellZZZ(Element objElem) throws ExceptionZZZ {
		super(new TagTypeTableCellZZZ(), objElem);		
	}
	
	public TagTableCellZZZ(IKernelZZZ objKernel, Element objElem) throws ExceptionZZZ {
		super(objKernel, new TagTypeTableCellZZZ(), objElem);		
	}
	
	public String toString() {
		String sReturn = null;
		main:{
			Element objElem = this.getElement();
			sReturn = objElem.toString();
			sReturn = "";
		}//end main
		return sReturn;
	}
	
	public static boolean isTableCell(Element objElement) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objElement==null) {
				ExceptionZZZ ez = new ExceptionZZZ("org.jdom.Element",iERROR_PARAMETER_MISSING, TagTableZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			String stemp = objElement.getQualifiedName();
			if(stemp.equalsIgnoreCase(TagTypeTableCellZZZ.sTAGNAME)){
				bReturn = true;
			}
		}//end main
		return bReturn;
	}
	
	
	
	
}
