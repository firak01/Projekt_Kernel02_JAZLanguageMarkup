package basic.zKernel.markup.content;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.output.Format.TextMode;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;

/** Merke: Anders als bei KernelContentZZZ wird hier intern ein JDOM-Dokument zum Speichern der Daten verwendet.
 * @author lindhaueradmin
 *
 */
public abstract class KernelContentXmlZZZ extends KernelUseObjectZZZ implements IKernelContentZZZ, IKernelContentMultiZZZ, IKernelContentXmlZZZ, IKernelContentComputableZZZ{
	private Document document=null;
	private String sRootName = "";
	
	public KernelContentXmlZZZ(){
		super(); 
	}	
	public KernelContentXmlZZZ(KernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel); 
		if(this.getFlag("init")==false){
			this.createDocument("");
		}
	}
	public KernelContentXmlZZZ(KernelZZZ objKernel, String sRootName) throws ExceptionZZZ{
		super(objKernel);
		if(this.getFlag("init")==false){
			Document document = this.createDocument(sRootName);
			if(document==null){
				ExceptionZZZ ez = new ExceptionZZZ("Unable to creatre JDom Document", iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			this.setDocument(document);
		}
	}
	
	/** Hiermit wird das Dokument erstellt und auch schon das erste Root-Element.
	* 
	* 
	* lindhauer; 22.02.2008 07:06:39
	 * @throws ExceptionZZZ 
	 */
	private Document createDocument(String sRootName) throws ExceptionZZZ{
		Document objReturn = new Document();
		main:{
			if(StringZZZ.isEmpty(sRootName)){
				ExceptionZZZ ez = new ExceptionZZZ("RootName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			Element elem = new Element(sRootName);
			objReturn.setRootElement(elem);
		}//end main:
		return objReturn;
	}
	
	
	//#### Interfaces ###################
	 /* this method must be overwritten by a customized contentStore
	 * @return boolean true/false indicating the success of the method
	 * @throws ExceptionZZZ
	 */
	abstract public boolean compute() throws ExceptionZZZ;
	
	public HashMapMultiZZZ getVarHm(String sVariableName) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}
	public String getVarString(String sVariableName) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sVariableName)){
				ExceptionZZZ ez = new ExceptionZZZ("VariableName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			Document document = this.getDocument();
			Element elemRoot = document.getRootElement();
			Element elemSub = elemRoot.getChild(sVariableName);
			if(elemSub==null) break main;
			
			sReturn = elemSub.getValue();
		}//end main
		return sReturn;
	}

	public boolean setVar(String sVariableName, String sVariableValue) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sVariableName)){
				ExceptionZZZ ez = new ExceptionZZZ("VariableName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			Document document = this.getDocument();
			Element elemRoot = document.getRootElement();
			
			Element elemSubNew = new Element(sVariableName);
			elemSubNew.setText(sVariableValue);
			
			elemRoot.addContent(elemSubNew);
			bReturn = true;
		}//end main:
		return bReturn;
	}

	
	public String getVarFirstString(String sVariableName) throws ExceptionZZZ {
		return getVarString(sVariableName);
	}
	
	
	/* (non-Javadoc)
	 * @see basic.zKernel.markup.content.IKernelContentMultiZZZ#getVarNthString(java.lang.String, int)
	 * 
	 * Der Index beginnt mit 0
	 */
	public String getVarNthString(String sVariableName, int iIndex) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sVariableName)){
				ExceptionZZZ ez = new ExceptionZZZ("VariableName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(iIndex <= -1) break main;
			
			Document document = this.getDocument();
			Element elemRoot = document.getRootElement();
			
			List list = elemRoot.getChildren(sVariableName);
			if(list.isEmpty()) break main;
			
			Element elemSub = (Element) list.get(iIndex);
			if(elemSub==null) break main;
				
			
			sReturn = elemSub.getValue();
			
		}
		return sReturn;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.markup.content.IKernelContentMultiZZZ#setVarAsNth(java.lang.String, java.lang.String, int)
	 * 
	 * Fügt den Wert an der Stelle hinzu
	 */
	public boolean setVarAsNth(String sVariableName, String sVariableValue, int iIndex) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sVariableName)){
				ExceptionZZZ ez = new ExceptionZZZ("VariableName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			Document document = this.getDocument();
			Element elemRoot = document.getRootElement();
			
			Element elemSubNew = new Element(sVariableName);
			elemSubNew.setText(sVariableValue);
			
			//!!! nun muss ALLES ans Ende sortiert werden, bzw. eine Position tiefer
			List list = elemRoot.getChildren();
			
			if(!list.isEmpty()){
				Element elemDummy =(Element) list.get(0);
				Element elem = null;
				int iListSizeAtStart = list.size();
				for(int icount = iIndex; icount <= iListSizeAtStart - 1; icount++){
					//System.out.println(ReflectCodeZZZ.getMethodCurrentName() + icount +"#" + elemDummy.getValue());					
					if(icount == iIndex){
						elemRoot.setContent(0, elemSubNew);  //gaaanz nach vorne stellen
						elem = (Element) elemDummy.clone();
						elemDummy = (Element) list.get(icount + 1);
						elemRoot.setContent(icount + 1, elem);
					}else if(icount == list.size()-1){
						elemDummy.detach();//Wichtig, sonst kann man es nicht mit addConent() hinzufügen
						elemRoot.addContent(elemDummy);   //Die in der vorherigen Schleifenrunde gesicherte Dummy Variable ganz ans Ende anhängen
					}else if(icount >= iIndex){
						//Nun umkopieren
						elem = (Element) elemDummy.clone();
						elemDummy = (Element) list.get(icount + 1);
						elemRoot.setContent(icount + 1, elem);
					}else{
						//nix
					}
				}
			}
			//Ende des "ans Ende sortierens"
			
			bReturn = true;
		}//end main:
		return bReturn;
	}

	
	/* (non-Javadoc)
	 * @see basic.zKernel.markup.content.IKernelContentMultiZZZ#replaceVarFirst(java.lang.String, java.lang.String)
	 */
	public boolean  replaceVarFirst(String sVariableName, String sValue) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sVariableName)){
				ExceptionZZZ ez = new ExceptionZZZ("VariableName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			Document document = this.getDocument();
			Element elemRoot = document.getRootElement();
			
			List list = elemRoot.getChildren(sVariableName);
			if(list.isEmpty()){
				this.setVar(sVariableName, sValue);
				break main;
			}
			
			//Falls es davon schon eine Variable gibt: Die erste ersetzten
			Element elemSub = (Element)list.get(0);
			elemSub.setText(sValue);
			bReturn = true;
		}
		return bReturn;
	}
	
	public boolean  replaceVarLast(String sVariableName, String sValue) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sVariableName)){
				ExceptionZZZ ez = new ExceptionZZZ("VariableName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			Document document = this.getDocument();
			Element elemRoot = document.getRootElement();
			
			List list = elemRoot.getChildren(sVariableName);
			if(list.isEmpty()){
				this.setVar(sVariableName, sValue);
				break main;
			}
			
			//Falls es davon schon eine Variable gibt: Die erste ersetzten
			Element elemSub = (Element)list.get(list.size()-1);
			elemSub.setText(sValue);
			bReturn = true;
		}
		return bReturn;
	}
	
	

	public boolean  replaceVarNth(String sVariableName, String sValue, int iIndex) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sVariableName)){
				ExceptionZZZ ez = new ExceptionZZZ("VariableName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			Document document = this.getDocument();
			Element elemRoot = document.getRootElement();
			
			List list = elemRoot.getChildren(sVariableName);
			if(list.isEmpty()){
				this.setVar(sVariableName, sValue);
				break main;
			}
			
			if(iIndex > list.size()-1){
				this.setVar(sVariableName, sValue);
				break main;
			}
			
			//Falls es davon schon eine Variable gibt: Die erste ersetzten
			Element elemSub = (Element)list.get(iIndex);
			elemSub.setText(sValue);
			bReturn = true;
		}
		return bReturn;
	}
	
	
	/* (non-Javadoc)
	 * @see basic.zKernel.markup.content.IKernelContentMultiZZZ#setVarAsFirst(java.lang.String, java.lang.String)
	 */
	public boolean setVarAsFirst(String sVariableName, String sVariableValue) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sVariableName)){
				ExceptionZZZ ez = new ExceptionZZZ("VariableName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			Document document = this.getDocument();
			Element elemRoot = document.getRootElement();
			
			Element elemSubNew = new Element(sVariableName);
			elemSubNew.setText(sVariableValue);
			
			//!!! nun muss ALLES ans Ende sortiert werden, bzw. eine Position tiefer
			List list = elemRoot.getChildren();
			
			if(!list.isEmpty()){
				Element elemDummy =(Element) list.get(0);
				Element elem = null;
				int iListSizeAtStart = list.size();
				for(int icount = 0; icount <= iListSizeAtStart - 1; icount++){
					//System.out.println(ReflectCodeZZZ.getMethodCurrentName() + icount +"#" + elemDummy.getValue());					
					if(icount == 0){
						elemRoot.setContent(0, elemSubNew);  //gaaanz nach vorne stellen
						elem = (Element) elemDummy.clone();
						elemDummy = (Element) list.get(icount + 1);
						elemRoot.setContent(icount + 1, elem);
					}else if(icount == list.size()-1){
						elemDummy.detach();//Wichtig, sonst kann man es nicht mit addConent() hinzufügen
						elemRoot.addContent(elemDummy);   //Die in der vorherigen Schleifenrunde gesicherte Dummy Variable ganz ans Ende anhängen
					}else{
						elem = (Element) elemDummy.clone();
						elemDummy = (Element) list.get(icount + 1);
						elemRoot.setContent(icount + 1, elem);
					}														
				}
			}
			//Ende des "ans Ende sortierens"
			
			bReturn = true;
		}//end main:
		return bReturn;
	}

		public String getVarLastString(String sVariableName) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sVariableName)){
				ExceptionZZZ ez = new ExceptionZZZ("VariableName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			Document document = this.getDocument();
			Element elemRoot = document.getRootElement();
			
			List list = elemRoot.getChildren(sVariableName);
			if(list.isEmpty()) break main;
			
			Element elemSub = (Element) list.get(list.size()-1);
			if(elemSub==null) break main;
				
			sReturn = elemSub.getValue();
		}
		return sReturn;
	}
	
		//########### HASHMAPMULTIZZZ ################################################
		/* (non-Javadoc)
		 * @see basic.zKernel.markup.content.IKernelContentZZZ#setVarHm(java.lang.String, basic.zBasic.util.abstractList.HashMapMultiZZZ)
		 * 
		 * Dies erzeugt einen XML-String eine Ebene höher als das Root, falls dort noch kein Element mit dem Namen vorhanden ist.
		 * Ansonsten wird das Element erzeugt.
		 */
		public boolean setVar(String sVariableName, HashMapMultiZZZ hmVariableValue) throws ExceptionZZZ {
					return false;  //Code muss noch entwickelt werden FGL 20080223
		}
		
		public HashMapMultiZZZ getVarFirstHm(String sVariableName) throws ExceptionZZZ {
			return getVarHm(sVariableName);
		}
	
		public HashMapMultiZZZ getVarNthHm(String sVariableName, int iIndex) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public boolean setVarAsNth(String sVariableName, HashMapMultiZZZ hmVariable, int iIndex) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
		}
		
		public boolean replaceVarFirst(String sVariableName, HashMapMultiZZZ hmVariableValue) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
			
		}
		
		public HashMapMultiZZZ getVarLastHm(String sVariableName) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return null;
		}
		
		public boolean replaceVarLast(String sVariableName, HashMapMultiZZZ hmVariableValue) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
		}
		
		public boolean replaceVarNth(String sVariableName, HashMapMultiZZZ hmVariableValue, int iIndex) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
		}
		
		public boolean setVarAsFirst(String sVariableName, HashMapMultiZZZ hmVariableValue) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;	
		}
	
	//######### DOCUMENT ###########################################################
		/* Füge das übergebene Xml-Dokument an das intern verwaltete Dokument an.
		 * Der Root des übergebenen Xml-Dokuments wird dabei in den Variablennamen umbenannt.
		 * 
		 * 
		 * 
		 * (non-Javadoc)
		 * @see basic.zKernel.markup.content.IKernelContentXmlZZZ#setVar(java.lang.String, org.jdom.Document)
		 */
		public boolean setVar(String sVariableName, Document objDocJDom) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				if(StringZZZ.isEmpty(sVariableName)){
					ExceptionZZZ ez = new ExceptionZZZ("VariableName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(objDocJDom==null){
					ExceptionZZZ ez = new ExceptionZZZ("JDom Document", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				Element elemRoot2Add = objDocJDom.getRootElement();
				if(elemRoot2Add==null){
					ExceptionZZZ ez = new ExceptionZZZ("JDom Document has no Root-Element", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				//######################################
				//1. Umbenennen des Roots vom hinzuzufügenden Dokument. Unter diesem Namen wird man wieder darauf zugreifen können.
				elemRoot2Add.setName(sVariableName);
				
				Document document = this.getDocument();
				Element elemRoot = document.getRootElement();
				
				//Umhängen des Elements von einem Dokument zu einem anderen !!!
				elemRoot2Add.detach(); //sonst gibt es die Fehelrmeldung, das dieses Element schon ein parent documetn hat.
				elemRoot.addContent(elemRoot2Add);				
				
				bReturn = true;
			}//end main:
			return bReturn;
		}
		
		public Document getVarDocument(String sVariableName)throws ExceptionZZZ{
			Document objReturn = null;
			main:{
				if(StringZZZ.isEmpty(sVariableName)){
					ExceptionZZZ ez = new ExceptionZZZ("VariableName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				Document document = this.getDocument();
				Element elemRoot = document.getRootElement();
				Element elemSub = elemRoot.getChild(sVariableName);
				if(elemSub==null) break main;
				
				//Neues Dokument für die Rückgabe erstellen und das bisherige Unterelement als Rootelement anhängen
				objReturn = new Document();
				elemSub.detach(); //!!! nicht vergessen, sonst Fehlermeldung
				objReturn.setRootElement(elemSub);
			}//end main
			return objReturn;
		}
		
	public Document getVarFirstDocument(String sVariableName) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}
	public Document getVarLastDocument(String sVariableName) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}
	public Document getVarNthDocument(String sVariableName, int iIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean replaceVarFirst(String sVariableName, Document objDocJdom) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean replaceVarLast(String sVariableName, Document objDocJdom) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean replaceVarNth(String sVariableName, Document objDocJdom, int iIndex) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean setVarAsFirst(String sVariableName, Document objDocJdom) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean setVarAsNth(String sVariableName, Document objDocJdom) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	
	//####################################################################################
	
	/** Das intern gespeichert dokument zu Debug und Testzwecken als Datei ausgeben lassen.
	* @param objFile
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 25.02.2008 09:39:58
	 */
	public boolean toFile(File objFile) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No FileObject", iERROR_PARAMETER_MISSING, this,ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			try{
				//FileWriter objWriter = new FileWriter(objFile, true); //true = anhängen
				FileWriter objWriter = new FileWriter(objFile); //true = anhängen
				
				XMLOutputter outputter = new XMLOutputter();
				
				 Format format = outputter.getFormat();				
				 format.setOmitDeclaration(false); //Merke: Bei true  wird die Zeile <?xml version="1.0" encoding="UTF-8"?> weggelassen, was z.B. bei einem HTML-Dokument ggf. für falsche Deutsche Umlaute sorgt.
				 
				 format.setOmitEncoding(false);   //Damit die Encoding Zeile angezeigt wird   
				// format.setExpandEmptyElements(true); //aus <aaa/> wird dann <aaa></aaa>    
				 //format.setTextMode(TextMode.PRESERVE);//Ohne diesen Formatierungshinweis, wird ggf. auch der META-Tag mit /> als Abschluss versehen. Dann funktioniert scheinbar dieser Tag im Browser nicht mehr. Die deutschen Umlaute gehen verloren. 
				format.setEncoding("ISO-8859-1");      //Ziel: "ISO-8859-1" für deutsch       //Ohne diesen Formatierungshinweis wird UTF-8 verwendet. Das bewirkt, dass z.B. die Deutschen Umlaute ä, etc. in die korrespondierende HTML-Umschreibung umgewandelt werden. 
				 
			    outputter.setFormat(format);  //Das muss man machen, sonst sind werden die neuen Format Einstellungen nicht übernommen				
				outputter.output(this.getDocument(),objWriter);
				
				//String sContent = this.getDocument().toString();				
				//objWriter.write(sContent);
				
				objWriter.close();	
			}catch(IOException ioe){
				ExceptionZZZ ez = new ExceptionZZZ("IOException: " + ioe.getMessage(), iERROR_RUNTIME, this,ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}			
		}//end main
		return bReturn;
	}
	
	
	
	//######## GETTER / SETTER
	public String getRootName() throws ExceptionZZZ{
		return this.sRootName;
	}
	
	/** Setzt den Namen des Root-Elements. Falls noch nix existiert wird es erzeugt.
	* @param sRootName
	* @throws ExceptionZZZ
	* 
	* lindhauer; 22.02.2008 07:24:35
	 */
	public void setRootName(String sRootName) throws ExceptionZZZ{
		main:{
			if(StringZZZ.isEmpty(sRootName)){
				ExceptionZZZ ez = new ExceptionZZZ("RootName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			this.sRootName = sRootName;
			
		//Nun aber auch im Dokument den neuen Wert nachziehen
			Document doc = this.getDocument();
			Element elem = doc.getRootElement();
			if(elem == null){
				elem = new Element(sRootName);				
				doc.setRootElement(elem);
			}else{
				elem.setName(sRootName);
			}
		}//end main
	}
	
	public Document getDocument() throws ExceptionZZZ{
		if(this.document==null){
			this.document = this.createDocument(this.getRootName());
		}
		return this.document;
	}
	
	/**Ersetzt das intern gespeicherte Dokument. 
	 *   Falls noch nicht vorhanden wird das Root Element auch erzeugt.
	* @param document
	* @throws ExceptionZZZ
	* 
	* lindhauer; 22.02.2008 07:31:11
	 */
	public void setDocument(Document document) throws ExceptionZZZ{
		if(document==null){
			ExceptionZZZ ez = new ExceptionZZZ("JDom Document", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if(document.hasRootElement()==false){
			String sRootName = this.getRootName(); //ggf. wird der Root-Name eines vorherigen Documents verwendet
			if(StringZZZ.isEmpty(sRootName)){
				ExceptionZZZ ez = new ExceptionZZZ("No Root in JDom Document", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}else{
				Element elem = new Element(sRootName);
				document.setRootElement(elem);
			}
		}
		this.document = document;
	}
	
	}

