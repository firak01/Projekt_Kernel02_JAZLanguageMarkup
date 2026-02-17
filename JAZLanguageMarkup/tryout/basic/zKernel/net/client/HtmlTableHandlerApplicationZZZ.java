package basic.zKernel.net.client;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.html.TagInputZZZ;
import basic.zKernel.html.TagTypeInputZZZ;
import basic.zKernel.html.reader.KernelReaderHtmlZZZ;
import basic.zKernel.net.client.KernelPingHostZZZ;
import basic.zKernel.net.client.KernelReaderPageZZZ;
import basic.zKernel.net.client.KernelReaderURLZZZ;

public class HtmlTableHandlerApplicationZZZ  extends AbstractApplicationZZZ implements IApplicationZZZ{
	
	//Die Eigenschaften DIESER Applikation. Sie stehen über das Application Objekt ueberall zur Verfuegung.
	//TODOGOON20230613; //Das sind die Eigenschaften die durch die ganzen neuen TableHandler Klassen gefüllt werden müssen
	
	private String sURL = null;	
	private String sIpURL = null;
	
	private String sIpRemote = null;

	//Ggf. ist dieser Wert aussagekräftiger als der Versuch über sIPVPN
	private String sPortRemoteScanned = null;
	private String sPortVpnScanned = null;

	private String sIPVPN = null;
	
	private String sProxyHost = null;
	private String sProxyPort = null;	
	private String sVpnIpRemote = null;
	private String sVpnIpLocal = null;
	private String sIPLocal = null;
	private String sIPIni = null;
	
	private String sTapAdapterUsed = null;
	
	private String sCertifierConfiguredFilename=null;	
	private String sKeyConfiguredFilename=null;
	
	public HtmlTableHandlerApplicationZZZ(IKernelZZZ objKernel, IMainZZZ objClient) throws ExceptionZZZ {
		super(objKernel, objClient);		
	}
	
	
	
