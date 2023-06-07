package basic.zKernel.html;

import basic.zBasic.ExceptionZZZ;

/**This interface provides methods for reading every type of attribute, which is allowed (or is necessary to be read) in a html-tag.
 * @author 0823
 *
 */
public interface IKernelTagTypeZZZ {
	public String sTagName = ""; 
	public String sDiscriminator = "";
	
	/** Returns a unique name. E.g. for input-tags this will be the name attribute.
	* @return String
	* 
	* lindhaueradmin; 02.07.2006 09:38:23
	 */
	public abstract String getTagKey(org.jdom.Element objElem) throws ExceptionZZZ;
	
	
	/**Returns the name of the tag
	 * @return String
	 *
	 * javadoc created by: 0823, 29.06.2006 - 11:58:29
	 */
	public abstract String getTagName(); 
	
	
	/**Reading the value from a tag depends on the attributes, which a tag-type has.
	 * This method uses different algorithms in the classes, where ist is implemented.
	 * @return String
	 *
	 * javadoc created by: 0823, 29.06.2006 - 17:33:49
	 */
	public abstract String readValue(org.jdom.Element objElem) throws ExceptionZZZ;
	public abstract boolean setValue(org.jdom.Element objElem, String sValue) throws ExceptionZZZ;
	
	public abstract String readName(org.jdom.Element objElem) throws ExceptionZZZ;
	public abstract String readId(org.jdom.Element objElem) throws ExceptionZZZ;
	
	//Damit werden spezielle Untertypen unterschieden. 
	//Z.B. table reicht nicht, es gibt auch TableWithHeaderZZZ
	//Das wird in der KernelTagFactoryZZZ zur Erzeugung der korrekten Klasse verwendet
	public String getZDiscriminator();
	public void setZDiscriminator(String sDiscriminator);
}
