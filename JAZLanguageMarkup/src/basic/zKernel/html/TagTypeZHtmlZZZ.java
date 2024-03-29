package basic.zKernel.html;

import org.jdom.Element;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;

public class TagTypeZHtmlZZZ  extends AbstractKernelTagTypeZZZ{
	public final static String sTAGNAME = "zhtml";
	
	//###### Constructor
	public TagTypeZHtmlZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
	}

//######### gerbt aus dem Interface
	public String getTagKey(Element objElem) throws ExceptionZZZ{
		return this.readName(objElem);
	}
	public String getTagName() {
		return TagTypeZHtmlZZZ.sTAGNAME;
	}
	
	@Override
	public String readTagKey(Element objElem) throws ExceptionZZZ {
		// TODO Auto-generated method stub
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
		
		
		sReturn = objElem.getValue();
		
		
		/* FGL: So liest man den Value-Wert aus. ABER: Beim ZHTML - Tag z�hlt nur der name
		//org.jdom.Attribute att = objElem.getAttribute("value");
		//if(att==null) break main;
		
		//sReturn = att.getValue();
		*/
		
		
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
}
