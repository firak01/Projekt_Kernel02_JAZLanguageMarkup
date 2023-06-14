package basic.zKernel.net.client;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.net.client.KernelPingHostZZZ;

public abstract class AbstractApplicationZZZ extends KernelUseObjectZZZ implements IMainUserZZZ{
	//Der Bezug zur Startenden Klasse
	private IMainZZZ objMain = null;
	
	//Merke: Die Eigenschaften dieser Applikation sind in den daraus erbenden Klassen hinterlegt. Sie stehen uber das Application Objekt ueberall zur Verfuegung.
	//..............
	
	
	public AbstractApplicationZZZ(IKernelZZZ objKernel, IMainZZZ objMain) throws ExceptionZZZ {
		super(objKernel);
		this.setMainObject(objMain);
	}

	//###### GETTER  / SETTER aus dem Interface IMainZZZ
	public IMainZZZ getMainObject() {
		return this.objMain;
	}
	public void setMainObject(IMainZZZ objMain) {
		this.objMain = objMain;
	}
}
