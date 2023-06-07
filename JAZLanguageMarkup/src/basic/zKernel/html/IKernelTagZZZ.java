package basic.zKernel.html;

import basic.zBasic.ExceptionZZZ;

/**This interface provides methods for reading every type of attribute, which is allowed (or is necessary to be read) in a html-tag.
 * @author 0823
 *
 */
public interface IKernelTagZZZ {
	public org.jdom.Element getElement();		
	public IKernelTagTypeZZZ getTagType();
	public String getValue() throws ExceptionZZZ;
	public String getName() throws ExceptionZZZ;
	public String getId() throws ExceptionZZZ;		
}
