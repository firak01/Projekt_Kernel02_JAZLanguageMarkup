package use.zKernel.html.step01.writer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjectZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import use.zKernel.html.step02.reader.Debug02_ReaderHtmlTableZZZ;

public class Debug01_WriterHtmlByXsltZZZ {

	public static void main(String[] args) {
		try {
		//XML Datei fuer die Werte
		File fileXml = FileEasyZZZ.searchFile("tryout\\use\\zKernel\\html\\step01\\writer","TableDataInput4Debug4XsdCreation.xml");
		
		//XSLT Datei fuer die Transformation
		//Diese enthaelt auch die HTML-Tags
		File fileXslt = FileEasyZZZ.searchFile("tryout\\use\\zKernel\\html\\step01\\writer","pagHtmlTableTagGenerated4Debug.xsl");
		
		
		KernelWriterHtmlByXsltZZZ objWriter = new KernelWriterHtmlByXsltZZZ();
	
		String sPackagePath = ReflectCodeZZZ.getPackagePath(Debug02_ReaderHtmlTableZZZ.class);
        String sFilePathTryout = FileEasyZZZ.joinFilePathName("tryout",sPackagePath);//sonst kommt src als erster Pfadteil
        
        String sFileName = FileEasyZZZ.getNameWithChangedEnd(fileXslt, "html");
        String sFilePathTotal = FileEasyZZZ.joinFilePathName(sFilePathTryout,sFileName);//sonst kommt src als erster Pfadteil
        System.out.println("Creating new file in directory '" + sFilePathTryout + "' by KernelWriterHtmlByXsltZZZ."); 
        System.out.println("The new file will have the same name as the xslt file with the ending html: '" + sFilePathTotal + "'");
        File fileHtmlOutput = new File(sFilePathTotal);
        objWriter.setFileHtmlOutput(fileHtmlOutput);

        Map<String,String> mapTableHeadLabel = new HashMap<String,String>();
        mapTableHeadLabel.put("IPNr", "IP Adresse");
		mapTableHeadLabel.put("ServerName", "Name des Severs");
		mapTableHeadLabel.put("IPPortListen", "Port für Listener");
		mapTableHeadLabel.put("IPPortConnect", "Port für Verbindung");
		mapTableHeadLabel.put("IPDate", "IP Datum");
		mapTableHeadLabel.put("IPTime", "IP Zeit");
		//Veralted, das wird nun alles direkt über eine HashMapIndex gemacht objWriter.setHashMapTableHeader(mapTableHeadLabel);
		
		//TODGOON: Aus der HashMap die Index HashMap errechnen.
		HashMapIndexedObjectZZZ<Integer, TableHeadZZZ> mapIndexedTableHeadLabel = new HashMapIndexedObjectZZZ<Integer, TableHeadZZZ>();               
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

        objWriter.setHashMapIndexedTableHeader(mapIndexedTableHeadLabel);

		boolean bSuccess = objWriter.transformFileOnStyle(fileXslt, fileXml);
		if(bSuccess) {
			System.out.println("The new file should be here: '" + fileHtmlOutput + "'");
		}else {
			System.out.println("A problem occured during transformation.");
		}
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
