package basic.zKernel.html;

import java.util.Iterator;
import java.util.List;

import org.jdom.Content;
import org.jdom.Element;
import org.jdom.Text;
import org.jdom.filter.ElementFilter;
import org.jdom.filter.Filter;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;

/**This tag describes a input-tag in html.
 * @author 0823
 *
 */
public class TagTypeTableCellZZZ extends AbstractKernelTagTypeZZZ{
	public final static String sTAGNAME = "td";
	
	//###### Constructor
	public TagTypeTableCellZZZ() throws ExceptionZZZ{
		super("");
	}
	
	public TagTypeTableCellZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
	}

//######### geerbt aus dem Interface
	@Override
	public String getTagName() {
		return TagTypeTableCellZZZ.sTAGNAME;
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
		return TagTypeTableCellZZZ.readValueStatic(objElem);
		
//		String sReturn = null;
//		main:{
//			check:{
//				if(objElem==null){
//					ExceptionZZZ ez = new ExceptionZZZ("JElement", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}
//			}//END check
//		
//		//Eine Zelle in einer Tabelle ist mit TD = Table Data gekennzeichnet
//		//Merke: Eine Tabellenspalte ist demnach eine Sammlung von Zellen über mehrer Zeilen hinweg...
//		//       TODO ggfs. dafuer eine eigene Klasse machen 
//		List listElem = objElem.getContent();
//		
//		//Dieser Scheiss Filter funktioniert nicht so einfach...
////		Filter objFilter = new ElementFilter("Text");		
////		Iterator it = objElem.getDescendants(objFilter);
////		while(it.hasNext()) {
////			Element el = (Element) it.next();
////			sReturn = el.toString();
////		}
//		
//		for(Object el : listElem) {
//			if(el instanceof Text) {
//				Text objText = (Text) el;
//				sReturn = objText.getTextTrim();//Whitespace wird darum entfernt.
//			}
//		}
//
//		}//END main
//		return sReturn;
	}
	
	/**Read the value from an element.
	 * This element is representing an "input" tag.
	 * E.g.: <input name='IPNr' type='Hidden' value='84.135.199.2'> 
	 * @return String, e.g. 84.135.199.2
	 *
	 * javadoc created by: 0823, 29.06.2006 - 17:31:59
	 * @throws ExceptionZZZ 
	 */	
	public static String readValueStatic(org.jdom.Element objElem) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(objElem==null){
				ExceptionZZZ ez = new ExceptionZZZ("JElement", iERROR_PARAMETER_MISSING, TagTypeTableHeaderCellZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		
		//Eine Tabelle hat selbst keinen Inhalt, nur weitere Elemente (Zeilen tr, Spalten td)
		List listElem = objElem.getContent();
		for(Object el : listElem) {
			if(el instanceof Text) {
				Text objText = (Text) el;
				sReturn = objText.getTextTrim();//Whitespace wird darum entfernt.
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
	
	public boolean setValue(org.jdom.Element objElem, String sValue) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			check:{
				if(objElem==null){
					ExceptionZZZ ez = new ExceptionZZZ("JElement", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//END check
	
			//Eine Zelle in einer Tabelle ist mit TD = Table Data gekennzeichnet
			//Merke: Eine Tabellenspalte ist demnach eine Sammlung von Zellen über mehrer Zeilen hinweg...
			//       TODO ggfs. dafuer eine eigene Klasse machen 
			List listElem = objElem.getContent();
			
			//Dieser Scheiss Filter funktioniert nicht so einfach...
		//	Filter objFilter = new ElementFilter("Text");		
		//	Iterator it = objElem.getDescendants(objFilter);
		//	while(it.hasNext()) {
		//		Element el = (Element) it.next();
		//		sReturn = el.toString();
		//	}
	    
	    int iIndex=-1;
	    boolean bAnyReplaced = false;
		for(Object el : listElem) {
			iIndex++;
			if(el instanceof Text) {
				Text objText = new Text(sValue);
				listElem.remove(el);
				listElem.add(iIndex, objText);
				bAnyReplaced = true;
			}

		}
		
		bReturn = bAnyReplaced;
	}//END main
	return bReturn;
	}


}
