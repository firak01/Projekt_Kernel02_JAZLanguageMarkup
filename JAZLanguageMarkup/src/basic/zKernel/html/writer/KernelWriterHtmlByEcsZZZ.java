/*
 * Created on 04.01.2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zKernel.html.writer;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.ecs.Document;
import org.apache.ecs.html.Html;

import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.markup.content.IKernelContentEcsZZZ;
import basic.zKernel.markup.content.IKernelContentFileZZZ;

/**
 * @author Lindhauer
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KernelWriterHtmlByEcsZZZ extends KernelWriterHtmlZZZ {
	private org.apache.ecs.Document objDoc=new Document();
	
	/** 
	 * @param objKernel
	 * @param objLog
	 * @param saFlagControl
	 */
	public KernelWriterHtmlByEcsZZZ(KernelZZZ objKernel, Document objDoc, String[] saFlagControl) throws ExceptionZZZ {
		super(objKernel,saFlagControl);
		if(this.getFlag("init")==false){		
			if(objDoc==null){
				ExceptionZZZ ez = new ExceptionZZZ("ECS Document Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			this.setFlag("useecs", true);
			this.objDoc = objDoc;
		}
	}
	
	/**
	 * customizable method. Use the desired Content store as a parameter
	 * @param objContent; Here ContentPageIPZZZ
	 * @return  boolean; true indicating the success of the method
	 * @throws ExceptionZZZ
	 */
	public boolean addContent(IKernelContentEcsZZZ objContent) throws ExceptionZZZ{
	boolean bReturn = false;
			main:{
				String sAlias;	
				try{
				if(objContent==null){				
				   ExceptionZZZ ez = new ExceptionZZZ("ContentObject", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				   throw ez;	
			}
			
				Document objDoc = this.getDocument();
			
				int iNrOfElement = objContent.NumberOfElement();
			
				for (int i=1; i<=iNrOfElement;i++){
					sAlias=objContent.getAliasByIndex(i-1);
					if(sAlias==null || sAlias.equals("")) break main;
					 Object obj=objContent.getElementList().get(sAlias);
					 KernelWriterHtmlZZZ.addContentBodyObject(objDoc, obj);
				}
				}catch(ExceptionZZZ ez){
					this.objException=ez;
					throw ez;
				}
			    bReturn = true;
			}//end main:
			return bReturn;
	}//end method addContent()
	
	/**
	 * replace the Document by a new one and addContent(...)
	 * this function has to be overwritten to handle the different content-store-objects
	 * @param objContent, ContentPageIPZZZ
	 * @return boolean indicating the success of the method
	 * @throws ExceptionZZZ
	 */
	public boolean replaceContent(IKernelContentEcsZZZ objContent) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			Document objDoc = new Document();
			this.setDocument(objDoc);
			
			bReturn = this.addContent(objContent);
		}
		return bReturn;
	}
	
	
	/**
	 * @return ECS-Document
	 */
	public org.apache.ecs.Document getDocument(){
		return this.objDoc;
	}
	
	/**
	 * @param objDoc, of Type ECS-Document
	 */
	public void setDocument(org.apache.ecs.Document objDoc){
		this.objDoc=objDoc;
	}
	
	 /** Create a file with the whole document-content
	 * @param sFilePath
	 * @return boolean, indicating the success of the method
	 */
	public boolean toFile(String sFilePath) throws ExceptionZZZ{
		boolean bReturn=false;
		main:{
			
			try{
			
			Html objHtml = this.getDocument().getHtml();
			
			    DataOutputStream output = null;
				output =  new DataOutputStream(new BufferedOutputStream(new FileOutputStream(sFilePath)));
				//String stemp = html.toString();
				//System.out.println(stemp);
				output.write(objHtml.toString().getBytes());
				output.close();//Ohne schliessen des Streams wird der Inhalt dort nicht eingefügt.
			}catch(FileNotFoundException e){
				ExceptionZZZ ez = new ExceptionZZZ("File '"+sFilePath+"' can not be found.", iERROR_RUNTIME,(Object)this,ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}catch(IOException e){
				ExceptionZZZ ez = new ExceptionZZZ(e.getMessage(), iERROR_RUNTIME,(Object)this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
					
			
		}//end main:
		return bReturn;
	}

	public boolean addContent(IKernelContentFileZZZ objContent) throws ExceptionZZZ {
		ExceptionZZZ ez = new ExceptionZZZ("Wrong content. Use a ECS content only", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
	}

	public boolean replaceContent(IKernelContentFileZZZ objContent) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
}
