package basic.zKernel.html;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.KernelUseObjectZZZ;

public class KernelTagZZZ extends KernelUseObjectZZZ {
		private org.jdom.Element objElem = null;
		private IKernelTagTypeZZZ objTagType = null;
		
		public KernelTagZZZ(IKernelZZZ objKernel, IKernelTagTypeZZZ objType, org.jdom.Element objElem) throws ExceptionZZZ{
			super(objKernel);
			KernelTagNew_(objType, objElem);
		}
		
		private void KernelTagNew_(IKernelTagTypeZZZ objType, org.jdom.Element objElem) throws ExceptionZZZ{
			main:{
				check:{
					if(objType==null){
						ExceptionZZZ ez = new ExceptionZZZ("KernelTagType", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					if(objElem==null){
						ExceptionZZZ ez = new ExceptionZZZ("Element", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}					
				}//END check
		
			this.setTagType(objType);
			this.setElement(objElem);			
			}//End main
		}
		
	/** @return String, the key to identify this tag. E.g. readName(), when searching for an input - tag.
	* @return 
	* 
	* lindhaueradmin; 02.07.2006 08:32:11
	 */
	public String getTagKey(org.jdom.Element objElem) throws ExceptionZZZ{
		return this.getTagType().getTagKey(objElem);
	}
	
	
	public String readValue() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			org.jdom.Element objElem; IKernelTagTypeZZZ objType;
			check:{
				objElem = this.getElement();
				if(objElem==null){
					ExceptionZZZ ez = new ExceptionZZZ("Element",iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				objType = this.getTagType();
				if(objType==null){
					ExceptionZZZ ez = new ExceptionZZZ("TagType",iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
									
			}//END check:
			
			sReturn = objType.readValue(objElem);
			
		}//END main:
		return sReturn;
	}
	
	public String readName() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			org.jdom.Element objElem; IKernelTagTypeZZZ objType;
			check:{
				objElem = this.getElement();
				if(objElem==null){
					ExceptionZZZ ez = new ExceptionZZZ("Element",iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				objType = this.getTagType();
				if(objType==null){
					ExceptionZZZ ez = new ExceptionZZZ("TagType",iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
									
			}//END check:
			
			sReturn = objType.readName(objElem);
			
		}//END main:
		return sReturn;
	}
		
		//######## Getter / Setter ##################
		public org.jdom.Element getElement(){
			return this.objElem;
		}
		private void setElement(org.jdom.Element objElem){
			this.objElem = objElem;
		}
		
		private void  setTagType(IKernelTagTypeZZZ objTagType){
			this.objTagType = objTagType;
		}
		public IKernelTagTypeZZZ getTagType(){
			return this.objTagType;
		}
}
