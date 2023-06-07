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
public class TagTypeTableWithHeaderZZZ extends TagTypeTableZZZ{
	public static final String sZDISCRIMINATOR = "WithHeader";//Damit werden Unterklassen unterschieden
	
	//###### Constructor
	public TagTypeTableWithHeaderZZZ() throws ExceptionZZZ{
		super();
	}
	
	public TagTypeTableWithHeaderZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
	}

//######### geerbt aus dem Interface
	public List<TagTableRowWithHeaderZZZ> readRowsWithHeader(org.jdom.Element objElem) throws ExceptionZZZ {
		List<TagTableRowWithHeaderZZZ>listReturn = new ArrayList<TagTableRowWithHeaderZZZ>();
		main:{
			List<Element> listElementBody = objElem.getChildren("TBODY");
			
			//Das sollte nur das BodyElement sein
			List<TagTableRowZZZ>listTableRow = new ArrayList<TagTableRowZZZ>();
			for(Element elementBody : listElementBody) {
				List<Element> listElementRows = elementBody.getChildren(new TagTypeTableRowZZZ().sTAGNAME.toUpperCase());
				for(Element element : listElementRows) {
					TagTableRowZZZ objRow = new TagTableRowZZZ(element);
					if(objRow.isTableRow()) {											
						listTableRow.add(objRow);
					}					
				}			
			}
			
			//Nun die Header Zeile
			List<TagTableRowZZZ>listTableHeader = new ArrayList<TagTableRowZZZ>();
			for(Element elementBody : listElementBody) {
				List<Element> listElementRows = elementBody.getChildren(new TagTypeTableRowZZZ().sTAGNAME.toUpperCase());
				for(Element element : listElementRows) {
					TagTableRowZZZ objRow = new TagTableRowZZZ(element);
					if(objRow.isTableHeader()) {											
						listTableHeader.add(objRow);
					}					
				}			
			}
			
			//Annahme: Es gibt nur 1 Headerzeile
			TagTableRowZZZ objHeaderRow = listTableHeader.get(0);
			
			
			for(TagTableRowZZZ objRow : listTableRow) {
				TagTableRowWithHeaderZZZ objRowWithHeader = new TagTableRowWithHeaderZZZ(objRow.getElement(), objHeaderRow.getElement());
				listReturn.add(objRowWithHeader);
			}
			
		}//end main
		return listReturn;
	}
	
	@Override
    public String getZDiscriminator() {
    	return TagTypeTableWithHeaderZZZ.sZDISCRIMINATOR;
    }
}
