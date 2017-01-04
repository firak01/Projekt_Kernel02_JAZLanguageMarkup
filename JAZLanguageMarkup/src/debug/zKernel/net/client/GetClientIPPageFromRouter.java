package debug.zKernel.net.client;
import java.net.*;
import java.io.*;

/**
 * @author Lindhauer
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class GetClientIPPageFromRouter {

	public static void main(String[] args) {
		
		try{
			InetAddress thisIP= InetAddress.getLocalHost();
			String sIP = thisIP.getHostAddress();
			
			System.out.println("local IP-Number is: " + sIP);
			
			
			//Socket objSocket = new Socket("192.168.3.253", 80);
			//InetAddress thisIPForeign = objSocket.getInetAddress();
			//String sIP2 = thisIPForeign.getHostAddress();
			
			//System.out.println("foreign IP-Number is: " + sIP2);
			
			
			//Connect to website of the router (Merke: die Angabe des Protokoll ist wichtig)
			//URL url = new URL("http://192.168.3.253");
			//o.k.URL url = new URL("http://192.168.3.253/doc/de/index.htm");
			//eine ebene tiefer: 
			URL url = new URL("http://192.168.3.253/doc/de/home.htm");
			//URL url = new URL("http://192.168.3.253/doc/flowctrl.htm");
			
			//URL url = new URL("http://192.168.3.253");
			//URL url = new URL("http://www.itelligence.de");
			//URL url = new URL("http://www.inselkampf.de"); hier kommt raus, dass men Browser ;) also diese Programm keine Frames unterstützt.
			//URL url = new URL("http://web1.austria093.server4free.de");
			
			/* Auslesen der Serverantwort funktioniert natürlich nur, wennkeine Passwortfrage kommt
			InputStream uin = url.openStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(uin));
			String line;
			while((line = in.readLine()) != null){
				System.out.println(line);
			} //hier sind einfach noch zu wenig Informationen
			*/
			
			URLConnection connection = url.openConnection();
			//hat man sich vorher schon connected gibt es beim setRequestProperty einen fehle
			//darum an dieser Stelle auskommentieren: connection.connect();
			//connection.getHeaderFields();
			
			//Einen Ausgabestrom anfordern, um Daten an den Webserver zu schicken
			connection.setDoOutput(true);
			
			//Das Kennwort
			String sInput = "admin:1fgl2fgl";
			String sEncoded = new sun.misc.BASE64Encoder().encode(sInput.getBytes());
			connection.setRequestProperty("Authorization", "Basic " + sEncoded);    //WICHTIG: DAS LEERZEICHEN HINTER DEM BASIC
			
			//Nachdem alle Properties gesetzt sind connecten
			connection.connect();
			
			//nochmal auslesen, merke über die URL stürzt es hier ab
			//InputStream uin = connection.getURL().openStream();
			//BufferedReader in = new BufferedReader(new InputStreamReader(uin));
			
			String sTemp = connection.toString();
			System.out.println(sTemp);
			
			
			//Diese connection wie folgt auslesen
			BufferedReader in = 
			new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while((line = in.readLine()) != null){
				System.out.println(line);
			} //hier sind einfach noch zu wenig Informationen
			
			
			/*nun ja, die Infos, die interessieren sind unterhalb
			 * ES SCHEINT MIR SO, ALS MÜSSTE FÜR JEDE NEUE URL EINE NEUE CONNECTION ERZEUGT WERDEN !!!
			 * DARUM DIE URL DIRECT ANSTEUERN ???
			url = new URL("http://192.168.3.253/doc/de/home.htm");
			
			// Auslesen der Serverantwort funktioniert natürlich nur, wennkeine Passwortfrage kommt
			InputStream uin = url.openStream();
			in = new BufferedReader(new InputStreamReader(uin));
			while((line = in.readLine()) != null){
				System.out.println(line);
			} //hier sind einfach noch zu wenig Informationen
			/*/
			
			
		System.out.println("End: check Variables");	
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
}
