/*
 * Created on 04.01.2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package custom.zKernel.html.writer;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.ecs.Document;
import org.apache.ecs.Element;
import org.apache.ecs.html.H1;
import org.apache.ecs.html.Html;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.html.writer.KernelWriterHtmlZZZ;
import basic.zKernel.markup.content.ContentEcsZZZ;
import basic.zKernel.markup.content.IKernelContentEcsZZZ;
import basic.zKernel.markup.content.IKernelContentFileZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.markup.content.ContentPageIPZZZ;

//import custom.zzzKernel.ExceptionZZZ;
//import custom.zzzKernel.KernelZZZ;
//import custom.zzzKernel.LogZZZ;
//import custom.zzzKernel.markup.content.ContentPageIPZZZ;
//import basic.zzzKernel.html.writer.KernelWriterHTMLZZZ;

/**
 * @author Lindhauer
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WriterHtmlZZZ extends KernelWriterHtmlZZZ {

	/**
	 * @param objKernel
	 * @param objLog
	 * @param saFlagControl
	 */
	public WriterHtmlZZZ(IKernelZZZ objKernel, String[] saFlagControl) throws ExceptionZZZ {
		super(objKernel, saFlagControl);
	}
	
	/**
	 * customizable method. Use the desired Content store as a parameter
	 * @param objContent; Here ContentPageIPZZZ
	 * @return  boolean; true indicating the success of the method
	 * @throws ExceptionZZZ
	 */
	public boolean addContent(ContentPageIPZZZ objContent) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sAlias;
			String stemp;
		
			try{
			if(objContent==null){				
				stemp = "'ContentObject' missing";
				 ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 					   
				 throw ez;
			}
			
			Document objDoc = this.getDocumentEcs();
			
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
	public boolean replaceContent(ContentPageIPZZZ objContent) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			Document objDoc = new Document();
			this.setDocumentEcs(objDoc);
			
			bReturn = this.addContent(objContent);
		}
		return bReturn;
	}

	/**
	 * this method must be overwritten by a custom-method which uses the desired Content-Store-Class
	 * @param objContent
	 * @return boolean, indicating the success of this method
	 * @throws ExceptionZZZ
	 */
	public boolean addContent(ContentEcsZZZ objContent) throws ExceptionZZZ{
		//TODO Exception, die nur die Meldung ausgibt, dass diese Methode zu �berschreiben sei !!!
		boolean bReturn = false;
		main:{
			String sAlias;
			String stemp;
			
			try{
				if(objContent==null){				
				stemp = "'ContentObject'";
				   ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + stemp, iERROR_PARAMETER_MISSING, WriterHtmlZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
				   //doesn�t work. Only works when > JDK 1.4
				   //Exception e = new Exception();
				   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
				   throw ez;	
				}
			
			
			//+++++++++++
			int iNrOfElement = objContent.NumberOfElement();
			
			
			Document objDoc = this.getDocumentEcs();
			for (int i=1; i<=iNrOfElement;i++){
				sAlias=objContent.getAliasByIndex(i);
				if(sAlias==null || sAlias.equals("")) break main;
				 Object obj=objContent.getElementList().get(sAlias);
				 KernelWriterHtmlZZZ.addContentBodyObject(objDoc, obj);
			}//end for
			}catch(ExceptionZZZ ez){
				this.objException=ez;
				throw ez;
			}
			
		}//end main:
		return bReturn;
	}
	
	public boolean addContent(IKernelContentEcsZZZ objContent) throws ExceptionZZZ {
		//TODO Exception, die nur die Meldung ausgibt, dass diese Methode zu �berschreiben sei !!!
				boolean bReturn = false;
				main:{
					String sAlias;
					String stemp;
					
					try{
						if(objContent==null){				
						stemp = "'ContentObject'";
						   ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + stemp, iERROR_PARAMETER_MISSING, WriterHtmlZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
						   //doesn�t work. Only works when > JDK 1.4
						   //Exception e = new Exception();
						   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
						   throw ez;	
						}
					
					
					//+++++++++++
					int iNrOfElement = objContent.NumberOfElement();
					
					
					Document objDoc = this.getDocumentEcs();
					for (int i=1; i<=iNrOfElement;i++){
						sAlias=objContent.getAliasByIndex(i);
						if(sAlias==null || sAlias.equals("")) break main;
						 Object obj=objContent.getElementList().get(sAlias);
						 KernelWriterHtmlZZZ.addContentBodyObject(objDoc, obj);
					}//end for
					}catch(ExceptionZZZ ez){
						this.objException=ez;
						throw ez;
					}
					
				}//end main:
				return bReturn;
	}

	public boolean addContent(IKernelContentFileZZZ objContent) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

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
			String stemp;
					if(obj==null){				
						stemp = "'Object'";
						   ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + stemp, iERROR_PARAMETER_MISSING, WriterHtmlZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
						   //doesn�t work. Only works when > JDK 1.4
						   //Exception e = new Exception();
						   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
						   throw ez;	
						}
						if(objDoc==null){
							stemp = "'Document'";
						   ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + stemp, iERROR_PARAMETER_MISSING, WriterHtmlZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
						   //doesn�t work. Only works when > JDK 1.4
						   //Exception e = new Exception();
						   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
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
	
	public boolean replaceContent(IKernelContentEcsZZZ objContent) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean replaceContent(IKernelContentFileZZZ objContent) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Create a file with the whole document-content
	 * @param sFilePath
	 * @return boolean, indicating the success of the method
	 */
	public boolean toFile(String sFilePath) throws ExceptionZZZ{
		boolean bReturn=false;
		main:{			
			try{			
				Html objHtml = this.getDocumentEcs().getHtml();
			
			    DataOutputStream output = null;
				output =  new DataOutputStream(new BufferedOutputStream(new FileOutputStream(sFilePath)));
				//String stemp = html.toString();
				//System.out.println(stemp);
				output.write(objHtml.toString().getBytes());
				output.close();//Ohne schliessen des Streams wird der Inhalt dort nicht eingef�gt.
				
				bReturn = true;
			}catch(FileNotFoundException fnfe){
					ExceptionZZZ ez = new ExceptionZZZ("File '"+sFilePath+"' can not be found.", iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName(), fnfe);
					throw ez;
			}catch(IOException ioe){
				ExceptionZZZ ez = new ExceptionZZZ("IOException for File '"+sFilePath+"'", iERROR_RUNTIME,this, ReflectCodeZZZ.getMethodCurrentName(), ioe);
				throw ez;	
			}								
		}//end main:
		return bReturn;
	}
	
}
