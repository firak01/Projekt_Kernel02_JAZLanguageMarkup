/*
 * Created on 26.11.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package debug.zKernel.script.reader;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.net.client.KernelReaderPageZZZ;
import basic.zKernel.net.client.KernelReaderURLZZZ;
import basic.zKernel.script.data.KernelScriptVariableZZZ;
import basic.zKernel.script.reader.KernelReaderScriptJavascriptZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;

/**
 * @author Lindhauer
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ReceiverJavascriptVarValueZZZ {

	public static void main(String[] args) {
		main:{

		KernelZZZ objKernel=null;
		LogZZZ objLog=null;
	
			try{
		
			//Create a Kernel-Object
			objKernel = new KernelZZZ("FGL", "01","", "ZKernelConfigKernel_default.ini",(String)null);
			objLog = objKernel.getLogObject();
			
			//Create a URLReader - Object, here: pass the password and the user 
			KernelReaderURLZZZ objReaderURL = new KernelReaderURLZZZ(objKernel, "http://192.168.3.253/doc/de/home.htm", "admin", "1fgl2fgl", null, "");
			KernelReaderPageZZZ objReaderPage = objReaderURL.getReaderPage();
			KernelReaderScriptJavascriptZZZ objReaderJS = objReaderPage.getReaderJavascript();
			
			//load the javascript-content
			objReaderJS.loadScriptContent();
			
			//get the variable
			KernelScriptVariableZZZ objVar=null;
			for(int iCount= 0; iCount <= objReaderJS.getScriptContent().size()-1;iCount++ ){
							
							//TODO: Funktion schreiben 'LineHasVariable'
							String sLine = (String)objReaderJS.getScriptContent().get(iCount);
							System.out.print(sLine);
							// Noch notwendig ??? boolean bFlagTagScriptStartFound = objReaderJS.getREScriptStart().match(sLine); 
							
							//nun die Variablendetails ermitteln
							objVar = objReaderJS.VarDetailParse(sLine); 
							System.out.println();
															 
				//TODO Function schreiben 'addVariableValue'						
//				TODO Function schreiben 'addVariable'
				if(objVar!=null){
					 objReaderJS.getVariableMap().put(objVar.getName(), objVar);
				}								
							
			}
			
			//Zuletzt den Wert einer Variablen auslesen
			String sVarToRead = "st_wan_ip";
			KernelScriptVariableZZZ objTemp=objReaderJS.getVarObjectByName(sVarToRead);
			if(objTemp==null){
				System.out.println("Variable  "+sVarToRead+" wurde nicht deklariert");
			}else{
				System.out.println("Variable "+sVarToRead+" hat den Wert: "+objTemp.getValuePure());
			}
			
			//Lan IP auslesen (das geht zwar auch anders, aber hier mal aus dem Router auslesen)
			sVarToRead = "st_lan_ip";
			objTemp=objReaderJS.getVarObjectByName(sVarToRead);
			if(objTemp==null){
				System.out.println("Variable  "+sVarToRead+" wurde nicht deklariert");
			}else{
				System.out.println("Variable "+sVarToRead+" hat den Wert: "+objTemp.getValuePure());
			}
			
			
			//....
			
			
			System.out.println("end");
			}catch(ExceptionZZZ ez){
				if(objLog==null){
					ez.printStackTrace();
					System.out.println(ez.getDetailAllLast());
				}else {
					try {
						objLog.writeLineDate(ez.getDetailAllLast());
					} catch (ExceptionZZZ e) {						
						e.printStackTrace();
						System.out.println(e.getDetailAllLast());
					}
				}
			}
		}//end main	
		
	}
}
