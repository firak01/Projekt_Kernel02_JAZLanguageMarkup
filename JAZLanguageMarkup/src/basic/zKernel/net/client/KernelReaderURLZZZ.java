/*
 * Created on 23.11.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zKernel.net.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;

import basic.zKernel.IKernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.web.cgi.UrlLogicZZZ;
import basic.zKernel.KernelUseObjectZZZ;


/**
 * @author Lindhauer
 *
 * Class handles everything related to URL-Reading.
 * e.g.
 * - making a connection (even if the URL needs a password)
 * - returning a vector of Strings, which contains the HTML-Code
 *  
 * 
 */
public class KernelReaderURLZZZ extends  KernelUseObjectZZZ{
	private String sURLToRead=null;
	private String sURLUsed = null; //die tatsächlich verwendete URL, z.B. wenn relative Dateien noch mit dem Protokoll und absoluten Dateipfad ergänzt werden müssen.
	private String sPassword=null;
	private String sAccount=null;
	private String sProxyHost=null;
	private String sProxyPort=null;
	private URLConnection objURLCon;
	private ArrayList obj_alsURLContent;
	private KernelReaderPageZZZ objReaderPage;
	private InputStream obj_inStreamURLContent=null;
	private boolean bFlagUseAccount=false;
	private boolean bFlagUseStream=false;	
	private boolean bFlagUseProxy=false;
	
	
	/**
	 * Use this constructor if you don�t need a password/user for the page
	 * @param objKernel
	 * @param objLog
	 * @param sURLToRead, e.g. "http://192.168.3.253/doc/de/home.htm"
	 * @param saFlagControl
	 * @param sFlagControl
	 */
	public KernelReaderURLZZZ(IKernelZZZ objKernel, String sURLToRead,String[] saFlagControl, String sFlagControl ) throws ExceptionZZZ{
		super(objKernel);
		KernelReaderURLNew_(sURLToRead, null, null ,saFlagControl, sFlagControl);
	}	//end constructor
	
	
	/**
	 * Use this constructor if you have to pass a password / User for the page
		 * @param objKernel
		 * @param objLog
		 * @param sURLToRead
		 * @param sAccount
		 * @param sPassword
		 * @param saFlagControl
		 * @param sFlagControl
		 */
	public KernelReaderURLZZZ(IKernelZZZ objKernel, String sURLToRead, String sAccount, String sPassword,String[] saFlagControl, String sFlagControl ) throws ExceptionZZZ{
		super(objKernel);
		KernelReaderURLNew_(sURLToRead, sAccount ,sPassword, saFlagControl, sFlagControl);
}//end constructor


	private boolean KernelReaderURLNew_(String sURLToReadIn, String sAccountIn, String sPasswordIn, String[] saFlagControl, String sFlagControl) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			
			//try{	
					
