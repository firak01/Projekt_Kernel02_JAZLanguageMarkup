package basic.zKernel.html;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;

/**Erweitert die einfache Datenzelle um Eigenschaften, die für eine Spalte wichtig sind.
 * @author Fritz Lindhauer, 06.06.2023, 18:53:27
 * 
 */
public class TagTableColumnCellZZZ extends TagTableCellZZZ{
	private static final long serialVersionUID = -4171802821086651895L;
	private org.jdom.Element objElemHeader = null; //Die Spaltenüberschrift

	public TagTableColumnCellZZZ(Element objElem, Element objElemHeader) throws ExceptionZZZ {
		//super(new TagTypeTableCellZZZ(), objElem);		
		super(objElem);
		TagTableColumnCellNew_(objElemHeader);
	}
	
	public TagTableColumnCellZZZ(IKernelZZZ objKernel, Element objElem) throws ExceptionZZZ {
		super(objKernel,objElem);	
		TagTableColumnCellNew_(objElemHeader);
	}
	private boolean TagTableColumnCellNew_(Element objElemHeader) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objElemHeader==null) {
				ExceptionZZZ ez = new ExceptionZZZ("org.jdom.Element as column-header",iERROR_PARAMETER_MISSING, TagTableZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			this.setColumnHeader(objElemHeader);
			
			this.setTagType(new TagTypeTableColumnCellZZZ());
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public static boolean isTableColumnCell(Element objElement) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objElement==null) {
				ExceptionZZZ ez = new ExceptionZZZ("org.jdom.Element",iERROR_PARAMETER_MISSING, TagTableZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			String stemp = objElement.getQualifiedName();
			if(stemp.equalsIgnoreCase(TagTypeTableColumnCellZZZ.sTAGNAME)){
				bReturn = true;
			}
		}//end main
		return bReturn;
	}
	
	public Element getColumnHeader() {
		return this.objElemHeader;
	}
	public void setColumnHeader(Element objElemHeader) {
		this.objElemHeader = objElemHeader;
	}
	
	public String getLabel() throws ExceptionZZZ {
		Element objElemHeader = this.getColumnHeader();
		String sLabel = TagTypeTableHeaderCellZZZ.readValueStatic(objElemHeader);
		return sLabel;
	}
}
