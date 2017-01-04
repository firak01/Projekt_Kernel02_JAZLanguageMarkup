package basic.zKernel.net.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelUseObjectZZZ;


/**This class is use by a thread to connect to the provided hostname and port.
 * Normaly a lot of threads are needed to scan an port range.
 * This runner will have only one chance.
 * Either he can connect or cannot.
 * @author 0823
 *
 */
public class PortScanRunnerZZZ extends KernelUseObjectZZZ implements Runnable {
	private int iPort;
	private KernelPortScanHostZZZ objPortScanner;
	
	private boolean bFlagIsConnected=false;
	private boolean bFlagHasError = false;
	private boolean bFlagHostUnknown = false;
	
	public PortScanRunnerZZZ(KernelZZZ objKernel, KernelPortScanHostZZZ objPortScanner, int iPort, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel);
		PortScanRunnerNew_(objPortScanner, iPort, saFlagControl);
	}
	private void PortScanRunnerNew_(KernelPortScanHostZZZ objPortScanner, int iPort, String[] saFlagControl) throws ExceptionZZZ{
		main:{
			
			if(saFlagControl != null){
				String stemp; boolean btemp;
				for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
					stemp = saFlagControl[iCount];
					btemp = setFlag(stemp, true);
					if(btemp==false){ 								   
						   ExceptionZZZ ez = new ExceptionZZZ( stemp, iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 						  
						   throw ez;		 
					}
				}
				if(this.getFlag("init")==true) break main;
			}

			this.objPortScanner = objPortScanner;
			this.iPort = iPort;
			
			}//END main:
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * FGL: Nur ein Versuch dei Verbindung aufzubauen. Danach Fehler oder Heuerka.
	 */
	public void run() {
		main:{
		Socket target = null;
		String sHost = null;
		try {
			sHost = this.getHost();
			if(sHost==null) break main;
			if(sHost.equals("")) break main;
			
		target = new Socket(sHost, iPort);		
		//System.err.println("Connected to " + sHost + " on Port " + iPort);
				
		target.close();
		this.setFlag("isConnected", true);
		//System.out.println("Connected to  "+ sHost + ":" + iPort);
		Integer intPort = new Integer(iPort); 
		this.objPortScanner.addPortConnected(intPort); //!!! diese Methode ist synchronsiert und darum minimal !!! Noc nicht einmal die Variablen Integer-Machung kommt da rein.
		
		
		
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: " + sHost);
			this.setFlag("IsUnknownHost", true);
			this.objPortScanner.setFlag("IsUnknownHost", true);
			if (target != null)
				try {
					target.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
				
			//Weitere Fehler treten "erwartet" auf, wenn der scan fehlschlägt.	Sie werden auch nicht weiter verfolgt.
			//zu  beachten: Das weitere target.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			this.setFlag("HasError", true);
			if (target != null)
				try {
					target.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
		}
		}//END main:
	}
	
	
	

	//###### FLAGS
	/* (non-Javadoc)
	@see zzzKernel.basic.KernelObjectZZZ#getFlag(java.lang.String)
	 * Flags used:<CR>
	 	- hasError
	- isconnected
	- HostUnknown
	 */
	public boolean getFlag(String sFlagName){
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.getFlag(sFlagName);
			if(bFunction==true) break main;
			
			//getting the flags of this object
			String stemp = sFlagName.toLowerCase();
			if(stemp.equals("haserror")){
				bFunction = bFlagHasError;
				break main;
			}else if(stemp.equals("isconnected")){
				bFunction = bFlagIsConnected;
				break main;
			}else if(stemp.equals("hostunknown")){
				bFunction = bFlagHostUnknown;
				break main;
			}
			/*
			else if(stemp.equals("stoprequested")){
				bFunction = bFlagStopRequested;
				break main;
			}*/
	
		}//end main:
		return bFunction;
	}

	/**
	 * @see zzzKernel.basic.KernelUseObjectZZZ#setFlag(java.lang.String, boolean)
	 * @param sFlagName
	 * Flags used:<CR>
	 	- hasError
	- isconnected
	- HostUnknown
	 */
	public boolean setFlag(String sFlagName, boolean bFlagValue){
		boolean bFunction = false;
		main:{			
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.setFlag(sFlagName, bFlagValue);
			if(bFunction==true) break main;
		
		//setting the flags of this object
		String stemp = sFlagName.toLowerCase();
		if(stemp.equals("haserror")){
			bFlagHasError = bFlagValue;
			bFunction = true;
			break main;

		}else if(stemp.equals("haserror")){
			bFlagHasError = bFlagValue;
			bFunction = true;
			break main;
		}else if(stemp.equals("isconnected")){
			bFlagIsConnected = bFlagValue;
			bFunction = true;
			break main;
		}else if(stemp.equals("isunknownhost")){
			bFlagHostUnknown = bFlagValue;
			bFunction = true;
			break main;
		}

		}//end main:
		return bFunction;
	}
	
	//###### GETTER / SETTER
	public String getHost(){
		String sHost = null;
		main:{
			check:{
			if(this.objPortScanner==null) break main;
			}
		sHost = this.objPortScanner.getHost();
		}
		return sHost;
	}
	public int getPort(){
		return this.iPort;
	}
}//END class
