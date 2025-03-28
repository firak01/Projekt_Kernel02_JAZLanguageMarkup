package basic.zKernel.net.server.ovpn01alt;

import java.io.File;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapIterableKeyZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;


public abstract class AbstractConfigStarterOVPN extends AbstractKernelUseObjectZZZ implements IConfigStarterOVPN, IMainUserOVPN, IConfigMapper4BatchUserOVPN{
	private IMainOVPN objMain = null;
	private IConfigMapper4BatchOVPN objMapper4Batch = null;
	
	private File objFileConfigOvpn=null;
	private File objFileTemplateBatch=null;
	private Process objProcess=null;
	private String sMyAlias = "-1";
	private boolean bFlagByBatch = false; //Merke: Das Problem. Wenn man einen Process per Batch startet, bekommt man erst einmal diese PID nicht.
										  //       Die Process ID gibt es über einen Umweg:
	                                      //       https://stackoverflow.com/questions/5284139/how-do-i-find-the-process-id-pid-of-a-process-started-in-java
										  //
	                                      //       Erst ab JDK 9 gibt es wohl die Möglichkeit mit der Klasse ProcessHandle 
	                                      //       https://www.baeldung.com/java-process-api
										  //       ... Optional<ProcessHandle> optionalProcessHandle = ProcessHandle.of(pid);
	String sOvpnContextClientOrServer=null;
		
