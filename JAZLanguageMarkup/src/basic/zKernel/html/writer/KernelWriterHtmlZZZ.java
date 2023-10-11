/*
 * Created on 04.01.2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zKernel.html.writer;

import java.io.BufferedOutputStream;
//import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.ecs.Document;
import org.apache.ecs.Element;
import org.apache.ecs.html.Html;

import basic.zKernel.IKernelZZZ;


import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.markup.content.IKernelContentEcsZZZ;
import basic.zKernel.markup.content.IKernelContentFileZZZ;
import basic.zKernel.markup.content.KernelContentZZZ;


/**
 * @author Lindhauer
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class KernelWriterHtmlZZZ extends AbstractKernelUseObjectZZZ{		
	private boolean bFlagUseFile = false;
	private boolean bFlagUseEcs = false;
	
	private org.apache.ecs.Document objDocEcs=null;
	private org.jdom.Document objDocJdom= null;
	
	public KernelWriterHtmlZZZ(IKernelZZZ objKernel, String[] saFlagControl) throws ExceptionZZZ {
		super(objKernel);
		main:{		
			if(saFlagControl != null){
				String stemp; boolean btemp; 
				
				for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
					stemp = saFlagControl[iCount];
					btemp = setFlag(stemp, true);
					if(btemp==false){ 								   
						   ExceptionZZZ ez = new ExceptionZZZ(stemp, IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 					   
						   throw ez;		 
					}
				}
				if(this.getFlag("INIT")==true)	break main; 
			}									
		}//end main:
	}//end constructor
	

	
	/**
	 * this method must be overwritten by a custom-method which uses the desired Content-Store-Class
	 * @param objContent
	 * @return boolean, indicating the success of this method
	 * @throws ExceptionZZZ
	 */
	abstract public boolean addContent(IKernelContentEcsZZZ objContent) throws ExceptionZZZ;
	abstract public boolean addContent(IKernelContentFileZZZ objContent) throws ExceptionZZZ;
	
	
	/**
	 * Replaces the current document by a new one and then addContent(..)
	 * @param objContent
	 * @return boolean indicating the success of the method
	 * @throws ExceptionZZZ
	 */
	abstract public boolean replaceContent(IKernelContentEcsZZZ objContent)throws ExceptionZZZ;
	abstract public boolean replaceContent(IKernelContentFileZZZ objContent) throws ExceptionZZZ;
	
	/**
	 * append the object to the document. This method is necessary, because a HashMap does only return the ECS-Elements as Object !!!
	 *This method appends the object to the document using the correct typecast (Element) !!!
	 * @param objDoc
	 * @param obj, an ECS-Element like org.apache.ecs.html.H1
	 * @return boolean, indicating the success of the method
	 */
	public static boolean addContentBodyObject(Document objDoc, Object obj) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
					if(obj==null){										
						  ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName() ); 						  
						   throw ez;	
						}
						if(objDoc==null){						
						   ExceptionZZZ ez = new ExceptionZZZ( "Document", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName() ); 
						   throw ez;	
						}
			
						objDoc.appendBody((Element) obj);
						
			/* das ist nicht notwendig, Typecast Element reicht !!!
			String sClass = obj.getClass().getName();
			if (sClass.equals("org.apache.ecs.html.H1")){
				objDoc.appendBody((H1) obj);
			}else if(sClass.equals("org.apache.ecs.html.HR")){
				objDoc.appendBody((HR) obj);
			}else if(sClass.equals("org.apache.ecs.html.Font")){
				objDoc.appendBody((Font) obj);
			}else if(....){
				
			}else if(sClass.equals("org.apache.ecs.StringElement"))
				objDoc.appendBody((StringElement) obj)
			//TODO erweitern auf andere Klassen der ECS-Elemente
			 			  
			 */
			bReturn = true;
		}//end main:
		return bReturn;
	}//end addContentBody(..)
	
	/**
	 * Create a file with the whole document-content
	 * @param sFilePath
	 * @return boolean, indicating the success of the method
	 */
	abstract public boolean toFile(String sFilePath) throws ExceptionZZZ;
	
	/**
	 * @return ECS-Document
	 */
	public org.apache.ecs.Document getDocumentEcs(){
		if(this.objDocEcs==null) {
			this.objDocEcs=new org.apache.ecs.Document();
		}
		return this.objDocEcs;
	}
	
	/**
	 * @param objDoc, of Type ECS-Document
	 */
	public void setDocument(org.apache.ecs.Document objDoc){
		this.objDocEcs=objDoc;
	}
	
	/**
	 * @return ECS-Document
	 */
	public org.jdom.Document getDocumentJDom(){
		if(this.objDocJdom==null) {
			this.objDocJdom=new org.jdom.Document();
		}
		return this.objDocJdom;
	}
	
	/**
	 * @param objDoc, of Type ECS-Document
	 */
	public void setDocument(org.jdom.Document objDoc){
		this.objDocJdom=objDoc;
	}

	/** Function can get the flags of this class or the super-class.
	 * The following new flags are supported:
	 * --- useFile
	 * --- useEcs
	 * @see basic.zBasic.IFunctionZZZ_loesch#getFlag(java.lang.String)
	 */
	public boolean getFlag(String sFlagName) {
		boolean bFunction = false;
	main:{
		if(StringZZZ.isEmpty(sFlagName)) break main;
	
		// hier Superclass aufrufen
		bFunction = super.getFlag(sFlagName);
		if(bFunction == true) break main;
		
		// Die Flags dieser Klasse setzen
		String stemp = sFlagName.toLowerCase();
		if(stemp.equals("usefile")){
			bFunction = this.bFlagUseFile;
			break main;
		}else if(stemp.equals("useecs")){
			bFunction = this.bFlagUseEcs;
			break main;
		}else{
			bFunction = false;
		}		
	}	// end main:
	
	return bFunction;	
	}

	/** Function can set the flags of this class or the super-class.
	 * The following new flags are supported:
	 * --- useFile
	 * --- useEcs
	 * @throws ExceptionZZZ 
 * @see basic.zBasic.IFunctionZZZ_loesch#setFlag(java.lang.String, boolean)
 */
public boolean setFlag(String sFlagName, boolean bFlagValue) throws ExceptionZZZ{
	boolean bFunction = false;
	main:{
		if(StringZZZ.isEmpty(sFlagName)) break main;
			
		// hier Superclass aufrufen
		bFunction = super.setFlag(sFlagName, bFlagValue);
		if(bFunction == true) break main;
		
		// Die Flags dieser Klasse setzen
		String stemp = sFlagName.toLowerCase();
		if(stemp.equals("usefile")){
			this.bFlagUseFile = bFlagValue;
			bFunction = true;                            //durch diesen return wert kann man "reflexiv" ermitteln, ob es in dem ganzen hierarchie-strang das flag �berhaupt gibt !!!
			break main;
		}else if(stemp.equals("useecs")){
			this.bFlagUseEcs = bFlagValue;
			bFunction = true;
			break main;
		}else{
			bFunction = false;
		}	
		
	}	// end main:
	
	return bFunction;	
}
}
