package basic.zKernel.net.client;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;

public abstract class AbstractMainZZZ extends KernelUseObjectZZZ implements Runnable,IMainZZZ{
	private IApplicationZZZ objApplication = null;
	//UI ... private ClientTrayUIZZZ objClientTray=null;
	
	private String sStatusCurrent = null; //Hierueber kann das Frontend abfragen, was gerade in der Methode "start()" so passiert.
	private ArrayList listaStatus = new ArrayList(); //Hierueber werden alle gesetzten Stati, die in der Methode "start()" gesetzt wurden festgehalten.
	                                                                      //Ziel: Das Frontend soll so Infos im laufende Prozess per Button-Click abrufen k�nnen.

	public AbstractMainZZZ() throws ExceptionZZZ {
		super();
	}
	public AbstractMainZZZ(IKernelZZZ objKernel, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel, saFlagControl);
	}
	
	/**Adds a line to the status arraylist PLUS writes a line to the kernel-log-file.
	 * Remark: The status arraylist is used to enable the frontend-client to show a log dialogbox.
	* @param sStatus 
	* 
	* lindhaueradmin; 13.07.2006 08:38:51
	 */
	public void logStatusString(String sStatus){
		if(sStatus!=null){
			this.addStatusString(sStatus);
			
			IKernelZZZ objKernel = this.getKernelObject();
			if(objKernel!= null){
				objKernel.getLogObject().WriteLineDate(sStatus);
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
	
	
	//##### GETTER / SETTER
	public String getJarFilePathUsed() {
		return AbstractMainZZZ.sJAR_FILE_USED;
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
	

	@Override
	public abstract void run();

	@Override
	public IApplicationZZZ getApplicationObject() {
		return this.objApplication;
	}

	@Override
	public void setApplicationObject(IApplicationZZZ objApplication) {
		this.objApplication = objApplication;
	}
}
