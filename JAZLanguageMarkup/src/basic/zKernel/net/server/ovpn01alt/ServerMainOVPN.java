package basic.zKernel.net.server.ovpn01alt;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import basic.zKernel.KernelZZZ;
import basic.zUtil.io.KernelFileExpansionZZZ;
import custom.zUtil.io.FileExpansionZZZ;
import custom.zUtil.io.FileZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceArrayZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.FileTextWriterZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zWin32.com.wmi.KernelWMIZZZ;

public class ServerMainOVPN extends AbstractMainOVPN {
//	private ServerApplicationOVPN objApplication = null;//Objekt, dass Werte, z.B. aus der Kernelkonfiguration holt/speichert
//	private ConfigChooserOVPN objConfigChooser = null;   //Objekt, dass Templates "verwaltet"
//	private ServerConfigMapperOVPN objConfigMapper = null; //Objekt, dass ein Mapping zu passenden Templatezeilen verwaltet.
	private ArrayList<File> listaConfigFile = null;

	private String sStatusCurrent = null; //Hier�ber kann das Frontend abfragen, was gerade in der Methode "start()" so passiert.
	private ArrayList listaStatus = new ArrayList(); //Hier�ber werden alle gesetzten Stati, die in der Methode "start()" gesetzt wurden festgehalten.
    																	//Ziel: Das Frontend soll so Infos im laufende Prozess per Button-Click abrufen k�nnen.
	private ArrayList listaConfigStarter = new ArrayList(); //Hier�ber werden alle Procese, die mit einem bestimmten Konfigurations-File gestartet wurden festgehalten.
	private boolean bFlagIsStarted=false;
	private boolean bFlagWatchRunnerStarted=false;
	private boolean bFlagHasError= false;
	                                                                      //Ziel: Das Frontend soll so Infos im laufende Prozess per Button-Click abrufen k�nnen.

