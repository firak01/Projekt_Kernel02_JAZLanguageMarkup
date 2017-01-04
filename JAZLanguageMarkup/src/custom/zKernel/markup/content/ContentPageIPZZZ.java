/*
 * Created on 09.01.2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package custom.zKernel.markup.content;

import org.apache.ecs.HtmlColor;
import org.apache.ecs.StringElement;
import org.apache.ecs.html.BR;
import org.apache.ecs.html.Font;
import org.apache.ecs.html.H1;
import org.apache.ecs.html.HR;

import basic.zKernel.KernelZZZ;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.markup.content.ContentEcsZZZ;

/**
 * customizable class, which determins the concrete design of a certain html-page
 * @author Lindhauer
 *
 */
public class ContentPageIPZZZ extends ContentEcsZZZ {

	/**
	 * @param objKernel
	 * @param objLog
	 * @param strings
	 */
	public ContentPageIPZZZ(KernelZZZ objKernel, String[] saFlagControl) throws ExceptionZZZ {
		super(objKernel);
		main:{
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
				if(this.getFlag("INIT")==true){	break main; 
				}
			}		
		}//end main:
	}//end constructor
	
	/* 
	 * this method overwrites the compute-Method. 
	 * Possible Variable-Placeholders can be replaced
	 * The ECS-Elements will be added to the internal HashMap.
	 * 
	 * @see basic.zzzKernel.markup.content.KernelContentZZZ#compute()
	 */
	public boolean compute() throws ExceptionZZZ{
		boolean bReturn=false;
		main:{
		
		try{
		//Baue eine einfache Seite, noch ohne Variablenersetzung
		H1 objECS_1 = new H1("externe IP-Adresse des Servers");
		this.addElement("Headerline", objECS_1);
		
		HR objECS_2 = new HR();
		this.addElement("HeaderlineUnderline", objECS_2);
		
		//IPDETAILS
		//ZEIT 	
		String sIPDate = this.getVarString("IPDate");
		String sIPTime = this.getVarString("IPTime");
		StringElement objECS_3b = new StringElement("Datum/Uhrzeit: " + sIPDate + " - " + sIPTime);
		//this.addElement("StringDateTime", objECS_3b);
		
		//org.apache.ecs.html.BR
		BR objECS_4 = new BR();
		//this.addElement("BRDateTime", objECS_4);
		
		//org.apache.ecs.StringElement
		String sIPNr = this.getVarString("IPNr");
		StringElement objECS_4a = new StringElement("IP-Nr: " + sIPNr);
		//this.addElement("IPString",objECS_4a);
		
		//Füge nun die Variable ein. Setze gleichzeitig die Schriftart, etc.				
		Font objECS_3a = new Font().setSize("-2").setColor(HtmlColor.RED);
		objECS_3a.addElement(objECS_3b);
		objECS_3a.addElement(objECS_4);
		objECS_3a.addElement(objECS_4a);
		this.addElement("IPDetail", objECS_3a);
	
		
		bReturn = true;
		}catch(ExceptionZZZ ez){
			this.objException=ez;
			throw ez;
		}
		}//end main:
		return bReturn;
	}
	

	
}//end class
