package basic.zKernel.net.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.nio.channels.UnsupportedAddressTypeException;

import org.apache.commons.lang.time.StopWatch;

import basic.zKernel.IKernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

/**Use this class to check a host on a certain port.
 * 
 * 
 * Remark In J2SE 1.5 there can be used InetAddress.isReachable(..), which
 * tests whether that address is reachable. Best effort is made by the implementation to try to reach the host, but firewalls and server configuration may block requests resulting in a unreachable status while some specific ports may be accessible. A typical implementation will use ICMP ECHO REQUESTs if the privilege can be obtained, otherwise it will try to establish a TCP connection on port 7 (Echo) of the destination host.
 * @author 0823
 *
 */
public class KernelPingHostZZZ extends KernelUseObjectZZZ{
	public final static String sPORT2CHECK = "80";
	private String sPort2Check = "";
	private long lMilliSeconds = 0; //Die Anzahl der Millisekunden, die es ben�tigt, einen ping durchzuf�hren.
	
	public KernelPingHostZZZ(IKernelZZZ objKernel, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel);
		KernelPingHostNew_(saFlagControl);
	}
	
	private void KernelPingHostNew_(String[] saFlagControl) throws ExceptionZZZ{
		
		main:{		
		if(saFlagControl != null){
			String stemp; boolean btemp;
			for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
				stemp = saFlagControl[iCount];
				btemp = setFlag(stemp, true);
				if(btemp==false){ 								   
					   ExceptionZZZ ez = new ExceptionZZZ( stemp, IFlagUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 					  
					   throw ez;		 
				}
			}
			if(this.getFlag("init")==true) break main;
		}
		
		}//END main:
	
	}
	
	/**Checks if a socket can be created.	
	 * @param sHost
	 *
	 * @return boolean
	 *
	 * javadoc created by: 0823, 08.08.2006 - 11:08:37
	 */
	public static boolean isHostKnown(String sHost, String sPort){
		return isHostKnown_(sHost, sPort);
	}
	
	/**Checks if a socket can be created.
	 * Uses port 80 as default port.
	 * @param sHost
	 *
	 * @return boolean
	 *
	 * javadoc created by: 0823, 08.08.2006 - 11:08:37
	 */
	public boolean isHostKnown(String sHost){
		return isHostKnown_(sHost, this.getPort2Check());
	}
	
	private static boolean isHostKnown_(String sHost, String sPort){
		boolean bReturn = false;
		main:{
			Socket target = null;
			try{
				check:{
					if(StringZZZ.isEmpty(sHost)) break main;
					if(StringZZZ.isEmpty(sPort)) break main;
				}//End check
			
		int iPort = Integer.parseInt(sPort);
			
		 target = new Socket(sHost, iPort);
		//System.err.println("Connected to " + sHost + " on Port " + iPort);
		bReturn = true;
		target.close();
		} catch (UnknownHostException e) {
			bReturn = false;
			if (target != null)
				try {
					target.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
				
			//Weitere Fehler treten "erwartet" auf, wenn der scan fehlschl�gt.	Sie werden auch nicht weiter verfolgt.
			//zu  beachten: Das weitere target.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();			
			if (target != null)
				try {
					target.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
		}
		}//End man:
		return bReturn;
	}
	
	
	public boolean ping(String sHost, String sPort) throws ExceptionZZZ{
		boolean bReturn = false; //Merke: Im Fehlerfall wird nix ausser der Exception zurückgegeben.
		main:{
			int iPort;
			StopWatch objStopWatch = null;
			try {
			check:{
				if(StringZZZ.isEmpty(sHost)){
					   ExceptionZZZ ez = new ExceptionZZZ(  "Hostname", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 					 
					   throw ez;		 			
				}
				
				if(StringZZZ.isEmpty(sPort)){
					   ExceptionZZZ ez = new ExceptionZZZ( "Port", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 					 
					   throw ez;		 				
				}else{
					try{
					iPort =  Integer.parseInt(sPort);
					}catch(NumberFormatException e){
						ExceptionZZZ ez = new ExceptionZZZ("Port is not a number: '" + sPort + "'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}
				
			}//END check:
		
		//InternetAdresse daraus machen.
			InetAddress objAddress = InetAddress.getByName(sHost);
			
			//AB 1.4 gibt es dann diese Klasse, die den R�ckagabewert (die 4 IP-Adressen-Bestandteile) einfacher handelt.
			InetSocketAddress objAddressSocket = new InetSocketAddress(objAddress, iPort);
			
		    //Nun die Zeit messen mit den jakarta-commons
		    objStopWatch = new StopWatch();
		    objStopWatch.start();
			
			//Nun einen Socket-Chanel �ffen					
			SocketChannel objSC = null;
			objSC = SocketChannel.open();
			objSC.configureBlocking(true); //Daraufhin wird .connect true zur�ckliefern. Ansonsten muss man warten.
		    bReturn = objSC.connect(objAddressSocket);  //Das f�hrt noch keine endg�ltige connection durch !!!
		    
		    bReturn = objSC.finishConnect(); //ggf. sollte man das Warten in einem anderen Thread durchf�hren
		  
		    objStopWatch.stop();
		    long ltemp =objStopWatch.getTime();
		    //System.out.println("It took: " + ltemp + " milliseconds");
		    this.setRequestTime(ltemp);
		    
			}catch(AlreadyConnectedException e){
				objStopWatch.stop();				   
				this.setRequestTime( objStopWatch.getTime());
				ExceptionZZZ ez = new ExceptionZZZ("Already connected to host: '"+sHost + " - " + sPort + "' (" + e.getMessage() + ")", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}catch(ConnectionPendingException e){
				objStopWatch.stop();				   
				this.setRequestTime( objStopWatch.getTime());
				ExceptionZZZ ez = new ExceptionZZZ("ConnectionPendingException: '"+sHost + " - " + sPort + "' (" + e.getMessage() + ")", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}catch(ClosedByInterruptException e){
				bReturn = false;
				objStopWatch.stop();				   
				this.setRequestTime( objStopWatch.getTime());
				ExceptionZZZ ez = new ExceptionZZZ("ClosedByInterruptException: '"+sHost + " - " + sPort + "' (" + e.getMessage() + ")", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}catch(AsynchronousCloseException e){
				objStopWatch.stop();				   
				this.setRequestTime( objStopWatch.getTime());
				ExceptionZZZ ez = new ExceptionZZZ("AsynchronousCloseException: '"+sHost + " - " + sPort + "' (" + e.getMessage() + ")", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}catch(ClosedChannelException e){
				 objStopWatch.stop();				   
				 this.setRequestTime( objStopWatch.getTime());
				ExceptionZZZ ez = new ExceptionZZZ("ClosedChanelException: '"+sHost + " - " + sPort + "' (" + e.getMessage() + ")", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}catch(UnresolvedAddressException e){
				objStopWatch.stop();				   
				this.setRequestTime( objStopWatch.getTime());
				ExceptionZZZ ez = new ExceptionZZZ("UnresolvedAddressException: '"+sHost + " - " + sPort + "' (" + e.getMessage() + ")", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}catch(UnsupportedAddressTypeException e){
				objStopWatch.stop();				   
				this.setRequestTime( objStopWatch.getTime());
				ExceptionZZZ ez = new ExceptionZZZ("UnsupportedAddessTypeException: '"+sHost + " - " + sPort + "' (" + e.getMessage() + ")", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}catch(SecurityException e){
				objStopWatch.stop();				   
				this.setRequestTime( objStopWatch.getTime());
				ExceptionZZZ ez = new ExceptionZZZ("SecurityException: '"+sHost + " - " + sPort + "' (" + e.getMessage() + ")", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (UnknownHostException e) {
				objStopWatch.stop();				   
				this.setRequestTime( objStopWatch.getTime());
				ExceptionZZZ ez = new ExceptionZZZ("UnknownHostException: '"+ sHost + " - " + sPort + "' (" + e.getMessage() + ")", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}catch(IOException e){
				objStopWatch.stop();				   
				this.setRequestTime( objStopWatch.getTime());
				ExceptionZZZ ez = new ExceptionZZZ("IOException: '"+ sHost + " - " + sPort + "' (" + e.getMessage() + ")", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//END main:
		return bReturn;
	}
	
	//###  Getter / Setter
	private void setRequestTime(long lMilliSeconds){
		this.lMilliSeconds = lMilliSeconds; 
	}
	/**After a ping(...) was executed. 
	 * This Method can return the time in milliseconds needed to perform connection.
	 * @return long
	 *
	 * javadoc created by: 0823, 24.07.2006 - 15:40:36
	 */
	public long getRequestTime(){
		return this.lMilliSeconds;
	}
	
	public String getPort2Check(){
		String sReturn = this.sPort2Check;
		if(sReturn.equals("")) sReturn = sPORT2CHECK;
		return sReturn;
	}
	public void setPort2Check(String sPort){
		this.sPort2Check = sPort;
	}
	
	
	
}//END class
