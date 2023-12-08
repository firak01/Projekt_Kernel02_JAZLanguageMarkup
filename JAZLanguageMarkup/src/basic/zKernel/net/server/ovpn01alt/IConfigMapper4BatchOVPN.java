package basic.zKernel.net.server.ovpn01alt;

import java.io.File;
import java.util.HashMap;

public interface IConfigMapper4BatchOVPN extends IConfigMapperOVPN{
	public void setFileTemplateBatchUsed(File fileTemplateBatch);
	public File getFileTemplateBatchUsed();
	
	public void setFileConfigOvpnUsed(File fileConfigOvpn);//fertige OVPN Konfigurationsdatei
	public File getFileConfigOvpnUsed();
}
