package basic.zKernel.net.client.job;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import custom.zKernel.LogZZZ;

public abstract class AbstractJobStepControllerZZZ implements IJobStepControllerZZZ{
	private IJobZZZ objJob = null;
	private ArrayList<IJobStepZZZ> listaJobStep = null;
		
	public AbstractJobStepControllerZZZ()throws ExceptionZZZ{
		//super();
	}
	public AbstractJobStepControllerZZZ(IJobZZZ objJob)throws ExceptionZZZ{
		//super(objJob);
		AbstractJobStepControllerNew_(objJob);
	}
	
	private boolean AbstractJobStepControllerNew_(IJobZZZ objJob) throws ExceptionZZZ{
		this.setJob(objJob);
		return true;
	}
	
	@Override
	public ArrayList<IJobStepZZZ> getJobSteps() {
		if(this.listaJobStep==null) {
			this.listaJobStep = new ArrayList<IJobStepZZZ>();
		}
		return this.listaJobStep;
	}
	
	@Override
	public void setJobSteps(ArrayList<IJobStepZZZ> listaJobStep) {
		this.listaJobStep = listaJobStep;
	}
	
	@Override
	public abstract boolean process() throws ExceptionZZZ;

	//### aus IJobUserZZZ
	@Override
	public IJobZZZ getJob() {
		return this.objJob;
	}
	@Override
	public void setJob(IJobZZZ objJob) {
		this.objJob = objJob;
	}
	
	//### aus IKernelUserZZZ
	@Override
	public IKernelZZZ getKernelObject() {
		return this.getJob().getApplicationObject().getKernelObject();
	}
	@Override
	public void setKernelObject(IKernelZZZ objKernel) {
		this.getJob().getApplicationObject().setKernelObject(objKernel);
	}
	@Override
	public LogZZZ getLogObject() {
		return this.getKernelObject().getLogObject();
	}
	@Override
	public void setLogObject(LogZZZ objLog) {
		this.getKernelObject().setLogObject(objLog);
	}
	@Override
	public void logLineDate(String sLog) {
		this.getLogObject().logLineDate(sLog);
	}
}
