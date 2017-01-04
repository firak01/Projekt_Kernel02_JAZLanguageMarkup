/*
 * Created on 23.11.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package debug.zKernel.net.client;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.net.client.KernelReaderPageZZZ;
import basic.zKernel.net.client.KernelReaderURLZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;


/**
 * @author Lindhauer
 *
 * This class receives the external IP-Address.
 * - for my spezial case there is a router in front of the client.
 * - therefore the IP-Address can only be found in a HTML-Document provided by the router.
 */
public class ExternalIPReceiverZZZ {

	public static void main(String[] args) {
		
		main:{

		KernelZZZ objKernel=null;
		LogZZZ objLog=null;
	
			try{
		
			//Create a Kernel-Object
			objKernel = new KernelZZZ("FGL-IP", "01","c:\\fglKernel\\KernelConfig", "ZKernelConfigKernel_default.ini",(String)null);
			objLog = objKernel.getLogObject();
			
			//Create a URLReader - Object, here: pass the password and the user 
			KernelReaderURLZZZ objReaderURL = new KernelReaderURLZZZ(objKernel, "http://192.168.3.253/doc/de/home.htm", "admin", "1fgl2fgl", null, "");
			KernelReaderPageZZZ objReaderPage = objReaderURL.getReaderPage();
			
			System.out.println("end");
			}catch(ExceptionZZZ ez){
				if(objLog!=null){
				objLog.WriteLineDate(ez.getDetailAllLast());
				break main;
				}else{
					System.out.println(ez.getDetailAllLast());
				}
			}
		}//end main	
	}//end function main
}//end class
