/*
 * Created on 09.01.2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package custom.zKernel.markup.content;

import org.apache.ecs.HtmlColor;
import org.apache.ecs.StringElement;
import org.apache.ecs.html.A;
import org.apache.ecs.html.BR;
import org.apache.ecs.html.Font;
import org.apache.ecs.html.H1;
import org.apache.ecs.html.HR;
import org.apache.ecs.html.Input;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
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
	public ContentPageIPZZZ(IKernelZZZ objKernel, String[] saFlagControl) throws ExceptionZZZ {
		super(objKernel);
		main:{
			if(saFlagControl != null){
				String stemp; boolean btemp; 
				for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
					stemp = saFlagControl[iCount];
					btemp = setFlag(stemp, true);
					if(btemp==false){ 								   
						   ExceptionZZZ ez = new ExceptionZZZ(stemp, IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
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
			
		//org.apache.ecs.StringElement
		String sIPNr = this.getVarString("IPNr");

		//StringElement objECS_4a = new StringElement("IP-Nr: " + sIPNr);
		//this.addElement("IPString",objECS_4a);
		
		//Nicht als String, sondern als Link. 
		//org.apache.ecs.html.A
		A objECS_4a = new A();
		objECS_4a.setHref("http://"+sIPNr);
		objECS_4a.setTagText(sIPNr);		
		
		//Zudem noch ein verborgenes Eingabefeld: Darin ist Intern das so wichtige IPNr-Attribut
		Input objECS_4b = new Input(Input.HIDDEN);
		objECS_4b.setName("IPNr");
		objECS_4b.setValue(sIPNr);
		
		//Zudem noch ein verborgenes Eingabefeld: Datum
		Input objECS_4c = new Input(Input.HIDDEN);
		objECS_4c.setName("IPDate");
		objECS_4c.setValue(sIPDate);
		
		//Zudem noch ein verborgenes Eingabefeld: Zeit
		Input objECS_4d = new Input(Input.HIDDEN);
		objECS_4d.setName("IPTime");
		objECS_4d.setValue(sIPTime);
		
		//Fuege nun die Variable ein. Setze gleichzeitig die Schriftart, etc.				
		Font objECS_3_1 = new Font().setSize("15px").setColor(HtmlColor.RED);
		objECS_3_1.addElement(objECS_3b);					
		this.addElement("IPMeta", objECS_3_1);	
		
		//org.apache.ecs.html.BR
		BR objECS_BR1a = new BR();	
		this.addElement("BR1a",objECS_BR1a);
		
		StringElement objECS_x1 = new StringElement("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		this.addElement("x1",objECS_x1);
		
		//org.apache.ecs.html.BR
		BR objECS_BR1b = new BR();
		this.addElement("BR1b",objECS_BR1b);
		
		
		
		Font objECS_3_2 = new Font().setSize("30px").setColor(HtmlColor.BLUE);
		objECS_3_2.addElement(objECS_4a);
		this.addElement("IPLink", objECS_3_2);
		
		//org.apache.ecs.html.BR
		BR objECS_BR2a = new BR();	
		this.addElement("BR2a",objECS_BR2a);
		
		StringElement objECS_x2 = new StringElement("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		this.addElement("x2",objECS_x2);
		
		//org.apache.ecs.html.BR
		BR objECS_BR2b = new BR();
		this.addElement("BR2b",objECS_BR2b);
		
		
		this.addElement("4b", objECS_4b);
		this.addElement("4c", objECS_4c);
		this.addElement("4d", objECS_4d);
	
		bReturn = true;
		}catch(ExceptionZZZ ez){
			this.objException=ez;
			throw ez;
		}
		}//end main:
		return bReturn;
	}
	

	
}//end class
