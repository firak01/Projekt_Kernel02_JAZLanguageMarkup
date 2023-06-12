package basic.zKernel.html;

import java.util.List;

import org.jdom.Content;
import org.jdom.Element;
import org.jdom.Text;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;

/**This tag describes a TH-Tag.
 * @author 0823
 *
 */
public class TagTypeTableHeaderCellZZZ extends AbstractKernelTagTypeZZZ{
	public final static String sTAGNAME = "th";
	
	//###### Constructor
	public TagTypeTableHeaderCellZZZ() throws ExceptionZZZ{
		super("");
	}
	
	public TagTypeTableHeaderCellZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
	}

//######### geerbt aus dem Interface
	@Override
	public String getTagKey(Element objElem) throws ExceptionZZZ{
		return this.readTagKey(objElem);
	}
	
	@Override
	public String readTagKey(Element objElem) throws ExceptionZZZ {
		return this.readId(objElem); //Merke: Das Attribut "name" ist fuer Tabellen nicht zulässing, darum "id" verwenden
	}
	
	@Override
	public String getTagName() {
		return TagTypeTableHeaderCellZZZ.sTAGNAME;
	}
	
	
	/**Read the value from an element.
	 * This element is representing an "input" tag.
	 * E.g.: <input name='IPNr' type='Hidden' value='84.135.199.2'> 
	 * @return String, e.g. 84.135.199.2
	 *
	 * javadoc created by: 0823, 29.06.2006 - 17:31:59
	 * @throws ExceptionZZZ 
	 */
	@Override
	public String readValue(org.jdom.Element objElem) throws ExceptionZZZ{
		if(objElem==null){
			ExceptionZZZ ez = new ExceptionZZZ("JElement", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
	
		return TagTypeTableHeaderCellZZZ.readValueStatic(objElem);
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
		return TagTypeTableCellZZZ.readValueStatic(objElem);
	}

}