	public ServerMainOVPN(IKernelZZZ objKernel, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel, saFlagControl);
	}

	/**Entrypoint for managing the configuration files for "OpenVPN" client.
	 * @param args, 
	 *
	 * @return void
	 *
	 * javadoc created by: 0823, 30.06.2006 - 10:24:31
	 */
	public boolean start() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			//TODO Dies in eine Methode "find fileConfigAvailableAndConfigured"
			this.logStatusString("Searching for configuration template files '*.ovpn'"); //Darueber kann dann ggf. ein Frontend den laufenden Process beobachten.
			IKernelZZZ objKernel = this.getKernelObject();			
						
			ServerApplicationOVPN objApplication = (ServerApplicationOVPN) this.getApplicationObject();//Im UI erzeugt und übergeben.
			ConfigChooserOVPN objChooser = new ConfigChooserOVPN(objKernel,"server", objApplication);
			this.setConfigChooserObject(objChooser);
			
			//### 1. Voraussetzung: OpenVPN muss auf dem Rechner vorhanden sein. Bzw. die Dateiendung .ovpn ist registriert. 						
			//Die Konfigurations-Template Dateien finden
			String sDirectoryConfigPath = objChooser.readDirectoryConfigPath();
			
			File[] objaFileConfigTemplate = objChooser.findFileConfigOvpnTemplates(null);
			if(objaFileConfigTemplate==null){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "No configuration file (ending .ovpn) was found in the directory: '" + sDirectoryConfigPath + "'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}else if(objaFileConfigTemplate.length == 0){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "No configuration file (ending .ovpn) was found in the directory: '" + sDirectoryConfigPath + "'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}else{
				this.logStatusString(objaFileConfigTemplate.length + " configuration TEMPLATE file(s) was (were) found in the directory: '" + sDirectoryConfigPath + "'");  //Darueber kann dann ggf. ein Frontend den laufenden Process beobachten.
			}
			
			//####################################################################
			//### DAS SCHREIBEN DER NEUEN KONFIGURATION
					
			//+++ A) Vorbereitung			
			//+++ 1. Die früher mal verwendeten Dateien entfernen
			this.logStatusString("Removing former configuration file(s)."); //Dar�ber kann dann ggf. ein Frontend den laufenden Process beobachten.
			ReferenceArrayZZZ<String> strUpdate=new ReferenceArrayZZZ<String>(null);
			int itemp = objChooser.removeFileConfigUsed(null,strUpdate);			
			this.logStatusString(strUpdate);
			
			//+++ B) Die gefundenen Werte überall eintragen: IN neue Dateien
			this.logStatusString("Creating new configuration-file(s) from template-file(s), using new line(s)");					
			//ArrayList listaFileConfig = new ArrayList(objaFileConfigTemplate.length);
			for(int icount = 0; icount <= objaFileConfigTemplate.length-1; icount++){	
				File fileConfigTemplateOvpnUsed = objaFileConfigTemplate[icount];
				ServerConfigMapper4TemplateOVPN objMapper = new ServerConfigMapper4TemplateOVPN(objKernel, this, fileConfigTemplateOvpnUsed);
				this.setConfigMapperObject(objMapper);
				
				//Mit dem Setzen der neuen Datei, basierend auf dem Datei-Template wird intern ein Parser für das Datei-Template aktiviert
				ServerConfigTemplateUpdaterOVPN objUpdater = new ServerConfigTemplateUpdaterOVPN(objKernel, this, objChooser, objMapper, null);
				File objFileNew = objUpdater.refreshFileUsed(fileConfigTemplateOvpnUsed);	
				if(objFileNew==null){
					this.logStatusString("Unable to create 'used file' file base on template template: '" + objaFileConfigTemplate[icount].getPath() + "'");					
				}else{
					
					boolean btemp = objUpdater.update(objFileNew, true); //Bei false werden also auch Zeilen automatisch hinzugefügt, die nicht im Template sind. Z.B. Proxy-Einstellungen.
					if(btemp==true){
						this.logStatusString( "'Used file' successfully created for template: '" + objaFileConfigTemplate[icount].getPath() + "'");
		
						//+++ Nun dieses used-file dem Array hinzuf�gen, dass f�r den Start der OVPN-Verbindung verwendet wird.
						//listaFileConfig.add(objUpdater.getFileUsed());
						this.getConfigFileObjectsAll().add(objUpdater.getFileUsed());
					}else{
						this.logStatusString( "'Used file' not processed, based upon: '" + objaFileConfigTemplate[icount].getPath() + "'");					
					}	
				}
			}//end for
			
			//ABER: Es werden nur die Konfigurationsdateien verwendet, die auch konfiguriert sind. Falls nix konfiguriert ist, werden alle gefundenen verwendet.
			ArrayList<File> listaFileConfigUsed = this.getConfigFileObjectsAll();		
			String[] saFileConfigConfigured = objKernel.getParameterArrayWithStringByProgramAlias("OVPN", "ProgConfigHandler", "ConfigFile");
			File[] objaFileConfigUsed = objChooser.findFileConfigUsed(null);//.findFileConfigTemplate(null);
			
			listaFileConfigUsed.clear();//Baue die Liste der letztendlich genutzten Konfigurationen neu auf.			
			if(StringArrayZZZ.isEmpty(saFileConfigConfigured)){
				//Wenn true: Die Arraylist besteht nun aus allen konfigurierten Dateien
				for(int icount=0; icount <= objaFileConfigUsed.length-1; icount++){
					listaFileConfigUsed.add(objaFileConfigUsed[icount]);
				}
			}else{
				//Aus der Liste aller zur Verfügung stehenden Dateien nur diejenigen raussortieren, die konfiguriert sind.
				for(int iCounter=0;iCounter<=saFileConfigConfigured.length-1;iCounter++) {
					String sFileConfigConfigured = saFileConfigConfigured[iCounter];				
					StringTokenizer objToken = new StringTokenizer(sFileConfigConfigured, File.separator);
					while(objToken.hasMoreTokens()){
						String stemp = objToken.nextToken();
						for(int icount=0; icount <= objaFileConfigUsed.length-1; icount++){
							File objFileTemp = objaFileConfigUsed[icount];						
							boolean bIsExpandedOrSameFilename = KernelFileExpansionZZZ.isExpansionOrSameFilename(objFileTemp, stemp, 3);
							if(bIsExpandedOrSameFilename) {
								listaFileConfigUsed.add(objFileTemp);
							}
						}//END for
					}//END while
				}//END for
			}//END if
			if(listaFileConfigUsed.size()==0){
				this.logStatusString("No configuration available which is configured in Kernel Ini File for Program 'ProgConfigHandler' and Property 'ConfigFile'.");
				bReturn = false;
				break main;
			}else{
				String stemp = "";
				for(int icount=0; icount <= listaFileConfigUsed.size()-1; icount++){
					File objFileTemp = (File) listaFileConfigUsed.get(icount);
					if(stemp.equals("")){
						stemp = objFileTemp.getName();
					}else{
						stemp = stemp + "; " + objFileTemp.getName();	
					}					 
				}//END for
				this.logStatusString("Finally used configuration  file(s): " + stemp);
			}
			
			//#############
			//Auslesen der "erlaubten" Clients und Datei dafür in einem bestimmten Verzeichnis anlegen.			
			//Das wird alles in einem Handler gekapselt, den Namen (auch der Methode noch optimieren)
			ServerConfigOnServerAllowedClientFacadeOVPN objClientConfigHandler = new ServerConfigOnServerAllowedClientFacadeOVPN(this.getKernelObject(), this, null);
			boolean bSuccess = objClientConfigHandler.execute();
			
			//######################################################
			//2. Diverse Dinge mit WMI testen.
			KernelWMIZZZ objWMI = new KernelWMIZZZ(objKernel, null);
			
			//++++++++++++++++++++++++++++++
			//Starterlaubnis: Läuft schon ovpn ???
		   //TODO KernelWMIZZZ eines win32 - packages.
			File objFileExe = ServerConfigFileOVPN.findFileExe();   //Anders als im Client muss nix weiter gemacht werden
			if(objFileExe!=null){
				String sExeCaption = objFileExe.getName();
								
				boolean bproof = objWMI.isProcessRunning(sExeCaption);
				if(bproof==true){
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "There were found processes '"+ sExeCaption + "' still running. Will not start new ones. Quitting.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				
			}else{
				//Wahrscheinlich ist .ovpn garnicht als Dateiendung installiert. Abbrechen.
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "OVPN seems not to be installed. '" + objChooser.readDirectoryConfigPath() + "'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			this.logStatusString("Open VPN not yet running. Continue starting process.");
			
            //+++++++++++++++++++++++++++++++			
			//Starterlaubnis: Läuft der Domino Server schon ???
			//Falls nicht: Warte eine konfigurierte Zeit. (Merke: Nicht abbrechen, weil ja ggf. Probleme existieren oder der Server gar nicht starten soll).
			String sDominoCaption = objKernel.getParameterByProgramAlias("OVPN","ProgProcessCheck","Process2Check").getValue();
			if(!StringZZZ.isEmpty(sDominoCaption)){
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Warte auf Start von Prozess '" + sDominoCaption + "'");
				
				String sTimeoutSecond = objKernel.getParameterByProgramAlias("OVPN","ProgProcessCheck","CheckTimeout").getValue();
				if(StringZZZ.isEmpty(sTimeoutSecond)){
					sTimeoutSecond = "1";
				}				
				int iTimeoutSecond = Integer.parseInt(sTimeoutSecond);
				boolean bProof = false;
				do{					
					bProof = objWMI.isProcessRunning(sDominoCaption);
					if(bProof == false){
						try{
							Thread.sleep(1000); //DIESEN THREAD fuer 1 Sekunde anhalten
							iTimeoutSecond--;
							//System.out.println(iTimeoutSecond);
							if(iTimeoutSecond<=0){
								ExceptionZZZ ez = new ExceptionZZZ(sERROR_RUNTIME_TIMEOUT + "Waiting for domino-servertask to start - '" + sDominoCaption + "'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
								throw ez;
							}
						}catch(InterruptedException e){
							ExceptionZZZ ez = new ExceptionZZZ(sERROR_RUNTIME + "Thread.sleep(...). " + e.getMessage(), iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
							throw ez;
						}
					}//bProof == false
				}while(bProof==false);
				this.logStatusString("Depending process '" + sDominoCaption + "' running. Continue starting process.");
			}//END if sDominoCaption isempty
			
			
			//##########################################################################################
			//+++ Diese OVPN-Konfigurationsfiles zum Starten der VPN-Verbindung verwenden !!!
			Thread[] threadaOVPN = new Thread[listaFileConfigUsed.size()];
			ProcessWatchRunnerZZZ[] runneraOVPN = new ProcessWatchRunnerZZZ[listaFileConfigUsed.size()];	
			int iNumberOfProcessStarted = 0;
			for(int icount=0; icount <= listaFileConfigUsed.size()-1; icount++){
				File objFileConfigOvpn = (File) listaFileConfigUsed.get(icount);
				String sAlias = Integer.toString(icount);
				String[] saTemp = {"ByBatch"}; //Weil auf dem Server der endgültige auszuführende Befehl über eine Batch gegeben werden muss. Herausgefunden durch Try and Error.
				//String[] saTemp = null; //Problem: Wenn man eine Batch startet, kann man nicht auf den Output eines in der Batch gestarteten Programs zugreifen.
				File objFileTemplateBatch = objChooser.findFileConfigBatchTemplateFirst();				
				ServerConfigStarterOVPN objStarter = new ServerConfigStarterOVPN(objKernel, (IMainOVPN) this, objFileTemplateBatch, objFileConfigOvpn, sAlias, saTemp);
				this.logStatusString("Requesting start of process #"+ icount + " (File: " + objFileConfigOvpn.getName() + ")");				
				Process objProcessTemp = objStarter.requestStart();			
				if(objProcessTemp==null){
					//Hier nicht abbrechen, sondern die Verarbeitung bei der n�chsten Datei fortf�hren
					this.logStatusString( "Unable to create process, using file: '"+ objStarter.getFileConfigOvpn().getPath()+"'");
				}else{			
					this.addProcessStarter(objStarter);
					
					//NEU: Einen anderen Thread zum "Monitoren" des Inputstreams des Processes verwenden. Dadurch werden die anderen Prozesse nicht angehalten.
					 runneraOVPN[icount] =new ProcessWatchRunnerZZZ(objKernel, objProcessTemp,icount, null);
					 threadaOVPN[icount] = new Thread(runneraOVPN[icount]);					
					 threadaOVPN[icount].start();
					 iNumberOfProcessStarted++;	
					//Das bl�ht das Log unn�tig auf .... zum Test aber i.o.
					 this.logStatusString("Finished starting thread # " + icount + " for listening to connection.");
					 this.setFlag("WatchRunnerStarted", true);
				}				
			}//END for
			
			//Merke: Es ist nun Aufgabe des Frontends einen Thread zu starten, der den Verbindungsaufbau und das "aktiv sein" der Processe monitored.			
			
			
		   bReturn = true;
	}//END main
		this.setFlag("isstarted", bReturn);
		return bReturn;
	}//END start()
	
	public void run() {
		try {
			this.start();
		} catch (ExceptionZZZ ez) {
			try {
				this.setFlag("haserror", true);
				this.getKernelObject().getLogObject().WriteLineDate(ez.getDetailAllLast());
			} catch (ExceptionZZZ e) {				
				e.printStackTrace();
			}			
		}
	}
	
	/** Adds a line to the status arraylist. This status is used to enable the frontend-client to show a log dialogbox.
	 * Remark: This method does not write anything to the kernel-log-file. 
	* @param sStatus 
	* 
	* lindhaueradmin; 13.07.2006 08:34:56
	 */
	public void addStatusString(String sStatus){
		if(sStatus!=null){
			this.sStatusCurrent = sStatus;
			this.listaStatus.add(sStatus);
		}
	}
	
	/**Adds a line to the status arraylist PLUS writes a line to the kernel-log-file.
	 * Remark: The status arraylist is used to enable the frontend-client to show a log dialogbox.
	* @param sStatus 
	* 
	* lindhaueradmin; 13.07.2006 08:38:51
	 * @throws ExceptionZZZ 
	 */
	public void logStatusString(String sStatus) throws ExceptionZZZ{
		if(sStatus!=null){
			this.addStatusString(sStatus);
			
			IKernelZZZ objKernel = this.getKernelObject();
			if(objKernel!= null){
				objKernel.getLogObject().WriteLineDate(sStatus);
			}
		}
	}
	
	public void logStatusString(String[] saStatus) throws ExceptionZZZ {
		if(saStatus!=null) {
			int iMax = Array.getLength(saStatus)-1;
			for(int icount=0;icount<=iMax;icount++) {
				this.logStatusString(saStatus[icount]);
			}
		}		
	}
	
	public void logStatusString(ArrayList<String>lista) throws ExceptionZZZ {
		String[]sa = ArrayListUtilZZZ.toStringArray(lista);
		this.logStatusString(sa);
	}
	
	public void logStatusString(ReferenceArrayZZZ<String>strStatus) throws ExceptionZZZ {
		ArrayList<String>listas=strStatus.getArrayList();
		this.logStatusString(listas);
	}
	
	/**This status is a type of "Log".
	 * This is the last entry.
	 * This is filled by ".addStatusString(...)"
	 * @return String
	 *
	 * javadoc created by: 0823, 17.07.2006 - 09:00:55
	 */
	public String getStatusStringCurrent(){
		return this.sStatusCurrent;
	}

	/**This status is a type of "Log".
	 * This are all entries.
	 * This is filled by ".addStatusString(...)"
	 * @return String
	 *
	 * javadoc created by: 0823, 17.07.2006 - 09:00:55
	 */
	public ArrayList getStatusStringAll(){
		return this.listaStatus;
	}
	
	 public  boolean addProcessStarter(ServerConfigStarterOVPN objStarter){
		 boolean bReturn = false;
		 main:{
			 check:{
				 if(objStarter==null) break main;
			 }//END check
		 
		 	this.listaConfigStarter.add(objStarter);
		 
		 }//END main
		 return bReturn;
	 }
	 
	 /**returns a ConfigStarterZZZ-object.
	 * @param iPosition, Remark: Starting at index postion 1
	 * @return boolean
	 *
	 * javadoc created by: 0823, 24.07.2006 - 09:32:09
	 */
	public ServerConfigStarterOVPN getProcessStarter(int iPosition){
		ServerConfigStarterOVPN objReturn=null;
		 main:{
			 check:{				
				 if(iPosition<= 0 || iPosition > listaConfigStarter.size()) break main;
			 }//END check
		 	objReturn = (ServerConfigStarterOVPN) listaConfigStarter.get(iPosition);
		 	
		 }//END main
		 return objReturn;
	 }
	
	/**Returns an ArrayList,which contains all ConfigStarterZZZ-objects
	 * @return ArrayList
	 *
	 * javadoc created by: 0823, 28.07.2006 - 13:51:51
	 */
	public ArrayList getProcessStarterAll(){
		return listaConfigStarter;
	}
	
	public boolean isListenOnStart() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			check:{
				if(this.objKernel==null) break main;				
			}//END check:
		
		//Das setzt voraus, das die Kernel-Konfigurationsdatei eine Modul-Section enthaelt, die wie der Application - Key aussieht. 
		String stemp = this.objKernel.getParameter("ListenOnStart").getValue();
		if(stemp==null) break main;
		if(stemp.equals("1")){
			bReturn = true;
		}
		}//END main
		return bReturn;
	}
		
