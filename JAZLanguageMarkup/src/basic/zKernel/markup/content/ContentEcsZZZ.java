package basic.zKernel.markup.content;

import java.util.ArrayList;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelZZZ;

public abstract class ContentEcsZZZ extends KernelContentZZZ implements IKernelContentEcsZZZ, IKernelContentComputableZZZ {
	private HashMap hmECS=new HashMap(); //Speicherung der ECS-Elemente: hmECS("Alias")= new H1(hmVar("UE1");
	private ArrayList alsECSIndex = new ArrayList(); //Speicherung der Reihenfolge der ECS-Elemente: alsECSIndex(i)="Alias"

	public ContentEcsZZZ(KernelZZZ objKernel) {
		super(objKernel);
	}

	abstract public boolean compute() throws ExceptionZZZ;

	
	/**
	 * this method adds an ECS-Element to the internal Hashmap 
	 * @return
	 */
	public boolean addElement(String sAlias, Object objECS) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sAlias)){				
			   ExceptionZZZ ez = new ExceptionZZZ("VariableName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 			  
			   throw ez;	
			}
			if(objECS==null){
				ExceptionZZZ ez = new ExceptionZZZ("ECS object to add. Used by the 'Variablename=" + sAlias + "'");
				throw ez;
			}
			this.hmECS.put(sAlias, objECS);
			this.alsECSIndex.add(sAlias);
		}//end main	
		return bReturn;
	}
	
	/**
	 * @return int, the size of the internal ArrayList, which contains the index/Alias - Mapping; -1 if the ArrayList is null;
	 */
	public int NumberOfElement(){
		int iReturn=-1;
		main:{
			if(this.alsECSIndex!=null){
				iReturn=this.alsECSIndex.size();	
			}
		}
		return iReturn;
	}
	
	/**
	 * @param iIndex
	 * @return String, the alias name, stored in the internal index ArrayList at the spezified position. Null,if the ArrayList is null; EmptyString if the Index is > Arraylist.size()
	 */
	public String getAliasByIndex(int iIndex) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(this.alsECSIndex==null) break main;
			if(iIndex<=-1){
				//Integer intIndex=new Integer(iIndex);
				String stemp=new String("'Indexposition'="+Integer.toString(iIndex));
				ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());				
				throw ez;	 
			}
			if(iIndex>this.alsECSIndex.size()){
				sReturn="";
				break main;
			}
			
			//normalfall
			sReturn=(String)this.alsECSIndex.get(iIndex);	
		}
		return sReturn;
	}
	
	
	/**
	 * @return, HashMap used to store the ECS-Objects
	 */
	public HashMap getElementList(){
		return this.hmECS;
	}
}//end class
