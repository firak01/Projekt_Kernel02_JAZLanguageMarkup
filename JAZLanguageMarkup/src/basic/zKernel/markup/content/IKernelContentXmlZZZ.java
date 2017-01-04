package basic.zKernel.markup.content;

import org.jdom.Document;

import basic.zBasic.ExceptionZZZ;

public interface IKernelContentXmlZZZ {
	//	### Behandlung von Xml-Dokumenten
	public Document getVarDocument(String sVariableName) throws ExceptionZZZ;
	public boolean setVar(String sVariableName, Document objDocJdom) throws ExceptionZZZ;
	
	
	//### An nter Stelle hinzufügen
	public boolean setVarAsFirst(String sVariableName, Document objDocJdom) throws ExceptionZZZ;
	public boolean setVarAsNth(String sVariableName, Document objDocJdom) throws ExceptionZZZ;
	
	public Document getVarFirstDocument(String sVariableName) throws ExceptionZZZ;
	public Document getVarLastDocument(String sVariableName) throws ExceptionZZZ;
	
	//### An nter Stelle ersetzen
	/** Ersetzt den Variablenwert an der Position. 
	* @param sVariableName
	* @param sValue
	* @return  true, wenn es dort schon einen Wert gab, ansonsten wird dort ein neuer Wert erzeugt, bzw. angehängt.
	* @throws ExceptionZZZ
	* 
	* lindhauer; 24.02.2008 11:13:39
	 */
	public boolean replaceVarFirst(String sVariableName, Document objDocJdom) throws ExceptionZZZ;
	
	/** Ersetzt den Variablenwert an der Position. 
	* @param sVariableName
	* @param sValue
	* @return  true, wenn es dort schon einen Wert gab, ansonsten wird dort ein neuer Wert erzeugt, bzw. angehängt.
	* @throws ExceptionZZZ
	* 
	* lindhauer; 24.02.2008 11:13:39
	 */
	public boolean replaceVarLast(String sVariableName, Document objDocJdom) throws ExceptionZZZ;
	
	/** Ersetzt den Variablenwert an der Position. 
	* @param sVariableName
	* @param sValue
	* @return  true, wenn es dort schon einen Wert gab, ansonsten wird dort ein neuer Wert erzeugt, bzw. angehängt.
	* @throws ExceptionZZZ
	* 
	* lindhauer; 24.02.2008 11:13:39
	 */
	public boolean replaceVarNth(String sVariableName, Document objDocJdom, int iIndex) throws ExceptionZZZ;
	
	
	//### Zugriff auf die nte Stelle 
	public Document getVarNthDocument(String sVariableName, int iIndex);
	
	
	
}

