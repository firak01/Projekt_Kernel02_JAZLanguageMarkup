package basic.zKernel.html;


import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;

import org.jdom.Element;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.KernelUseObjectZZZ;

public abstract class AbstractKernelTagTypeZZZ extends KernelUseObjectZZZ implements IKernelTagTypeZZZ {
	private static final long serialVersionUID = -8395401121654554620L;
	private String sZDiscriminator = ""; //Damit werden unterklassen unterschieden. Z.B. table und tableWithHeader.
	
	public AbstractKernelTagTypeZZZ() throws ExceptionZZZ {
		super("");
	}
	
	public AbstractKernelTagTypeZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);
	}	
	
	public AbstractKernelTagTypeZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
	}
	 
	//################## VOM INTERFACE GEERBT ########################
	public abstract String getTagName();
	
		
	public abstract String getTagKey(org.jdom.Element objElem) throws ExceptionZZZ;
    public abstract String readTagKey(org.jdom.Element objElem) throws ExceptionZZZ;
	
    @Override
    public String getZDiscriminator() {
    	return this.sZDiscriminator;
    }
    
    @Override
    public void setZDiscriminator(String sZDiscriminator) {
    	this.sZDiscriminator=sZDiscriminator;
    }
    
    
	/**Read the value from an element.
	 * This element is representing an "input" tag.
	 * E.g.: <input name='IPNr' type='Hidden' value='84.135.199.2'>
	 * @return String, e.g. IPNr
	 *
	 * javadoc created by: 0823, 29.06.2006 - 17:31:59
	 * @throws ExceptionZZZ 
	*/
	public String readName(Element objElem) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			check:{
				if(objElem==null){
					ExceptionZZZ ez = new ExceptionZZZ("JElement", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//END check
		
		org.jdom.Attribute att = objElem.getAttribute("name");
		if(att==null) break main;
		
		sReturn = att.getValue();

		}//END main
		return sReturn;
	}
	
	/** Merke: Für table einen anderen Wert als "name" verwenden, denn...
	 * 
name is not an allowed attribute of <table> in HTML5. From the fine specification:

    Permitted attributes

    global attributes & border

And if you look at the global attributes you won't find name in the list.

So you can't put a name attribute on a <table> and still have valid HTML. id however is a global attribute so you can use that instead of name (if of course your name is going to be unique within the page).

Try running this:

<!DOCTYPE html>
<head><title>t</title></head>
<body>
    <table name="pancakes"></table>
</body>

through http://validator.w3.org and you'll see that it doesn't like a name on <table>.

If you're still writing HTML4, you still can't put a name attribute on a <table>. From the fine specification:

<!ATTLIST TABLE                        -- table element --
  %attrs;                              -- %coreattrs, %i18n, %events --
  summary     %Text;         #IMPLIED  -- purpose/structure for speech output--
  width       %Length;       #IMPLIED  -- table width --
  border      %Pixels;       #IMPLIED  -- controls frame width around table --
  frame       %TFrame;       #IMPLIED  -- which parts of frame to render --
  rules       %TRules;       #IMPLIED  -- rulings between rows and cols --
  cellspacing %Length;       #IMPLIED  -- spacing between cells --
  cellpadding %Length;       #IMPLIED  -- spacing within cells --
  >

There's no name in that list and no name in coreattrs, i18n, or events either.


	 * 
	 * @param objElem
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 02.06.2023, 08:38:57
	 */
	public String readId(Element objElem) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			check:{
				if(objElem==null){
					ExceptionZZZ ez = new ExceptionZZZ("JElement", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//END check
		
		org.jdom.Attribute att = objElem.getAttribute("id");
		if(att==null) break main;
		
		sReturn = att.getValue();

		}//END main
		return sReturn;
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
		
		
		org.jdom.Attribute att = objElem.getAttribute("value");
		if(att==null) break main;
		
		sReturn = att.getValue();
		
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
	
	/**Set the value to an element.
	 * This element is representing an "input" tag.
	 * E.g.: <input name='IPNr' type='Hidden' value='84.135.199.2'> 
	 * @return boolean true, wenn es geklappt hat.
	 *
	 * javadoc created by: 0823, 29.06.2006 - 17:31:59
	 * @throws ExceptionZZZ 
	 */
	public boolean setValue(org.jdom.Element objElem, String sValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			check:{
				if(objElem==null){
					ExceptionZZZ ez = new ExceptionZZZ("JElement", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//END check
		
		
		org.jdom.Attribute att = objElem.getAttribute("value");
		if(att==null) break main;
		
		att.setValue(sValue);
		
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
		return bReturn;
	}
}
