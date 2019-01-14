/*
 * Created on 09.01.2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zKernel.markup.content;

import java.util.HashMap;

import org.apache.commons.collections.map.MultiValueMap;
import org.jdom.Document;

import basic.zKernel.IKernelZZZ;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelUseObjectZZZ;


/**
 * global class, which contains methods relevant to all content aspects. e.g. saving content to file
 * - Handling Variable-Content
 * 
 * Von diser Klasse erben spezielle abstrakte KernelKlassen. Z.B. f�r Ecs, File, .....
 * Merke: Xml wird hiermit nicht behandelt, da dabei intern ein JDOM Dokument verwendet wird.
 * @author Lindhauer
 */
public class KernelContentZZZ extends KernelUseObjectZZZ implements IKernelContentZZZ{
	private HashMapMultiZZZ hmVar=new HashMapMultiZZZ();//Speicherung der Variablen: hmVar("UE1")="Beispiel �berschrift"
	//private MultiValueMap hmVar = new MultiValueMap();
	
	private boolean bFlagRemoveTagZHTML = false;
	
	public KernelContentZZZ(){
		super();
	}
	 
	public KernelContentZZZ(IKernelZZZ objKernel){ 
		super(objKernel);
	}
	
	/**
	 * add a string value to the named variable.
	 * sVarValue==null will result in removing the placeholder from the internal placeholder list.
	 * This variable can then used as as placeholder in compute(...), using the getVarString(sVarName) - Method
	 * @param sVarName
	 * @param sVarValue
	 */
	public boolean  setVar(String sVarName, String sVarValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			//Check
			if(StringZZZ.isEmpty(sVarName)){				
			   ExceptionZZZ ez = new ExceptionZZZ("VariableName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
			   throw ez;				
			}
			if(sVarValue==null){
				this.hmVar.remove(sVarName);
			}else{
				this.hmVar.put(sVarName, sVarValue);	
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}//end setVarString
	
	/**
	 * get a variable value from the internal list.
	 * @param sVarName
	 * @return the String value of the variable
	 * @throws ExceptionZZZ
	 */
	public String getVarString(String sVarName) throws ExceptionZZZ{
		String sReturn=null;
		main:{			
					if(StringZZZ.isEmpty(sVarName)){						
					   ExceptionZZZ ez = new ExceptionZZZ("VariableName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					   throw ez;						
					}
					Object objTest = (Object) this.hmVar.get(sVarName);
					if(objTest==null) break main;
					
					Class cl = objTest.getClass();
					//System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#Klassenname: '" + cl.getName() + "'");
					if(cl.getName().equals("java.lang.String")){
						sReturn = (String) this.hmVar.get(sVarName);
					}else if(cl.getName().equals(HashMapMultiZZZ.class.getName())){
						//???
					}else{
						sReturn = objTest.toString();
					}
					
					
					
		}//end main
		return sReturn;
	}//end getVarString()
	
	public boolean setVar(String sVarName, HashMapMultiZZZ hmVarValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sVarName)){						
			   ExceptionZZZ ez = new ExceptionZZZ("VariableName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			   throw ez;						
			}
			this.hmVar.put(sVarName,hmVarValue);
			bReturn = true;
		}//end main:
		return bReturn;
	}
	/* (non-Javadoc)
	 * @see basic.zKernel.markup.content.IKernelContentZZZ#getVarHm(java.lang.String)
	 * 
	 *!!! Merke: Es wird immer das zuletzt hinzugef�gte Objekt zur�ckgegeben.
	 */
	public HashMapMultiZZZ getVarHm(String sVarName) throws ExceptionZZZ{
		HashMapMultiZZZ objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sVarName)){						
				   ExceptionZZZ ez = new ExceptionZZZ("VariableName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				   throw ez;						
			}
			objReturn = (HashMapMultiZZZ) this.hmVar.get(sVarName);
		}
		return objReturn;
	}
		
	/* (non-Javadoc)
	 * @see basic.zBasic.ObjectZZZ#setFlag(java.lang.String, boolean)
	 * 
	 * Verwendete Flags:
	 * - RemoveZHTML
	 */
	public boolean setFlag(String sFlagName, boolean bFlagValue){
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.setFlag(sFlagName, bFlagValue);
		if(bFunction==true) break main;
	
		//setting the flags of this object
		String stemp = sFlagName.toLowerCase();
		if(stemp.equals("removezhtml")){
			bFlagRemoveTagZHTML = bFlagValue;
			bFunction = true;
			break main;
		}
		}//end main:
		return bFunction;
	}
	
	public boolean getFlag(String sFlagName){
		boolean bFunction = false; 
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.getFlag(sFlagName);
			if(bFunction==true) break main;
		
			//getting the flags of this object
			String stemp = sFlagName.toLowerCase();
			if(stemp.equals("removezhtml")){
				bFunction = bFlagRemoveTagZHTML;
				break main;
			}
		}//end main:
		return bFunction;
	}
}//end class KernelContentZZZ
