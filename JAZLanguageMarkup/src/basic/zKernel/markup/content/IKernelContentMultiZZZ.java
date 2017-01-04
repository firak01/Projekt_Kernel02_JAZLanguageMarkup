package basic.zKernel.markup.content;

import org.jdom.Document;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;

public interface IKernelContentMultiZZZ {
	/** Methoden, mit denen auf einer Ebene auch mehrere Werte verwaltet werden können
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 22.02.2008 07:43:41
	 */
	
	
		
	//### An die erste stelle hinzufügen
	/** Setze diesen Wert an erste Stelle der Werte in dem XML-Dokument
	* @param sVariableName
	* @param sVariableValue
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 25.02.2008 09:10:07
	 */
	public boolean setVarAsFirst(String sVariableName, String sVariableValue) throws ExceptionZZZ;
	public boolean setVarAsFirst(String sVariableName, HashMapMultiZZZ hmVariableValue) throws ExceptionZZZ;

	//### An nter Stelle hinzufügen
	public boolean setVarAsNth(String sVariableName, String sValue, int iIndex) throws ExceptionZZZ;
	public boolean setVarAsNth(String sVariableName, HashMapMultiZZZ hmVariable, int iIndex) throws ExceptionZZZ;
	
	
	public String getVarFirstString(String sVariableName) throws ExceptionZZZ;
	public HashMapMultiZZZ getVarFirstHm(String sVariableName) throws ExceptionZZZ;
	
	
	public String getVarLastString(String sVariableName) throws ExceptionZZZ;
	public HashMapMultiZZZ getVarLastHm(String sVariableName) throws ExceptionZZZ;
	
	
	/** Ersetzt den Variablenwert an der Position. 
	* @param sVariableName
	* @param sValue
	* @return  true, wenn es dort schon einen Wert gab, ansonsten wird dort ein neuer Wert erzeugt, bzw. angehängt.
	* @throws ExceptionZZZ
	* 
	* lindhauer; 24.02.2008 11:13:39
	 */
	public boolean replaceVarFirst(String sVariableName, String sValue) throws ExceptionZZZ;
	
	/** Ersetzt den Variablenwert an der Position. 
	* @param sVariableName
	* @param sValue
	* @return  true, wenn es dort schon einen Wert gab, ansonsten wird dort ein neuer Wert erzeugt, bzw. angehängt.
	* @throws ExceptionZZZ
	* 
	* lindhauer; 24.02.2008 11:13:39
	 */
	public boolean replaceVarFirst(String sVariableName, HashMapMultiZZZ hmVariableValue) throws ExceptionZZZ;
	
	
	
	
	/** Ersetzt den Variablenwert an der Position. 
	* @param sVariableName
	* @param sValue
	* @return  true, wenn es dort schon einen Wert gab, ansonsten wird dort ein neuer Wert erzeugt, bzw. angehängt.
	* @throws ExceptionZZZ
	* 
	* lindhauer; 24.02.2008 11:13:39
	 */
	public boolean replaceVarLast(String sVariableName, String sValue) throws ExceptionZZZ;
	
	/** Ersetzt den Variablenwert an der Position. 
	* @param sVariableName
	* @param sValue
	* @return  true, wenn es dort schon einen Wert gab, ansonsten wird dort ein neuer Wert erzeugt, bzw. angehängt.
	* @throws ExceptionZZZ
	* 
	* lindhauer; 24.02.2008 11:13:39
	 */
	public boolean replaceVarLast(String sVariableName, HashMapMultiZZZ hmVariableValue) throws ExceptionZZZ;
	

	
	
	//### Indexbasierter Zugriff
	public String getVarNthString(String sVariableName, int iIndex) throws ExceptionZZZ;
	public HashMapMultiZZZ getVarNthHm(String sVariableName, int iIndex);

	/** Ersetzt den Variablenwert an der Position. 
	* @param sVariableName
	* @param sValue
	* @return  true, wenn es dort schon einen Wert gab, ansonsten wird dort ein neuer Wert erzeugt, bzw. angehängt.
	* @throws ExceptionZZZ
	* 
	* lindhauer; 24.02.2008 11:13:39
	 */
	public boolean replaceVarNth(String sVariableName, String sValue, int iIndex) throws ExceptionZZZ;
	
	/** Ersetzt den Variablenwert an der Position. 
	* @param sVariableName
	* @param sValue
	* @return  true, wenn es dort schon einen Wert gab, ansonsten wird dort ein neuer Wert erzeugt, bzw. angehängt.
	* @throws ExceptionZZZ
	* 
	* lindhauer; 24.02.2008 11:13:39
	 */
	public boolean replaceVarNth(String sVariableName, HashMapMultiZZZ hmVariableValue, int iIndex) throws ExceptionZZZ;
	
	
}
