package basic.zKernel.html.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.output.Format.TextMode;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.html.IKernelTagTypeZZZ;
import basic.zKernel.html.KernelTagFactoryZZZ;
import basic.zKernel.html.AbstractKernelTagTypeZZZ;
import basic.zKernel.html.AbstractKernelTagZZZ;
import basic.zKernel.html.TagMetaZZZ;
import basic.zKernel.html.TagTypeBodyZZZ;
import basic.zKernel.html.TagTypeHeadZZZ;
import basic.zKernel.html.TagTypeInputZZZ;
import basic.zKernel.html.TagTypeMetaZZZ;

/** This class reads html an is able to return specified content.
 * 
 * This class uses nekoHTML to parse a html file. (remark: Xerces is used for nekoHTML)
 * jdom will be used to handle the structure of the result of the parsing process.
 */
public class KernelReaderHtmlZZZ extends AbstractKernelUseObjectZZZ{
	private File objFile = null;
	private InputStream inStream = null;
	private org.jdom.Document objDocument = null;
	private org.cyberneko.html.parsers.DOMParser objHTMLParser = null;
	private org.jdom.input.DOMBuilder objDOMParser = null;
	 
	
	public KernelReaderHtmlZZZ() throws ExceptionZZZ{
		String[] satemp = {"init"};
		KernelReaderHtmlNew_((File) null, satemp);
	}
	
	public KernelReaderHtmlZZZ(InputStream inStream, String[] saFlagControl) throws ExceptionZZZ {
		super("");
		KernelReaderHTMLNew_(inStream, saFlagControl);
	}
	
	public KernelReaderHtmlZZZ(IKernelZZZ objKernel, InputStream inStream, String[] saFlagControl) throws ExceptionZZZ {
		super(objKernel);
		KernelReaderHTMLNew_(inStream, saFlagControl);
	}
	
