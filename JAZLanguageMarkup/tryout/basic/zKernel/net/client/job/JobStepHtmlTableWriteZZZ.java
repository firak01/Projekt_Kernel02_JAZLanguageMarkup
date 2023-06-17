package basic.zKernel.net.client.job;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.net.client.IApplicationZZZ;
import basic.zKernel.net.client.IMainZZZ;
import custom.zKernel.LogZZZ;
import use.zKernel.html.step01.writer.KernelWriterHtmlByXsltZZZ;
import use.zKernel.html.step01.writer.TableHeadZZZ;
import use.zKernel.html.step02.reader.Debug02_ReaderHtmlTableZZZ;

public class JobStepHtmlTableWriteZZZ extends AbstractJobStepZZZ {
	public static String sJOBSTEP_ALIAS="HtmlTableWriterStep";
	
	public JobStepHtmlTableWriteZZZ() throws ExceptionZZZ {
		super();
	}
	
	public JobStepHtmlTableWriteZZZ(JobStepControllerZZZ objController) throws ExceptionZZZ {
		super(objController);
		JobStepHtmlTableWriterNew_();
	}
	private boolean JobStepHtmlTableWriterNew_() throws ExceptionZZZ {
		return true;
	}
		
	@Override
	public String getJobStepAliasCustom() throws ExceptionZZZ {
		return sJOBSTEP_ALIAS;
	}
	
