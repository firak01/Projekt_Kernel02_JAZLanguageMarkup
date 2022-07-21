package basic.zKernel.xml.writerJdom;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagUserZZZ;
import basic.zKernel.markup.content.IKernelContentXmlZZZ;

public abstract class KernelWriterXmlZZZ  extends KernelUseObjectZZZ {
	public KernelWriterXmlZZZ(KernelZZZ objKernel, String[] saFlagControl) throws ExceptionZZZ {
		super(objKernel);
		main:{		
			if(saFlagControl != null){
				String stemp; boolean btemp; 
				
				for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
					stemp = saFlagControl[iCount];
					btemp = setFlag(stemp, true);
					if(btemp==false){ 								   
						   ExceptionZZZ ez = new ExceptionZZZ(stemp, IFlagUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 					   
						   throw ez;		 
					}
				}
				if(this.getFlag("INIT")==true)	break main; 
			}									
		}//end main:
	}//end constructor
	
	/* this method must be overwritten by a custom-method which uses the desired Content-Store-Class
	 * @param objContent
	 * @return boolean, indicating the success of this method
	 * @throws ExceptionZZZ
	 */
	abstract public boolean addContent(IKernelContentXmlZZZ objContent) throws ExceptionZZZ;
	
	/**
	 * Replaces the current document by a new one and then addContent(..)
	 * @param objContent
	 * @return boolean indicating the success of the method
	 * @throws ExceptionZZZ
	 */
	abstract public boolean replaceContent(IKernelContentXmlZZZ objContent)throws ExceptionZZZ;
	
	/**
	 * Create a file with the whole document-content
	 * @param sFilePath
	 * @return boolean, indicating the success of the method
	 */
	abstract public boolean toFile(String sFilePath) throws ExceptionZZZ;
}
