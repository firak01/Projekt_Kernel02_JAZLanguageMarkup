package basic.zKernel.html.writer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.ecs.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.output.Format.TextMode;

import basic.zKernel.IKernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.html.reader.KernelReaderHtmlZZZ;
import basic.zKernel.markup.content.IKernelContentEcsZZZ;
import basic.zKernel.markup.content.IKernelContentFileZZZ;

public class KernelWriterHtmlByFileZZZ extends KernelWriterHtmlZZZ {	
	private KernelReaderHtmlZZZ objReader = null;
	
	public KernelWriterHtmlByFileZZZ(IKernelZZZ objKernel, String[] saFlagControl) throws ExceptionZZZ {
		super(objKernel, saFlagControl);	
		if(this.getFlag("init")==false){		
			this.setFlag("useecs", false);		
		}
	}

	public boolean addContent(IKernelContentEcsZZZ objContent) throws ExceptionZZZ {
		ExceptionZZZ ez = new ExceptionZZZ("Wrong content. Use a File - content only", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
	}

	public boolean addContent(IKernelContentFileZZZ objContentStore) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{				
			if(objContentStore==null){				
			   ExceptionZZZ ez = new ExceptionZZZ("ContentStore-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
			   throw ez;	
			}
					
		    bReturn = this.replaceContent(objContentStore);
		}//end main:
		return bReturn;
	}

	public boolean replaceContent(IKernelContentEcsZZZ objContent) throws ExceptionZZZ {
		ExceptionZZZ ez = new ExceptionZZZ("Wrong content. Use a IKernelContentFileZZZ content only", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
	}

	public boolean replaceContent(IKernelContentFileZZZ objContentStore) throws ExceptionZZZ {
		if(objContentStore==null){				
			   ExceptionZZZ ez = new ExceptionZZZ("ContentStore-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
			   throw ez;	
			}
		
			
		KernelReaderHtmlZZZ objReader = objContentStore.getReaderCurrent();
		this.setReaderCurrent(objReader);
		
		org.jdom.Document doc = objReader.getDocument();
		this.setDocument(doc);				
		return true;
	}

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
				*/
			  
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
			  
			//Vor dem ggfs. erstmaligen Erstellen ggfs. die notwendigen Verzeichnisse erzeugen.
				boolean bErg = FileEasyZZZ.createDirectoryForFile(objFile);
				if(bErg) {
					this.getLogObject().writeLineDate("Directory for filepath created/exists: '"+sFilePath+"'");
				}
			  
			  //2. Stream fuer das Schreiben in die Datei holen.
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
	
	public String toStringContent() throws ExceptionZZZ {
		String sReturn = null;
		main:{			 			 			  
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
				*/
			  			  
			  //2. Outputter - Objekt holen und das Format einstellen
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
			
			//3. Endlich: Das XML-Dokument als String
			sReturn = xmlout.outputString(doc);			  
		}//end main:	
		return sReturn;
	}
	
	
	//####### GETTER / SETTER	
	public org.jdom.Document getDocument(){
		return this.getDocumentJDom();
	}
	private void setReaderCurrent(KernelReaderHtmlZZZ objReader){
		this.objReader = objReader;
	}
	private KernelReaderHtmlZZZ getReaderCurrent(){
		return this.objReader;
	}
	
	
}
