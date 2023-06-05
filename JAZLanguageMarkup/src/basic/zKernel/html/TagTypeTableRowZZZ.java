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
public class TagTypeTableRowZZZ extends AbstractKernelTagTypeZZZ{
	public final static String sTAGNAME = "tr";
	
	//###### Constructor
	public TagTypeTableRowZZZ() throws ExceptionZZZ{
		super("");
	}
	
	public TagTypeTableRowZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
	}

//######### geerbt aus dem Interface
	@Override
	public String getTagName() {
		return TagTypeTableRowZZZ.sTAGNAME;
	}
	
	@Override
	public String getTagKey(Element objElem) throws ExceptionZZZ{
		return this.readTagKey(objElem); //Merke: Das Attribut "name" ist fuer Tabellen nicht zulässing, darum "id" verwenden
	}
	
	@Override
	public String readTagKey(Element objElem) throws ExceptionZZZ {
		return null;
	}
	
		
	
	
	
	/**Read the value from an element.
	 * This element is representing an "input" tag.
	 * E.g.: <input name='IPNr' type='Hidden' value='84.135.199.2'> 
	 * @return String, e.g. 84.135.199.2
	 *
	 * javadoc created by: 0823, 29.06.2006 - 17:31:59
	 * @throws ExceptionZZZ 
	 */
	public String readValue(org.jdom.Element objElem) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			check:{
				if(objElem==null){
					ExceptionZZZ ez = new ExceptionZZZ("JElement", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//END check
		
		//Eine Zeile hat selbst keinen Inhalt, nur weitere Elemente (Spalten TD)
		//List listElem = objElem.getContent();
		//sReturn = listElem.toString();
		
		List<TagTableCellZZZ>listCell=new ArrayList<TagTableCellZZZ>();
		List<Element> listElement = objElem.getChildren(new TagTypeTableCellZZZ().sTAGNAME.toUpperCase());
		for(Element element : listElement) {
			TagTableCellZZZ objCell = new TagTableCellZZZ(element);
			listCell.add(objCell);
		}
		
		boolean bHasAnyValue = false; String sSeparator = "|"; 
		for(TagTableCellZZZ objCell : listCell) {
			if(bHasAnyValue) {
				sReturn = sReturn + sSeparator + objCell.readValue();
			}else {
				sReturn = objCell.readValue();
				bHasAnyValue = true;
			}
		}
				
//		org.jdom.Attribute att = objElem.getAttribute("value");
//		if(att==null) break main;
//		
//		sReturn = att.getValue();
		
		/* FGL: DAS IST MIR ZU KOMPLIZIERT, so liest man den Attribut-Wert aus
		boolean bIPNrElement = false;
		List lAtt = elem.getAttributes();
		if(lAtt !=null){
			Iterator iteratorAtt = lAtt.iterator();
			while(iteratorAtt.hasNext()){
				//In dieser Schleife alle Attribute eines Inputfelds durchgehen
				Attribute att = (Attribute) iteratorAtt.next();
				System.out.print(att.getName());
				System.out.println(" ... " + att.getValue());
				if(att.getName().equals("name")){
					if(att.getValue().equals("IPNr")) bIPNrElement = true;
				}
				if(att.getName().equals("value") && bIPNrElement ==true){
					sURLNext = att.getValue();
				}
				
			}
		}
			*/			
		
		
		}//END main
		return sReturn;
	}

	public static boolean isTableRow(Element objElement) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objElement==null) {
				ExceptionZZZ ez = new ExceptionZZZ("org.jdom.Element",iERROR_PARAMETER_MISSING, TagTableZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			String stemp = objElement.getQualifiedName();
			if(stemp.equalsIgnoreCase(TagTypeTableRowZZZ.sTAGNAME)){
				
				//Das reicht noch nicht Darunter duerfen keine TH-Elemente sein
				List<Element> list = objElement.getChildren(TagTypeTableHeaderZZZ.sTAGNAME.toUpperCase());
				if(!list.isEmpty()) break main;
				
				bReturn = true;
			}
		}//end main
		return bReturn;
	}
	
	public static boolean isTableHeader(Element objElement) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objElement==null) {
				ExceptionZZZ ez = new ExceptionZZZ("org.jdom.Element",iERROR_PARAMETER_MISSING, TagTableZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			String stemp = objElement.getQualifiedName();
			if(stemp.equalsIgnoreCase(TagTypeTableRowZZZ.sTAGNAME)){
				
				//Das reicht noch nicht Darunter dürfen muessen TH-Elemente sein
				List<Element> list = objElement.getChildren(TagTypeTableHeaderZZZ.sTAGNAME.toUpperCase());
				if(list.isEmpty()) break main;
				
				bReturn = true;
			}
		}//end main
		return bReturn;
	}
}
