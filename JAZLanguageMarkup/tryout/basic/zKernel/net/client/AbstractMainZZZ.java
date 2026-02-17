package basic.zKernel.net.client;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.component.AbstractKernelModuleZZZ;

public abstract class AbstractMainZZZ extends AbstractKernelModuleZZZ implements Runnable,IMainZZZ{
	protected IApplicationZZZ objApplication = null;
	//UI ... private ClientTrayUIZZZ objClientTray=null;
	
	protected String sStatusCurrent = null; //Hierueber kann das Frontend abfragen, was gerade in der Methode "start()" so passiert.
	protected ArrayList<String> listaStatus = new ArrayList<String>(); //Hierueber werden alle gesetzten Stati, die in der Methode "start()" gesetzt wurden festgehalten.
	                                                                      //Ziel: Das Frontend soll so Infos im laufende Prozess per Button-Click abrufen koennen.

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
	 * @throws ExceptionZZZ 
	 */
	public void logStatusString(String sStatus) throws ExceptionZZZ{
		if(sStatus!=null){
			//this.addStatusString(sStatus);
			
			IKernelZZZ objKernel = this.getKernelObject();
			if(objKernel!= null){
				objKernel.getLogObject().writeLineDate(sStatus);
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
//	public ArrayList getStatusStringAll(){
//		return this.listaStatus;
//	}
	

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
	
	//### Aus IKernelModuleZZZ
	@Override
	public boolean reset() {
		// TODO Auto-generated method stub
		return false;
	}
}
