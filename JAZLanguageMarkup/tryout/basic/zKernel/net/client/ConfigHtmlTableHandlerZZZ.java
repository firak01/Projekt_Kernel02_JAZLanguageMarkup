package basic.zKernel.net.client;

import static java.lang.System.out;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.AbstractKernelConfigZZZ;
import basic.zKernel.config.KernelConfigEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class ConfigHtmlTableHandlerZZZ  extends AbstractKernelConfigZZZ{
	private static String sPROJECT_NAME = "JAZLanguageMarkup";
	private static String sPROJECT_PATH = "Projekt_Kernel02_JAZLanguageMarkup";
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
	public String getProjectName() {
		return ConfigHtmlTableHandlerZZZ.sPROJECT_NAME;
	}
	@Override
	public String getProjectDirectory() {
		return ConfigHtmlTableHandlerZZZ.sPROJECT_PATH;
	}
}