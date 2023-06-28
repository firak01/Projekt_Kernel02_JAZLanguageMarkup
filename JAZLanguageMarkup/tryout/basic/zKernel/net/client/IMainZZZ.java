package basic.zKernel.net.client;

import basic.zBasic.ExceptionZZZ;

public interface IMainZZZ extends IApplicationUserZZZ,IMainConstantZZZ{
	public boolean start(String[] args);	
	public void logStatusString(String sStatus) throws ExceptionZZZ;
	public void addStatusString(String sStatus);
	public String getJarFilePathUsed();
	
	
	//public ConfigChooserOVPN getConfigChooserObject();
	//public void setConfigChooserObject(ConfigChooserOVPN objConfigChooser);
}