	public KernelReaderHtmlZZZ(IKernelZZZ objKernel, File objFile, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel);		
		KernelReaderHtmlNew_(objFile, saFlagControl);
	}

	private void KernelReaderHtmlNew_(File objFile, String[] saFlagControl) throws ExceptionZZZ {
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
				if(this.getFlag("init")) break main;
			}
			
			
			//Falls der InputStream null ist, wird ein entsprechender Fehler ausgegeben.
			if(objFile == null){
				ExceptionZZZ ez = new ExceptionZZZ( "FileObject", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());					
				throw ez;								
			}					
			this.setInputFile(objFile);  //setzt auch gleichzeitig den InputStream

	
		
		
		/*TEST: Ausgabe des InputStreams
		 * MERKE: Danach kann JTidy den Stream nicht mehr lesen: Fehlermeldung Root element not set
		BufferedReader in = 
		new BufferedReader(new InputStreamReader(inStream));
		String line;
		try {
			while((line = in.readLine()) != null){
				System.out.println(line);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //hier sind einfach noch zu wenig Informationen
		*/
				
		org.cyberneko.html.parsers.DOMParser parser = new org.cyberneko.html.parsers.DOMParser();
	
		this.objHTMLParser = parser;
		try {
			parser.parse( new InputSource(inStream) );
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DOMBuilder builder = new DOMBuilder();
		org.jdom.Document document = builder.build( this.getDOMDocument());
		this.objDOMParser = builder;
		this.objDocument = document;
		
 		 //+++ F�r debug-zwecke, WICHTIG, NICHT L�SCHEN
		 //Element root = document.getRootElement();
	     //listChildren(root, 0); 
		 //listNodes(root, 0);
		 //listChildrenValue(root, 0);  //!!!Hierdurch erf�hrt man z.B. dass bis zu desem Zeitpunkt die deutschen Umlaute noch vorhanden sind
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		
		/* SCHEISSE, mit JTidy ist nicht zu arbeiten.
		this.objTidy = new Tidy();
		this.objTidy.setErrfile(this.getLogObject().getFilenameExpanded());
		this.objTidy.setOnlyErrors(true);  
		this.objTidy.setShowWarnings(false);
		this.objTidy.setQuiet(true); //FGL: Ohne dies wird z.B. ausgegeben: "... no warnings or errors were found ..."
		try {
			PrintStream pout = new PrintStream("d:\\tempfgl\\test.txt");
			Document objDocument = objTidy.parseDOM(inStream,null);
			
		objTidy.pprint(objDocument, pout);
			
String stemp2 = pout.toString();
System.out.println("test:" + stemp2);
			pout.flush();
			pout.close();
			this.setDocument(objDocument);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/
		
		
		}//End main:
	}
	
	private void KernelReaderHTMLNew_(InputStream inStream, String[] saFlagControl) throws ExceptionZZZ {
		main:{
			if(saFlagControl != null){
				for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
					String stemp = saFlagControl[iCount];
					boolean btemp = setFlag(stemp, true);
					if(btemp==false){ 								   
						   ExceptionZZZ ez = new ExceptionZZZ( stemp, IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 					
						   throw ez;		 
					}
				}
				if(this.getFlag("init")) break main;
			}
			
			//Falls der InputStream null ist, wird ein entsprechender Fehler ausgegeben.
			if(inStream == null){
				ExceptionZZZ ez = new ExceptionZZZ( "InputStream", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());					
				throw ez;								
			}	
			this.setInputStream(inStream);
	
	
		/*TEST: Ausgabe des InputStreams
		 * MERKE: Danach kann JTidy den Stream nicht mehr lesen: Fehlermeldung Root element not set
		BufferedReader in = 
		new BufferedReader(new InputStreamReader(inStream));
		String line;
		try {
			while((line = in.readLine()) != null){
				System.out.println(line);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //hier sind einfach noch zu wenig Informationen
		*/
				
		//  http://nekohtml.sourceforge.net/faq.html#hierarchy
		//	nekohtml-1.9.21.zip
		org.cyberneko.html.parsers.DOMParser parser = new org.cyberneko.html.parsers.DOMParser();
		this.objHTMLParser = parser;
		try {
			parser.parse( new InputSource(inStream) );
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DOMBuilder builder = new DOMBuilder();
		org.jdom.Document document = builder.build( this.getDOMDocument());
		this.objDOMParser = builder;
		this.objDocument = document;
		
 		 //F�r debug-zwecke 
		 //Element root = document.getRootElement();
	     //listChildren(root, 0); 
		 //listNodes(root, 0);
		
		/* SCHEISSE, mit JTidy ist nicht zu arbeiten.
		this.objTidy = new Tidy();
		this.objTidy.setErrfile(this.getLogObject().getFilenameExpanded());
		this.objTidy.setOnlyErrors(true);  
		this.objTidy.setShowWarnings(false);
		this.objTidy.setQuiet(true); //FGL: Ohne dies wird z.B. ausgegeben: "... no warnings or errors were found ..."
		try {
			PrintStream pout = new PrintStream("d:\\tempfgl\\test.txt");
			Document objDocument = objTidy.parseDOM(inStream,null);
			
		objTidy.pprint(objDocument, pout);
			
String stemp2 = pout.toString();
System.out.println("test:" + stemp2);
			pout.flush();
			pout.close();
			this.setDocument(objDocument);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/
		
		
		}//End main:
	}
	
	/** Gibt den verwendeten Encoding Typen zur�ck, wie er im Tag <Meta .... > verwendet wird
	* @return
	* @param document
	* 
	* lindhaueradmin; 11.04.2007 09:32:48
	 * @throws ExceptionZZZ 
	 */
	public String readEncodingUsed(Document docin) throws ExceptionZZZ{
		String sReturn = null;			
		main:{
			org.jdom.Document doc = null;
			if(docin==null){
				doc = this.getDocument();
				if (doc==null){
					ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}else{
				doc = docin;
			}
						
			TagTypeMetaZZZ metaType = new TagTypeMetaZZZ(this.getKernelObject());
			org.jdom.Element metaElem = this.readHeadFirst(doc, metaType);
			if(metaElem==null) break main;
			
			TagMetaZZZ metaTag = new TagMetaZZZ(objKernel, metaElem);			
			sReturn = metaTag.readCharset();            //.getAttributeValue("content");	//MErke: Der Tag sieht so aus:    <META content="text/html; charset=ISO-8859-1" http-equiv="content-type" />
		}//END main:
		return sReturn;
	}
	
	
	/**Receive the Body from the document as a org.jdom.Element.
	 * Remark: The Body-element contains all other html-content-elements.
	 * @param docin
	 * @throws ExceptionZZZ, 
	 *
	 * @return org.jdom.Element
	 *
	 * javadoc created by: 0823, 28.06.2006 - 15:57:31
	 */
	public org.jdom.Element readElementBody(org.jdom.Document docin) throws ExceptionZZZ{
		Element elemReturn = null;
		main:{
			org.jdom.Document doc = null;
			check:{
				if(docin==null){
					doc = this.getDocument();
					if (doc==null){
						ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}else{
					doc = docin;
				}
			}//END check
			
			Element elemRoot = doc.getRootElement();
			if(elemRoot==null){
				ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document seems to have no root-element", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			//Warum geht das nicht ??? elemReturn = elemRoot.getChild("body");
			
			List l = elemRoot.getChildren();
			if(l.size()==0){
				ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document seems to have no element-tag. At least a Body Tag is expected for a html-file.",iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			/*
			else if(l.size()>= 2){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE+"JDOM-Document seems to have more th�n one body-tag. Only one Body Tag is expected for a html-file.",iERROR_PARAMETER_VALUE, this, ReflectionZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			*/

			
			Iterator objIterator = l.iterator();
			while(objIterator.hasNext()){
				Element elem = (Element)objIterator.next();
				//System.out.println(elem.getName());
				
				if(elem.getName().equalsIgnoreCase(TagTypeBodyZZZ.sTAGNAME)){
					elemReturn = elem;
					break main;
				}
			}
			
			//elemReturn = (Element) l.get(0);
			ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document seems to have no body-tag. A Body Tag is expected for a html-file.",iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
			
		}//End main
		return elemReturn;
	}
	
	
	/**Receive the HEAD from the document as a org.jdom.Element.
	 * Remark: The HEAD-element contains all html-metadata-elements.
	 * @param docin
	 * @throws ExceptionZZZ, 
	 *
	 * @return org.jdom.Element
	 *
	 * javadoc created by: 0823, 28.06.2006 - 15:57:31
	 */
	public org.jdom.Element readElementHead(org.jdom.Document docin) throws ExceptionZZZ{
		Element elemReturn = null;
		main:{
			org.jdom.Document doc = null;
			check:{
				if(docin==null){
					doc = this.getDocument();
					if (doc==null){
						ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}else{
					doc = docin;
				}
			}//END check
			
			Element elemRoot = doc.getRootElement();
			if(elemRoot==null){
				ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document seems to have no root-element", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			//Warum geht das nicht ??? elemReturn = elemRoot.getChild("body");
			
			List l = elemRoot.getChildren();
			if(l.size()==0){
				ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document seems to have no element-tag. At least a HEAD Tag is expected for a html-file.",iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			/*
			else if(l.size()>= 2){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE+"JDOM-Document seems to have more th�n one body-tag. Only one Body Tag is expected for a html-file.",iERROR_PARAMETER_VALUE, this, ReflectionZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			*/

			
			Iterator objIterator = l.iterator();
			while(objIterator.hasNext()){
				Element elem = (Element)objIterator.next();
				//System.out.println(elem.getName());
				 
				if(elem.getName().equalsIgnoreCase(TagTypeHeadZZZ.sTAGNAME)){
					elemReturn = elem;
					break main;
				}
			}
			
			//elemReturn = (Element) l.get(0);
			ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document seems to have no HEAD-tag. A HEAD Tag is expected for a html-file, when using this method.",iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
			
		}//End main
		return elemReturn;
	}
	
	/** Liest aus dem HEAD des Html-Dokuments den gew�nschten Tag aus.
	 *   Merke: Anders als bei den Tags des BODY, wird hier davon ausgegangen, das es keine Tags mit dem Attribut 'Name' gibt, 
	 *              nach denen gesucht werden kann.
	* @param docin
	* @param objTagType
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 11.04.2007 10:09:40
	 */
	public org.jdom.Element readHeadFirst(org.jdom.Document docin, AbstractKernelTagTypeZZZ objTagType) throws ExceptionZZZ{
		org.jdom.Element elemReturn = null;
		main:{
			org.jdom.Document doc = null;
			if(docin==null){
				doc = this.getDocument();
				if (doc==null){
					ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}else{
				doc = docin;
			}
			if(objTagType==null){
				ExceptionZZZ ez = new ExceptionZZZ("TagTypeZZZ", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			org.jdom.Element head = this.readElementHead(doc);
			if(head==null) break main;
			
			List objList = head.getChildren();
			Iterator objIterator = objList.iterator();
			while (objIterator.hasNext()) {
			      Element elemChild = (Element) objIterator.next();
			      //System.out.println(elemChild.getName() + "  vs.  "+ objTagType.getTagName());
			      if(elemChild.getName().equalsIgnoreCase(objTagType.getTagName())){
			    	  
			    	elemReturn = elemChild;
			    	break main;
			      }			    
			    }		 
			
		}//End main:
		return elemReturn;	
	}
	
	
	
	/**Read the parsed jdocument. Add every element, which has the name 'input' to the returning arraylist
	 *
	 * @return ArrayList
	 *
	 * javadoc created by: 0823, 28.06.2006 - 15:36:34
	 * @throws ExceptionZZZ 
	 */
	public ArrayList readElementAll(org.jdom.Document docin, IKernelTagTypeZZZ objTagType) throws ExceptionZZZ{
		ArrayList alsReturn = new ArrayList();
		main:{
			org.jdom.Document doc = null;
			check:{
				if(docin==null){
					doc = this.getDocument();
					if (doc==null){
						ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}else{
					doc = docin;
				}
				if(objTagType==null){
					ExceptionZZZ ez = new ExceptionZZZ("TagTypeZZZ", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//END check
			
			//1. Das Body-Element holen
			Element elemBody = this.readElementBody(doc);
			if (elemBody==null){
				ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document seems to be no html-document", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

			//2. Ausgehend vom Body-Element alle anderen children elemente durchsuchen
			searchElementAll(elemBody, objTagType, alsReturn);
			
			
		}//end main:
		return alsReturn;
	}
	
	/**Fill the ArrayList with all Tags, which have the provided name.
	 * This method uses recursion.
	 * 
	 * @param elem, the starting element
	 * @param sName, the Tag-Name to search for
	 * @param alsReturn, the ArrayList which will be filled
	 *
	 * @return void
	 *
	 * javadoc created by: 0823, 28.06.2006 - 16:23:58
	 * @throws ExceptionZZZ 
	 */
	public void searchElementAll(Element elemin, IKernelTagTypeZZZ objTagType, ArrayList alsReturn) throws ExceptionZZZ{
		main:{ 
			Element elem = null;
			check:{				
				if(elemin!=null){
					elem = elemin;
				}else{
					//ExceptionZZZ ez = new ExceptionZZZ("JElement", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					//throw ez;
					
					org.jdom.Document doc =this.getDocument();
					if (doc==null){
						ExceptionZZZ ez = new ExceptionZZZ("JElement-Document, there is no JDom-Document available to use the Body-Element as default.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					//Das Body-Element holen
					elem = this.readElementBody(doc);
					if (elem==null){
						ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document seems to be no html-document (no elemen-parameter provided, tried to use BODY as default element)", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}//end if (elemin != null)
				
				if(objTagType==null){
					ExceptionZZZ ez = new ExceptionZZZ("TagTypeZZZ", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(alsReturn==null){
					ExceptionZZZ ez = new ExceptionZZZ("ArrayList", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//ENd check:
	
	
		List objList = elem.getChildren();
		Iterator objIterator = objList.iterator();
		while (objIterator.hasNext()) {
		      Element elemChild = (Element) objIterator.next();
		      String stemp = elemChild.getName(); 
		      if(stemp.equalsIgnoreCase(objTagType.getTagName())){
		    	  alsReturn.add(elemChild);
		      }
		      searchElementAll(elemChild, objTagType, alsReturn);
		    }		
		}//End main
	}
	
	public org.jdom.Element searchElementFirst(Element elem, IKernelTagTypeZZZ objTagType, String sName) throws ExceptionZZZ{
		Element objReturn = null;
		main:{
			check:{
				if(elem==null){
					ExceptionZZZ ez = new ExceptionZZZ("Element", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(objTagType==null){
					ExceptionZZZ ez = new ExceptionZZZ("TagTypeZZZ", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(sName==null){
					ExceptionZZZ ez = new ExceptionZZZ("Name", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//END check
		
		List objList = elem.getChildren();
		Iterator objIterator = objList.iterator();
		while (objIterator.hasNext()) {
		      Element elemChild = (Element) objIterator.next();
		      if(elemChild.getName().equalsIgnoreCase(objTagType.getTagName())){
		    	  
		    	  //Nun daraus einen TagMachen und den Key dieses Tags mit dem Namen vergleichen
		    	  AbstractKernelTagZZZ objTag = KernelTagFactoryZZZ.createTagByElement(this.getKernelObject(), elemChild, objTagType);
		    	  if(objTag.getTagKey(elemChild)==sName){
		    		objReturn = elemChild;  
		    	  }		    	  		    	 
		      }
		      searchElementFirst(elemChild, objTagType, sName);
		    }		
		
		}//END main
		return objReturn;
	}
	
	/** Starting at the provided element, all sub elements will be searched.
	* @return KernelTagZZZ
	* @param elem
	* @param objTagType
	* @param sName
	* @throws ExceptionZZZ 
	* 
	* lindhaueradmin; 13.07.2006 09:27:27
	 */
	public AbstractKernelTagZZZ searchTagFirst(Element elem, AbstractKernelTagTypeZZZ objTagType, String sName) throws ExceptionZZZ{
		AbstractKernelTagZZZ objReturn = null;
		main:{
			check:{
				if(elem==null){
					ExceptionZZZ ez = new ExceptionZZZ("Element", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(objTagType==null){
					ExceptionZZZ ez = new ExceptionZZZ("TagTypeZZZ", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(sName==null){
					ExceptionZZZ ez = new ExceptionZZZ("Name", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//END check
		
		List objList = elem.getChildren();
		Iterator objIterator = objList.iterator();
		while (objIterator.hasNext()) {
		      Element elemChild = (Element) objIterator.next();
		      if(elemChild.getName().equalsIgnoreCase(objTagType.getTagName())){
		    	  
		    	  //Nun daraus einen Tag machen und den Key dieses Tags mit dem Namen vergleichen
		    	  objReturn = KernelTagFactoryZZZ.createTagByElement(this.getKernelObject(), elemChild, objTagType);
		    	  String stemp = objReturn.getTagKey(elemChild);
		    	  if(stemp.equals(sName)){
		    		break main;  
		    	  }else{
		    		  objReturn = null;
		    	  }
		      }
		      
		      Element elemFound = searchElementFirst(elemChild, objTagType, sName);
		      if(elemFound!=null) {
		    	  //Nun daraus einen Tag machen und den Key dieses Tags mit dem Namen vergleichen
		    	  objReturn = KernelTagFactoryZZZ.createTagByElement(this.getKernelObject(), elemChild, objTagType);
		    	  String stemp = objReturn.getTagKey(elemChild);
		    	  if(stemp.equals(sName)){
		    		break main;  
		    	  }else{
		    		objReturn = null;
		    	  }
		      }
		    }		
		
		}//END main
		return objReturn;
	}
	
	/**Searches for the first tag of the current document.
	 * - which has the tagtype
	 * - which has the name (how the name identifies the tag is specified in the tagtype)
	 * 
	* @return KernelTagZZZ
	* @param docin
	* @param objTagType
	* @param sName
	* @throws ExceptionZZZ 
	* 
	* lindhaueradmin; 13.07.2006 09:25:08
	 */
	public AbstractKernelTagZZZ readTagFirstZZZ(Document docin, AbstractKernelTagTypeZZZ objTagType, String sName) throws ExceptionZZZ{
		AbstractKernelTagZZZ objReturn = null;
		main:{
			org.jdom.Document doc = null;
			check:{
				if(docin==null){
					doc = this.getDocument();
					if (doc==null){
						ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}else{
					doc = docin;
				}
				if(objTagType==null){
					ExceptionZZZ ez = new ExceptionZZZ("TagTypeZZZ", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(sName==null){
					ExceptionZZZ ez = new ExceptionZZZ("Name", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//END check
		
//			1. Das Body-Element holen
			Element elemBody = this.readElementBody(doc);
			if (elemBody==null){
				ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document seems to be no html-document", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

			//2. Ausgehend vom Body-Element alle anderen children elemente durchsuchen
			objReturn = searchTagFirst(elemBody, objTagType, sName);
			
		
		}//END main
		return objReturn;
	}
	
	/** Searches for the first tag of the current document.
	 * - which has the tagtype
	 * - which has the name (how the name identifies the tag is specified in the tagtype)
	 * 
	 * Remarkt: This method was developed to enable another class, which is not in this package, to use this functionality without importing the "jdom.Document".
	* @return KernelTagZZZ
	* @param objTagType
	* @param sName
	* @throws ExceptionZZZ 
	* 
	* lindhaueradmin; 13.07.2006 09:20:35
	 */
	public AbstractKernelTagZZZ readTagFirstZZZ(AbstractKernelTagTypeZZZ objTagType, String sName) throws ExceptionZZZ{
		AbstractKernelTagZZZ objReturn = null;
		main:{
			org.jdom.Document doc = null;
			check:{
					doc = this.getDocument();
					if (doc==null){
						ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}				
			}//END check
			
			objReturn = this.readTagFirstZZZ(doc, objTagType, sName);
		
		}//END main
		return objReturn;
	}
	
	
	
	
	/** Find the first element in the document, which has the given name.
	* @return ArrayList
	* @param document
	* @param objTagTypeInput
	* @param sName
	* @return org.jdom.Element
	* 
	* lindhaueradmin; 02.07.2006 08:00:03
	 * @throws ExceptionZZZ 
	 */
	public org.jdom.Element readElementFirst(Document docin, IKernelTagTypeZZZ objTagType, String sName) throws ExceptionZZZ {
		org.jdom.Element objReturn= null;
		main:{
			org.jdom.Document doc = null;
			check:{
				if(docin==null){
					doc = this.getDocument();
					if (doc==null){
						ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}else{
					doc = docin;
				}
				if(objTagType==null){
					ExceptionZZZ ez = new ExceptionZZZ("TagTypeZZZ", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(sName==null){
					ExceptionZZZ ez = new ExceptionZZZ("Name", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//END check
			
			//1. Das Body-Element holen
			Element elemBody = this.readElementBody(doc);
			if (elemBody==null){
				ExceptionZZZ ez = new ExceptionZZZ("JDOM-Document seems to be no html-document", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

			//2. Ausgehend vom Body-Element alle anderen children elemente durchsuchen
			objReturn = searchElementFirst(elemBody, objTagType, sName);
			
		}
		return objReturn;
	}
 
    //################################################################################
	/**Sends all child elements of a current element to System.out.
	 * This should be used for debugging only.
	 * @param current
	 * @param depth,
	 * @return void
	 *
	 * javadoc created by: 0823, 28.06.2006 - 13:45:26
	 */
	public static void listChildren(Element current, int depth) {		   
	    printSpaces(depth);
	    System.out.println(current.getName()); //!!!
	    List children = current.getChildren();
	    Iterator iterator = children.iterator();
	    while (iterator.hasNext()) {
	      Element child = (Element) iterator.next();
	      listChildren(child, depth+1);
	    }
	}
	
	/**Sends all child elements (VALUES !!!) of a current element to System.out. 
	 * This should be used for debugging only.
	 * @param current
	 * @param depth,
	 * @return void
	 *
	 * javadoc created by: 0823, 28.06.2006 - 13:45:26
	 */
	public static void listChildrenValue(Element current, int depth){
		printSpaces(depth);
	    System.out.println(current.getValue());  // !!!
	    List children = current.getChildren();
	    Iterator iterator = children.iterator();
	    while (iterator.hasNext()) {
	      Element child = (Element) iterator.next();
	      listChildrenValue(child, depth+1);
	    }
	}
	
	/*Sends all objects (as children of the provided object) to System.out.
	 * This should be used for debugging only.
	 * Remark: element.getContent() will receive all kinds of object-types, which are direct children to the element.
	 * 
	 * Handled object-types are:
	 * - Element
	 * - Document
	 * - Comment
	 * - CDATA
	 * - Text
	 * - EntityRef
	 * - ProcessingIntruction
	 *  
	 * @param object
	 * @param depth, 
	 *
	 * @return void
	 *
	 * javadoc created by: 0823, 28.06.2006 - 14:22:13
	 */
	public static void listNodes(Object o, int depth){
		printSpaces(depth);
		
		if (o instanceof org.jdom.Element){
			Element element = (Element) o;
			System.out.println("Element: " + element.getName());
			List children = element.getContent();
			Iterator iterator = children.iterator();
			while(iterator.hasNext()){
				Object child = iterator.next();
				listNodes(child, depth + 1);				
			}
		}else if(o instanceof org.jdom.Document){
			System.out.println("Document");
			Document doc = (Document) o;
			List children = doc.getContent();
			Iterator iterator = children.iterator();
			while(iterator.hasNext()){
				Object child = iterator.next();
				listNodes(child, depth+1);
			}
		}else if(o instanceof org.jdom.Comment){
			System.out.println("Comment");
		}else if(o instanceof org.jdom.CDATA){
			System.out.println("CDATA-Section");
			//CDATA ist eine unterklasse von text, daher muss erst auf cdata abgepr�ft werden.
		}else if(o instanceof org.jdom.Text){
			System.out.println("Text");			
		}else if(o instanceof org.jdom.EntityRef){
			System.out.println("Entity reference");			
		}else if(o instanceof org.jdom.ProcessingInstruction){
			System.out.println("Processing instruction");			
		}else{
			System.out.println("Unexpected type: " + o.getClass().getName());
		}
	}
	
	public String getDocumentAsString() {
		return this.getDocumentAsString(null);
	}
	
	public String getDocumentAsString(Format formatIn) {
		String sReturn = null;
		main:{
			Document doc = this.getDocument();
			if(doc==null)break main;
			
			XMLOutputter xmlout = new XMLOutputter();			 
			Format format=null;
			if(formatIn!=null) {
				format = formatIn;
			}else {
				format = xmlout.getFormat();				
				format.setOmitDeclaration(true); //Ohne diese Formatierungshinweise, wird die Zeile <?xml version="1.0" encoding="UTF-8"?> vorangestellt, was bei einem HTML-Dokument ggf. fuer falsche Deutsche Umlaute sorgt.
				format.setOmitEncoding(true);   
				format.setExpandEmptyElements(true); //aus <aaa/> wird dann <aaa></aaa>    
				format.setTextMode(TextMode.PRESERVE);//Ohne diesen Formatierungshinweis, wird ggf. auch der META-Tag mit /> als Abschluss versehen. Dann funktioniert scheinbar dieser Tag im Browser nicht mehr. Die deutschen Umlaute gehen verloren.
			}			
			xmlout.setFormat(format);
			 
			sReturn = xmlout.outputString(doc);
			 
		}//end main:
		return sReturn;
	}
	
	private static void printSpaces(int n) {	    
	    for (int i = 0; i < n; i++) {
	      System.out.print(' '); 
	    }	    
	  }
	
	
	
	//############################################
	//### Getter/Setter
	private void setInputStream(InputStream in){
		this.inStream = in;
	}

	/**Returns the input-stream passed to the object by the constructor. Remark: No public setter-method exists. 
	 * @return InputStream
	 *
	 * javadoc created by: 0823, 28.06.2006 - 13:33:16
	 */
	private InputStream getInputStream(){
		return this.inStream;
	}
	
	/** Setzt den InputFile. Setzt dadurch gleichzeitig auch den InputStream.
	* @param objFile
	* 
	* lindhaueradmin; 01.04.2007 10:07:45
	 * @throws ExceptionZZZ 
	 */
	private void setInputFile(File objFile) throws ExceptionZZZ{
		main:{
			if(objFile==null){
				this.setInputStream(null);
				this.objFile=null;
				break main;
			}
						
			if(objFile.exists()==false){
				ExceptionZZZ ez = new ExceptionZZZ( "FileObject does not exist: " + objFile.getPath(), iERROR_PARAMETER_VALUE,   this, ReflectCodeZZZ.getMethodCurrentName());					
				throw ez;		
			}
			
			
			//Nun aus dem FileObjekt den InputStream manchen.
			try {
				FileInputStream fileStreamIn = new FileInputStream(objFile);
				
				InputStream streamIn = (InputStream) fileStreamIn;
				this.setInputStream(streamIn);
											
			} catch (FileNotFoundException e) {
				//Das wird zwar schon vorher abgefragt. Kann sich aber doch w�hrend der Verarbeitung �ndern.
				ExceptionZZZ ez = new ExceptionZZZ("File not found exception: "+e.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
		}//end main
	
	
	}

	/**Normaly the document is created in the constructor by parsing the input stream with nekoHTML (and receiving a DOM Document), followed by Parsing the DOM-Document with jdom. 
	 * This is the JDOM-Document. 
	 * @param objDocument
	
	 * @return void
	 *
	 * javadoc created by: 0823, 28.06.2006 - 13:34:19
	 */
	public void setDocument(org.jdom.Document objDocument){
		this.objDocument = objDocument;
	}

	
	/**The DOM Document is created in the constructor by parsing the input stream with nekoHTML (and receiving a DOM Document)
	 * @return org.w3c.dom.Document
	 *
	 * javadoc created by: 0823, 28.06.2006 - 15:15:25
	 */
	public org.w3c.dom.Document getDOMDocument(){
		org.cyberneko.html.parsers.DOMParser parser = this.getParser4HTML();
		if(parser != null){
			return parser.getDocument();
		}else{
			return null;
		}
	}

	/**Normaly the document resulted by parsing the input stream with JDom. This is an JDOM-Document.
	 * @return, org.jdom.Document
	 *
	 * javadoc created by: 0823, 28.06.2006 - 13:44:42
	 */
	public org.jdom.Document getDocument(){
		return this.objDocument;
	}
	
	/**The Parser-Object for HTML is created in the constructor
	 * @return
	
	 * @return org.cyberneko.html.parsers.DOMParser
	 *
	 * javadoc created by: 0823, 28.06.2006 - 13:42:48
	 */
	public org.cyberneko.html.parsers.DOMParser getParser4HTML(){
		return this.objHTMLParser;
	}
	
	/**The Parser-Object for DOM is created in the constructor
	 * @return, 
	 *
	 * @return org.jdom.input.DOMBuilder
	 *
	 * javadoc created by: 0823, 28.06.2006 - 14:09:13
	 */
	public org.jdom.input.DOMBuilder getParser4DOM(){
		return this.objDOMParser;
	}	
}//END Class