	/**Reads a port from the configuration-file. Default: Port 80.
	 * This port is used to check the connection. 
	 * @throws ExceptionZZZ, 
	 *
	 * @return String
	 *
	 * javadoc created by: 0823, 17.07.2006 - 09:05:05
	 */
	public String readVpnPort2Check() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			IKernelZZZ objKernel = this.getKernelObject();
			sReturn = objKernel.getParameterByProgramAlias("OVPN","ProgVPNCheck","VPNPort2Check").getValue();
			if(StringZZZ.isEmpty(sReturn)) sReturn = KernelPingHostZZZ.sPORT2CHECK;			
		}//END main:
		return sReturn;
	}
		
	public String readVpnIpLocal() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			IKernelZZZ objKernel = this.getKernelObject();
			sReturn = objKernel.getParameterByProgramAlias("OVPN","ProgConfigValues","VpnIpLocal").getValue();					
		}//END main:
		return sReturn;
	}
	
	
	
	/**Read from the configuration file the URL where the dynamic ip was written to.
	 * @throws ExceptionZZZ, 
	 *
	 * @return String
	 *
	 * javadoc created by: 0823, 11.07.2006 - 14:19:23
	 */
	public String readUrlToParse() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			IKernelZZZ objKernel = this.getKernelObject();
			sReturn = objKernel.getParameterByProgramAlias("HtmlTableHandler","ProgIPReader","URL2Read").getValue();		
		}//END main:
		return sReturn;
	}
	
	
	/**Reads the dynamic IP from a URL (uses a html-parser therefore).
	 * Checks the necessarity of enabling a proxy and will enable the proxy.
	 * The proxy has to be configured in the kernel-configuration-file.
	* @return String, the IP found.
	* @throws ExceptionZZZ 
	* 
	* lindhaueradmin; 13.07.2006 09:12:43
	 */
	public String readIpURL() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			
			String[] satemp = {"UseStream"};
			KernelReaderURLZZZ objReaderURL = new KernelReaderURLZZZ(objKernel, sURL,satemp, "");
			this.readProxyEnabled();
			if(this.getFlag("useProxy")==true) objReaderURL.setProxyEnabled(this.getProxyHost(), this.getProxyPort());
			
			
			//+++ Nachdem nun ggf. der Proxy aktiviert wurde, die Web-Seite versuchen auszulesen				
			//+++ Den IP-Wert holen aus dem HTML-Code der konfigurierten URL
			KernelReaderPageZZZ objReaderPage = objReaderURL.getReaderPage();
			KernelReaderHtmlZZZ objReaderHTML = objReaderPage.getReaderHTML();
			 
			//Nun alle input-Elemente holen und nach dem Namen "IPNr" suchen.
			TagTypeInputZZZ objTagTypeInput = new TagTypeInputZZZ(objKernel);			
			TagInputZZZ objTag = (TagInputZZZ) objReaderHTML.readTagFirstZZZ(objTagTypeInput, "IPNr");
			sReturn = objTag.readValue();
		}//END main
		return sReturn;
	}
	
	/** Hier die Möglichkeit andere Quellen als nur die URL zu definiern
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.04.2023, 07:38:26
	 */
	public String readIpRemote() throws ExceptionZZZ{
		String sReturn = null;
		main:{						
			String sIpUsed = null;
			String sIpIni = this.getIpIni();
			if(StringZZZ.isEmpty(sIpIni)) {
				String sIpUrl = this.getIpURL();				
				sIpUsed = sIpUrl;
			}else {
				sIpUsed = sIpIni;
			}
			sReturn = sIpUsed;
		}//END main
		return sReturn;
	}
	
	//######################################################
	//### Getter / Setter
	
		
	public String getUrlToParse() throws ExceptionZZZ{
		if(this.sURL==null) {
			this.sURL = this.readUrlToParse();
		}
		return this.sURL;
	}
	public void setURL2Parse(String sURL) {
		this.sURL = sURL;
	}
	
	public String getIpURL() throws ExceptionZZZ{
		if(this.sIpURL==null) {
			this.sIpURL = this.readIpURL();
		}
		return this.sIpURL;
	}
	public void setIpURL(String sIpURL) {
		this.sIpURL = sIpURL;
	}
			
	public String getIpRemote() throws ExceptionZZZ{
		if(this.sIpRemote==null) {
			this.sIpRemote = this.readIpRemote();
		}
		return this.sIpRemote;
	}
	public void setIpRemote(String sIpRemote) {
		this.sIpRemote = sIpRemote;
	}
	
	/**Der Versuch anzugeben, �ber welchen Port die VPN-Verbindung erfolgreich war.
	 * @return String
	 *
	 * javadoc created by: 0823, 11.07.2006 - 17:29:48
	 */
	public String getRemotePortScanned(){
		return this.sPortRemoteScanned;
	}
	public void setRemotePortScanned(String sPortRemoteScanned) {
		this.sPortRemoteScanned = sPortRemoteScanned;
	}
	
	//Achtung: Im Gegensatz zu sIPRemote ist das f�r jede Konfiguration verschieden. Darf also nur dann gesetzt werden, wenn die Verbindung erfolgreich hergestellt wurde.
	public String getVpnIpEstablished(){
		return this.sIPVPN;
	}
	public void setVpnIpEstablished(String sIPVPN) {
		this.sIPVPN = sIPVPN;
	}
	
	
	/**This is a string filled by a port-scanner, after the connection was established.
	 * This string is read out by the fronteend ui - class to set the status.
	 * @return String
	 *
	 * javadoc created by: 0823, 17.07.2006 - 08:58:49
	 */
	public String getVpnPortScanned(){
		return this.sPortVpnScanned;
	}
	public void setVpnPortScanned(String sPortVpnScanned) {
		this.sPortVpnScanned = sPortVpnScanned;
	}
	
	/*STEHEN LASSEN: DIE PROBLEMATIK IST, DAS NICHT NACHVOLLZIEHBAR IST, �BER WELCHEN PORT DIE VPN-VERBINDUNG HERGESTELLT WURDE 
	 * Zumindest nicht PER PING-BEFEHL !!!
	 
	public String getVpnPortEstablished(){
		return this.sPortVPN;
	}
	*/
	
	
	//###### GETTER  / SETTER, speziell für DIESE Application
	public String getProxyHost(){
		//Werte werden in readProxyEnabled gesetzt
		return this.sProxyHost;
	}
	public void setProxyHost(String sProxyHost) {
		this.sProxyHost = sProxyHost;
	}
	public String getProxyPort(){
		//Werte werden in readProxyEnabled gesetzt
		return this.sProxyPort;
	}
	
	public String getVpnIpRemote() throws ExceptionZZZ {
		if(this.sVpnIpRemote==null) {
			this.sVpnIpRemote = this.readVpnIpRemote(); 
		}
		return this.sVpnIpRemote;
	}
	public void setVpnIpRemote(String sVpnIpRemote) {
		this.sVpnIpRemote = sVpnIpRemote;
	}
	
	public String getVpnIpLocal() throws ExceptionZZZ {
		if(this.sVpnIpLocal==null) {
			this.sVpnIpLocal = this.readVpnIpLocal(); 
		}
		return this.sVpnIpLocal;
	}
	public void setVpnIpLocal(String sVpnIpLocal) {
		this.sVpnIpLocal = sVpnIpLocal;
	}
	
	public String getIpIni() throws ExceptionZZZ{
		if(StringZZZ.isEmpty(this.sIPIni)) {
			this.sIPIni = this.readIpIni();
		}
		return this.sIPIni;
	}
	public void setIpIni(String sIPini) {
		this.sIPIni=sIPini;
	}
	
	public String getIpLocal() throws ExceptionZZZ{
		if(this.sIPLocal==null) {
			this.sIPLocal = this.readIpLocal();
		}
		return this.sIPLocal;
	}
	public void setIpLocal(String sIPLocal) {
		this.sIPLocal = sIPLocal;
	}		
	
	public String getTapAdapterUsed() throws ExceptionZZZ {
		if(this.sTapAdapterUsed==null) {
			this.sTapAdapterUsed = this.readTapAdapterUsed(); 
		}
		return this.sTapAdapterUsed;
	}
	public void setTapAdapterUsed(String sTapAdapterUsed) {
		this.sTapAdapterUsed = sTapAdapterUsed;
	}
	
	public String getCertifierConfiguredFilename() throws ExceptionZZZ{
		if(this.sCertifierConfiguredFilename==null) {
			this.sCertifierConfiguredFilename = this.readCertifierConfiguredFilename();
		}
		return this.sCertifierConfiguredFilename;
	}
	
	public String getKeyConfiguredFilename() throws ExceptionZZZ{
		if(this.sKeyConfiguredFilename==null) {
			this.sKeyConfiguredFilename = this.readKeyConfiguredFilename();
		}
		return this.sKeyConfiguredFilename;
	}		
	
	
	//#######################################################################################
	
	/**Read from the configuration file a proxy which might be necessary to use AND enables the proxy for this application.
	 * Remember: This proxy is used to read the url (containing the ip adress)
	 *                    AND
	 *                    The proxy is added to the open vpn configuration file(s) 
	 * @throws ExceptionZZZ, 
	 *
	 * @return boolean
	 *
	 * javadoc created by: 0823, 11.07.2006 - 14:20:24
	 */
	public boolean readProxyEnabled() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			IKernelZZZ objKernel = this.getKernelObject();
			
		    //+++ Ggf. notwendige Proxy-Einstellung pr�fen.
			//Z.B. bei der itelligence bin ich hinter einem Proxy. Die auszulesende Seite ist aber im Web.
			IKernelConfigSectionEntryZZZ objEntryHost = objKernel.getParameterByProgramAlias("OVPN","ProgProxyHandler","ProxyHost");			
			if(objEntryHost.hasAnyValue()){		//Eine Proxy-Konfiguration ist nicht Pflicht
				this.sProxyHost = objEntryHost.getValue();	
				if(StringZZZ.isEmpty(this.sProxyHost)) {
					this.getMainObject().logStatusString("Proxy host as empty configured.");
					break main;
				}
				
				
				IKernelConfigSectionEntryZZZ objEntryPort = objKernel.getParameterByProgramAlias("OVPN","ProgProxyHandler","ProxyPort");
				if(objEntryHost.hasAnyValue()){
					this.sProxyPort = objEntryPort.getValue();
					if(StringZZZ.isEmpty(this.sProxyPort)) {
						this.getMainObject().logStatusString("Proxy port as empty configured.");
						break main;
					}
					
					//+++ Nun versuchen herauszufinden, ob der Proxy auch erreichbar ist und existiert. Nur nutzen, falls er existiert
					KernelPingHostZZZ objPing = new KernelPingHostZZZ(objKernel, null);
					try{ //Hier soll nicht abgebrochen werden, wenn es nicht klappt. Lediglich ins Log soll etwas geschrieben werden.
						this.getMainObject().logStatusString( "Trying to reach the proxy configured. '" + sProxyHost + " : " + sProxyPort +"'");									
						bReturn = objPing.ping(sProxyHost, sProxyPort);								
						this.getMainObject().logStatusString("Configured proxy reached. " + sProxyHost + " : " + sProxyPort +"'");
										
					}catch(ExceptionZZZ ez){
						objKernel.getLogObject().writeLineDate("Will not use the proxy configured, because: " + ez.getDetailAllLast());
						this.getMainObject().logStatusString("Configured proxy unreachable. " + sProxyHost + " : " + sProxyPort +"'. No proxy will be enabled.");
					}	
				}else {
					this.getMainObject().logStatusString("No proxy port configured.");
				}				
			}else{
				this.getMainObject().logStatusString("No proxy host configured.");								
			}//END 	if(sProxyHost!=null && sProxyHost.equals("")==false){		//Eine Proxy-Konfiguration ist nicht Pflicht		
		}//END main
		this.setFlag("UseProxy", bReturn);
		return bReturn;
	}
	
	public String readVpnIpRemote() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			IKernelZZZ objKernel = this.getKernelObject();
			sReturn = objKernel.getParameterByProgramAlias("OVPN","ProgConfigValues","VpnIpRemote").getValue();					
		}//END main:
		return sReturn;
	}
	
		
	public String readIpIni() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			////Z.B. [IP-ClientContext!02]
			IKernelZZZ objKernel = this.getKernelObject();
			sReturn = objKernel.getParameterByProgramAlias("OVPN","IP-ClientContext","IPExternal").getValue();					
		}//END main:
		return sReturn;
	}
	
	
	/** Read the used local IP.
	 * @return
	 * @throws ExceptionZZZ
	 */
	public String readIpLocal() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			sReturn = EnvironmentZZZ.getHostIp();
		}//END main
		this.sIPLocal = sReturn;
		return sReturn;
	}
	
	public String readTapAdapterUsed() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			IKernelZZZ objKernel = this.getKernelObject();
			sReturn = objKernel.getParameterByProgramAlias("OVPN","ProgConfigValues","TapAdapterUsedLocal").getValue();					
		}//END main:
		return sReturn;
	}
	
	/**Read from the configured certifier filename.
	 * Remark: If this is empty a default filename containing the Hostname will be expected. E.g. HANNIBALDEV04VM_CLIENT.crt
	 * The file is to be expected in the OpenVPN Configuration directory: E.g. C:\Programme\OpenVPN\config
	 * @throws ExceptionZZZ, 
	 *
	 * @return String
	 */
	public String readCertifierConfiguredFilename() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			IKernelZZZ objKernel = this.getKernelObject();
			sReturn = objKernel.getParameterByProgramAlias("OVPN","ProgConfigValues","CertifierFilename").getValue();		
		}//END main:
		return sReturn;
	}
	
	/**Read from the configured key filename.
	 * Remark: If this is empty a default filename containing the Hostname will be expected. E.g. HANNIBALDEV04VM_CLIENT.key
	 * The file is to be expected in the OpenVPN Configuration directory: E.g. C:\Programme\OpenVPN\config
	 * @throws ExceptionZZZ, 
	 *
	 * @return String
	 */
	public String readKeyConfiguredFilename() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			IKernelZZZ objKernel = this.getKernelObject();
			sReturn = objKernel.getParameterByProgramAlias("OVPN","ProgConfigValues","KeyFilename").getValue();		
		}//END main:		
		return sReturn;
	}
}
