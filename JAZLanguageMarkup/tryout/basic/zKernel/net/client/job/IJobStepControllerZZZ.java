package basic.zKernel.net.client.job;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;

public interface IJobStepControllerZZZ extends IJobUserZZZ{
	
	public ArrayList<IJobStepZZZ>getJobSteps();
	void setJobSteps(ArrayList<IJobStepZZZ> listaJobStep);
	
	boolean process() throws ExceptionZZZ;
	
}
