package debug.zKernel.net.server;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.net.server.ovpn01alt.ServerConfigStarterOVPN;
import basic.zKernel.net.server.ovpn01alt.ServerConnectionListenerZZZ_in_OVPN_ersetzt_ggfs_woanders_nutzen;
import basic.zKernel.net.server.ovpn01alt.ServerMainOVPN;
import custom.zKernel.LogZZZ;

public class DebugServerConnectionListenerZZZ {
	/** TODO What the method does.
	 * @return void
	 * @param args 
	 *  
	 * lindhaueradmin; 13.07.2006 10:05:07
	 */
	public static void main(String[] args) {
		main:{
			IKernelZZZ objKernel = null;		
			ServerConfigStarterOVPN objConfig = null;
			ServerConnectionListenerZZZ_in_OVPN_ersetzt_ggfs_woanders_nutzen objListener = null;
			File objFile = null;
			try {
				objKernel = new KernelZZZ("OVPN", "01", "",  "ZKernelConfig_OVPNServer.ini", (String)null);
				objKernel.getLogObject().WriteLineDate("TEST");
				
				ServerMainOVPN objServer = new ServerMainOVPN(objKernel, null);
				 
				//!!! Damit die lokale VPN-IP-Adresse aufgel�st werden kann, muss der entsprehcende Netzwerkadapter "verbunden" sein.
				objFile = new File("C:\\Programme\\OpenVPN\\config\\client_TCP_443.ovpn");
				//objFile = new File("C:\\Programme\\OpenVPN\\config\\serverVPN1_TCP_443.ovpn");				
				objConfig = new ServerConfigStarterOVPN(objKernel, objServer, objFile, null);
								
				objListener = new ServerConnectionListenerZZZ_in_OVPN_ersetzt_ggfs_woanders_nutzen(objKernel, objConfig, null);
				objListener.start();
			
			} catch (ExceptionZZZ ez) {
				if(objKernel!=null){
					LogZZZ objLog = objKernel.getLogObject();
					if(objLog!=null){
						objLog.WriteLineDate(ez.getDetailAllLast());
					}else{
						ez.printStackTrace();
					}				
				}else{
					ez.printStackTrace();
				}
			}//END Catch
			}//END main:
			System.out.println("finished everything");
	}//END main()
}
