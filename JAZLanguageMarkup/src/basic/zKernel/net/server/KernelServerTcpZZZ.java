package basic.zKernel.net.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;

import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

public class KernelServerTcpZZZ extends KernelUseObjectZZZ implements Runnable{
	private String sPort="";	
	private String sHost = "";
	private short shPort=0;     //Der umgewandelte portname
	private InetAddress objInetAdress = null;                      //der umgewandelte hostname
	private ServerSocket objSockServer=null;                               //der in start() erstellte ServerSocket
	private Socket objSockCur=null;										//der in start() erstellte client-socket. Basiert aus this.objSockSserver.accept()
	
	private boolean bFlagIsConnected = false;
	private boolean bFlagIsStarted = false;
	private boolean bFlagIsTimeout = false;
	private boolean bFlagHasError = false;
		
	public static final int BACKLOG = 20;    //Die Anzahl der Verbindungen, die gleichzeitig verarbeitet werden k�nnen, bevor der Server die Meldung "ausgelastet" zur�ckgibt.
	public static final int iTIMEOUT_CONNECTED = 60000;  //Die Zeit, welche f�r einen Timeout eingestellt wird.
	private int iTimeout=-1; //d.h. bei -1 wird der defaultwert genommen. Zeit in Millisekunden. Gesetzt mit setTimeout(..). 
	
	
	public KernelServerTcpZZZ(IKernelZZZ objKernel, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel);
		KernelServerTcpNewFlag_(saFlagControl);
		//Bei diesem Konstruktor ist man darauf angewiesen, dass der Host und der Port ggf. gesetzt werden.
	}
	
	public KernelServerTcpZZZ(IKernelZZZ objKernel, String sHost, String sPort, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel);
		boolean btemp = KernelServerTcpNewFlag_(saFlagControl);
		if(btemp==true){
			KernelServerTcpNew_(sHost, sPort);
		}
	}
	
	private boolean KernelServerTcpNewFlag_(String[] saFlagControl) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
						
				if(saFlagControl != null){
					String stemp; 	boolean btemp;
					for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
						stemp = saFlagControl[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){ 								   
							   ExceptionZZZ ez = new ExceptionZZZ(stemp, IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 							   
							   throw ez;		 
						}
					}
					if(this.getFlag("INIT")==true){
						bReturn = false;
						break main; 
					}
				}
				bReturn = true;			
		}//End main
		return bReturn;
	}
	
	private void KernelServerTcpNew_(String sHost, String sPort) throws ExceptionZZZ{
		try{
			main:{
				check:{
					this.setHost(sHost);
					
					this.setPort(sPort);
					
				}//END check
			}//END main
		}finally{
			
		}
	}
	
