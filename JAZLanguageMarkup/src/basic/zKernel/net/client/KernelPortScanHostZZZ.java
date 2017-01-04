package basic.zKernel.net.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;

public class KernelPortScanHostZZZ  extends KernelUseObjectZZZ{
	private String sHost = null;
	private String sPortKnown = null; //Der Port wird für den 
	public final static String sPORT_KNOWN = "80";
	private ArrayList listaPortConnected = new ArrayList();
	private int iPortRangeLowest;
	private int iPortRangeHighest;
	private final int iTHREAD_STORAGE_SIZE = 20;
	private int iThreadStorageSize=0;
	private boolean bFlagUnknownHost=false;
	private boolean bFlagHasRuntimeError=false;
	
	public KernelPortScanHostZZZ(KernelZZZ objKernel, String sHost, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel);
		KernelPortScanHostNew_(sHost, saFlagControl);
	}
	
	private void KernelPortScanHostNew_(String sHost, String[] saFlagControl) throws ExceptionZZZ{
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
				if(this.getFlag("init")==true) break main;
			}
						
			this.sHost = sHost;
			}//END main:
	}
	
	/**Scanns the ports between the provideed portnumbers of the given host.
	 * The ports found will be filled from the started threads in an arraylist, which you can receive with .getPortConnected().
	 * This arraylist will be cleared on every scan.
	 * 
	 * If the scan finishes without an error, it returns true. This doesn´t mean, that there was any open port found !!!
	 * If the scan finishes with an unknownHost-Error, it will return false..
	 * @param iPortRangeLowestIn
	 * @param iPortRangeHighestIn
	 * @return
	 * @throws ExceptionZZZ, 
	 *
	 * @return boolean
	 *
	 * javadoc created by: 0823, 14.07.2006 - 15:31:23
	 */
	public boolean scan(int iPortRangeLowestIn, int iPortRangeHighestIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			check:{
				if(iPortRangeLowestIn <= 0){
					this.iPortRangeLowest = 1;
				}else{
					this.iPortRangeLowest = iPortRangeLowestIn;
				}
				if(iPortRangeHighestIn <= 0){
					this.iPortRangeHighest = 1;
				}else{
					this.iPortRangeHighest = iPortRangeHighestIn;
				}
				if(this.iPortRangeHighest < this.iPortRangeLowest){
					ExceptionZZZ ez = new ExceptionZZZ("expected higher port > lower port", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				//Vor den Scans das durchführen. Merke: Während des Scans wird das auch immer wieder überprüft.
				boolean btemp = KernelPingHostZZZ.isHostKnown(this.sHost, this.getPortKnown());
				if(btemp==false){
					this.setFlag("IsUnknownHost", true);
					ExceptionZZZ ez = new ExceptionZZZ("The host is not known: '"+sHost+"'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//END check:
		//Damit wird ein vorheriger Port-Scan wieder "gelöscht"
		this.listaPortConnected.clear();

		int iStorageSize = this.getThreadStorageSize();
		Thread[] tha = new Thread[iStorageSize];
		PortScanRunnerZZZ[] objaRunner = new PortScanRunnerZZZ[iStorageSize];
		for (int iPort=this.getPortRangeLowest(); iPort<this.getPortRangeHighest();iPort++){
			PortScanRunnerZZZ objRunner =  new  PortScanRunnerZZZ(objKernel, this, iPort, null);
			Thread th = new Thread(objRunner);		
			
			//Nun einen freien Thread-platz finden
			boolean bStored = false;
			do{				
				for(int icount = 0; icount <= iStorageSize-1; icount ++){
					Thread thtemp = tha[icount];
					if(thtemp==null){
						tha[icount]=th;
						objaRunner[icount]=objRunner;
						bStored = true;
						break; //For Schleife verlassen
					}else if(thtemp.isAlive()==false){
						tha[icount] = th;
						objaRunner[icount]=objRunner;
						bStored = true;
						break; //For schleife verlassen
					}				
				}//END for
				try{
					if(bStored==false) {
						//System.out.println("Waiting for finished runner thread. Scanning port: "+iPort);
						Thread.sleep(200);
					}else{
						Thread.sleep(10);   //Damit schläft DIESER Thread und nicht th
					}					
				}catch(InterruptedException ex){					
				}				
			}while(bStored==false);
			th.start();  //Der Thread füllt die Arraylist der Portscans
			try{
				Thread.sleep(1);
			}catch(InterruptedException ex){					
			}		
		
			//Die Runner nun nach Fehlern durchsuchen
			for(int icount = 0; icount <= iStorageSize-1; icount ++){
				objRunner =  objaRunner[icount];
				if (objRunner!=null){
					if(objRunner.getFlag("IsUnknownHost")){
						this.setFlag("IsUnknownHost", true);
						this.setFlag("HasRuntimeError", true); //Anders als ein UnknownHost-Fehler beim Check des Scanns.
						break main;
					}else{
						break; // es reicht ein runner-objekt auf den "UnknownHost" Fehler zu prüfen.
					}
				}
			}//END for
			
		}//END for
			//System.out.println("End of port scan.");
		bReturn = true;
		}//END main:
	return bReturn;
	}
	
	public synchronized void addPortConnected(Integer intPort){
		this.listaPortConnected.add(intPort);
	}
	
	
	


	//###### FLAGS
	/**
	 * @see zzzKernel.basic.KernelUseObjectZZZ#setFlag(java.lang.String, boolean)
	 * @param sFlagName
	 * Flags used:<CR>
	 	- isUnknownHost
	 */
	public boolean getFlag(String sFlagName){
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.getFlag(sFlagName);
			if(bFunction==true) break main;
			
			//getting the flags of this object
			String stemp = sFlagName.toLowerCase();
			if(stemp.equals("isunknownhost")){
				bFunction = bFlagUnknownHost;
				break main;
			}else if(stemp.equals("hasruntimeerror")){
				bFunction = bFlagHasRuntimeError;
				break main;
			}
			/*
			 * else if(stemp.equals("hostunknown")){
				bFunction = bFlagHostUnknown;
				break main;
			}else if(stemp.equals("stoprequested")){
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
	 	- isUnknownHost
	 */
	public boolean setFlag(String sFlagName, boolean bFlagValue){
		boolean bFunction = false; 
		main:{			
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.setFlag(sFlagName, bFlagValue);
			if(bFunction==true) break main;
		

		//setting the flags of this object
		String stemp = sFlagName.toLowerCase();
		if(stemp.equals("isunknownhost")){
			bFlagUnknownHost = bFlagValue;
			bFunction = true;
			break main;
		}else if(stemp.equals("hasruntimeerror")){
			bFlagHasRuntimeError = bFlagValue;
			bFunction = true;
			break main;
		}
		/*
		else if(stemp.equals("isconnected")){
			bFlagIsConnected = bFlagValue;
			bFunction = true;
			break main;
		}else if(stemp.equals("hostunknown")){
			bFlagHostUnknown = bFlagValue;
			bFunction = true;
			break main;
		}
		*/

		}//end main:
		return bFunction;
	}
	
	//###### GETTER / SETTER
	public String getHost(){
		return this.sHost;
	}
	
	public int getPortRangeLowest(){
		return this.iPortRangeLowest;
	}
	public int getPortRangeHighest(){
		return this.iPortRangeHighest;
	}
	public ArrayList getPortConnected(){
		return this.listaPortConnected;
	}
	
	/**This getter is used for the check if a host is known, which depends on an open port. 
	 * @return String
	 *
	 * javadoc created by: 0823, 08.08.2006 - 11:25:22
	 */
	public String getPortKnown(){
		String sReturn="";
		if(StringZZZ.isEmpty(this.sPortKnown)){
			sReturn = sPORT_KNOWN;
		}else{
			sReturn = this.sPortKnown;
		}
		return sReturn;
	}
	public void setPortKnown(String sPort){
		this.sPortKnown = sPort;
	}
	
	public int getThreadStorageSize(){
		if(this.iThreadStorageSize<=0){
			return this.iTHREAD_STORAGE_SIZE;			
		}else{
			return this.iThreadStorageSize;
		}
	}
	/**The "thread storage size" are the number of threads running concurrent scanning the ports.  
	 * If not set, a default value will used. 
	 * Remark: The default value intends to be very secure (20)
	 *              So, for increasing the performance a "thread storage size" e.g. of 200 is recommended.
	 * @param i, 
	 * @return void
	 *
	 * javadoc created by: 0823, 14.07.2006 - 14:41:12
	 */
	public void setThreadStorageSize(int i){
		this.iThreadStorageSize = i;
	}
	
}//END class
