/*
 * Created on 26.11.2004
 Class can parse Javascript code.
 currently not handled:
 - If / Switch statements, which skip execution of code
 - Array declaration with Variables (e.g. var myarray[a] ....)
 - Loops
 - any formula compution
 - if an expression is in a String (e.g. My father said:" e=mc isn�t correct" will result in a Variable e with the text left to the =
   Idea: make a regular expression for Strings. Remember strings can start with " or with ' and have to end with the same letter.

 */
package basic.zKernel.script.reader;

import java.util.ArrayList;

import org.apache.regexp.RE;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.script.data.KernelScriptVariableZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;


/**
 * @author Lindhauer
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KernelReaderScriptJavascriptZZZ extends KernelReaderScriptZZZ {
		
	/**
	 * @param objKernel
	 * @param objLog
	 * @param obj_alsHTML
	 * @param saFlagControl
	 * @param sFlagControl
	 * @throws ExceptionZZZ
	 */
	public KernelReaderScriptJavascriptZZZ(IKernelZZZ objKernel, ArrayList obj_alsHTML, String[] saFlagControl, String sFlagControl) throws ExceptionZZZ {
		super(objKernel, obj_alsHTML, saFlagControl, sFlagControl);
	}
	
	public boolean loadScriptContent() throws ExceptionZZZ{
		return loadScriptContent_(null,false,"");
	}
	
	private boolean loadScriptContent_(ArrayList obj_alsHTMLIn, boolean bFlagUseList, String sFlagContol) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{		
		//try {
			if(bFlagUseList==false){
				//Falls kein Content vorhanden ist, wird ein entsprechender Fehler ausgegeben.
				if(this.getPageContent()== null){
					ExceptionZZZ ez = new ExceptionZZZ("PageContent", iERROR_PROPERTY_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;			
					  }
			}else{
				//Verwende die �bergebene ArrayList
			    if(obj_alsHTMLIn == null){
			    	ExceptionZZZ ez = new ExceptionZZZ("PageContent", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;		
			    }else{
			    	this.setPageContent(obj_alsHTMLIn);	
			    }
			}
			
			ArrayList obj_alsPage = this.getPageContent();
			if(obj_alsPage.size()==0){
				ExceptionZZZ ez = new ExceptionZZZ("PageContent", iERROR_PARAMETER_EMPTY,   this, ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;	
			}
			
			//now parse the arraylist and get all javascript-sections
			ArrayListZZZ obj_alsScript = new ArrayListZZZ(100);			
			int istart = 0;
			int iend = 0;
			for(int icount = 0; icount <= obj_alsPage.size()-1;icount++){
				istart = this.getScriptStart(obj_alsPage, icount);
				if(istart == -1) break;
				iend = this.getScriptEnd(obj_alsPage, istart);
				
				obj_alsScript.addList(obj_alsPage, istart, iend);
				icount = iend + 1;
			}
			
			this.setScriptContent((ArrayList)obj_alsScript);
			
			
			bReturn = true;										
	}//end main:
		return bReturn;
	}
	
	
	/**
	 * parses a line and returns a variable if available, filled with the value (if available)
	 * @param sLine, the ScriptLine to parse
	 * @return
	 */
	public KernelScriptVariableZZZ VarDetailParse(String sLine) throws ExceptionZZZ {
		KernelScriptVariableZZZ objReturn = null;
		main:{			
			if(sLine== null){
				ExceptionZZZ ez = new ExceptionZZZ("Line", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
  
			 //Search for a variable
			  if(sLine.equals(""))break main;
			
			//Das Script-Start-Tag ist keine Variablendeklaration
			boolean bFlagTagScriptStartFound = this.getREScriptStart().match(sLine);
			if (bFlagTagScriptStartFound == true)	break main;
				
								
			//Variablendeklarationen herausfinden				
			if(this.getREVarDeclare().match(sLine)==true){
				//System.out.print("   <--- DECLARATION VARIABLENZEILE GEFUNDEN");
								
				//Variablendklaration
				objReturn = this.VarDeclare(sLine);

			}else if(this.getREVarDecline().match(sLine)==true){   //nicht das StartTag als Variablen initialisierung missdeuten
				//System.out.print("   <XXX DECLINATION VARIABLENZEILE GEFUNDEN");
			
				//Falls keine Variablendeklaration sondern Initialisierung (versuchen)
				objReturn = this.VarDecline(sLine); 										
			}else{											
				objReturn = null;  	
			}
				
		}//end main
		return objReturn;
	}

	/**
	 * @param sLine, the Line which has to be parsed
	 * @return ScriptVariableZZZ, may filled with a value(s). If no variable can be found 'null' will be returned.
	 */
	public KernelScriptVariableZZZ VarDeclare(String sLine) throws ExceptionZZZ{
		return VarDeclare_(sLine);
	}
	private KernelScriptVariableZZZ VarDeclare_(String sLine) throws ExceptionZZZ{
		KernelScriptVariableZZZ objReturn = null;
		main:{			
			if(sLine== null){
				ExceptionZZZ ez = new ExceptionZZZ( "Line", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());					  
				throw ez;	
			  }
			  
			  //Search for a variable
			  if(sLine.equals(""))break main;
			  
			  //#####################
			String sVarVAR = null;  
			String sVarVAL = null; 
			if(this.getREVarDeclare().match(sLine)==true){
					//Variable line found. Is this a new one ?
					//TODO: vor dem Input: Ggf. Splitten der Zeile mit dem Tokenizer in mehrere mit Semikolon getrennte Werte
		
					//den Variablennamen ermitteln
				     sVarVAR = this.VarNameParse(sLine);
					if(sVarVAR==null) break main; 
					
										
					//den VARIABLENWERT ermitteln
					sVarVAL = this.VarValueParse(sLine);
					
					//ARRAY DEKLARATION, ggf VARIABLENDEKLARATION UND WERTZUWEISUNG 										
					if(sVarVAL.toLowerCase().startsWith("new array")){
					//Declare a new Array
						int itemp3 = sVarVAL.indexOf("(");
						if (itemp3 == -1){
							//Man brauch in Javascript keine Arrays zu deklarieren
							objReturn = new KernelScriptVariableZZZ(this.getKernelObject(),  sVarVAR,0);
							break main;
						}
						
						//falls die geschlossene Klammer fehlt, dann ignorieren wir dies, eine explizite Array-Deklaration darf aber nicht passieren
						int itemp4 = sVarVAL.indexOf(")");
						if(itemp4 == -1){
							objReturn = new KernelScriptVariableZZZ(this.getKernelObject(), sVarVAR,0);
							break main;
						}
						
						//Index-Grenze des Arrays herausfinden
						String sIndex = sVarVAL.substring(itemp3+1, itemp4);
						
						if(sIndex.equals("")== false){
							Integer intTemp = new Integer(sIndex);													
							objReturn = new KernelScriptVariableZZZ(this.getKernelObject(), sVarVAR, intTemp.intValue());																							
						}
					}else{
							//Single Variable, directe Wertzuweisung
							objReturn = new KernelScriptVariableZZZ(this.getKernelObject(), sVarVAR,0);
							sVarVAL=this.VarValueParse(sLine);		//Merke: Bisher ist VarVAL noch nicht der endg�ltige Wert. Es kann z.B. ein Semikolon anh�ngen.													
							objReturn.setValueString(sVarVAL);									
					}		
			}
			  
			  
			  
			  
		}//end main
		return objReturn;
	}//end function declareVar_
	
	/**
	 * Get a ScrptVariableZZZ from a Line of Code which is not declared but declined
	 * @param sLine
	 * @return ScriptVariableZZZ, or null if there is no Variable in the line of code.
	 * @throws ExceptionZZZ
	 */
	public KernelScriptVariableZZZ VarDecline(String sLine) throws ExceptionZZZ{
		return VarDecline_(sLine);
	}
	
	private KernelScriptVariableZZZ VarDecline_(String sLine) throws ExceptionZZZ{
		KernelScriptVariableZZZ objReturn = null;
				main:{								
								if(sLine== null){
									ExceptionZZZ ez = new ExceptionZZZ("Line", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());				  
									throw ez;	
								  }
			  
								  //Search for a variable
								  if(sLine.equals(""))break main;
			  					
									//Damit soll zwar eine Zuweisung erkannt werden aber die einleitende Zuweisung im Script-Tag bleibt aussen vor			  					
			  						if(this.getREVarDecline().match(sLine)==true && this.getREScriptStart().match(sLine)==false){										
									
										//1. Variablennamen ermitteln
										String sVar = this.VarNameParse(sLine);

										//Bestehendes Variablenobjekt ermitteln
										objReturn = this.getVarObjectByName(sVar); 
										
										//2. M�glichen Index auslesen
										int iIndex = this.VarIndexParse(sLine, sVar);
										if(iIndex==-1) break main;  //dann gibt es die Variable garnicht in der Zeile !!!
										
										if(objReturn==null){
											//neues Variablen-Objekt erstellen																									
											objReturn= new KernelScriptVariableZZZ(this.getKernelObject(), sVar, iIndex);										    
										}	
										
										//3. Variablenwert ermitteln und dem gefundenen/erzeugtem Variablenobject zuweisen.
										String sVal = this.VarValueParse(sLine);
										if(sVal != null){
											objReturn.setValueString(sVal);																	
										}else{
											objReturn.setValueString("");
										}
			  						}
				}
				return objReturn;
	}
	
	








	/**
	 * @param obj_alsPage   ArrayList with HTML/Script-Codelines
	 * @param icount             Starting Index for the search in the ArrayList
	 * @return Integer value of the index in the obj_alsPage, where the starting script-tag was found
	 */
	public int getScriptStart(ArrayList obj_alsPage, int icountIn) throws ExceptionZZZ {
		int iReturn=-1;
		main:{
		String stemp;
		if(obj_alsPage== null){
			ExceptionZZZ ez = new ExceptionZZZ("PageContent", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName() );				  
			throw ez;	
		  }
		if(icountIn > obj_alsPage.size()) break main;
		
		
		//Parse the ArrayList
		//TODO Eine Funktion zur Verf�gung stellen, oder ein Array mit allen m�glichen Start - Script Tags, mit dem dann quasi ein "ArrayStringContains" durchgef�hrt werden kann.
		int icount=0;
		for(icount = icountIn; icount <= obj_alsPage.size()-1; icount++ ){
			stemp = (String)obj_alsPage.get(icount);
			stemp = stemp.trim().toLowerCase();
			//if(stemp.startsWith("<script language=\"javascript")|| stemp.startsWith("<script language='javascript")){
			if(this.getREScriptStart().match(stemp)){
					iReturn = icount;
					break main;
			}
		}
			
		}//end main:
		return iReturn;
	}//end function
	
	public RE getREScriptStart(){
		return super.getREScriptStart("javascript");	
	}
	
	public RE getREVarDeclare(){ 
		return super.getREVarDeclare("javascript");
	}
	
	public RE getREVarDecline(){
		return super.getREVarDecline("javascript");	
	}	
	
	public RE getRELineEnd(){
		return super.getRELineEnd("javascript");
	}
	
	/**
	 	 * Get a variablename from a line of code. If there is an array, no array-index will be returned
		 * @param sLine
		 * @return String, the Variable Name found in the String or null if no Variable Name exists there.
		 */
		public String VarNameParse(String sLine){
			String sReturn = null;
			main:{
			
				//Falls eine Variablen - Deklaration vorne steht, ist diese zu entfernen
				RE objREVar = new RE("[ ]*[vV][aA][rR] ");
				int itemp2 = 0;
				String sLineTemp=sLine;
				
				while (objREVar.match(sLineTemp)){
					itemp2= objREVar.getParenEnd(0);
					sLineTemp = sLineTemp.substring(itemp2);
				}
				//noch mal trimmen
				sLineTemp = sLineTemp.trim();

				//Links von dem Gleichheitszeichen
							itemp2 = sLineTemp.indexOf("=");
							if( itemp2==-1) break main;
							sReturn = sLineTemp.substring(0,itemp2).trim();
				
							//Falls es eckige Klammern [] enth�lt sind diese und deren Inhalt zu entfernen !!!
							itemp2 = sReturn.indexOf("[");
							if(itemp2 == 0){  //eckige Klammer direkt am Anfang, igitt !!!
								sReturn=null;
								break main;
							}
							if(itemp2 >=1){
								sReturn=sReturn.substring(0,itemp2);
							}
				

			}//end main
			return sReturn;
		}

		public String VarValueParse(String sLine){
			String sReturn = null;
			main:{
				//Rechts vom Gleichheitszeichen
				int itemp2 = sLine.indexOf("=");
				if( itemp2==-1) break main;
				sReturn = sLine.substring(itemp2+1).trim();	
				
				//ggf. alles was am Ende sein kann (Leerzeichen, Kommentare // oder */ Semikolon) entfernen
				  RE objREEnd = this.getRELineEnd();
				  while (objREEnd.match(sReturn)){
					  sReturn = sReturn.substring(0,objREEnd.getParenStart(0));
				  }
				  //noch mal trimmen
				  sReturn = sReturn.trim();
											
			}//end main
			return sReturn;
		}
		
		/**
		 * Use this method to get information about the index-parameter, which is necessary in the constructor of the ScriptVariableZZZ - Object.
		 * @param sLine, String, Line of Code.
		 * @param sVarName, String the VariableName
		 * @return int, the value x found between the brackets [x], -1 if the Variable does not exist, 0 either if there are no brackets or x==0.
		 */
		public int VarIndexParse(String sLine, String sVarName) throws ExceptionZZZ{
			int iReturn=-1;
			main:{
				String stemp;
				if(StringZZZ.isEmpty(sLine)){
					ExceptionZZZ ez = new ExceptionZZZ("Line of code", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());				
					throw ez;
				}				
				if(StringZZZ.isEmpty(sVarName)){
					ExceptionZZZ ez = new ExceptionZZZ("Variable Name", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());				
					throw ez;											
				}
				if(sVarName.equals("")){
					ExceptionZZZ ez = new ExceptionZZZ("Variable Name", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());				
					throw ez;											
				}
								
								
				//Parsen der Zeile
				int itemp2 = sLine.lastIndexOf(sVarName);				
				if(itemp2==-1){
					//Variable ist �berhaupt nicht in der Zeile vorhanden
					iReturn = -1;
					break main;
				}
				
				//Es wird als index der Stringanfang zur�ckgegeben, also
				itemp2=itemp2+sVarName.length();
				
				stemp=sLine.substring(itemp2);
				itemp2=stemp.indexOf("[");
				if(itemp2==-1){
					//Variable ist per se kein Array, wird aber in der Klasse ScriptVariableZZZ als Array der Gr��e 0 behandelt.
					iReturn=0;
					break main;
				}
				int itemp3 = stemp.indexOf("]");
				if(itemp3==-1){
					//Fehlende geschlossene klammer, also wird der Wert ignoriert
					iReturn = -1;
					break main;					
				}
				
				//R�ckgabe des Indexwerts
				stemp=stemp.substring(itemp2+1, itemp3);
				if(stemp.equals("")){
					iReturn = 0;
				}else{
					//TODO Was tun, wenn eine Variable (also Buchstaben) die Index-Gr��e bestimmt ????
				    iReturn = Integer.parseInt(stemp);	
				}
											
			}//end main:
			return iReturn;
		}
	
}//end class
