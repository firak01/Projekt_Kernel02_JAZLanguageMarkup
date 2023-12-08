package basic.zKernel.net.server.ovpn01alt;

import java.io.File;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;

public interface IConfigMapper4TemplateOVPN extends IConfigMapperOVPN{	
	public void setFileTemplateOvpnUsed(File fileTemplateOvpn);
	public File getFileTemplateOvpnUsed();
	
	public String getConfigRegExp(String sConfig) throws ExceptionZZZ;	
}
