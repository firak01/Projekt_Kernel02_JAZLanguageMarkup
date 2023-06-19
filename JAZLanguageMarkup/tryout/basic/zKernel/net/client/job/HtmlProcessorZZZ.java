package basic.zKernel.net.client.job;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.net.client.IApplicationZZZ;

public class HtmlProcessorZZZ extends AbstractJobZZZ{
	public static String sJOB_ALIAS="HtmlProcessorJob";
	
	public HtmlProcessorZZZ() throws ExceptionZZZ {	
		super();
	}
	
	public HtmlProcessorZZZ(IApplicationZZZ objApplication) throws ExceptionZZZ {
		super(objApplication);
	}
	
	@Override
	public String getJobAliasCustom() throws ExceptionZZZ {
		return sJOB_ALIAS;
	}

	@Override
	public boolean process() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//1. Definiere die JobSteps und ihren Controller
			JobStepControllerZZZ objController = new JobStepControllerZZZ(this);
			ArrayList<IJobStepZZZ> listaStep = objController.getJobSteps();
			
			//1.1 HtmlTableHead
			IJobStepOutputProviderZZZ stepHtmlTableHeader = new JobStepHtmlTableColumnHeaderZZZ(objController);
			listaStep.add((IJobStepZZZ) stepHtmlTableHeader);
			
			//1.1 HtmlTableWriter
			IJobStepZZZ stepHtmlReader = new JobStepHtmlTableWriteZZZ(objController);
			listaStep.add(stepHtmlReader);

			
			//2. Verarbeiten
			boolean bSuccess = objController.process();
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
}
