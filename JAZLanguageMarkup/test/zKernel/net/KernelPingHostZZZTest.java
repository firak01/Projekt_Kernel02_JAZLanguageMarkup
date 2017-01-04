package zKernel.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.net.client.KernelPingHostZZZ;
import basic.zKernel.xml.reader.DTDErrorZZZ;
import basic.zKernel.xml.reader.ParserXMLDOMZZZ;
import basic.zKernel.KernelContextZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;

public class KernelPingHostZZZTest extends TestCase {
	//+++ TestHost festlegen	 
	
	//Kernel und Log-Objekt
	private KernelZZZ objKernel;
	private LogZZZ objLog;
			
	/// +++ Die eigentlichen Test-Objekte
	// Parsen einer Datei
	private KernelPingHostZZZ  objPingTest;
	
	protected void setUp(){
		try {			
							
			//Kernel + Log - Object dem TestFixture hinzuf�gen. Siehe test.zzzKernel.KernelZZZTest
			//alt: objKernel = new KernelZZZ("Ping", "01", "", "ZKernelConfigPing_test.ini",(String)null);
			//ohne speziell konfiguriertes Log objKernel=new KernelZZZ("TEST", "01", "", "ZKernelConfigLanguageMarkup_test.ini", (String) null);
			//Weil auf Programmebene das Log definiert sein soll
			KernelContextZZZ objContext = new KernelContextZZZ(this.getClass());
			objKernel = new KernelZZZ("TEST", "01", "", "ZKernelConfigLanguageMarkup_test.ini", objContext, (String) null);
			
			//Das ist nun ein Log, das ggf. auf dem konfiguriertem LogPfaden des Programmkonfigurations-Abschnitts in der ini-Datei beruht.
			objLog = objKernel.getLogObject();
			
			
			//### Die TestObjecte						
			//+++The main object used for testing:
			objPingTest = new KernelPingHostZZZ(objKernel, null);
			
			
			//++ Proxy aufbauen'
			String sProxyHost = objKernel.getParameter("ProxyHost");
			if(StringZZZ.isEmpty(sProxyHost)){
				
			}else{
				String sProxyPort = objKernel.getParameter("ProxyPort");
				
				System.setProperty( "proxySet", "true" );
				System.setProperty( "proxyHost", sProxyHost);
				System.setProperty( "proxyPort", sProxyPort );
			}
			
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	
	}//END setup
	
	public void testContructor(){
		
		try{
		
				//+++An object just initialized
				String[] saFlag={"init"};
				KernelPingHostZZZ objPingInit = new KernelPingHostZZZ(objKernel, saFlag);
				boolean btemp = objPingInit.getFlag("init");
				assertTrue("Unexpected: The init flag was expected to be set", btemp);
		
				//+++ This is not correct when using the test object
				btemp = objPingTest.getFlag("init");
				assertFalse("Unexpected: The init flag was expected to NOT be set", btemp);
				
				
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}//END testConstructor
	
	public void testPing(){
		try{
			String sHost = "192.168.3.102";
			//String sHost = "10.0.0.1";
			String sPort = "80";
			
			boolean btemp = objPingTest.ping(sHost, sPort);
			assertTrue("Unable to connect to host: '" + sHost + " - " + sPort + "'. Is server running?", btemp);
	
			try{
			sPort = "1234"; //Der sollte nicht vorhanden sein
			btemp = objPingTest.ping(sHost, sPort);
			}catch(ExceptionZZZ ez){
				btemp = false;//Merke: Im Fehlerfall wird nix ausser der Exception zurückgegeben. Wir müssen die Variable also künstlich setzen				
				System.out.println("Time for exception (Milliseconds): " + objPingTest.getRequestTime());
				System.out.println("Expected failure: Method throws an exception." + ez.getMessageLast());
			}	
			assertFalse("Able to connect to host: '" + sHost + " - " + sPort + "'. This port shouldn´t be available on a running server!", btemp);

		}catch(ExceptionZZZ ez){
			System.out.println("Time for exception (Milliseconds): " + objPingTest.getRequestTime());
			fail("Method throws an exception." + ez.getMessageLast());
		}				
	}//END testPing()
	
	

}