	@Override
	public boolean process() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			String stemp;
			try {
			//1. Hole die Interne Application für diesen Step.
			IJobStepControllerZZZ objController = this.getJobStepController();
			IJobZZZ objJob = objController.getJob();
			
			IApplicationZZZ objApplication = objJob.getApplicationObject();
			IKernelZZZ objKernel = objApplication.getKernelObject();
			
			String sAlias = this.getJobStepAlias();
			
			//Nun die Brücke zu dem Step, den es erst einmal nur als Tryout-Debug gab.
			//TODOGOON20230616;//Den Code nun verteilen auf die richtigen Klassen und Objekte. 
			                 //Dabei die Werte aus dem Applications-Objekt holen
			                 //Dabei in der Ini Datei diesen STEP (also die Klasse incl. Packagenamen) ein Program definieren.
			                 //Die Werte dann im Program hinterlegen, also Pfad
			                 //auf die Ausgangswerte, die auf Applicationseben/Modulebene liegen.
			
				//XML Datei fuer die Werte				
				//File fileXml = FileEasyZZZ.searchFile("tryout\\use\\zKernel\\html\\step01\\writer","TableDataInput4Debug4XsdCreation.xml");
				String sPropertyUsed = "InputDirectoryPath";
				IKernelConfigSectionEntryZZZ objEntryPath = objKernel.getParameterByProgramAlias(sAlias, sPropertyUsed, true);
				if(!objEntryPath.hasAnyValue()) {
					String sLog = "Missing parameter: '" + sPropertyUsed + "' for Program '" + sAlias + "' in file '" + objKernel.getFileConfigKernelIni().getFileObject().getAbsolutePath() + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				stemp = objEntryPath.getValue();
				String sDirectoryPath = FileEasyZZZ.joinFilePathName("tryout",stemp);//sonst kommt src als erster Pfadteil
			
				sPropertyUsed = "InputFileNameXml";
				IKernelConfigSectionEntryZZZ objEntryFileXml = objKernel.getParameterByProgramAlias(sAlias, sPropertyUsed, true);
				if(!objEntryFileXml.hasAnyValue()) {
					String sLog = "Missing parameter: '" + sPropertyUsed + "' for Program '" + sAlias + "' in file '" + objKernel.getFileConfigKernelIni().getFileObject().getAbsolutePath() + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				String sFileNameInputXml = objEntryFileXml.getValue();						
				File fileXml = FileEasyZZZ.searchFile(sDirectoryPath,sFileNameInputXml);
				
				//XSLT Datei fuer die Transformation. Diese enthaelt auch die HTML-Tags
				//File fileXslt = FileEasyZZZ.searchFile("tryout\\use\\zKernel\\html\\step01\\writer","pagHtmlTableTagGenerated4Debug.xsl");
				sPropertyUsed = "InputFileNameXsl";
				IKernelConfigSectionEntryZZZ objEntryFileXsl = objKernel.getParameterByProgramAlias(sAlias, sPropertyUsed, true);
				if(!objEntryFileXsl.hasAnyValue()) {
					String sLog = "Missing parameter: '" + sPropertyUsed + "' for Program '" + sAlias + "' in file '" + objKernel.getFileConfigKernelIni().getFileObject().getAbsolutePath() + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				String sFileNameInputXsl = objEntryFileXsl.getValue();
				File fileXslt = FileEasyZZZ.searchFile(sDirectoryPath,sFileNameInputXsl);
				
				KernelWriterHtmlByXsltZZZ objWriter = new KernelWriterHtmlByXsltZZZ();
			
				//Dateipfad fuer das Ergebnis Html
				//String sPackagePath = ReflectCodeZZZ.getPackagePath(Debug02_ReaderHtmlTableZZZ.class);
				sPropertyUsed = "OutputDirectoryPath";
				IKernelConfigSectionEntryZZZ objEntryDirectoryHtml = objKernel.getParameterByProgramAlias(sAlias, sPropertyUsed, true);
				if(!objEntryDirectoryHtml.hasAnyValue()) {
					String sLog = "Missing parameter: '" + sPropertyUsed + "' for Program '" + sAlias + "' in file '" + objKernel.getFileConfigKernelIni().getFileObject().getAbsolutePath() + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				stemp = objEntryDirectoryHtml.getValue();				
		        String sFilePathTryout = FileEasyZZZ.joinFilePathName("tryout",stemp);//sonst kommt src als erster Pfadteil
		        
		        String sFileName = FileEasyZZZ.getNameWithChangedEnd(fileXslt, "html");
		        String sFilePathTotal = FileEasyZZZ.joinFilePathName(sFilePathTryout,sFileName);//sonst kommt src als erster Pfadteil
		        System.out.println("Creating new file in directory '" + sFilePathTryout + "' by KernelWriterHtmlByXsltZZZ."); 
		        System.out.println("The new file will have the same name as the xslt file with the ending html: '" + sFilePathTotal + "'");
		        File fileHtmlOutput = new File(sFilePathTotal);
		        objWriter.setFileHtmlOutput(fileHtmlOutput);

		        sPropertyUsed = "TableHeaderMap";		        
		        Map<String,String> mapTableHeadLabel = objKernel.getParameterHashMapWithStringByProgramAlias(sAlias, sPropertyUsed);
		        if(mapTableHeadLabel.isEmpty()) {
					String sLog = "Missing parameter: '" + sPropertyUsed + "' for Program '" + sAlias + "' in file '" + objKernel.getFileConfigKernelIni().getFileObject().getAbsolutePath() + "'.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				objWriter.setHashMapTableHeader(mapTableHeadLabel);
				
				TODGOON20230617: Aus der HashMap die Index HashMap errechnen.
				HashMapIndexedZZZ<Integer, TableHeadZZZ> mapIndexedTableHeadLabel = new HashMapIndexedZZZ<Integer, TableHeadZZZ>();               
				TableHeadZZZ h02 = new TableHeadZZZ("ServerName", "Name des Servers");
				mapIndexedTableHeadLabel.put(h02);
				
				TableHeadZZZ h01 = new TableHeadZZZ("IPNr", "IP Adresse");
			    mapIndexedTableHeadLabel.put(h01);
					
				
				TableHeadZZZ h03 = new TableHeadZZZ("IPPortListen", "Port für Listener");
				mapIndexedTableHeadLabel.put(h03);
				
				TableHeadZZZ h04 = new TableHeadZZZ("IPPortConnect", "Port für Verbindung");
				mapIndexedTableHeadLabel.put(h04);
				
				TableHeadZZZ h05 = new TableHeadZZZ("IPDate", "IP Datum");
				mapIndexedTableHeadLabel.put(h05);
				
				TableHeadZZZ h06 = new TableHeadZZZ("IPTime", "IP Zeit");
				mapIndexedTableHeadLabel.put(h06);
				
				String sDummy = new String("komme ich im XSLT an?");
				mapIndexedTableHeadLabel.setDummy(sDummy);
		        objWriter.setHashMapIndexedTableHeader(mapIndexedTableHeadLabel);
				
				int iRun = 1;
				boolean bSuccess = objWriter.transformFileOnStyle(fileXslt, fileXml, iRun);
				if(bSuccess) {
					System.out.println("The new file should be here: '" + fileHtmlOutput + "'");
				}else {
					System.out.println("A problem occured during transformation.");
				}
				iRun++;	
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			//Aus dem ApplicationObject
			
			bReturn = true;
		}//end main:
		return bReturn;
	}

	
}
