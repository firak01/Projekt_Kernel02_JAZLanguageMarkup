package basic.zKernel.html.writer;

import java.io.File;

import basic.zBasic.util.file.FileEasyZZZ;

public class DebugWriterHtmlByXsltZZZ {

	public static void main(String[] args) {
		try {
		//XML Datei fuer die Werte
		File fileXml = FileEasyZZZ.searchFile("tryout\\basic\\zKernel\\html\\writer","TableDataInput4Debug.xml");
		
		//XSLT Datei fuer die Transformation
		//Diese enthaelt auch die HTML-Tags
		File fileXslt = FileEasyZZZ.searchFile("tryout\\basic\\zKernel\\html\\writer","pagHtmlTableTagGenerated4Debug.xsl");
		
		
		KernelWriterHtmlByXsltZZZ objWriter = new KernelWriterHtmlByXsltZZZ();
		objWriter.setDirectoryOutput("c:\\temp");
			
		int iRun = 1;
		objWriter.transformFileOnStyle(fileXslt, fileXml, iRun);
		iRun++;	
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
