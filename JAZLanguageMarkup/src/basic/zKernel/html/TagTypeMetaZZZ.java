package basic.zKernel.html;

import org.jdom.Element;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;

public class TagTypeMetaZZZ  extends KernelTagTypeZZZ {	
	public final static String TagName = "meta";
	 
	//######## Constructor
	public TagTypeMetaZZZ(IKernelZZZ objKernel){
		super(objKernel);
	}
	
	//### gerbt aus dem Interface
	public String getTagName() {
		return TagTypeMetaZZZ.TagName;
	}

	/* Gibt NULL zur�ck, weil es nur einen META-TAG gibt, und nix womit man die Tags unterscheiden kann.
	 * 
	 * (non-Javadoc)
	 * @see basic.zKernel.html.IKernelTagTypeZZZ#getTagKey(org.jdom.Element)
	 */
	public String getTagKey(Element objElem) throws ExceptionZZZ {	
		return null;
	}

	/* Gibt hier null zur�ck, weil dazwischen ja kein Wert steht
	 * 
	 * (non-Javadoc)
	 * @see basic.zKernel.html.IKernelTagTypeZZZ#readValue(org.jdom.Element)
	 */
	public String readValue(Element objElem) throws ExceptionZZZ {	
		return null;
	}

	/* Gibt NULL zur�ck, weil es kein NAME-Attribut gibt
	 *  (non-Javadoc)
	 * @see basic.zKernel.html.IKernelTagTypeZZZ#readName(org.jdom.Element)
	 */
	public String readName(Element objElem) throws ExceptionZZZ {	
		return null;
	}
	
	/** Liest aus dem Tag das Attribut 'content' aus. Zerlegt dies und gibt das 'charset' zur�ck.
	 *   Merke: Der Tag sieht so aus:    <META content="text/html; charset=ISO-8859-1" http-equiv="content-type" ........
	* @param objElem
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 11.04.2007 10:29:57
	 */
	public String readCharset(Element objElem) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(objElem==null){
				ExceptionZZZ ez = new ExceptionZZZ("JElement", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			org.jdom.Attribute att = objElem.getAttribute("content");
			if(att==null) break main;
			
			sReturn = att.getValue();
			if(StringZZZ.isEmpty(sReturn)) break main;
			
			//sReturn ist momentan z.B.: text/html; charset=ISO-8859-1)
			//Das reicht noch nicht. Nun muss daraus der Wert nach dem charset ausgelesen werden
			sReturn = sReturn.toLowerCase();
			sReturn = StringZZZ.right(sReturn, "charset=");
			if(StringZZZ.isEmpty(sReturn)) break main;
			
			//TODO: Was, wenn noch weitere Werte dahinter folgen ???
			
			//Nun ist der Charset immer in Gro�buchstaben, darum einfach wieder zur�ckwandeln
			sReturn = sReturn.toUpperCase();
			
			
			
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
			
			
		}//end main:
		return sReturn;
	}
}