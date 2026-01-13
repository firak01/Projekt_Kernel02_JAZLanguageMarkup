package basic.zKernel.html.writer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.output.Format.TextMode;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.html.reader.KernelReaderHtmlZZZ;
import basic.zKernel.markup.content.ContentXmlZZZ;
import basic.zKernel.markup.content.IKernelContentEcsZZZ;
import basic.zKernel.markup.content.IKernelContentFileZZZ;

/** Klasse dient dazu den Inhalt von ContentXmlZZZ auszugeben.
 * @author lindhauer
 *
 */
public class KernelWriterXmlByContentZZZ  extends AbstractKernelUseObjectZZZ{
	private ContentXmlZZZ objContent = null;
	private String sEncodingUsed = "ISO-8859-1"; // für deutsch";
	
	public KernelWriterXmlByContentZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
	}
	
	/** �bergib den Content an diese Klasse. 
	 *   Merke: Intern wird das im Conent gespeicherte jdom-Dokument 
	* @param objContent
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 03.03.2008 11:33:30
	 */
	public boolean addContent(ContentXmlZZZ objContent) throws ExceptionZZZ {
	boolean bReturn = false;
		main:{				
			if(objContent==null){				
			   ExceptionZZZ ez = new ExceptionZZZ("ContentStore-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
			   throw ez;	
			}
					
		    bReturn = this.replaceContent(objContent);
		}//end main:
		return bReturn;
	}

	public boolean replaceContent(ContentXmlZZZ objContent) throws ExceptionZZZ {
		if(objContent==null){				
			   ExceptionZZZ ez = new ExceptionZZZ("ContentStore-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
			   throw ez;	
			}		
			this.objContent = objContent;			
			return true;
	}

	/*
	public boolean toFile(String sFilePathin) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			 
			  if(StringZZZ.isEmpty(sFilePathin)){
				  ExceptionZZZ ez = new ExceptionZZZ("FilePath", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				  throw ez;
			  }
			  
			  org.jdom.Document doc = this.getDocument();
			  if(doc==null){
				  ExceptionZZZ ez = new ExceptionZZZ("No jdom-document available", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				  throw ez;
			  }
			  
			  //+++ Nun w�re System.out ein m�glicher Outputstream. Hier soll aber in eine Datei ausgegeben werden.
			  /*
			   XMLOutputter xmlout = new XMLOutputter();
			    try {
					xmlout.output(doc, System.out);
				} catch (IOException e) {
					e.printStackTrace();
				}
				######################### 
			  
			  //1. den Dateinamen pr�fen					 
			  File objFileProof = new File(sFilePathin);
			  File objFile;
			  String sFilePath;
			  if(objFileProof.isAbsolute()){
				  sFilePath = sFilePathin;
				  objFile = objFileProof;
			  }else{
				  sFilePath = "." + File.pathSeparator + sFilePathin;
				  objFile = new File(sFilePath);
			  }
			  
			  //2. Stream f�r das Schreiben in die Datei holen.
			  try {
				FileOutputStream fout = new FileOutputStream(objFile);
				BufferedOutputStream bfout = new BufferedOutputStream(fout);
				
				 XMLOutputter xmlout = new XMLOutputter();
				
				 Format format = xmlout.getFormat();				
				 format.setOmitDeclaration(true); //Ohne diese Formatierungshinweise, wird die Zeile <?xml version="1.0" encoding="UTF-8"?> vorangestellt, was bei einem HTML-Dokument ggf. f�r falsche Deutsche Umlaute sorgt.
				 format.setOmitEncoding(true);   
				// format.setExpandEmptyElements(true); //aus <aaa/> wird dann <aaa></aaa>    
				 format.setTextMode(TextMode.PRESERVE);//Ohne diesen Formatierungshinweis, wird ggf. auch der META-Tag mit /> als Abschluss versehen. Dann funktioniert scheinbar dieser Tag im Browser nicht mehr. Die deutschen Umlaute gehen verloren.
				 
				 //!!! Den Encoding String kann man als Information aus dem Tage <META .... > holen.
				 KernelReaderHtmlZZZ objReader = this.getReaderCurrent();
				 String sCharset = objReader.readEncodingUsed(doc);
				 if(!StringZZZ.isEmpty( sCharset)) format.setEncoding(sCharset);      //Ziel: "ISO-8859-1" f�r deutsch       //Ohne diesen Formatierungshinweis wird UTF-8 verwendet. Das bewirkt, dass z.B. die Deutschen Umlaute �, etc. in die korrespondierende HTML-Umschreibung umgewandelt werden. 
				 
				 xmlout.setFormat(format);  //Das muss man machen, sonst sind werden die neuen Format Einstellungen nicht �bernommen
				 xmlout.output(doc, bfout);
				 
				 bReturn = true;
			} catch (FileNotFoundException e) {
				ExceptionZZZ ez = new ExceptionZZZ("FileNotFoundException: " + e.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (IOException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IOException: " + e.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			  
		}//end main:	
		return bReturn;
	}
	*/
	
	public String toStringContent() throws ExceptionZZZ {
		String sReturn = null;
		main:{			 			 			  
		
			//+++ Aus dem jdom-Dokument einen String machen
			  org.jdom.Document doc = this.getDocument();
			  if(doc==null){
				  ExceptionZZZ ez = new ExceptionZZZ("No jdom-document available", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				  throw ez;
			  }
			  
			  //+++ Nun w�re System.out ein m�glicher Outputstream. Hier soll aber in eine Datei ausgegeben werden.		  
			  //1. Outputter - Objekt holen
			  XMLOutputter xmlout = new XMLOutputter();
				 
			  //2. Format einstellen
			 Format format = xmlout.getFormat();		
			 format.setOmitDeclaration(false); //Die Declaration ist die Zeile <?xml version="1.0" encoding="UTF-8"?>  am Anfang des XMLs, dies ist bei  einem HTML-Dokument nicht gew�nscht.
			// format.setExpandEmptyElements(true); //aus <aaa/> wird dann <aaa></aaa>    
			 format.setTextMode(TextMode.PRESERVE);//Ohne diesen Formatierungshinweis, wird ggf. auch der META-Tag mit /> als Abschluss versehen. Dann funktioniert scheinbar dieser Tag im Browser nicht mehr. Die deutschen Umlaute gehen verloren.
				 
			String sCharset = this.getEncoding();
			 if(!StringZZZ.isEmpty( sCharset)){
				 format.setOmitEncoding(false);    //Ohne das Encoding wird es unm�glich den deutschen Zeichensatz zur Verf�gung zu stellen. Dies wird als Attribut dem Declarations-Tag am Anfang des XMLs zugef�gt.
				 //System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#Setze den encoding String: '" + sCharset + "'");
				 format.setEncoding(sCharset);      //Ziel: "ISO-8859-1" f�r deutsch       //Ohne diesen Formatierungshinweis wird UTF-8 verwendet. Das bewirkt, dass z.B. die Deutschen Umlaute �, etc. in die korrespondierende HTML-Umschreibung umgewandelt werden. 
			 }else{
				 format.setOmitEncoding(true);    //Ohne das Encoding wird es unm�glich den deutschen Zeichensatz zur Verf�gung zu stellen
				 //System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#Setze KEINEN spezifischen  encoding String.");
			 }
			xmlout.setFormat(format);  //Das muss man machen, sonst sind werden die neuen Format Einstellungen nicht �bernommen
			
			//3. Endlich: Das XML-Dokument als String
			sReturn = xmlout.outputString(doc);				 
		}//end main:	
		return sReturn;
	}
	
	//#################################
	//### GETTER / SETTER 
	public ContentXmlZZZ getContent(){
		return this.objContent;
	}
	public void setContent(ContentXmlZZZ objContent){
		this.objContent = objContent;
	}
	
	
	/** !!! holt das Dokument aus dem objContent.
	 * !!! es gibt absichtlich keine Methode des Setzens des objContent
	* lindhauer; 03.03.2008 11:46:15
	 * @throws ExceptionZZZ 
	 */
	public Document getDocument() throws ExceptionZZZ{
		Document objReturn = null;
		main:{
			ContentXmlZZZ objContent = this.getContent();
			if(objContent==null) break main;
			
			objReturn = objContent.getDocument();
			
		}//end main:
		return objReturn;
	}
	
	/** setze den verwendeten Zeichensatz "ISO-8859-1" f�r deutsch, steht im xml-Tag, nomalerweise als Attribut hinter 'version'
	* @param sEncoding
	* 
	* lindhauer; 03.03.2008 15:24:23
	 * @throws ExceptionZZZ 
	 */
	public void setEncoding(String sEncoding) throws ExceptionZZZ{
		if(StringZZZ.isEmpty(sEncoding)){
			this.sEncodingUsed = "";
		}else{
			this.sEncodingUsed = sEncoding;
		}
		
	}
	public String getEncoding(){
		return this.sEncodingUsed;
	}
	
}
