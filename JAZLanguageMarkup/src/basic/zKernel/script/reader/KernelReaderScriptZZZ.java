/*
 * Created on 26.11.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zKernel.script.reader;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.regexp.RE;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.script.data.KernelScriptVariableZZZ;


/**
 * @author Lindhauer
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KernelReaderScriptZZZ extends KernelUseObjectZZZ{
	private ArrayList obj_alsHTML;
	private ArrayList obj_alsScript;
    private RE objRegExp_VarCompare; //Regul�rer Ausdruck f�r den Vergleichsoperator (z.B. in der if-Klause
	private RE objRegExp_ScriptStart; //Regul�rer Ausdruck f�r das Start Tag eines Scriptteils
	private RE objRegExp_VarDeclare;  //Regul�rer Ausdruck "Variablendeklaration"
	private RE objRegExp_VarDecline;  //Regul�rer Ausdruck "Variablenzuweisung" (nicht if-Abragen vergleich)
	private RE objRegExp_LineEnd;      //Regul�rer Ausdruck "Zeilenende"
	private HashMap obj_hm_objVar; //Die Hashmap der Variablen. Darf nur Objecte vom Typ ScriptVariableZZZ enthalten
	
	public KernelReaderScriptZZZ(IKernelZZZ objKernel, ArrayList obj_alsHTML, String[] saFlagControl, String sFlagControl) throws ExceptionZZZ{
		super(objKernel);
		KernelReaderScriptNew_(obj_alsHTML, saFlagControl, sFlagControl);
	}
	
	/**
	 * @param objKernel
	 * @param objLog
	 * @param obj_alsHTML
	 * @param saFlagControl
	 * @param sFlagControl
	 */
	private boolean KernelReaderScriptNew_(ArrayList obj_alsHTML, String[] saFlagControl, String sFlagControl) throws ExceptionZZZ {
		boolean bReturn = false;
						main:{
						
							//try{	
					
							//Falls die URL null ist, wird ein entsprechender Fehler ausgegeben.
							if(obj_alsHTML == null){
								ExceptionZZZ ez = new ExceptionZZZ("ArrayList", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName() );
								throw ez;									
							}
							if(obj_alsHTML.size()==0){
								ExceptionZZZ ez = new ExceptionZZZ("ArrayList", iERROR_PARAMETER_EMPTY,   this, ReflectCodeZZZ.getMethodCurrentName());
								throw ez;			
							}				
							this.setPageContent(obj_alsHTML);
				
						
							if(saFlagControl != null){
								String stemp; boolean btemp;
								for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
									stemp = saFlagControl[iCount];
									btemp = setFlag(stemp, true);
									if(btemp==false){ 								   
										   ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 										
										   throw ez;		 
									}
								}
								if(this.getFlag("INIT")==true){
									bReturn = true;
									break main; 
								}		
							}

						}//end main:
						return bReturn;			
	}//end private constructor

	public RE getREScriptStart(String sScriptLanguage){
				RE objReturn=null;
					main:{
						if(sScriptLanguage==null) break main;
						if(sScriptLanguage.equals("")) break main;
						if(this.objRegExp_ScriptStart!=null){
									objReturn = this.objRegExp_ScriptStart;
						}else{
								if(sScriptLanguage.toLowerCase().equals("javascript")){
									//<script language='javascript'>
									// in allen varianten der Gro�-Kleinschreibung
									// mit einfachen Hochkommata oder doppelten Hochkommata
									// mit beliebigen Zeichen vorher und nachher, sowie mit beliebig vielen f�llenden Leerzeichen 
										this.objRegExp_ScriptStart = new RE(".*< *[sS][cC][rR][iI][pP][tT] *[lL][aA][nN][gG][uU][aA][gG][eE] *= *['\"][jJ][aA][vV][aA][sS][cC][rR][iI][pP][tT]['\"] *>.*");
										objReturn = this.objRegExp_ScriptStart;
									}
						}//end if !=null
					}//end main
					return objReturn;
			}

	/**
	 * @param obj_alsPage
	 * @param istart
	 * @return index-position of the script-end-tag
	 */
	public int getScriptEnd(ArrayList obj_alsPage, int icountin) throws ExceptionZZZ{
		int iReturn=-1;
	main:{
	String stemp;
	if(obj_alsPage== null){
		ExceptionZZZ ez = new ExceptionZZZ("PageContent", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());	
		 throw ez;	
	  }
	if(icountin> obj_alsPage.size()) break main;
		
		
	//Parse the ArrayList
	//TODO Eine Funktion zur Verf�gung stellen, oder ein Array mit allen m�glichen Start - Script Tags, mit dem dann quasi ein "ArrayStringContains" durchgef�hrt werden kann.
	int icount=0;
	for(icount = icountin;icount <= obj_alsPage.size(); icount++ ){
		stemp = (String)obj_alsPage.get(icount);
		stemp = stemp.trim().toLowerCase();
		if(stemp.startsWith("</script>")){
				iReturn = icount;
				break main;
		}
	}
			
	}//end main:
	return iReturn;
	}


	/**
	 * @return, ArrayList, containing the strings of the whole page
	 */
	public ArrayList getPageContent() {
		return obj_alsHTML;
	}

	/**
	 * @param list, ArrayList, containing the strings of the whole page
	 */
	public void setPageContent(ArrayList list) {
		obj_alsHTML = list;
	}
	
	public ArrayList getScriptContent(){
		return this.obj_alsScript;
	}
	
	public void setScriptContent(ArrayList obj_alsScript){
		this.obj_alsScript = obj_alsScript;
	}

	/**
	 * @param sScriptLanguage
	 * @return RE - Object of Type RegularExpression used for identifying variable declaration
	 */
	public RE getREVarDeclare(String sScriptLanguage){
		RE objReturn=null;
		main:{
		if(sScriptLanguage==null) break main;
		if(sScriptLanguage.equals("")) break main;
		if(this.objRegExp_VarDeclare!=null){
			objReturn = this.objRegExp_VarDeclare;
		}else{
			if(sScriptLanguage.toLowerCase().equals("javascript")){
				//d.h. am Anfang, mit beliebig vielen Leerzeichen 
				//oder nach beliebigen Zeichen mit einem Semikolon Ende 
				//ok aber unzureichend this.objRegExp_VarDeclare = new RE("^([ ]*[vV][aA][rR])|([.]*[;][ ]*[vV][aA][rR]) ");
				this.objRegExp_VarDeclare = new RE("[ ]*[vV][aA][rR].*[=].*|[.]*[;][ ]*[vV][aA][rR] .*[=].*");			
				objReturn = this.objRegExp_VarDeclare;
			}
		}//end if objRegExp != null
		}//end main
		return objReturn;
	
	
	}//end getREVarDeclare
	
	
	/**
	 * @param sScriptLanguage
	 * @return RE - Object of Type RegularExpression used for identifying variable declination.
	 */
	public RE getREVarDecline(String sScriptLanguage){
		RE objReturn=null;
		main:{
			if(sScriptLanguage==null) break main;
			if(sScriptLanguage.equals("")) break main;
			if(this.objRegExp_VarDecline!=null){
						objReturn = this.objRegExp_VarDecline;
			}else{
					if(sScriptLanguage.toLowerCase().equals("javascript")){
						//gleichzeichen nur einmal und nicht mehrmals vorkommen !!!
						//TODO Es darf nicht vorkommen <script language='javascript'>
							//this.objRegExp_VarDecline = new RE("[={1,1}][^={2,}]");
						this.objRegExp_VarDecline = new RE("[^=!]=[^=]");  //also nur einmal ein Gleichheitszeichen
							objReturn = this.objRegExp_VarDecline;
						}
			}//end if !=null
		}//end main
		return objReturn;
	}//end getREVarDecline
	
	public RE getREVarCompare(String sScriptLanguage){
		RE objReturn=null;
				main:{
					if(sScriptLanguage==null) break main;
					if(sScriptLanguage.equals("")) break main;
					if(this.objRegExp_VarCompare!=null){
								objReturn = this.objRegExp_VarCompare;
					}else{
							if(sScriptLanguage.toLowerCase().equals("javascript")){
								//gleichzeichen nur einmal und nicht mehrmals vorkommen !!!
								//TODO Es darf nicht vorkommen <script language='javascript'>
									//this.objRegExp_VarDecline = new RE("[={1,1}][^={2,}]");
								this.objRegExp_VarCompare = new RE("([^=]==[^=])|(!=)");  //also nur zweimal ein Gleichheitszeichen
									objReturn = this.objRegExp_VarCompare;
								}
					}//end if !=null
				}//end main
				return objReturn;
	}//end getREVarCompare
	
	
	
	
	/**
	 * @param sScriptLanguage
	 * @return RE - Object of Type RegularExpression used for identifying command line End.
	 */
	public RE getRELineEnd(String sScriptLanguage){
		RE objReturn=null;
		main:{
			if(sScriptLanguage==null) break main;
			if(sScriptLanguage.equals("")) break main;
			if(this.objRegExp_LineEnd!=null){
						objReturn = this.objRegExp_LineEnd;
			}else{
					if(sScriptLanguage.toLowerCase().equals("javascript")){
						//gleichzeichen nur einmal und nicht mehrmals vorkommen !!!
						this.objRegExp_LineEnd = new RE("( *; *)$|(//.*)$|(/\\*.*)$");
						objReturn = this.objRegExp_LineEnd;
						}
			}//end if !=null
		}//end main
		return objReturn;
	}
	
		
	/**
	 * @return HashMap containing Objects of Type VariableScriptZZZ
	 */
	public HashMap getVariableMap() {
		if(this.obj_hm_objVar==null){
			this.obj_hm_objVar = new HashMap();
		}
		return this.obj_hm_objVar;
	}

	/**
	 * @param map
	 */
	public void setVariableMap(HashMap map) {
		this.obj_hm_objVar = map;
	}
	
	/**
	 * provides direct access to content of the VariableMap-Object
	 * @param sVar, String Name of the Variable
	 * @return ScriptVariableZZZ Object
	 */
	public KernelScriptVariableZZZ getVarObjectByName(String sVar) throws ExceptionZZZ {
		KernelScriptVariableZZZ objReturn= null;
		main:{		
			if(StringZZZ.isEmpty(sVar)){
				ExceptionZZZ ez = new ExceptionZZZ("Variable-Name", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());	
				 throw ez;	
			}
		
			
	  //Aus der Hashmap nun den Eintrag lesen, Merke gibt es das HashMap-Object noch nicht wird es im getter initialisiert
	  HashMap obj_hm_Var = this.getVariableMap();
	  objReturn = (KernelScriptVariableZZZ)obj_hm_Var.get(sVar);
	  		
		}//end main
		return objReturn;
	}

}

