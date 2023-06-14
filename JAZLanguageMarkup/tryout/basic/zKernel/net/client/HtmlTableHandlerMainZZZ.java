package basic.zKernel.net.client;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;

public class HtmlTableHandlerMainZZZ extends AbstractMainZZZ {	
	/**Entry point for the OVPN-Client-Starter.
	 * @return void
	 * @param args 
	 * 
	 * lindhaueradmin; 08.07.2006 08:24:16
	 */
	public static void main(String[] args) {
		try {
			HtmlTableHandlerMainZZZ objClient = new HtmlTableHandlerMainZZZ();		
			objClient.start(args);
		} catch (ExceptionZZZ ez) {			
				ez.printStackTrace();
				System.out.println(ez.getDetailAllLast());			
		}//END Catch
	}//END main()
	
	public HtmlTableHandlerMainZZZ() throws ExceptionZZZ {
		super();
	}
	public HtmlTableHandlerMainZZZ(IKernelZZZ objKernel, String[] saFlagControl) throws ExceptionZZZ {
		super(objKernel, saFlagControl);
	}
	
	public boolean start(String[] saArg){
		boolean bReturn = false;
		main:{						
			try {
				//Parameter aus args auslesen
				String[]saFlag = {"useExpression","useFormula"};
				ConfigHtmlTableHandlerZZZ objConfig = new ConfigHtmlTableHandlerZZZ(saArg, saFlag);
				IKernelZZZ objKernel = new KernelZZZ(objConfig, (String) null); //Damit kann man über die Startparameter ein anders konfiguriertes Kernel-Objekt erhalten.
				this.setKernelObject(objKernel);
																
				//DAS BACKEND handlebar machen
				IApplicationZZZ objApplication = new HtmlTableHandlerApplicationZZZ(objKernel, null);
				objApplication.setMainObject(this);
				this.setApplicationObject(objApplication);
				
				//TEST:
				String sUrl = objApplication.getUrlToParse();
				System.out.println("URL to parse ='" + sUrl + "'");
				
				
				
				
				//DAS UI handlebar machen
				//### 1. Voraussetzung: OpenVPN muss auf dem Rechner vorhanden sein. Bzw. die Dateiendung .ovpn ist registriert. 
				//this.objClientTray = new ClientTrayUIZZZ(objKernel, this.objClientMain, (String[]) null);				 			
			} catch (ExceptionZZZ ez) {
				if(objKernel!=null){
					LogZZZ objLog = objKernel.getLogObject();
					if(objLog!=null){
						objLog.WriteLineDate(ez.getDetailAllLast());
					}else{
						ez.printStackTrace();
						System.out.println(ez.getDetailAllLast());
					}				
				}else{
					ez.printStackTrace();
					System.out.println(ez.getDetailAllLast());
				}
			}//END Catch
			}//END main:
		System.out.println("finished starting application.");
		return bReturn;			
	}
	
	public boolean start() throws ExceptionZZZ{
		return false;
	}

	@Override
	public void run() {
		try {
			this.start();
		} catch (ExceptionZZZ e) {
			try {
				this.setFlag("haserror", true);
			} catch (ExceptionZZZ e1) {				
				e1.printStackTrace();
			}
			this.getKernelObject().getLogObject().WriteLineDate(e.getDetailAllLast());
		}
	}
	
}//END class
