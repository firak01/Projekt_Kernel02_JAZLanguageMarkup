package basic.zKernel.net.client;

import basic.zBasic.ExceptionZZZ;

public interface IApplicationZZZ extends IMainUserZZZ{
	
	//Aktuelle Eigenschaften
	String getUrlToParse() throws ExceptionZZZ;
	
	
	//ALTE EIGENSCHAFTEN AUS OVPN PROJEKT
	//TODO Loeschen
	String getProxyHost();

	String getProxyPort();

	String getIpIni() throws ExceptionZZZ;
	
	String getIpLocal() throws ExceptionZZZ;
	
	String getVpnIpLocal() throws ExceptionZZZ;

	String getTapAdapterUsed() throws ExceptionZZZ;

	String getVpnIpRemote() throws ExceptionZZZ;

}