			//Falls die URL null ist, wird ein entsprechender Fehler ausgegeben.
			if(sURLToReadIn == null){
				ExceptionZZZ ez = new ExceptionZZZ("URL-String to read", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;		
			}
			if(sURLToReadIn.equals("")){
				ExceptionZZZ ez = new ExceptionZZZ("URL-String to read", iERROR_PARAMETER_EMPTY,   this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;		
			}							
			this.setURLString(sURLToReadIn);
				
						
			if(saFlagControl != null){
				String stemp; 	boolean btemp;
				for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
					stemp = saFlagControl[iCount];
					btemp = setFlag(stemp, true);
					if(btemp==false){ 								   
						   ExceptionZZZ ez = new ExceptionZZZ( stemp, iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
						   throw ez;		 
					}
				}
				if(this.getFlag("INIT")==true){
					bReturn = true;
					break main; 
				}		
			}
		
			
			//Ggf. notwendige Accountdateen �bernehmen
			if(!StringZZZ.isEmpty(sAccountIn)){ 
					this.setAccountEnabled(sAccountIn, sPasswordIn);					
				}					
		}//end main:
		return bReturn;		
	}//end private constructor function
	
	
	public KernelReaderPageZZZ getReaderPage() throws ExceptionZZZ{
		KernelReaderPageZZZ objReturn=null;
		main:{
			//Check out if a ReaderPage exists
			if (this.objReaderPage == null){
				//create a new Object
				//connect to the url
				this.connect();
			
				//read the content
				this.loadURLContent(this.getURLConnection());
				
				if(this.getFlag("UseStream")){
					//make the page reader object from a stream
					InputStream in = this.getURLContentStream();
					
					  //make the page reader object
					  objReturn = new KernelReaderPageZZZ(this.getKernelObject(), in, (String[]) null, "");
				}else{
					//make the page reader object form an arraylist
					ArrayList obj_alsURLContent = this.getURLContentArrayList();
					/*
					  for(int iCount= 0; iCount <= obj_alsURLContent.size()-1;iCount++ ){
						  System.out.println(obj_alsURLContent.get(iCount));	
					  }
					  */
					  
					  //make the page reader object
					  objReturn = new KernelReaderPageZZZ(this.getKernelObject(), obj_alsURLContent, (String[]) null, "");
					}//getFlag("Stream")
			}else{
				//return the formerly gotten object
				objReturn = this.objReaderPage;
				break main;
			}
		}//end main:
		return objReturn;
	}//end function getReaderPage()
	

/**
 * connects to a URL which requires a password.
 *  If the account string is "" or null, the current acount will be used.
 *  If the password is null, the current password will be used. (Remark: A password can be ""). 
 *  To use a proxy .setProxyEnabled(...) must be used before. 
 * @param sAccount
 * @param sPassword
 * @return
 * @throws ExceptionZZZ
 */
public boolean connect(String sAccount, String sPassword) throws ExceptionZZZ{
	this.setAccountEnabled(sAccount, sPassword);
	return connect_(true);
}//end function

/**
 * connects to a URL. No Account and Password will be used.
 * To use a proxy .setProxyEnabled(...) must be used before. 
 * @return
 * @throws ExceptionZZZ
 */
public boolean connect() throws ExceptionZZZ{
	return connect_(this.getFlag("useAccount"));
}

private boolean connect_(boolean bFlagUseAccount) throws ExceptionZZZ{
	boolean bReturn = false;
		main:{
			String sAccount = null; String sPassword = null;
			try {
				if(bFlagUseAccount==true){					
						  sAccount = this.getAccountString();
						  if(sAccount==null){
							  ExceptionZZZ ez = new ExceptionZZZ("Account", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());
								throw ez;		
						  }else if(sAccount.equals("")){
						  		  ExceptionZZZ ez = new ExceptionZZZ("Account", iERROR_PARAMETER_EMPTY,   this, ReflectCodeZZZ.getMethodCurrentName());
						  		  throw ez;						  		
				  		}	
						  
						  sPassword = this.getPasswordString();
						  if(sPassword==null){							
							  	  ExceptionZZZ ez = new ExceptionZZZ("Password", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());
									throw ez;								
						  }
						  this.setFlag("useaccount", true);												 				
				  }//bFlagUseAccount==trueelse{
				
				
			/* Das explizite abfragen, ob die prox-verbindung steht, sollte hier nicht gemacht werden
			//falls ein Proxy verwendet werden soll
			if(this.getFlag("useproxy")==true){
				this.setProxyEnabled("", "");
			}
			*/
	
							
			String sUrl = this.getURLString();
			String sUrlToUse = "";
			if(UrlLogicZZZ.hasProtocolValid(sUrl)){
				sUrlToUse = sUrl;				
			}else{
				File objFile = FileEasyZZZ.searchFile(sUrl);
				String sFilePath = objFile.getAbsolutePath();				
				sUrlToUse = "file" + UrlLogicZZZ.sURL_SEPARATOR_PROTOCOL_FILE + sFilePath;	//Ein Slash mehr als das Protokoll
			}
			this.setURLStringUsed(sUrlToUse);
				
			//get the URL-Object
			URL url = new URL(this.getURLStringUsed());
												
			//FGL 20070306 NEU: Falls das Protokoll 'file' ist. Z.B.: file:///F:\Workplace\Eclipse3FGL\JAZVideoInternetArchive\pagIPLinkFGLTest.html
			//Merke: Beim File Protokoll gibt es bei absoluten Pfaden noch ein / vor dem Pfad.
			if (url.getProtocol().equalsIgnoreCase("file")) {
				URI uri;
				try {
					uri = new URI (url.toString());
					
//					toURI gibt es erst ab Java 1.5   File file = new File(url.toURI());
					File file = new File(uri);
		 			if(file.exists()){
		 				URLConnection connection = url.openConnection();		
		 				connection.setDoOutput(false);
		 				this.setURLConnection(connection);
						
						//After all properties are set, connect
						connection.connect();
						bReturn = true;
		 			}else{
		 				System.out.println(ReflectCodeZZZ.getMethodCurrentName()+"#file not found: " + file.getPath());
		 				break main;
		 			}
				} catch (URISyntaxException e) {					
					e.printStackTrace();					
				}	 			
			}else{
//				get the URL-Connection Object. That has more functionality
				URLConnection connection = url.openConnection();		
			
				
					if(bFlagUseAccount == false){
							//only receiving data
							//TODO Fall, das eine nicht kennwortgesch�tzte URL aufgerufen werden soll
							//???
							connection.setDoOutput(false);					
					}else{
							//now make a login
							//Requesting an output-stream for sending data to the web-server
							connection.setDoOutput(true);
					
							//BASE64 encoding of the account/passord-combination
							String sInput = this.getAccountString() + ":" + this.getPasswordString();
							new Base64();
							//Problem in Java 6: Untenstehender BASE64Encoder() ist verborgen.
							//https://stackoverflow.com/questions/5908574/base64-decoding-using-jdk6-only
							//String sEncoded = new sun.misc.BASE64Encoder().encode(sInput.getBytes());
							//Als Alternative verwende ich den BASE64 Encoder, der von Apache Commons Codec 1.7 bereitgestellt wird.
							String sEncoded = Base64.encodeBase64String(sInput.getBytes());
							connection.setRequestProperty("Authorization", "Basic " + sEncoded);    //IMPORTANT: BLANC behind the 'Basic'-word
					}
				this.setURLConnection(connection);
															
				//After all properties are set, connect
				connection.connect();
			}//End if url.getProtocol().equalsIgnoreCase("file")) {
				   //Proof connection, if the connection doesn�t work it will throw a IOException, but here i additionally check this by a workarround function									
				   //FGL DAs liefert im Domino - Servertask immer einen Fehler !!! boolean btemp = this.isconnected();
				   /* if(btemp==false){
						//if the passed URL - String is not a valid URL
						stemp =  "Proof of connection to the URL returns false. Either no connection established or no content available: '" + this.getURLString() + "'";
						iCode = 1000;
						sMethod = KernelZZZ.getMethodCurrentName();
						ExceptionZZZ ez = new ExceptionZZZ(stemp, iCode, this, sMethod, "");
						this.setExceptionObject(ez);
						throw ez;
					}
				*/										
				bReturn = true;
			
			}catch(MalformedURLException e){
				//if the passed URL - String is not a valid URL
				ExceptionZZZ ez = new ExceptionZZZ("'URL-String to read' is not a valid URL: '" + this.getURLString() + "'", iERROR_PARAMETER_VALUE,   this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}catch(IOException e){
				//e.g. thrown by URL.openConnection()
				ExceptionZZZ ez = new ExceptionZZZ("'URL-String to read' cannot used to open a URLConnection: '" + this.getURLString() + "'", iERROR_PARAMETER_VALUE,   this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;										
			}										
		}//end main:
		return bReturn;
}

/**
 * the property connected of the URLConnection-Klass is protected and i can�t find a getter-method for it, so this is a try to evaluate it
 * @return
 */
public boolean isconnected() {
	boolean bReturn=false;
	main:{
		URLConnection objURLCon = this.getURLConnection();
		//no connection-object available, then return false
		if(objURLCon == null){
			bReturn = false;
			break main;
		}
		
		//now evaluate a connection proof
		//String stemp = objURLCon.toString(); //gibt wohl immer Strings wie z.B. 'sun.net.www.protocol.http.HttpURLConnection:http://192.168.3.253/doc/de/home.htm' zur�ck					
		// stemp = objURLCon.getHeaderField("Connection"); //gibt wohl immer 'close' zur�ck
		// int itemp = objURLCon.getContentLength(); //nicht signifikant, gibt im Test auch -1 zur�ck, wenn die Verbindung geklappt hat.
		// String stemp = objURLCon.getContentType(); //gibt auch ohne connection einen wert z.B: 'txt/html' zur�ck.
		
		
		//Das Setzen diverser Porperties liefert einen Fehler, wenn schon connect()-Methode ausgef�hrt worden ist.
		//Diesen Umstand machen wir uns zu Nutze:
		long ltemp = objURLCon.getIfModifiedSince();
		try{
			objURLCon.setIfModifiedSince(10000);
			
			//Falls der Fehler nicht aufgetreten ist, muss der originalzustand wieder hergestellt werden
		    objURLCon.setIfModifiedSince(ltemp);
		    bReturn = false;
		    break main;
		}catch(IllegalStateException e){
			//Falls dieser Fehler auftritt, dann wurde schon connect() ausgef�hrt
			bReturn =  true;
			break main;	
		}
			 		
	}//end main
	return bReturn;
}


/**
 * Fills the array list with the found HTML/Javascript, etc. code. Get the ArrayList by .getURLContent(), uses the URLConnection found by .connect()
 * @return
 * @throws ExceptionZZZ
 */
public boolean loadURLContent() throws ExceptionZZZ{
	return loadURLContent_(this.getURLConnection());
}

/**
 * Fills the array list with the found HTML/Javascript, etc. code. Get the ArrayList by .getURLContent()
 * @param objURLCon, an URLConnection, e.g. created by .connect()
 * @return
 * @throws ExceptionZZZ
 */
public boolean loadURLContent(URLConnection objURLCon) throws ExceptionZZZ{
	return loadURLContent_(objURLCon);	
}//end function


private boolean loadURLContent_(URLConnection objURLCon) throws ExceptionZZZ{
	boolean bReturn = false;
		main:{	
			ArrayList obj_alsURLContent;
		
			try{
			if(objURLCon==null){
				 ExceptionZZZ ez = new ExceptionZZZ("URLConnection", iERROR_PARAMETER_MISSING,  this, ReflectCodeZZZ.getMethodCurrentName());
				 throw ez;		
			}
			this.setURLConnection(objURLCon);

			//nochmal auslesen, merke �ber die URL st�rzt es hier ab
				//InputStream uin = connection.getURL().openStream();
				//BufferedReader in = new BufferedReader(new InputStreamReader(uin));
			
			
				//Nur Test: String sTemp = connection.toString();
				//System.out.println(sTemp);
			
		
		
				if(this.getFlag("UseStream")== true){
					//Zum Parsen von HTML wird hier ein Inputstream verlangt
					InputStream in = objURLCon.getInputStream();
					this.obj_inStreamURLContent = in;
					
				}else{
					//TODO Das BufferedReaderObjekt zur Verf�gung stellen, damit andere Klassen damit weiterarbeiten k�nnen
					//TODO Die Strings des Buffered Reader in eine Arraylist packen, den dann die Z-Kernel-Script-Reader-Klassen verarbeiten k�nnen.
					//Diese connection wie folgt auslesen
					BufferedReader in = 
					new BufferedReader(new InputStreamReader(objURLCon.getInputStream()));
					
					//Die JavaScript-Reader-Klasse erfordert eine Arraylist, oder besser einen String, zum Parsen.
					if(this.getURLContentArrayList()==null){
						obj_alsURLContent = new ArrayList(100);
						this.setURLContentArrayList(obj_alsURLContent);
					}else{
						obj_alsURLContent = this.getURLContentArrayList();
						obj_alsURLContent.clear();
					}
				
					String sline;
					while((sline = in.readLine()) != null){
						obj_alsURLContent.add(sline);
						//System.out.println(sline);
					} //hier sind einfach noch zu wenig Informationen
				
				
								/*nun ja, die Infos, die interessieren sind unterhalb
								 * ES SCHEINT MIR SO, ALS M�SSTE F�R JEDE NEUE URL EINE NEUE CONNECTION ERZEUGT WERDEN !!!
								 * DARUM DIE URL DIRECT ANSTEUERN ???
								url = new URL("http://192.168.3.253/doc/de/home.htm");
				
								// Auslesen der Serverantwort funktioniert nat�rlich nur, wennkeine Passwortfrage kommt
								InputStream uin = url.openStream();
								in = new BufferedReader(new InputStreamReader(uin));
								while((line = in.readLine()) != null){
									System.out.println(line);
								} //hier sind einfach noch zu wenig Informationen
								/*/	
				}//END getFlag("useStream")
				
			bReturn = true;
		
			}catch(IOException e){
				//e.g. thrown by URL.openConnection()
				 ExceptionZZZ ez = new ExceptionZZZ("'URL-String to read' cannot used to open a URLConnection: '" + this.getURLString() + "'. Check network or operation system - status. Reported error: '" + e.getMessage() + "'", iERROR_PARAMETER_VALUE,   this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;		
			}			
		}//end main:
		return bReturn;
}//end function

	/**
	 * @return
	 */
	public String getURLString() {
		return sURLToRead;
	}

	/**
	 * @param string
	 */
	public void setURLString(String string) {
		sURLToRead = string;
	}
	
	public String getURLStringUsed(){
		return this.sURLUsed;
	}
	
	/**Ist private, weil dieser Wert erst beim .connect() gesetzt wird und die tatsächliche URL enthalten soll.
	 * @param sUrl
	 */
	private void setURLStringUsed(String sUrl){
		this.sURLUsed = sUrl;
	}
	
	/**
	 * @return
	 */
	public String getAccountString() {
		return sAccount;
	}

	/**
	 * @return
	 */
	public String getPasswordString() {
		return sPassword;
	}

	/**
	 * @param string
	 */
	private void setAccountString(String string) {
		sAccount = string;
	}

	/**
	 * @param string
	 */
	private void setPasswordString(String string) {
		sPassword = string;
	}

	/**
	 * There is no public setter-method. A URLConnection is created by the method .connect(..)
	 * @return
	 */
	public URLConnection getURLConnection() {
		return objURLCon;
	}
	private void setURLConnection(URLConnection objURLCon){
		this.objURLCon = objURLCon;
	}

	/**
	 * There is no public setter-method. Returns the ArrayList filled with strings by the method .readURLContent(..)
	 * Remember: KernelScriptReader-Class needs an ArrayList for Input
	 * @return 
	 */
	public ArrayList getURLContentArrayList() {
		return obj_alsURLContent;
	}
	private void setURLContentArrayList(ArrayList list) {
		obj_alsURLContent = list;
	}

	/**
	 * There is no public setter-method. Returns the InputStream created by the method .readURLContent(..), when the setFlag("useStream") == true
	 * Remember: KernelHTMLReader-Class needs an Stream for Input
	 * @return 
	 */
	public InputStream getURLContentStream(){
		return obj_inStreamURLContent;
	}

	private void setURLContentStream(InputStream in){
		obj_inStreamURLContent = in;
	}
	
	
	/**No public setter available, use .setProxyEnabled(...)
	 * @return String
	 *
	 * javadoc created by: 0823, 29.06.2006 - 14:32:58
	 */
	public String getProxyHost() {
		return sProxyHost;
	}
	private void setProxyHost(String proxyHost) {
		sProxyHost = proxyHost;
	}


	/**No public setter available, use .setProxyEnabled(...)
	 * @return String
	 *
	 * javadoc created by: 0823, 29.06.2006 - 14:32:58
	 */
	public String getProxyPort() {
		return sProxyPort;
	}
	private void setProxyPort(String proxyPort) {
		sProxyPort = proxyPort;
	}
	
	
	/**Enables proxy settings. Either the proxy-host/port parameters were set before or the can be passed now.
	 * The flag ("usePorxy") will be set = true.
	 * @param sProxyHostIn
	 * @param sProxyPortIn
	 * @throws ExceptionZZZ, 
	 *
	 * @return boolean
	 *
	 * javadoc created by: 0823, 29.06.2006 - 14:13:35
	 */
	public boolean setProxyEnabled(String sProxyHostIn, String sProxyPortIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sProxyHost; String sProxyPort;
			check:{				
				if(StringZZZ.isEmpty(sProxyHostIn)){
					sProxyHost = this.getProxyHost();
					if(StringZZZ.isEmpty(sProxyHost)){
						ExceptionZZZ ez = new ExceptionZZZ("ProxyHost", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}				
				}else{
					sProxyHost=sProxyHostIn;
					this.setProxyHost(sProxyHost);
				}
				
				
				if(StringZZZ.isEmpty(sProxyPortIn)){
					sProxyPort = this.getProxyPort();
					if(StringZZZ.isEmpty(sProxyPort)){
						ExceptionZZZ ez = new ExceptionZZZ("ProxyPort", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}			
				}else{
					sProxyPort=sProxyPortIn;
					this.setProxyPort(sProxyPort);
				}
			}//End check
		
				System.setProperty( "proxySet", "true" );
				System.setProperty( "proxyHost", sProxyHost);
				System.setProperty( "proxyPort", sProxyPort );
				this.setFlag("useproxy", true);
			
			bReturn = true;
		}//End main
		return bReturn;
	}
	
	/** Disables the use of a proxy. 
	 * The flag ("usePorxy") will be set = false, too.
	 * @return boolean
	 *
	 * javadoc created by: 0823, 29.06.2006 - 14:39:37
	 */
	public boolean setProxyDisabled(){
		System.setProperty( "proxySet", "false" );
		this.setFlag("useproxy", false);		
		return true;
	}
	
	public boolean setAccountEnabled(String sAccountin, String sPasswordIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			check:{
				if(StringZZZ.isEmpty(sAccountin)){
					ExceptionZZZ ez = new ExceptionZZZ("Account", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;			
				}
				
				if(sPasswordIn==null){
					//Remark: Empty String is allowed
					ExceptionZZZ ez = new ExceptionZZZ("Password", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}				
			}//End check
		
			this.setAccountString(sAccountin);
			this.setPasswordString(sPasswordIn);
			this.setFlag("useaccount", true);
		}//End main
		return bReturn;
	}
	
	
	//######### GetFlags - Handled ##############################################
	/* (non-Javadoc)
	@see zzzKernel.basic.KernelObjectZZZ#getFlag(java.lang.String)
	Flags used:<CR>
	- UseAccount
	- UseStream
	- UseProxy
	 */
	public boolean getFlag(String sFlagName){
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.getFlag(sFlagName);
			if(bFunction==true) break main;
			
			//getting the flags of this object
			String stemp = sFlagName.toLowerCase();
			if(stemp.equals("useaccount")){
				bFunction = bFlagUseAccount;
				break main;
			}else if(stemp.equals("usestream")){				
				bFunction = bFlagUseStream;
				break main;
			}else if(stemp.equals("useproxy")){
				bFunction = bFlagUseProxy;
				break main;
			}
		}//end main:
		return bFunction;
	}
	
	


	/**
	 * @see zzzKernel.basic.KernelUseObjectZZZ#setFlag(java.lang.String, boolean)
	 * @param sFlagName
	 * Flags used:<CR>
	 * - useaccount
	 * - usestream
	 * - useproxy
	 */
	public boolean setFlag(String sFlagName, boolean bFlagValue){
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.setFlag(sFlagName, bFlagValue);
			if(bFunction==true) break main;
	
		//setting the flags of this object
		String stemp = sFlagName.toLowerCase();
		if(stemp.equals("useaccount")){
			bFlagUseAccount = bFlagValue;
			bFunction = true;
			break main;
		}else if(stemp.equals("usestream")){
			bFlagUseStream = bFlagValue;
			bFunction = true;
			break main;
		}else if(stemp.equals("useproxy")){
			bFlagUseProxy = bFlagValue;
			bFunction = true;
			break main;
		}
		}//end main:
		return bFunction;
	}




}//end class
