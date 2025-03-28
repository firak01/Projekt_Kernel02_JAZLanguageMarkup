package basic.zKernel.net.server.ovpn01alt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import org.jdesktop.jdic.filetypes.Action;
import org.jdesktop.jdic.filetypes.Association;
import org.jdesktop.jdic.filetypes.AssociationService;


import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashtableIndexedZZZ;
import basic.zBasic.util.abstractList.HashtableSortedZZZ;
import basic.zBasic.util.abstractList.HashtableZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.txt.TxtReaderZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.net.client.KernelPingHostZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public class ConfigFileTemplateBatchOVPN extends AbstractKernelUseObjectZZZ{
	public static String sFILE_TEMPLATE_PREFIX="template_";
	public static String sFILE_TEMPLATE_SUFFIX="_starter";
	private File fileTemplateBatch=null;
	private File fileTemplateOvpn=null;
	private HashtableIndexedZZZ<Integer, String> htLines = null;
		
	public ConfigFileTemplateBatchOVPN(IKernelZZZ objKernel, File fileTemplateBatch, File fileConfigOvpn, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel);
		ConfigFileTemplateBatchNew_(fileTemplateBatch, fileConfigOvpn, saFlagControl);		
	}

	
	private void ConfigFileTemplateBatchNew_(File fileTemplateBatch, File fileConfigOvpn, String[] saFlagControl) throws ExceptionZZZ{
		main:{
			
					
			check:{
		 		
				if(saFlagControl != null){
					String stemp; boolean btemp;
					for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
						stemp = saFlagControl[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){ 								   
							   ExceptionZZZ ez = new ExceptionZZZ(stemp, IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 							  
							   throw ez;		 
						}
					}
					if(this.getFlag("init")) break main;
				}
				
				
				if(fileTemplateBatch==null){
					ExceptionZZZ ez = new ExceptionZZZ("FileTemplateBatch", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else{
					this.setFileConfigTemplateBatch(fileTemplateBatch);
				}
				
				if(fileConfigOvpn==null){
					ExceptionZZZ ez = new ExceptionZZZ("FileConfigOvpn", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else{
					this.setFileConfigTemplateOvpn(fileConfigOvpn);
				}
				
			}//End check
			
			this.refreshLines();
	
		}//END main
	}
	
	public int refreshLines()throws ExceptionZZZ{
		return this.refreshLines(null);
	}
	public int refreshLines(File fileConfigTemplateBatchIn) throws ExceptionZZZ {
		int iReturn=0;
		main:{			
			if(fileConfigTemplateBatchIn!=null) {
				this.setFileConfigTemplateBatch(fileConfigTemplateBatchIn);
			}
			File fileConfigTemplateBatch = this.getFileConfigTemplateBatch();			
			if(fileConfigTemplateBatch==null){
				ExceptionZZZ ez = new ExceptionZZZ("FileTemplateBatch", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Zeilen der Datei einlesen
			TxtReaderZZZ objReader = new TxtReaderZZZ(fileConfigTemplateBatch);
			Vector<String> vecLine = objReader.readVectorStringByByte(0);
			
			//Alle Zeilen nun mit einer Nummer versehen und der HashTable "indiziert" übergeben.
			for(String sLine : vecLine) {
				iReturn = iReturn + 1;
				Integer intLine = new Integer(iReturn);				
				this.getLines().put(intLine,sLine);
			}
		}
		return iReturn;
	}
	
			
	public static boolean isTemplate(String sFilename) {
		boolean bReturn = false;
		main:{
			//Template Dateinamen fangen mit dem vorangesetzten String an.
			if(sFilename.toLowerCase().startsWith(ConfigFileTemplateBatchOVPN.sFILE_TEMPLATE_PREFIX)) bReturn = true;
			if(bReturn = false) break main;
			
			//Template Batch Dateinamen enden mit einem Suffix (ggfs. auch noch die Endung definert)
			if(sFilename.toLowerCase().endsWith(ConfigFileTemplateBatchOVPN.sFILE_TEMPLATE_SUFFIX)) bReturn = true;
			if(bReturn = false) {
				if(sFilename.toLowerCase().endsWith(ConfigFileTemplateBatchOVPN.sFILE_TEMPLATE_SUFFIX+".txt")) bReturn = true;
			}
		}
		return bReturn;
	}
	
	
	
	//#### GETTER / SETTER
	public void setFileConfigTemplateBatch(File fileTemplateBatch){
		this.fileTemplateBatch = fileTemplateBatch;
	}
	public File getFileConfigTemplateBatch(){
		return this.fileTemplateBatch;
	}
	
	public void setFileConfigTemplateOvpn(File fileTemplateOvpn){
		this.fileTemplateOvpn = fileTemplateOvpn;
	}
	public File getFileConfigTemplateOvpn(){
		return this.fileTemplateOvpn;
	}
	
	public HashtableIndexedZZZ<Integer,String> getLines() throws ExceptionZZZ{
		if(this.htLines==null) {
			this.htLines = new HashtableIndexedZZZ<Integer,String>();
		}
		return this.htLines;
	}
	public void setLines(HashtableIndexedZZZ<Integer,String>htLines) {
		this.htLines = htLines;
	}
	public HashMap<String, String> getLinesAsHashMap_StringString() throws ExceptionZZZ{
		HashMap<String,String> hmReturn=null;
		main:{
			HashtableIndexedZZZ<Integer,String>htIndexed = this.getLines();
			hmReturn = HashtableZZZ.toHashMap_IntegerString(htIndexed);
		}
		return hmReturn;
	}
}//END class
