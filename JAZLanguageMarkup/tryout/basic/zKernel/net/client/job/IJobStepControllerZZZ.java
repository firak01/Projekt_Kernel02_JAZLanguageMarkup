package basic.zKernel.net.client.job;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;

public interface IJobStepControllerZZZ extends IJobUserZZZ{
	
	public ArrayList<IJobStepZZZ>getJobSteps();
	void setJobSteps(ArrayList<IJobStepZZZ> listaJobStep);
	
	public IJobStepZZZ getJobStep(String sJobStepAlias) throws ExceptionZZZ;
	public IJobStepOutputProviderZZZ getJobStepForOutput(String sJobStepAlias) throws ExceptionZZZ; 
	
	boolean process() throws ExceptionZZZ;
	
}