//	######### GetFlags - Handled ##############################################
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
			}else if(stemp.equals("watchrunnerstarted")){
				bFunction = bFlagWatchRunnerStarted;
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
		}else if(stemp.equals("watchrunnerstarted")){
				bFlagWatchRunnerStarted = bFlagValue;
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

	//##### GETTER / SETTER
//	public ConfigChooserOVPN getConfigChooserObject() {
//		return this.objConfigChooser;
//	}
//	public void setConfigChooserObject(ConfigChooserOVPN objConfigChooser) {
//		this.objConfigChooser = objConfigChooser;
//	}
//	
//	public ServerConfigMapperOVPN getConfigMapperObject() {
//		return this.objConfigMapper;
//	}
//	public void setConfigMapperObject(ServerConfigMapperOVPN objConfigMapper) {
//		this.objConfigMapper = objConfigMapper;
//	}
//	
//	public ServerApplicationOVPN getApplicationObject() {
//		return this.objApplication;
//	}
//	public void setApplicationObject(ServerApplicationOVPN objApplication) {
//		this.objApplication = objApplication;
//	}
	
	
	public ArrayList<File> getConfigFileObjectsAll() {
		if(this.listaConfigFile==null) {
			this.listaConfigFile = new ArrayList<File>();
		}
		return this.listaConfigFile;
	}
	public void setConfigFileObjectsAll(ArrayList<File> listaConfigFile) {
		this.listaConfigFile = listaConfigFile;
	}
}//END class
