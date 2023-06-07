package basic.zKernel.html.writer;

import java.io.File;

import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.html.reader.Debug02_ReaderHtmlTableZZZ;

public class Debug01_WriterHtmlByXsltZZZ {

	public static void main(String[] args) {
		try {
		//XML Datei fuer die Werte
		File fileXml = FileEasyZZZ.searchFile("tryout\\basic\\zKernel\\html\\writer","TableDataInput4Debug4XsdCreation.xml");
		
		//XSLT Datei fuer die Transformation
		//Diese enthaelt auch die HTML-Tags
		File fileXslt = FileEasyZZZ.searchFile("tryout\\basic\\zKernel\\html\\writer","pagHtmlTableTagGenerated4Debug.xsl");
		
		
		KernelWriterHtmlByXsltZZZ objWriter = new KernelWriterHtmlByXsltZZZ();
	
		String sPackagePath = ReflectCodeZZZ.getPackagePath(Debug02_ReaderHtmlTableZZZ.class);
        String sFilePathTryout = FileEasyZZZ.joinFilePathName("tryout",sPackagePath);//sonst kommt src als erster Pfadteil
        
        String sFileName = FileEasyZZZ.getNameWithChangedEnd(fileXslt, "html");
        String sFilePathTotal = FileEasyZZZ.joinFilePathName(sFilePathTryout,sFileName);//sonst kommt src als erster Pfadteil
        System.out.println("Creating new file in directory '" + sFilePathTryout + "' by KernelWriterHtmlByXsltZZZ."); 
        System.out.println("The new file will have the same name as the xslt file with the ending html: '" + sFilePathTotal + "'");
        File fileHtmlOutput = new File(sFilePathTotal);
        objWriter.setFileHtmlOutput(fileHtmlOutput);
      
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
	}

}
