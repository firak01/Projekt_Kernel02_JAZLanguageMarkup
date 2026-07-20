package basic.zKernel.net.client;

import static java.lang.System.out;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.AbstractKernelConfigZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class ConfigHtmlTableHandlerZZZ  extends AbstractKernelConfigZZZ{
	
	//#################################################
	//Merke: Die Konstanten sind meist nicht final, damit sie von der konkreten Konfiguration
	//       ueberschrieben werden koennen.
	//       Final sind die fuer den Kernel selbst wichtige Konstanten
	
	//#####################################################################
	//####### Reflektion zum Gesamtprojekt
	static String sPROJECT_DIRECTORY = "Projekt_Kernel02_JAZLanguageMarkup";
	static String sPROJECT_NAME = "JAZLanguageMarkup";
	
	public ConfigHtmlTableHandlerZZZ() throws ExceptionZZZ{
		super();
	}
	public ConfigHtmlTableHandlerZZZ(String[] saArg) throws ExceptionZZZ {
		super(saArg); 
	} 
	public ConfigHtmlTableHandlerZZZ(String[] saArg, String[] saFlagControl) throws ExceptionZZZ {
		super(saArg, saFlagControl); 
	} 
	public ConfigHtmlTableHandlerZZZ(String[] saArg, String sFlagControl) throws ExceptionZZZ {
		super(saArg, sFlagControl); 
	}
	
	
	public String getApplicationKeyDefault() {
		return "HtmlTableHandler"; 
	}
	
	public String getSystemNumberDefault() {
		return "01";
	}

	public String getConfigDirectoryNameDefault() {
		return "<z:Null/>";
	}
	
	public String getConfigFileNameDefault() {
		return "ZKernelConfig_HtmlTableHandler.ini";
	}
	
	@Override
	public String getProjectNameDefault() throws ExceptionZZZ {
		return ConfigHtmlTableHandlerZZZ.sPROJECT_NAME;
	}
	
	@Override
	public String getProjectDirectoryDefault() throws ExceptionZZZ {
		return ConfigHtmlTableHandlerZZZ.sPROJECT_DIRECTORY;
	}
}