	public AbstractConfigStarterOVPN(IKernelZZZ objKernel, IMainOVPN objMain, File objFileOConfigvpn, String sMyAlias, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel);
		ConfigStarterNew_(objMain, sMyAlias, null, objFileOConfigvpn, saFlagControl);
	}
	
	public AbstractConfigStarterOVPN(IKernelZZZ objKernel, IMainOVPN objMain, File objFileTemplateBatch, File objFileOConfigvpn, String sMyAlias, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel);
		ConfigStarterNew_(objMain, sMyAlias, objFileTemplateBatch, objFileOConfigvpn, saFlagControl);
	}
	
	/**Choose this constructor, if a you don�t want to use the .getNumber() - Method.
	 * 
	 * @param objKernel
	 * @param objFile
	 * @param saFlagControl
	 * @throws ExceptionZZZ
	 */
	public AbstractConfigStarterOVPN(IKernelZZZ objKernel, IMainOVPN objMain, File objFileTemplateBatch, File objFileConfigOvpn, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel);
		ConfigStarterNew_(objMain, "-1", objFileTemplateBatch, objFileConfigOvpn, saFlagControl);
	}
	
	private void ConfigStarterNew_(IMainOVPN objMain, String sMyAlias, File objFileTemplateBatch, File objFileConfigOvpn, String[] saFlagControl) throws ExceptionZZZ{
		main:{
				 
			check:{
		 		
				if(saFlagControl != null){
					String stemp; boolean btemp;
					for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
						stemp = saFlagControl[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){ 								   
							   ExceptionZZZ ez = new ExceptionZZZ( stemp, IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 							 
							   throw ez;		 
						}
					}
					if(this.getFlag("init")) break main;
				}
				
				if(objMain==null) {
					ExceptionZZZ ez = new ExceptionZZZ("MainApplicationObject", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				this.setMainObject(objMain);
				
				if(objFileConfigOvpn==null){
					ExceptionZZZ ez = new ExceptionZZZ("OvpnConfigurationFile", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				this.setFileConfigOvpn(objFileConfigOvpn);
				
				
				//Falls nicht explizit das Flag gesetzt ist, wirf keinen Fehler. Übernimm dann trotzdem die Batchdatei - wenn übergeben -, was man hat dat hat man. 
				if(objFileTemplateBatch==null){
					if(this.getFlag("byBatch")) {
						ExceptionZZZ ez = new ExceptionZZZ("BatchConfigurationFile", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}else{
					this.setFileTemplateBatch(objFileTemplateBatch);
				}
				
				
				this.sMyAlias = sMyAlias;
				
				String sOvpnContextClientOrServer = StringZZZ.left(objFileConfigOvpn.getName(), "_");
				this.setOvpnContextUsed(sOvpnContextClientOrServer);
			}//End check
		}//END main
	}
	
		
	/**Should destroy a process. But this does not work.
	 * A workaround used, is to kill all openvpn.exe - processes when the cientUI is closed/stopped. 
	 * @return void
	 *
	 * javadoc created by: 0823, 11.07.2006 - 12:55:49
	 */
	public void requestStop(){
		main:{
			Process objProcess = null;
			check:{
				objProcess = this.objProcess;
				if(objProcess==null) break main;
			}
		
			objProcess.destroy();
		}//END main
	}
	
	public boolean isProcessAlive(){
		boolean bReturn = false;
		main:{
			check:{
				if(this.objProcess==null) break main;				
			}//END check:
		
			try{
				//TODO GOON den exit status des Processes auch sicher abpr�fbar machen !!!
				//Merke: Einen Exit-Status abzurufen, wenn der Process noch l�uft, wirft eine IllegalThreadStateException
				this.objProcess.exitValue();
				return false;
			}catch(IllegalThreadStateException e){
				return true;
			}	
		
		}//END Main:
		return bReturn;
	}

	
	//###### GETTER  / SETTER
	public IMainOVPN getMainObject() {
		return this.objMain;
	}
	public void setMainObject(IMainOVPN objMain) {
		this.objMain = objMain;
	}
	
	@Override
	public IConfigMapper4BatchOVPN getConfigMapperObject() throws ExceptionZZZ {
		IConfigMapper4BatchOVPN objReturn = null;
		if(this.objMapper4Batch==null) {			
			objReturn = this.createConfigMapperObject();
			this.setConfigMapperObject(objReturn);		
		}
		return this.objMapper4Batch;	
	}
	
	public abstract IConfigMapper4BatchOVPN createConfigMapperObject() throws ExceptionZZZ;
		
	
	@Override
	public void setConfigMapperObject(IConfigMapper4BatchOVPN objConfigMapper) {
		this.objMapper4Batch = objConfigMapper;
	}
	
	public void setFileConfigOvpn(File objFileConfigOvpn){
		this.objFileConfigOvpn = objFileConfigOvpn;
	}
	public File getFileConfigOvpn(){
		return this.objFileConfigOvpn;
	}
	
		
	@Override
	public void setFileTemplateBatch(File objFileTemplateBatch) {
		this.objFileTemplateBatch = objFileTemplateBatch;
	}

	@Override
	public File getFileTemplateBatch() {
		return this.objFileTemplateBatch;
	}
	
	public Process getProcess(){
		return this.objProcess;
	}
	public void setProcess(Process objProcess) {
		this.objProcess = objProcess;
	}
	
	/**This is not the process id.
	 * It is just an alias, which was provided at the constructor.
	 * @return int
	 *
	 * javadoc created by: 0823, 28.07.2006 - 15:04:25
	 */
	public String getAlias(){
		return this.sMyAlias;
	}
	
	public String getOvpnContextUsed() {
		return this.sOvpnContextClientOrServer;
	}
	public void setOvpnContextUsed(String sOvpnContextClientOrServer) {
		this.sOvpnContextClientOrServer = sOvpnContextClientOrServer;
	}
	
	
//	###### FLAGS
	/* (non-Javadoc)
	@see zzzKernel.basic.KernelObjectZZZ#getFlag(java.lang.String)
	Flags used: 
	- hasError
	- hasOutput
	- hasInput
	- stoprequested
	 */
	public boolean getFlag(String sFlagName){
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.getFlag(sFlagName);
			if(bFunction==true) break main;
							
			//getting the flags of this object
			String stemp = sFlagName.toLowerCase();
			if(stemp.equals("bybatch")){
				bFunction = bFlagByBatch;
				break main;
			}
			/*
			else if(stemp.equals("hasoutput")){
				bFunction = bFlagHasOutput;
				break main;
			}else if(stemp.equals("hasinput")){
				bFunction = bFlagHasInput;
				break main;
			}else if(stemp.equals("stoprequested")){
				bFunction = bFlagStopRequested;
				break main;
			}
			*/
	
		}//end main:
		return bFunction;
	}

	/**
	 * @see zzzKernel.basic.KernelUseObjectZZZ#setFlag(java.lang.String, boolean)
	 * @param sFlagName
	 * Flags used:<CR>
	 	- hasError
	- hasOutput
	- hasInput
	- stoprequested
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
		if(stemp.equals("bybatch")){
			bFlagByBatch = bFlagValue;
			bFunction = true;
			break main;
		}
		/*
		else if(stemp.equals("hasoutput")){
			bFlagHasOutput = bFlagValue;
			bFunction = true;
			break main;
		}else if(stemp.equals("hasinput")){
			bFlagHasInput = bFlagValue;
			bFunction = true;
			break main;
		}else if(stemp.equals("stoprequested")){
			bFlagStopRequested = bFlagValue;
			bFunction = true;
			break main;
		}
		*/
		}//END main:
		return bFunction;
	}

	@Override
	public abstract Process requestStart() throws ExceptionZZZ;
	
	@Override
	public ArrayList<String> computeBatchLines(File fileConfigTemplateBatch, File fileConfigTemplateOvpn) throws ExceptionZZZ {		
		ArrayList<String>listasReturn=new ArrayList<String>();
		main:{			
			IConfigMapper4BatchOVPN objMapperBatch = this.getConfigMapperObject(); //new ServerConfigMapper4BatchOVPN(this.getKernelObject(), this.getServerObject(), fileConfigTemplateBatch);
			HashMapIterableKeyZZZ<String, String>hmBatchLines = objMapperBatch.readTaskHashMap();								
							
			for(String sKey : hmBatchLines) {
				String sLine = hmBatchLines.getValue(sKey);
				listasReturn.add(sLine);
			}
			
		}
		return listasReturn;
	}	
	
}//END class