public boolean start(){
	boolean bReturn = false;
	
	//Socket clientSock=null;	
	try{
		main:{
			check:{
				//Ggf. ist es notwendig vorher zu pr�fen, ob irgendwelche Randbedingungen (z.B. ist die Netzwerkverbindung hergestellt erf�llt sind.
				boolean btemp = this.customQueryProcess();
				if(btemp == false){
					String stemp = "(" + this.sHost +":"+this.sPort+")";
					ExceptionZZZ ez = new ExceptionZZZ("customQueryProcess returns false. " + stemp, iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else{
					this.getLogObject().WriteLineDate("customQueryProcess was successfully ended. #" + ReflectCodeZZZ.getMethodCurrentName());
				}
			}//END check:
	
			try{
				this.objSockServer = new ServerSocket(this.getPort(), BACKLOG, this.getHost());		
				
				//TODO Dies Konfigurierbar machen, so dass eine Klasse, die hiervon erbt individuell die TimeoutZeit einstellen kann (falls �berhaupt gew�nscht)
				int iTimeout = this.getTimeout();
				if(iTimeout >= 1){
					this.objSockServer.setSoTimeout(iTimeout); //60000 Millisekunden, d.h. jede Minute erwarte ich eine Connection.
				}
				
				
				this.setFlag("isstarted", true);
				
				//Merke: Dies ist eine Endlosschleife. Mit telnet kann man sich immer wieder verbinden.
				//TODO GOON: Wie erreicht man, dass nach einer erfolgreichen Verbindung, alle x sekunden wieder eine erfolgreiche Verbindung erwartet wird.
				//                    Falls dann keine Verbindung zustande kommt, wird die connection auf den Status "unterbrochen" gesetzt.
				/*
				while((clientSock = this.objSock.accept()) != null){
					this.setFlag("isconnected", true);
					customProcess();
				}
				*/
				do{
				do{
					try{
						this.objSockCur = this.getServerSocketObject().accept();
					}catch(java.net.SocketTimeoutException e){
						//Nun gibt es einen Timeout
						if(this.getFlag("isconnected")==true) {
							this.setFlag("isconnected", false);
							this.setFlag("istimeout", true);
							///? das haserrror flag ebenfalls auf false setzen ???
							customProcessOnTimeoutConnected();
							if(this.getSocketObjectCurrent()!=null) this.getSocketObjectCurrent().close(); //Sonst muessen vom Client immer wieder neue Ports zur Verfuegung gestellt werden.
						}else{
							customProcessOnTimeout();
							if(this.getSocketObjectCurrent()!=null) this.getSocketObjectCurrent().close();  //Sonst muessen vom Client immer wieder neue Ports zur Verfuegung gestellt werden.
						}						
						break; //Damit die innere do while schleife verlassen, es ist ein timeout-fall.
					}
					this.setFlag("istimeout", false);
					this.setFlag("isconnected", true);
					this.setFlag("HasError", false);
					customProcess();
					if(this.getSocketObjectCurrent()!=null) this.getSocketObjectCurrent().close();  //Sonst m�ssen vom Client immer wieder neue Ports zur Verf�gung gestellt werden.
					//TODO GOON Warum werden soviele Ports beim Client "verbraucht"
					//Danach wird die Verbindung zum Pingenden Client abgebrochen  !!! Darum nicht tun. if(this.getServerSocketObject()!=null) this.getServerSocketObject().close();      //Sonst m�ssen vom Client immer wieder neue Ports zur Verf�gung gestellt werden.
				}while(this.objSockCur!=null);
				}while(true); //Endlasschleife, die auch im timeoutfall bewirkt, dass der server weiter l�uft.
			}catch(IOException e){
				String stemp = "(" + this.sHost +":"+this.sPort+")";
				ExceptionZZZ ez = new ExceptionZZZ("IOException thrown. " + stemp + ": " + e.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
				//e.printStackTrace();
			}finally{
				if(this.getServerSocketObject()!=null){
					try {
						this.getServerSocketObject().close();
						if(this.getSocketObjectCurrent()!=null) this.getSocketObjectCurrent().close();
					} catch (IOException e) {
						//Da dies im finally ausgef�hrt wird, erwarte ich keinen Fehler, der durch .accept() ausgel�st werden k�nnte.
						e.printStackTrace();
						this.setFlag("HasError", true);
					}		
					bReturn = true; /*wird also nur return zur�ckgebgeben, wenn ein ServerSocket mal erfolgreich initialisiert worden ist */
				}
				this.getLogObject().WriteLineDate("listener ended. #" + ReflectCodeZZZ.getMethodCurrentName());
			}						

		}//END main
	}catch(ExceptionZZZ ez){
		try {
			this.getLogObject().WriteLineDate(ez.getDetailAllLast());
			System.out.println(ez.getDetailAllLast());
			this.setFlag("HasError", true);
		} catch (ExceptionZZZ e) {	
			System.out.println(ez.getDetailAllLast());
			e.printStackTrace();
		}
	}finally{
		if(this.getSocketObjectCurrent()!=null){
			try {
				this.getSocketObjectCurrent().close();
			} catch (IOException e){
				e.printStackTrace();
				try {
					this.setFlag("HasError", true);
				} catch (ExceptionZZZ e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	return bReturn;
}

/**This method does the processing.
 *  It is called in the run/start method when a client has successfully connected to this server.
 *  Overwerite this method when you extend a custom-class with this Kernel-class.
 *	 @return boolean 
 *  @throws ExceptionZZZ, 
 *
 * javadoc created by: 0823, 07.08.2006 - 08:47:56
 */
public boolean customProcess() throws ExceptionZZZ{
	System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# Accepted from server " + this.objSockServer.getInetAddress() + " by client " + this.objSockCur.getRemoteSocketAddress());
	return true;
}

public boolean customProcessOnTimeout() throws ExceptionZZZ{
	System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# timeout when listening for a client to connect " + this.objSockServer.getInetAddress());
	return true;
}

public boolean customProcessOnTimeoutConnected() throws ExceptionZZZ{
	System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# timeout when listening to a connected client " + this.objSockServer.getInetAddress());
	return true;
}

/**This method is called before the process-method (in the method "start()")
 * if this method returns false then process() will not be started.
 * Overwerite this method when you extend a custom-class with this Kernel-class.
 * @return boolean
 * @throws ExceptionZZZ
 * 
 * javadoc created by: 0823, 08.08.2006 - 09:09:19
 */
public boolean customQueryProcess() throws ExceptionZZZ{
	System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# QueryProcess is accepted.");
	return true;
}

public void run() {
	this.start();
}


//### GETTER / SETTER
public ServerSocket getServerSocketObject(){
	return this.objSockServer;
}

/**This object will be filled by ServerSocket.accept()
 * @return Socket
 *
 * javadoc created by: 0823, 09.08.2006 - 08:06:33
 */
public Socket getSocketObjectCurrent(){
	return this.objSockCur;
}

public String getPortString(){
	return this.sPort;
}
public short getPort(){
	return this.shPort;
}
public void setPort(String sPort) throws ExceptionZZZ{	
	if(StringZZZ.isEmpty(sPort)){
		   ExceptionZZZ ez = new ExceptionZZZ("Port", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName()); 						   
		   throw ez;		 
	}
	this.sPort = sPort;
	
	//Der Portname muss nun noch in short umgewandelt werden
	int itemp = Integer.parseInt(sPort);
	this.shPort = (short) itemp;	
}



public String getHostString(){
	return this.sHost;
}
public InetAddress getHost(){
	return this.objInetAdress;
}
public void setHost(String sHost) throws ExceptionZZZ{
	try{
	if(StringZZZ.isEmpty(sHost)){
		   ExceptionZZZ ez = new ExceptionZZZ("Host", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName()); 						  
		   throw ez;		 
	}
	this.sHost = sHost;
	this.objInetAdress = InetAddress.getByName(sHost);  //Damit wird der Socket im Konstruktor an eine bestimmte Adresse gebunden.
	}catch(UnknownHostException e){
		ExceptionZZZ ez = new ExceptionZZZ("Host - throws UnknownHostException", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
	}
}

public int getTimeout(){
	int  iReturn = 0;
	if(this.iTimeout<=-1){
		iReturn = iTIMEOUT_CONNECTED;
	}else{
		iReturn = iTimeout;
	}
	return iReturn;
}

public void setTimeout(int iTimeOut){
	this.iTimeout = iTimeout;
}




//######### GetFlags - Handled ##############################################
/** (non-Javadoc)
@see zzzKernel.basic.KernelObjectZZZ#getFlag(java.lang.String)
Flags used:<CR>
-  isConnected	
- haserror
 */
public boolean getFlag(String sFlagName){
	boolean bFunction = false;
	main:{
		if(StringZZZ.isEmpty(sFlagName)) break main;
		bFunction = super.getFlag(sFlagName);
		if(bFunction==true) break main;
	
		//getting the flags of this object
		String stemp = sFlagName.toLowerCase();
		if(stemp.equals("isstarted")){
			bFunction = bFlagIsStarted;
			break main;
		}else if(stemp.equals("isconnected")){
			bFunction = bFlagIsConnected;
			break main;
		}else if(stemp.equals("istimeout")){
			bFunction = this.bFlagIsTimeout;
			break main;
		}else if(stemp.equals("haserror")){				
			bFunction = bFlagHasError;
			break main;
		}
	}//end main:
	return bFunction;
}




/**
 * @see zzzKernel.basic.KernelUseObjectZZZ#setFlag(java.lang.String, boolean)
 * @param sFlagName
 * Flags used:<CR>
 * - isconnected
 * - haserror
 * @throws ExceptionZZZ 
 */
public boolean setFlag(String sFlagName, boolean bFlagValue) throws ExceptionZZZ{
	boolean bFunction = false;
	main:{
		if(StringZZZ.isEmpty(sFlagName)) break main;
		bFunction = super.setFlag(sFlagName, bFlagValue);
		if(bFunction==true) break main;

	//setting the flags of this object
	String stemp = sFlagName.toLowerCase();
	if(stemp.equals("isstarted")){
		bFlagIsStarted = bFlagValue;
		bFunction = true;
		break main;	
	}else if(stemp.equals("isconnected")){
		bFlagIsConnected = bFlagValue;
		bFunction = true;
		break main;	
	}else if(stemp.equals("istimeout")){
		bFlagIsTimeout = bFlagValue;
		bFunction = true;
		break main;
	}else if(stemp.equals("haserror")){
		bFlagHasError = bFlagValue;
		bFunction = true;
		break main;	
	}
	}//end main:
	return bFunction;
}



}//END class
