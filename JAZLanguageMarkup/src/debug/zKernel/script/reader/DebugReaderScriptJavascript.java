/*
 * Created on 21.11.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package debug.zKernel.script.reader;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.net.client.KernelReaderURLZZZ;

import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;



/**
 * @author Lindhauer
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DebugReaderScriptJavascript {

	public static void main(String[] args) {	
		
		KernelZZZ objKernel=null;
		LogZZZ objLog=null;
	
		main:{	
			try{
		//Create a Kernel-Object
		objKernel = new KernelZZZ("FGL", "01","", "ZKernelConfigKernel_default.ini",(String)null);
		objLog = objKernel.getLogObject();
			
		//Create a URLReader - Object, here: pass the password and the user 
		KernelReaderURLZZZ objURLReader = new KernelReaderURLZZZ(objKernel, "http://fgl.homepage.t-online.de/pagIPLinkFGL.html", "", "", null, "");
			
		//connect to the url
		objURLReader.connect();
			
		//read the content
		objURLReader.loadURLContent(objURLReader.getURLConnection());
			
		ArrayList obj_alsURLContent = objURLReader.getURLContentArrayList();
		for(int iCount= 0; iCount <= obj_alsURLContent.size()-1;iCount++ ){
			System.out.println(obj_alsURLContent.get(iCount));	
		}
		System.out.println("end");
		
		}catch(ExceptionZZZ ez){
			if(objLog==null){
				ez.printStackTrace();
				System.out.println(ez.getDetailAllLast());
			}else {
				try {
					objLog.WriteLineDate(ez.getDetailAllLast());
				} catch (ExceptionZZZ e) {					
					e.printStackTrace();
					System.out.println(e.getDetailAllLast());
				}
			}
		}
	}//end main:
		
	}//end function main
}//end class
