package basic.zKernel.net.client;

public interface IMainZZZ extends IApplicationUserZZZ,IMainConstantZZZ{
	public boolean start(String[] args);	
	public void logStatusString(String sStatus);
	public void addStatusString(String sStatus);
	public String getJarFilePathUsed();
	
	
	//public ConfigChooserOVPN getConfigChooserObject();
	//public void setConfigChooserObject(ConfigChooserOVPN objConfigChooser);
}
