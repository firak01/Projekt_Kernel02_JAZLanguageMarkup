package basic.zKernel.html;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Content;
import org.jdom.Element;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;

/**This tag describes a input-tag in html.
 * @author 0823
 *
 */
public class TagTypeTableZZZ extends AbstractKernelTagTypeZZZ{
	public final static String sTAGNAME = "table";
	
	//###### Constructor
	public TagTypeTableZZZ() throws ExceptionZZZ{
		super("");
	}
	
	public TagTypeTableZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
	}

//######### geerbt aus dem Interface
	
	@Override
	public String getTagName() {
		return TagTypeTableZZZ.sTAGNAME;
	}
		
	@Override
	public String getTagKey(Element objElem) throws ExceptionZZZ{
		return this.readTagKey(objElem);
	}
			
	@Override
	public String readTagKey(Element objElem) throws ExceptionZZZ {
		return this.readId(objElem); //Merke: Das Attribut "name" ist fuer Tabellen nicht zulässing, darum "id" verwenden
	}
	
	/**Read the value from an element.
	 * This element is representing an "input" tag.
	 * E.g.: <input name='IPNr' type='Hidden' value='84.135.199.2'> 
	 * @return String, e.g. 84.135.199.2
	 *
	 * javadoc created by: 0823, 29.06.2006 - 17:31:59
	 * @throws ExceptionZZZ 
	 */
//	public String readValue(org.jdom.Element objElem) throws ExceptionZZZ{
//		String sReturn = null;
//		main:{
//			check:{
//				if(objElem==null){
//					ExceptionZZZ ez = new ExceptionZZZ("JElement", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}
//			}//END check
//		
//			//Eine Tabelle hat selbst keinen Inhalt, nur weitere Elemente (Zeilen tr, Spalten td)
//			List listElem = objElem.getContent();
//			sReturn = listElem.toString();			
//		}//END main
//		return sReturn;
//	}
	
	/**Read the Lines from the table. And separate them by \n 
	 * Use a default Column Seperator.
	 */
	public String readValue(org.jdom.Element objElem) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			check:{
				//if(this.getElement()==null){
				if(objElem==null) {
					ExceptionZZZ ez = new ExceptionZZZ("JElement", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//END check
		
			//Eine Tabelle hat selbst keinen Inhalt, nur weitere Elemente (Zeilen tr, Spalten td)
			boolean bAnyRow=false;			
			List<TagTableRowZZZ> listRow = this.readRows(objElem);
			for(TagTableRowZZZ objRow: listRow) {
				String sRow = objRow.getValue();					
				if(bAnyRow) {
					sReturn = sReturn + "\n" + sRow;
				}else {
					sReturn = sRow;
				}
				bAnyRow=true;
			}
		}//END main
		return sReturn;
	}
	
	public List<TagTableRowZZZ> readRows(org.jdom.Element objElem) throws ExceptionZZZ {
		List<TagTableRowZZZ>listReturn = new ArrayList<TagTableRowZZZ>();
		main:{
			List<Element> listElementBody = objElem.getChildren("TBODY");
			
			//Das sollte nur das BodyElement sein
			for(Element elementBody : listElementBody) {
				List<Element> listElementRows = elementBody.getChildren(new TagTypeTableRowZZZ().sTAGNAME.toUpperCase());
				for(Element element : listElementRows) {
					TagTableRowZZZ objRow = new TagTableRowZZZ(element);
					if(objRow.isTableRow()) {											
						listReturn.add(objRow);
					}
				}			
			}
		}//end main
		return listReturn;
	}
}
