package basic.zKernel.html;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.KernelUseObjectZZZ;

public class KernelTagFactoryZZZ extends KernelUseObjectZZZ{

	
	public static AbstractKernelTagTypeZZZ createTagTypeByElement(IKernelZZZ objKernel, org.jdom.Element objElem) throws ExceptionZZZ{
		AbstractKernelTagTypeZZZ objReturn = null;
		main:{			
			check:{
					if(objElem == null){
						ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING+"Element", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
						throw ez;					
					}
			}//end check
		
			String sTagName = objElem.getName().toLowerCase();
			switch (sTagName) {
			case "input":
				objReturn = new TagTypeInputZZZ(objKernel);
				break;
			case "body":
				objReturn = new TagTypeBodyZZZ(objKernel);
				break;
			case "table":
				objReturn = new TagTypeTableZZZ(objKernel);
				break;			
			default:
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE+"unhandled element-type '" + objElem.getName() + "'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;				
			}
		
		}//end main
		return objReturn;
	}
	

	/** Creates a concrete tag-object. Use cast to convert from KernelTagZZZ to the concrete tag-object.
	 *    e.g.: TagInputZZZ objTagInput = (TagInputZZZ) KernelTagFactoryZZZ.createTagByElement(KernelZZZ, org.jdom.Element);
	* @return KernelTagZZZ
	* @param objKernel
	* @param objElem
	* @throws ExceptionZZZ 
	* 
	* lindhaueradmin; 30.06.2006 07:23:31
	 */
	public static AbstractKernelTagZZZ createTagByElement(IKernelZZZ objKernel, org.jdom.Element objElem) throws ExceptionZZZ{
		AbstractKernelTagZZZ objReturn = null;
		main:{ 
			check:{
				if(objElem == null){
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING+"Element", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;					
				}
			}//END check:
		
		AbstractKernelTagTypeZZZ objType = KernelTagFactoryZZZ.createTagTypeByElement(objKernel, objElem);
		String sTagName = objType.getTagName().toLowerCase();
		switch (sTagName) {
		case "input":
			objReturn = new TagInputZZZ( objKernel, objElem);
			break;
		case "table":
			objReturn = new TagTableZZZ( objKernel, objElem);
			break;			
		default:
			ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE+"unhandled tag '"+ objType.getTagName() +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
			throw ez;	
		}
		
		
		}//END main
		return objReturn;
	}
	
	/**  Creates a concrete tag-object. Use cast to convert from KernelTagZZZ to the concrete tag-object.
	 *    e.g.: TagInputZZZ objTagInput = (TagInputZZZ) KernelTagFactoryZZZ.createTagByElement(KernelZZZ, org.jdom.Element, KernelTagTypeZZZ);
	* @return KernelTagZZZ
	* @param objKernel
	* @param objElem
	* @param objType
	* @return
	* @throws ExceptionZZZ 
	* 
	* lindhaueradmin; 02.07.2006 08:22:02
	 */
	public static AbstractKernelTagZZZ createTagByElement(IKernelZZZ objKernel, org.jdom.Element objElem, IKernelTagTypeZZZ objType) throws ExceptionZZZ{
		AbstractKernelTagZZZ objReturn = null;
		main:{ 
			check:{
				if(objElem == null){
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING+"Element", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;					
				}
				if(objType == null){
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING+"TagType", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;		
				}
			}//END check:

		String sTagName = objType.getTagName().toLowerCase();
		switch(sTagName) {
		case "input":
			objReturn = new TagInputZZZ(objElem);			
			break;
		case "table":
			if(objType.getZDiscriminator().equals(TagTypeTableWithHeaderZZZ.sZDISCRIMINATOR)){
				objReturn = new TagTableWithHeaderZZZ(objElem);
			}else {
				objReturn = new TagTableZZZ(objElem);
			}
			break;		
		default:
			ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE+"unhandled tag '"+ objType.getTagName() +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
			throw ez;	
		}
		
		objReturn.setKernelObject(objKernel);
		
		}//END main
		return objReturn;
	}
	
	
	
}
