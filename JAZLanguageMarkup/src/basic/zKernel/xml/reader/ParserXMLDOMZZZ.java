package basic.zKernel.xml.reader;

	import java.io.File;
	import java.io.IOException;
	import java.util.ArrayList;

	import javax.xml.parsers.DocumentBuilder;
	import javax.xml.parsers.DocumentBuilderFactory;
	import javax.xml.parsers.ParserConfigurationException;

	import org.w3c.dom.Document;
	import org.w3c.dom.Element;
	import org.w3c.dom.Node;
	import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
	/**
	 * @author 0823
	 *
	 * To change this generated comment edit the template variable "typecomment":
	 * Window>Preferences>Java>Templates.
	 * To enable and disable the creation of type comments go to
	 * Window>Preferences>Java>Code Generation.
	 */
	public class ParserXMLDOMZZZ extends AbstractKernelUseObjectZZZ{
		private File objFile;
		private Document domdoc;
		private Element domroot;

		private Object domNodeFound;

		//Flags
		private boolean bFlagInit = false;

//#############################################
//# Getter / Setter
	/** File, no public setter method available. This property will only return a file-object, when it was provided in the constructor
	* lindhaueradmin; 26.06.2006 10:19:22
	 * @return
	 */
	public	File getFile(){
		return objFile;
	}
	private void setFile(File objFile){
		this.objFile = objFile;
	}
	/** Document, no public setter method available
	* lindhaueradmin; 25.06.2006 14:23:02
	 * @return
	 */
	public Document getDocument(){
		return domdoc;
	}
	private void setDocument(Document objDoc){
		this.domdoc = objDoc;
	}

	/** Constructor. Flag "init" will be set.
	* lindhaueradmin; 26.06.2006 10:10:42
	 * @throws ExceptionZZZ
	 */
	public ParserXMLDOMZZZ() throws ExceptionZZZ{
		String[] saTemp = {"init"};
		ParserXMLDOMNew_(saTemp);
	} //end Konstruktor

	/** Constructor, document-object is provided directly.
	* lindhaueradmin; 26.06.2006 10:11:27
	 * @param objKernel
	 * @param objDoc
	 * @param saFlagControl
	 * @param sFlagControl
	 * @throws ExceptionZZZ
	 */
	public ParserXMLDOMZZZ(KernelZZZ objKernel, Document objDoc, String[] saFlagControl, String sFlagControl) throws ExceptionZZZ{
		super(objKernel);
		ParserXMLDOMNew_(saFlagControl);
		if(this.getFlag("init")==false){
			ParserXMLDOMDocNew_(objDoc, sFlagControl);
		}
	}
	

	private void ParserXMLDOMNew_(String[] saFlagControl) throws ExceptionZZZ{
		
		if(saFlagControl != null){
			String stemp; boolean btemp;
			for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
				stemp = saFlagControl[iCount];
				btemp = setFlag(stemp, true);
				if(btemp==false){ 								   
					   ExceptionZZZ ez = new ExceptionZZZ( stemp, IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 					 
					   throw ez;		 
				}
			}

		}
	}
	
	private void ParserXMLDOMDocNew_(Document objDoc, String sFlagControl) throws ExceptionZZZ{
		main:{
			check:{
				if(objDoc==null){
					   ExceptionZZZ ez = new ExceptionZZZ("XML-Document", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					   throw ez;		 
				}
			}//End check;
	
		this.setDocument(objDoc);
		}//End main:
	}
	
	private void ParserXMLDOMFileNew_(File objFile, String sFlagControl) throws ExceptionZZZ{
	
	main:{
		try{
			check:{	
				if(objFile==null){
					   ExceptionZZZ ez = new ExceptionZZZ("File-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					   throw ez;		 
				}
			}//end check
			this.setFile(objFile);
		
		
			//DocumentBuilderObjekt und Document erstellen
			DocumentBuilderFactory domfactory = DocumentBuilderFactory.newInstance();
			//+++ G�ltigkeitspr�fung aktivieren
			domfactory.setValidating(true);
			//+++ Whitespace ignorieren
			domfactory.setIgnoringElementContentWhitespace(true);
		
			DocumentBuilder dombuilder = domfactory.newDocumentBuilder();
		
			//+++ Einen default-Errorhandler bestimmen, das ist notwendig wg. der G�ltigkeitspr�fung
			//Merke: Die Original-Klasse gibt nix aus, darum mit meiner erweiterten Klasse arbeiten.
			DTDErrorZZZ eh = new DTDErrorZZZ();
			dombuilder.setErrorHandler(eh);
		
			//+++ Einlesen der Datei in das XML-Dokument
			domdoc = dombuilder.parse(objFile);
		
			//+++ Pr�fen des documents
			//TODO GOON: Das muss auch f�r andere Dokumente gehen !!! Ggf. muss der Root Name konfigurierbar sein
			/*
			String sTemp = "ursuppe";
			boolean bTemp = this.proofDocumentRootName(sTemp);
			if (bTemp == false){
				System.out.println("This is no '" + sTemp +"'-XML-File");
				break main;
			}else{
				System.out.println("This is a(n) '" + sTemp + "'-XML-File");
			}
			*/

		} //end try
		// die verschiedenen Catch - Bl�cke
		//TODO GOON: alle auf ExceptionZZZ runterbrechen. 
		catch(ParserConfigurationException e){
			e.printStackTrace();
			break main;
		}
		catch(SAXException e){
			e.printStackTrace();
			break main;
		}
		catch(IOException e){
			e.printStackTrace();
			break main;
		}
		catch(Exception e){
			e.printStackTrace();
			break main;
		}
			
	}//end main:
	}
	
	/**
	 * Constructor ParserXMLDOMZZZ. XML-File-Objekt is provided and parsed with the DOM-Parser.
	 * @param objFile
	 * @param string
	 */
	public ParserXMLDOMZZZ(KernelZZZ objKernel, File objFile, String[] saFlagControl, String sFlagControl) throws ExceptionZZZ{
		super(objKernel);
		ParserXMLDOMNew_(saFlagControl);
		if(this.getFlag("init")==false){
			ParserXMLDOMFileNew_(objFile, sFlagControl);
		}
	} //end Constructor

/** String, Return the name of the root node.
* lindhaueradmin; 26.06.2006 09:48:39
 * @return
 * @throws ExceptionZZZ 
 */
public String readTagRootName() throws ExceptionZZZ{
	String sReturn=null;
	main:{
		
		Document objDoc;
		check:{
			objDoc = this.getDocument();
			if(objDoc==null){
				ExceptionZZZ ez = new ExceptionZZZ( "Document-Object", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;		 
			}
		}
	Element objElem = objDoc.getDocumentElement();
	sReturn = objElem.getTagName();
	}//END Main
	return sReturn;
}


	/** boolean, returns all values of the tag. This will fill the provided ArrayList.
	* lindhaueradmin; 26.06.2006 10:56:04
	 * @param sTagName
	 * @param alsCardValue
	 * @return
	 */
	public boolean readTagValueStringSimple(String sTagName, ArrayList alsCardValue){
		boolean bFunction = true;

	main:
	{
		//#### Analyse des Documents
		NodeList domCardList = domdoc.getElementsByTagName("name");
		int iNumberOfTag = domCardList.getLength();
		System.out.println("Number of cards found by tag 'name': " + iNumberOfTag);

		//Die Gr��e der ArrayListe festlegen
		alsCardValue.ensureCapacity(iNumberOfTag);

		//String Wert in die ArrayListe aufnehmen
		for(int iCounter = 0; iCounter < iNumberOfTag; iCounter++){
			Node domTagTemp = domCardList.item(iCounter);
			String sContent = domTagTemp.getFirstChild().getNodeValue();
			/*
			short shType = domTagTemp.getNodeType();
			short shType2 = domTagTemp.getFirstChild().getNodeType();
			System.out.println((iCounter+1) + ". Tag-name: " + domTagTemp.getNodeName() +
			" # tag-node-type: " + shType +
			" # tag-value: " + sContent +
			" # tag-value-type: " + shType2);
			*/
			alsCardValue.add(iCounter, sContent);
		} //end for

	}
	end:
	{
		return bFunction;
	}
	} //end Function




	public boolean readTagValueStringSimpleByKey(String sTagNameToFind, ArrayList alsCardName, String sKeyNameToFind, String sKeyToFind){
		//Variable
		boolean bFunction = true;
		short shNodeType = 0;
		String sTemp = "";
		Node domNodeFound = null;
		NodeList nl2 = null; //enth�lt die Kinderdokument
		
	main:
	{	
		//Handle auf das DOM-Dokument
		Document domdoc = this.getDocument();
		
		//Nodelist mit dem Schl�sselwert
		NodeList nl = domdoc.getElementsByTagName(sKeyNameToFind);
		System.out.println("Number of Cards found by tag '" + sKeyNameToFind + "': " + nl.getLength());
		//Node aus der Nodelist, der dem Schl�sselwert entspricht
		for(int iCounter = 0; iCounter <= nl.getLength()-1;iCounter++){
			Node domTagTemp = nl.item(iCounter);
			shNodeType = domTagTemp.getNodeType();
			switch(shNodeType){
			case 1:
				//### Den richtigen Knoten finden, der auf den Key-Wert passt.
				
				//1. den Elternknoten ansteuern		
			    Node domNodeParent = domTagTemp.getParentNode();
			    
			    //2. vom Elternknoten aus alle Kinderknoten ansteuern
			    nl2 = domNodeParent.getChildNodes();
			    
		    	//3. Aus den Kinderknoten denjenigen mit dem Key-Wert finden
		    	domNodeFound = readNodeFirstByKey(nl2, sKeyNameToFind, sKeyToFind);
				break;
			case 3:
			//ist dieser Zweig sinnvoll ???
			sTemp = domTagTemp.getNodeValue();
			if(sTemp.equals(sKeyToFind)){
				System.out.println("Wert SOFORT gefunden: " + sKeyToFind +"'");
				domNodeFound = domTagTemp;
				break;	
			}		
			}	//end switch
			
			//Wurde ein Knoten gefunden, dann den Wert in die ArrayList packen.
			if(domNodeFound != null){
				bFunction = readTagValue(nl2, sTagNameToFind,alsCardName);			
			}// end if (domNodeFound != null)	 
		} //end �usserste For - Schleife
	}
	end:
	{	
		return bFunction;
	}
	}//end function
	

	/* (non-Javadoc)
	@see zzzKernel.basic.KernelObjectZZZ#getFlag(java.lang.String)
	Flags used: nothing
	 */
	public boolean getFlag(String sFlagName){
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.getFlag(sFlagName);
			if(bFunction==true) break main;
			/*
			
			if(sFlagName == null) break main;
			if(sFlagName.equals("")) break main;
					
			//getting the flags of this object
			String stemp = sFlagName.toLowerCase();
			if(stemp.equals("filechanged")){
				bFunction = bFlagFileChanged;
				break main;
			}else if(stemp.equals("fileunsaved")){
				bFunction = bFlagFileUnsaved;
				break main;
			}else if(stemp.equals("filenew")){
				bFunction = bFlagFileNew;
				break main;
			}
			*/
		}//end main:
		return bFunction;
	}

	/**
	 * @see AbstractKernelUseObjectZZZ.basic.KernelUseObjectZZZ#setFlag(java.lang.String, boolean)
	 * @param sFlagName
	 * Flags used:<CR>
	 * - FileChanged, if a value is written to the file the flag is changed to true,
	 * - FileUnsaved, if a value is written to the file the flag is changed to true, if the save() method is used it is reset to false.
	 * 			  source_remove: after copying the source_files will be removed.
	 * @throws ExceptionZZZ 
	 */
	public boolean setFlag(String sFlagName, boolean bFlagValue) throws ExceptionZZZ{
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.setFlag(sFlagName, bFlagValue);
			if(bFunction==true) break main;
		
			/*
		//setting the flags of this object
		String stemp = sFlagName.toLowerCase();
		if(stemp.equals("fileunchanged")){
			bFlagFileChanged = bFlagValue;
			bFunction = true;
			break main;
		}else if(stemp.equals("fileunsaved")){
			bFlagFileUnsaved = bFlagValue;
			bFunction = true;
			break main;
		}else if(stemp.equals("filenew")){
			bFlagFileNew = bFlagValue;
			bFunction = true;
			break main;
		}
		*/
		}//end main:
		return bFunction;
	}
	
	/** String, returns the STRING value of the first parent-node, which has  the tagname. 
	* lindhaueradmin; 26.06.2006 10:53:34
	 * @param objNodein
	 * @param sTagNamein
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public String readTagValueFirstParent(Node objNodein, String sTagNamein) throws ExceptionZZZ{
		String sFunction = null;
		int iCounter = 0;
		Node objNodeParent = null;
		NodeList objNodelist = null;
		Node objNodeChild = null;
		NodeList objNodelistChild = null;
		boolean bTemp = false;
		
		main:{
			paramcheck:{
				if(objNodein==null){
					ExceptionZZZ ez = new ExceptionZZZ("Node", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());	
					throw ez;	
				}
				if (sTagNamein.length()==0){
					break main;
				}
	/*			if (objNodein.getNodeName()=="name"){
					System.out.println("STOP");
					System.out.println("1");
			}*/
				if(objNodein.getNodeName().equals(sTagNamein)) {
					short shNodeType = objNodein.getNodeType();
					switch(shNodeType){
					case 1:				
						objNodelistChild = objNodein.getChildNodes();
						objNodeChild = objNodelistChild.item(0);								
						sFunction = objNodeChild.getNodeValue();
						break;
					case 3:
						sFunction = objNodein.getNodeValue();	
						break;
					} //end switch
					if(sFunction != null){
						break main;
					}
				}						
			}//end paramcheck
			
			// Erst einmal das Parent-Elemente holen
			objNodeParent = objNodein.getParentNode();
			if(objNodeParent == null){
				break main; //die root erreicht/�berschritten !!!			
			}
			//Falls es sich um die erste Ebene nach dem Root handelt, abbrechen damit man nicht in einen ganz anderen Zweig kommt.
			bTemp = proofNodeIsLevelFirst(objNodein);
			if(bTemp==true){
				break main;
			}
			
			// nun die Child-Nodes des Parent-Elements holen
			objNodelist = objNodeParent.getChildNodes();
			for(iCounter = 0; iCounter <= objNodelist.getLength()-1; iCounter++){
					objNodeChild = objNodelist.item(iCounter);
					sFunction = readTagValueFirstChildren(objNodeChild, sTagNamein);
												
					//Wurde ein Knoten gefunden, dann haben fertig ;).
					if(sFunction != null){
						break main;
					}
			}//end for
			
			// hier der rekursive Aufruf
			sFunction = readTagValueFirstParent(objNodeParent, sTagNamein);
			
			//Wurde ein Knoten gefunden, dann haben fertig ;).
					if(sFunction != null){
						break main;
					}
			

		} // end main
		
		end:{
			return sFunction;
		}
	}

	/** String, returns the STRING value of the first child-node, which has  the tagname. 
	* lindhaueradmin; 26.06.2006 10:53:34
	 * @param objNodein
	 * @param sTagNamein
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public String readTagValueFirstChildren(Node objNodein, String sTagNamein) throws ExceptionZZZ{
		String sFunction = null;
		int iCounter = 0;
		NodeList objNodelist = null;
		NodeList objNodelistChild = null;
		Node objNodeChild = null;
		
		main:{
			paramcheck:{
				if(objNodein==null){
					ExceptionZZZ ez = new ExceptionZZZ("Node", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());		  
					throw ez;	
				}
				if (sTagNamein.length()==0)	break main;
			
				if(objNodein.getNodeName().equals(sTagNamein)){
					short shNodeType = objNodein.getNodeType();
					switch(shNodeType){
					case 1:									
						objNodelistChild = objNodein.getChildNodes();
						objNodeChild = objNodelistChild.item(0);								
						sFunction = objNodeChild.getNodeValue();
						break;
					case 3:
					} //end switch
					if(sFunction != null) break main;
				}
			}//end paramcheck
			
			// Erst einmal die Child-Elemente holen
			objNodelist = objNodein.getChildNodes();
			for(iCounter = 0; iCounter <= objNodelist.getLength()-1; iCounter++){
					objNodeChild = objNodelist.item(iCounter);
					sFunction = readTagValueFirstChildren(objNodeChild, sTagNamein);
					
								
					//Wurde ein Knoten gefunden
					if(sFunction != null) break main;
			}//end for

		} // end main
		
		end:{
			return sFunction;
		}
	} // end function

	/** String, read the value of the first tag with this name.
	 * Starting from the node.
	 * This method will be called in recursion.
	 * 
	* lindhaueradmin; 26.06.2006 10:48:51
	 * @param objNodein
	 * @param sTagNamein
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public String readTagValueFirst(Node objNodein, String sTagNamein) throws ExceptionZZZ{
		String sFunction = null;
		String sTemp = null;
		
		main:{
			paramcheck:{
				if(objNodein==null){
					ExceptionZZZ ez = new ExceptionZZZ("Node", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());	
					throw ez;	
				}
				if (sTagNamein.length()==0){
					break main;
				}
				if(objNodein.getNodeName().equals(sTagNamein)){
					sFunction = objNodein.getNodeValue();
					break main;
				}
			}//end paramcheck
			
			// Erst einmal das child-Element checken
			sTemp = readTagValueFirstChildren(objNodein, sTagNamein);
			if(sTemp != null){
				sFunction = sTemp;
				break main;
			}else{
				sTemp = readTagValueFirstParent(objNodein, sTagNamein);
				if(sTemp != null){
					sFunction = sTemp;
					break main;
				}
			}	

		} // end main
		
		end:{
			return sFunction;
		}
	}
		
		
		//end main
	 
	/** boolean, fill the ArrayList with all values of the named tag.
	 * The search starts with the nodelist.
	 * This method will be called in recursion.
	 * 
	* lindhaueradmin; 26.06.2006 10:48:51
	 * @param nl2
	 * @param sTagNameToFind
	 * @param alsCardName
	 * @return
	 */
	public boolean readTagValue(NodeList nl2, String sTagNameToFind, ArrayList alsCardName){
		//Variable
		boolean bFunction = true;
		int iCounter;
		String sTemp = "";
		
		main:
		{
			for(iCounter = 0; iCounter <= nl2.getLength()-1; iCounter++){
					Node domNodeChild = nl2.item(iCounter);
					//Todo: hier vorher noch den Datentyp abfragen (Text oder Element)
					
					sTemp = domNodeChild.getNodeName();
					
					//Entspricht der Kinderwert dem gesuchten Wert. ???
					if (sTemp.equals(sTagNameToFind)){
						Node domNodeTemp = domNodeChild.getFirstChild();
						//System.out.println("Wert betr�gt: " + domNodeTemp.getNodeValue());
						
						//Wert der Liste hinzuf�gen !!! Sie wird nicht gel�scht !!!
						if(alsCardName != null){
							alsCardName.add(domNodeTemp.getNodeValue());
						}else{
							alsCardName = new ArrayList(1);
							alsCardName.add(domNodeTemp.getNodeValue());	
						}
						break main;
					}else{
						//N�chsten Knoten ermitteln
						NodeList nl5 = domNodeChild.getChildNodes();
						bFunction = readTagValue(nl5,sTagNameToFind, alsCardName);
						
					}//end if (sTemp.equals(sTagNameToFind))	
				}//end for
		}
		
		end:
		{
		return bFunction;	
		}
			
	}//end function

	/** Node; all child nodes will be searched
	* lindhaueradmin; 26.06.2006 10:36:18
	 * @param domNodein
	 * @param sTagKeyName, name of the tag which will be searched.
	 * @param sKey               , the value which will be searched for.
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public Node readNodeFirstByKey(Node domNodein, String sTagKeyName, String sKey) throws ExceptionZZZ{
		
		Node domNodeParent = null;
		Node domNodeFound = null;
		String stemp;

	main:
	{	
		paramcheck:{
			if(domNodein == null){
				ExceptionZZZ ez = new ExceptionZZZ("Node", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName() );	
				 throw ez;	
			}	
			
			if(domNodein.getNodeName()==sTagKeyName){
				if(domNodein.getNodeValue() == sKey){
					domNodeFound = domNodein;
					break main;
				}
			}
		} //End paramcheck
		
		/*
		//Eltern-Nodelist des Node ermitteln
		domNodeParent = domNodein.getParentNode();	
		if(domNodeParent == null){
			nl = domNodein.getChildNodes();
		}else{
			nl = domNodeParent.getChildNodes();
		}
		*/
		
	
		
		//Child-Nodelist des Node ermitteln
		NodeList nl = domNodein.getChildNodes();
		
		
		
		//Node aus der Nodelist, der dem Schl�sselwert entspricht
		for(int iCounter2 = 0; iCounter2 <= nl.getLength()-1; iCounter2++){				
					Node domNodeChild = nl.item(iCounter2);					
					stemp = domNodeChild.getNodeName();
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#" + stemp + "----" + iCounter2);
					if (stemp.equals(sTagKeyName)){
						
						//DEN RICHTIGEN ELTERN-KNOTEN GEFUNDEN ???
						NodeList nl3 = domNodeChild.getChildNodes();
						for(int iCounter3 = 0; iCounter3 <= nl3.getLength()-1; iCounter3++){
							Node domNodeTemp = nl3.item(iCounter3);
							switch(domNodeTemp.getNodeType()){
								case 3:
								//Textvergleich, wenn das Ende des "Zweigs" erreicht ist.
								stemp = domNodeTemp.getNodeValue();
								if(stemp.equals(sKey)){
									//4. Den richtigen Knoten gefunden
									domNodeFound = domNodeTemp;							
								} //end if
								break;
								
								case 1:
								//Rekursiver Aufruf dieser Funktion, um das Ende des "Zweigs" zu erreichen
								NodeList nl4 = domNodeTemp.getChildNodes();
								domNodeFound = readNodeFirstByKey(nl4, sTagKeyName, sKey);
								break;		

							} //end switch
							if(domNodeFound != null){
								break main;	
							} //end if
							
						}//end for
					} else { //vom sTemp.equals(sTagKeyName)
						//Rekursiver Aufruf dieser Funktion, um das Ende des "Zweigs" zu erreichen
						NodeList nl4 = domNodeChild.getChildNodes();
						domNodeFound = readNodeFirstByKey(nl4, sTagKeyName, sKey);
						if(domNodeFound != null) break main;	
						
					} //end if sTemp.equals(sTagKeyName)
			    } //end for
			    
			    //Falls immer noch nix gefunden wurde, diese Funktion rekursiv aufrufen.
			    //Dabei vom Parent-Node ausgehen.
			    //Ziel ist es dadurch zur n�chsten ParentNode zu gehen
			    if(domNodeFound == null){
			    	domNodeFound = readNodeFirstByKey(domNodeParent, sTagKeyName, sKey);
			    } //end if
			    		    
	} //end main
	end:
	{
		return domNodeFound;
	}
	} //end function NodeByKeyGet
	
	/** Node, searches in the provided nodelist (and the childs of the nodes) for a tag which has the key-value (STRING) 
	 * This method will be called in recursioin.
	 * 
	* lindhaueradmin; 26.06.2006 10:41:45
	 * @param nl
	 * @param sTagKeyName
	 * @param sKey
	 * @return
	 */
	public Node readNodeFirstByKey(NodeList nl, String sTagKeyName, String sKey){
		Node domNodeFound = null;
		String sTemp;
		
	main:
	{
		//Node aus der Nodelist, der dem Schl�sselwert entspricht
		for(int iCounter2 = 0; iCounter2 <= nl.getLength()-1; iCounter2++){				
					Node domNodeChild = nl.item(iCounter2);
					sTemp = domNodeChild.getNodeName();
					if (sTemp.equals(sTagKeyName)){
						
						//DEN RICHTIGEN ELTERN-KNOTEN GEFUNDEN ???
						NodeList nl3 = domNodeChild.getChildNodes();
						for(int iCounter3 = 0; iCounter3 <= nl3.getLength()-1; iCounter3++){
							Node domNodeTemp = nl3.item(iCounter3);
							switch(domNodeTemp.getNodeType()){
								case 3:
								//Textvergleich, wenn das Ende des "Zweigs" erreicht ist.
								sTemp = domNodeTemp.getNodeValue();
								if(sTemp.equals(sKey)){
									//4. Den richtigen Knoten gefunden
									domNodeFound = domNodeTemp;							
								} //end if
								break;
								
								case 1:
								//Rekursiver Aufruf dieser Funktion, um das Ende des "Zweigs" zu erreichen
								NodeList nl4 = domNodeTemp.getChildNodes();
								domNodeFound = readNodeFirstByKey(nl4, sTagKeyName, sKey);
								break;		

							} //end switch
							if(domNodeFound != null)	break main;	
							
						}//end for
					} else { //vom sTemp.equals(sTagKeyName)
						//Rekursiver Aufruf dieser Funktion, um das Ende des "Zweigs" zu erreichen
						NodeList nl4 = domNodeChild.getChildNodes();
						domNodeFound = readNodeFirstByKey(nl4, sTagKeyName, sKey);
						if(domNodeFound != null){
							break main;	
						}
						
					} //end if sTemp.equals(sTagKeyName)
			    } //end for
	} //end main
	end:
	{
		return domNodeFound;
	}
	} //end function NodeByKeyGet

	/** int, the provided ArrayList will be filled with all "text"-nodes found.
	 	* It will start from a nodelist.
	 	* This method will be called in recursion.
	 	* 
	* lindhaueradmin; 26.06.2006 10:28:33
	 * @param nl
	 * @param alNode
	 * @param sToSearchFor
	 * @param iSearchMethod, 0=search exactly the whole word.
	 * @return
	 */
	public int readNodeAllByText(NodeList nl,ArrayList alNode, String sToSearchFor, int iSearchMethod){
		int iFunction = 0;	
		int iFound = 0;
		int iCounter; 	int iNumberOfTag;
		short shTagType;
		String sValue ="";
		
		Node objNodeTemp;
				
	main:{
		iNumberOfTag = nl.getLength();
		for(iCounter = 0;iCounter < iNumberOfTag; iCounter++){
			//objNodeListTemp = nl.item(iCounter).getChildNodes();
			//iNumberOfTag2 = objNodeListTemp.getLength();
			
			//for(iCounter2 = 0; iCounter2 < iNumberOfTag2; iCounter2++){
				objNodeTemp = nl.item(iCounter);
				shTagType = objNodeTemp.getNodeType();
				//Wurde der Begriff gefunden ? Wenn ja, ihn der ArrayList hinzuf�gen.
				if(shTagType==Node.TEXT_NODE){
					sValue = objNodeTemp.getNodeValue();
					
					//Suche nach dem Text
					if(sValue.equals(sToSearchFor)){
						alNode.add(objNodeTemp);
						iFound++;
					}
				}else if(shTagType==Node.ELEMENT_NODE){
					//Rekursiver Aufruf		
						NodeList objNodeListTemp2 = objNodeTemp.getChildNodes();		
						int iTemp = readNodeAllByText(objNodeListTemp2,alNode, sToSearchFor, iSearchMethod);										
						//System.out.println("Insgesamt gefunden unterhalb des Knotens '" + objNodeTemp.getNodeName() + "':" + iTemp);
						iFound = iFound + iTemp;
				}//end if
		} //end for
	} //end main:


	end:{	
		iFunction = iFound;
		return iFunction;
	}
	}//end Function NodeAllByTextExactGet


	/** Node, starting from a nodelist, all children nodes will be searched.
	 * This method will be called in recursion.
	 * 
	* lindhaueradmin; 26.06.2006 10:43:54
	 * @param nl                     , the starting nodelist
	 * @param sToSearchFor , the string to search for
	 * @param iSearchMethod, 0; exactiy search for the string
	 * @return
	 */
	public Node readNodeFirstByText(NodeList nl,String sToSearchFor, int iSearchMethod){
		Node objNodeFunction = null;	
		int iCounter;
		short shTagType;
		int iNumberOfTag;
		String sValue ="";
		Node objNodeTemp;
		
		
	main:{
		paramcheck:{
			if(nl==null){
				break main;
			}
			if(sToSearchFor.length()==0){
				break main;
			}
		}//end paramcheck
		
		
		iNumberOfTag = nl.getLength();
		for(iCounter = 0;iCounter < iNumberOfTag; iCounter++){
		
				objNodeTemp = nl.item(iCounter);
				shTagType = objNodeTemp.getNodeType();
				//Wurde ein Textelement gefunden ?
				if(shTagType==Node.TEXT_NODE){
					sValue = objNodeTemp.getNodeValue();
					
					//Suche nach dem Text
					if(sValue.equals(sToSearchFor)){
						objNodeFunction = objNodeTemp;
						break main;
					}
				}else if(shTagType==Node.ELEMENT_NODE){
					//Rekursiver Aufruf		
						NodeList objNodeListTemp2 = objNodeTemp.getChildNodes();		
						objNodeFunction = readNodeFirstByText(objNodeListTemp2,sToSearchFor, iSearchMethod);															
				}//end if
				
				//Falls wir den Eintrag gefunden haben, die Schleife abbrechen
				if(objNodeFunction != null){
					break main;
				}
				
			//}
		}
	} //end main:


	end:{	
		return objNodeFunction;
	}
	}//end Function NodeFirstByTextGet	
		


	/** boolean, check the string for beeing the name of the root element.
	* lindhaueradmin; 26.06.2006 09:36:07
	 * @param sToProof
	 * @return
	 */
	public boolean proofDocumentRootName(String sToProof){
	boolean bFunction = true;

	main:
	{
		try{
		domroot = domdoc.getDocumentElement();
		if (domroot.getTagName() != sToProof){
			bFunction = false;
			break main;
		}
		} //end try
		// die verschiedenen Catch - Bl�cke
		catch(Exception e){
			e.printStackTrace();
			break main;
		}
	}
	end:
	{
		return bFunction;
	}
	} //end function

	/** boolean, true, if the node is a root.
	* lindhaueradmin; 26.06.2006 10:27:56
	 * @param objNodein
	 * @return
	 */
	public boolean proofNodeIsRoot(Node objNodein){
		 boolean bFunction = false;
		 Node objNodeRoot = null;
		 main:{
		 	paramcheck:{
		 		if(objNodein==null){
		 			break main;
		 		}
		 		objNodeRoot = (Node)this.getDocument().getDocumentElement();
		 	}	//end paramcheck	 	
		 	
		 	if(objNodeRoot.equals(objNodein)){
		 		bFunction = true;
		 		break main;
		 	}	 			 	
		 } //end main
		 end:{
		 	return bFunction;	
		 }
	}//end function NodeIsRootProof
	
	/** boolean, true, if the parent of the node is the root.
	* lindhaueradmin; 26.06.2006 10:26:26
	 * @param objNodein
	 * @return
	 */
	public boolean proofNodeIsLevelFirst(Node objNodein){
		 boolean bFunction = false;
		 Node objNodeRoot = null;
		 Node objNodeParent = null;
		 main:{
		 	paramcheck:{
		 		if(objNodein==null){
		 			break main;
		 		}	 	
		 			
		 		objNodeRoot = (Node)this.getDocument().getDocumentElement();
		 		if(objNodeRoot == null){
		 			break main;
		 		}
		 		
		 		objNodeParent = objNodein.getParentNode();
		 		if(objNodeParent == null){
		 			break main;
		 		}	 		
		 	}	//end paramcheck
		 	
		 		 	
		 	
		 	if(objNodeRoot.equals(objNodeParent)){
		 		bFunction = true;
		 		break main;
		 	}	 			 	
		 } //end main
		 end:{
		 	return bFunction;	
		 }
	}//end function NodeIsRootProof
	public Node getNodeRoot() {
		Node objReturn = null;
		main:{
			Document objDoc = null;
			chek:{
				objDoc = this.getDocument();
				if(objDoc==null) break main;
			}//end check
			objReturn = (Node) objDoc.getDocumentElement();
			
		}//end main:
		return objReturn;
	}

	}//end class

