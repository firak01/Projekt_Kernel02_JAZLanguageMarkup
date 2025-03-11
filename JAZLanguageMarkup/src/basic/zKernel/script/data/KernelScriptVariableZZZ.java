/*
 * Created on 02.12.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zKernel.script.data;

import java.util.ArrayList;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;


/**
 * @author Lindhauer
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KernelScriptVariableZZZ extends AbstractKernelUseObjectZZZ{
	private String sName;
	private ArrayList obj_alVar=new ArrayList(0);  //Merke: Intern wird n�mlich auch eine einfache Variable in solch einer ArrayList gespeichert.
	
	private boolean bFlagIsArray;
	
	
	public KernelScriptVariableZZZ(IKernelZZZ objKernel, String sName, String sValue) throws ExceptionZZZ{
		super(objKernel);
		String[] saTemp = {""};
		KernelScriptVariableNew_(sName, 0, sValue, saTemp, "");
	}//end constructor
	public KernelScriptVariableZZZ(IKernelZZZ objKernel, String sName, int iRange) throws ExceptionZZZ{
		super(objKernel);
		String[] saTemp=null;
		if(iRange >= 1 ){
			saTemp = new String[1];
			saTemp[0] = new String("IsArray");	
		}
		
		KernelScriptVariableNew_(sName, iRange, null, saTemp, "");
		}//end constructor
		
		private boolean KernelScriptVariableNew_( String sNameIn, int iRange, String sValue, String[] saFlagControl, String sFlagControl) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
								
								//try{	
						
								if(saFlagControl != null){
									String stemp; 	boolean btemp;
									for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
										stemp = saFlagControl[iCount];
										btemp = setFlag(stemp, true);
										if(btemp==false){ 								   
											   ExceptionZZZ ez = new ExceptionZZZ( stemp, IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 											  
											   throw ez;		 
										}
									}
									if(this.getFlag("INIT")==true){
										bReturn = true;
										break main; 
									}			
								}
												
					
								//Falls der Variablenname null ist, wird ein entsprechender Fehler ausgegeben.
								if(sNameIn == null){
									ExceptionZZZ ez = new ExceptionZZZ("'Name of variable'", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());
									throw ez;																			
								}
								if(sNameIn.equals("")){
									ExceptionZZZ ez = new ExceptionZZZ("'Name of variable'", iERROR_PARAMETER_EMPTY,   this, ReflectCodeZZZ.getMethodCurrentName());
									throw ez;	
								}				
								this.setName(sNameIn);
								
								if(sValue!=null){
									this.getArrayList().add(sValue);
								}
								if(iRange>0){
									this.setFlag("isarray", true);
									this.getArrayList().ensureCapacity(iRange+1);
								}
																
							}//end main:
			return bReturn;
		}
		
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetFunctionZZZ#getFlag(java.lang.String)
	 * Flags:
	 * 	- IsArray
	 */
	public boolean getFlag(String sFlagName){
			boolean bFunction = false;
			main:{
				if(StringZZZ.isEmpty(sFlagName)) break main;
				bFunction = super.getFlag(sFlagName);
				if(bFunction==true) break main;
				
				//getting the flags of this object
				String stemp = sFlagName.toLowerCase();
				if(stemp.equals("isarray")){
					bFunction = bFlagIsArray;
					break main;			
				}
			}//end main:
			return bFunction;
		}//end getflag
		
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetFunctionZZZ#setFlag(java.lang.String, boolean)
	 * Flags:
	 * 	- IsArray
	 */
	public boolean setFlag(String sFlagName, boolean bFlagValue) throws ExceptionZZZ{
			boolean bFunction = false;
			main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.setFlag(sFlagName, bFlagValue);
			if(bFunction==true) break main;
	
			//setting the flags of this object
			String stemp = sFlagName.toLowerCase();
			if(stemp.equals("isarray")){
				bFlagIsArray = bFlagValue;
				bFunction = true;
				break main;		
			}
			}//end main:
			return bFunction;
		}
	
	/**
	 * @return
	 */
	public String getName() {
		return sName;
	}

	/**
	 * Gibt den so ausgelesenen Wert zur�ck. Falls die Variable ein Array ist, wird die Indexposition 0 zur�ckgegeben.
	 * Falls es ein String ist, steht der Wert dabei in Anf�hrungszeichen.
	 * @return //null, wenn es keine Variable in der ArrayListe der Variablen gibt. Ansonsten die Position 0 der ArrayListe
	 */
	public String getValuePure() {
		return this.getValuePure(0);
	}
	
	/**
	 * Gibt den so ausgelesenen Wert zur�ck. Dabei steht der Wert in Anf�hrungszeichen, wenn es ein String ist.
	 * @param iIndex
	 * @return // null, wenn es entweder die Variable nicht gibt, oder die Position in der Arrayliste nicht vorhanden ist.
	 */
	public String getValuePure(int iIndex){
		String sReturn = null;
		main:{
			ArrayList obj_als = this.getArrayList();
			if(obj_als.size()==0)break main;
			if(obj_als.size()<=iIndex) break main;//Eine Indexposition gibt es hier nicht
			sReturn = (String)obj_als.get(iIndex);			
		}
		return sReturn;
	}
	
	/**
	 * @return String, falls es ein Array ist, wird die Indexposition 0 zur�ckgegeben.
	 * Aber im Gegensatz zu "ValuePure" werden hier ggf. die String-Anf�hrungszeichen entfernt.
	 */
	public String getValueString(){
		return this.getValueString(0);
	}
	
	
	/**
	 * @param iIndex
	 * @return String, falls es ein Array ist, wird die Indexposition zur�ckgegeben.
	 * Aber im Gegensatz zu "ValuePure" werden hier ggf. die String-Anf�hrungszeichen entfernt.
	 */
	public String getValueString(int iIndex){
		String sReturn = null;
		main:{
			String stemp = this.getValuePure(iIndex);
			if(stemp==null) break main;
			if(stemp.startsWith("\"")){
				sReturn=stemp.substring(1,stemp.length()-1);
			}else{
				sReturn = stemp;
			}
		}
		return sReturn;
	}
	
	
	
	

	/**
	 * @param string
	 */
	public void setName(String string) {
		sName = string;
	}

	/**
	 * Adds the String - Value to the ArrayList at index-position 0
	 * @param string
	 */
	public void setValueString(String string) {
		main:{
			ArrayList obj_als = this.getArrayList();
			obj_als.add(0, string);				
		}
	}
	
	/**
	 * Adds the String - Value to the ArrayList at the spezified index-position
	 * @param sString
	 * @param iIndex
	 */
	public void setValueString(String sString, int iIndex){
		ArrayList obj_als = this.getArrayList();
		obj_als.add(iIndex, sString);		
	}

	/**
	 * @return
	 */
	public ArrayList getArrayList() {
		return obj_alVar;
	}

	/**
	 * @param list
	 */
	public void setArrayList(ArrayList list) {
		obj_alVar = list;
	}
	
	

	}//end class KernelScriptVariable
