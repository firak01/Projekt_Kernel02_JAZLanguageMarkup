/*
 * Created on 26.11.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zKernel.net.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.html.reader.KernelReaderHtmlZZZ;
import basic.zKernel.script.reader.KernelReaderScriptJavascriptZZZ;


/**
 * @author Lindhauer
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KernelReaderPageZZZ extends KernelUseObjectZZZ{	
	private ArrayList obj_alsURLContent; 
	private KernelReaderScriptJavascriptZZZ objReaderJS;
	private InputStream obj_inStreamURLContent;
	private KernelReaderHtmlZZZ objReaderHTML;
	
	public KernelReaderPageZZZ(ArrayList obj_alsURLContent, String[] saFlagControl, String sFlagControl ) throws ExceptionZZZ{
		super("");
		KernelPageReaderNew_(saFlagControl);
		if(this.getFlag("init")==false){
			KernelPageReaderNewByArrayList_(obj_alsURLContent, sFlagControl);
		}
	}
	
	public KernelReaderPageZZZ(InputStream obj_inStreamURLContent, String[] saFlagControl, String sFlagControl) throws ExceptionZZZ{
		super("");		
		KernelPageReaderNew_(saFlagControl);
		if(!this.getFlag("init")){
			KernelPageReaderNewByStream_(obj_inStreamURLContent, sFlagControl);
		}
	}
	
	
	public KernelReaderPageZZZ(IKernelZZZ objKernel, ArrayList obj_alsURLContent, String[] saFlagControl, String sFlagControl ) throws ExceptionZZZ{
		super(objKernel);
		KernelPageReaderNew_(saFlagControl);
		if(this.getFlag("init")==false){
			KernelPageReaderNewByArrayList_(obj_alsURLContent, sFlagControl);
		}
	}
	
	public KernelReaderPageZZZ(IKernelZZZ objKernel, InputStream obj_inStreamURLContent, String[] saFlagControl, String sFlagControl) throws ExceptionZZZ{
		super(objKernel);		
		KernelPageReaderNew_(saFlagControl);
		if(this.getFlag("init")==false){
			KernelPageReaderNewByStream_(obj_inStreamURLContent, sFlagControl);
		}
	}
	
	private void KernelPageReaderNew_(String[] saFlagControl) throws ExceptionZZZ{
		main:{
		if(saFlagControl != null){
			String stemp; boolean btemp; String sLog;
			for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
				stemp = saFlagControl[iCount];
				btemp = setFlag(stemp, true);
				if(btemp==false){ 								   
					 String sKey = stemp; 
					 sLog = "the passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
					 this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
					//							  Bei der "Übergabe auf Verdacht" keinen Fehler werfen!!!							
					// ExceptionZZZ ez = new ExceptionZZZ(stemp, IFlagUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 							
					// throw ez;		 
				}
			}
			if(this.getFlag("init")==true) break main;
		}
		}//END main:
	}
	
	private boolean KernelPageReaderNewByArrayList_(ArrayList obj_alsURLContent, String sFlagControl) throws ExceptionZZZ{
		boolean bReturn = false;
				main:{					
					//try{	
					//Falls die URL null ist, wird ein entsprechender Fehler ausgegeben.
					if(obj_alsURLContent == null){
						ExceptionZZZ ez = new ExceptionZZZ("URL-Content ArrayList", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;								
					}
					if(obj_alsURLContent.size()==0){
						ExceptionZZZ ez = new ExceptionZZZ("URL-Content ArrayList", iERROR_PARAMETER_EMPTY,   this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;		
					}				
					this.setURLContentArrayList(obj_alsURLContent);
					 this.setFlag("UseArrayList", true);
				}//end main:
				return bReturn;		
			}//end private constructor
	
	private boolean KernelPageReaderNewByStream_(InputStream obj_InStream, String sFlagControl) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			//try{	
			//Falls die URL null ist, wird ein entsprechender Fehler ausgegeben.
			if(obj_InStream == null){
				ExceptionZZZ ez = new ExceptionZZZ("URL-InputStream", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;								
			}		
			this.setURLContentStream(obj_InStream);
			 this.setFlag("UseStream", true);
		}//end main:
		return bReturn;		
	}//end private constructor


	
	/**
	 * @return, ArrayList of the string on this page.
	 */
	public ArrayList getURLContentArrayList() {
		return obj_alsURLContent;
	}

	/**
	 * @param list, ArrayList of the string on this page.
	 */
	public void setURLContentArrayList(ArrayList list) {
		obj_alsURLContent = list;
	}
	
	public InputStream getURLContentStream(){
		return obj_inStreamURLContent;		
	}
	
	public void setURLContentStream(InputStream in){
		this.obj_inStreamURLContent = in;
	}
	
	/**
	 * @return ReaderScriptJavascriptZZZ-Object, based on the Page-Content
	 * @throws ExceptionZZZ
	 */
	public KernelReaderScriptJavascriptZZZ getReaderJavascript() throws ExceptionZZZ{
		if( this.objReaderJS==null){
			this.objReaderJS = new KernelReaderScriptJavascriptZZZ(this.getKernelObject(), this.getURLContentArrayList(), null, "");		
		}
		return this.objReaderJS;
	}
	
	public KernelReaderHtmlZZZ getReaderHTML() throws ExceptionZZZ{
		if(this.objReaderHTML==null){
			this.objReaderHTML = new KernelReaderHtmlZZZ(this.getURLContentStream(), (String[]) null);			
		}
		return this.objReaderHTML;
	}
	
	public void finalize(){
		if(this.obj_inStreamURLContent!= null){
			try {
				this.obj_inStreamURLContent.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}//end class
