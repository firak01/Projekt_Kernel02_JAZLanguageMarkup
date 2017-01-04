package debug.zKernel.net.client;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.net.client.KernelPortScanHostZZZ;
import basic.zKernel.KernelZZZ;

public class DebugKernelPortScanHostZZZ {

	/**Debug und Entwicklung des Port Scanners
	 * @param args, 
	 *
	 * @return void
	 *
	 * javadoc created by: 0823, 14.07.2006 - 12:27:08
	 */
	public static void main(String[] args) {
		KernelPortScanHostZZZ objScan=null;
		try {
			KernelZZZ objKernel = new KernelZZZ("Scan", "01", "", "ZKernelConfigPortScan_test.ini",(String)null);
			String sHost = objKernel.getParameter("HostDefault");
			objScan = new KernelPortScanHostZZZ(objKernel, sHost, null);
			
			
			String sPortLowest = objKernel.getParameter("PortLowest");
			String sPortHighest = objKernel.getParameter("PortHighest");
			String sPortKnown = objKernel.getParameter("PortKnown");
			objScan.setPortKnown(sPortKnown);
			
			//Zum testen der Verbindung mit port 5000
			sPortLowest = "5000";
			sPortHighest = "5000";
			
			
			int iPortLowest = Integer.parseInt(sPortLowest);
			int iPortHighest = Integer.parseInt(sPortHighest);
			
			String sThreadStorageSize = objKernel.getParameter("ThreadStorageSize");
			if(sThreadStorageSize!=null){
				int iTemp = Integer.parseInt(sThreadStorageSize);
				objScan.setThreadStorageSize(iTemp);
			}
			
			boolean btemp = objScan.scan(iPortLowest, iPortHighest);
			if(btemp==true){
				ArrayList listaPortConnected = objScan.getPortConnected();
				if(listaPortConnected.isEmpty()==true){
					System.out.println("No port found open on host: " + sHost);					
				}else{
					System.out.println( listaPortConnected.size() + " Ports found open on host: " + sHost);
					for(int icount=0; icount < listaPortConnected.size(); icount++){
						Integer intPort = (Integer) listaPortConnected.get(icount);
						System.out.println(intPort.toString());
					}
				}
			}else{
				
				//Es muss zur Laufzeit ein Fehler aufgetreten sein
				if(objScan.getFlag("IsUnknownHost")){
					System.out.println("Unknonw Host: " + sHost);
				}else{
					System.out.println("Unknown error.");
				}
			}
			
			
			
		} catch (ExceptionZZZ e) {
			if(objScan!=null){
				if(objScan.getFlag("IsUnknownHost")){
					System.out.println("The host is not known - Error catched.");
					System.exit(1);
				}
			}
			//Alle möglichen anderen Fehler
			e.printStackTrace();			
			System.out.println(e.getDetailAllLast());
		}
		
		
	}

}
