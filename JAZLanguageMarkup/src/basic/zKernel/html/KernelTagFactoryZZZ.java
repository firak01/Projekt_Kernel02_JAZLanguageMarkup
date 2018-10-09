package basic.zKernel.html;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.KernelUseObjectZZZ;

public class KernelTagFactoryZZZ extends KernelUseObjectZZZ{

	
	public static KernelTagTypeZZZ createTagTypeByElement(IKernelZZZ objKernel, org.jdom.Element objElem) throws ExceptionZZZ{
		KernelTagTypeZZZ objReturn = null;
		main:{			
			check:{
					if(objElem == null){
						ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING+"Element", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
						throw ez;					
					}
			}//end check
		
		if(objElem.getName().equalsIgnoreCase("input")){
				objReturn = new TagTypeInputZZZ(objKernel);
		}else if(objElem.getName().equalsIgnoreCase("body")){
				objReturn = new TagTypeBodyZZZ(objKernel);
		}else{
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
	public static KernelTagZZZ createTagByElement(IKernelZZZ objKernel, org.jdom.Element objElem) throws ExceptionZZZ{
		KernelTagZZZ objReturn = null;
		main:{ 
			check:{
				if(objElem == null){
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING+"Element", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;					
				}
			}//END check:
		
		KernelTagTypeZZZ objType = KernelTagFactoryZZZ.createTagTypeByElement(objKernel, objElem);
		if(objType.getTagName().equalsIgnoreCase("input")){
			objReturn = new TagInputZZZ( objKernel, objType, objElem);
		}else{
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
	public static KernelTagZZZ createTagByElement(IKernelZZZ objKernel, org.jdom.Element objElem, KernelTagTypeZZZ objType) throws ExceptionZZZ{
		KernelTagZZZ objReturn = null;
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

		if(objType.getTagName().equalsIgnoreCase("input")){
			objReturn = new TagInputZZZ( objKernel, objType, objElem);
		}else{
			ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE+"unhandled tag '"+ objType.getTagName() +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
			throw ez;	
		}
		
		}//END main
		return objReturn;
	}
	
}
