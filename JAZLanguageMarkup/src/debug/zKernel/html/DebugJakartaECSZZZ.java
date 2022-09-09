/*
 * Created on 04.01.2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package debug.zKernel.html;

import java.io.BufferedOutputStream;
//import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.apache.ecs.html.A;
import org.apache.ecs.html.BR;
import org.apache.ecs.html.Body;
import org.apache.ecs.html.Form;
import org.apache.ecs.html.H1;
import org.apache.ecs.html.H3;
import org.apache.ecs.html.Head;
import org.apache.ecs.html.Html;
import org.apache.ecs.html.Input;
import org.apache.ecs.html.Title;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;


/**
 * @author Lindhauer
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DebugJakartaECSZZZ {

	public static void main(String[] args) {
		
		main:{
	      KernelZZZ objKernel=null;
	      LogZZZ objLog=null;
	      try{
		//Konfiguration-File
		objKernel = new KernelZZZ("FGL", "01","", "ZKernelConfigKernel_default.ini",(String)null);
		objLog = objKernel.getLogObject();
											
		//Hier der direkte Jakarta Code ohne eine Wrapper Klasse		
		 Html html = new Html();
		 Head head = new Head();
		 head.addElement(new Title("Log Report"));
		 html.addElement(head);
		 Body body = new Body();
		 body.addElement(new H1("Logs for application A"));
		 body.addElement(new H3("Generated on Mar 03rd, 2004"));
		 body.addElement("Following table lists down status of all testcases.");
		 
		 body.addElement(new BR());
		 
		 body.addElement(new A("http://www.tagesschau.de/", "Tagesschau-Link"));
		 
		 body.addElement(new BR());
		 
		 Form objForm = new Form("TESTFORM");
		 //Sichtbarer Text  Input objInput = new Input("Text","IPNr","192.168.3.101");
		 Input objInput = new Input("Hidden","IPNr","192.168.3.101");
		 objForm.addElement(objInput);
		 body.addElement(objForm);
		 html.addElement(body);
		 
		 //Ausgabe des HTML-Codes in eine Datei
		 //TODO Dies durch eine Z-Kernel-Wrapper-Klasse erledigen
		 File objFileConfig = objKernel.getFileConfigModuleOLDDIRECT("IPPage");
		 String sFile = objKernel.getParameterByProgramAlias(objFileConfig, "ProgPage", "TargetFile").getValue(); 
		 String sDir = objKernel.getParameterByProgramAlias(objFileConfig, "ProgPage", "TargetDirectory").getValue(); 
		 if(sDir==null||sDir.equals("")){
		 	ExceptionZZZ ez = new ExceptionZZZ(objKernel.sERROR_CONFIGURATION_MISSING+"'TargetDirectory' of Program 'ProgPage'", objKernel.iERROR_CONFIGURATION_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
		 	throw ez;
		 }
		 if(sFile==null||sFile.equals("")){
			 ExceptionZZZ ez = new ExceptionZZZ(objKernel.sERROR_CONFIGURATION_MISSING+"'TargetFile' of Program 'ProgPage'", objKernel.iERROR_CONFIGURATION_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
			 throw ez;
		 }
		 
		//20190712: Ziel ist es nun UTF-8 Datei zu erstellen
//		DataOutputStream output = null;
//  		output =  new DataOutputStream(new BufferedOutputStream(new FileOutputStream(sDir+File.separator+sFile)));
//  		String stemp = html.toString();
//  		System.out.println(stemp);
//  		output.write(html.toString().getBytes());
//  		output.close();//Ohne schliessen des Streams wird der Inhalt dort nicht eingef�gt.
  		
  		//20190712: Ziel ist es nun UTF-8 Datei zu erstellen
     	OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(sDir+File.separator+sFile),"UTF-8"); 
     	writer.write(html.toString());
     	writer.close();
     	
	      }catch(ExceptionZZZ ez){
	    	  if (objLog != null) objLog.WriteLineDate(ez.getDetailAllLast());
	    	  System.out.println(ez.getDetailAllLast());									
		} catch (Exception e) {
  			e.printStackTrace();
		}		
	}//end main:
}//end function main
}//End class
