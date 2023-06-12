package use.zKernel.html.step02.reader;

import java.awt.List;
import java.io.File;
import java.net.URL;

import org.apache.sis.io.wkt.Symbols;
import org.jdom.Element;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.html.IKernelTagZZZ;
import basic.zKernel.html.TagInputZZZ;
import basic.zKernel.html.TagTableRowZZZ;
import basic.zKernel.html.TagTableZZZ;
import basic.zKernel.html.TagTypeInputZZZ;
import basic.zKernel.html.TagTypeTableZZZ;
import basic.zKernel.html.reader.KernelReaderHtmlZZZ;
import basic.zKernel.net.client.KernelReaderPageZZZ;
import basic.zKernel.net.client.KernelReaderURLZZZ;

public class Debug02_ReaderHtmlTableZZZ {

	public static void main(String[] args) {
		try {
		//HTML Datei, die zuvor generiert worden ist aus Werten einer XML Datei.
		String sFilepath = "tryout\\use\\zKernel\\html\\step02\\reader\\pagHtmlTableTagGenerated4Debug.html";
		File fileHtml = FileEasyZZZ.searchFile(sFilepath);
		if(fileHtml==null) {
			ExceptionZZZ ez = new ExceptionZZZ("File not found: '" + sFilepath + "'");
			throw ez;
		}
		
		URL objUrl = fileHtml.toURL();
		String sUrl = objUrl.toString();
		
		String[] satemp = {"UseStream"};
		KernelReaderURLZZZ objReaderURL = new KernelReaderURLZZZ(sUrl,satemp);			
				
		//+++ Nachdem nun ggf. der Proxy aktiviert wurde, die Web-Seite versuchen auszulesen				
		//+++ Den IP-Wert holen aus dem HTML-Code der konfigurierten URL
		KernelReaderPageZZZ objReaderPage = objReaderURL.getReaderPage();
		KernelReaderHtmlZZZ objReaderHTML = objReaderPage.getReaderHTML();
		 
		//Nun die Tabelle holen, danach die Zeilen und dann  nach dem dem Namen "IPNr" in der passenden Spalte suchen.
		TagTypeTableZZZ objTagTypeTable = new TagTypeTableZZZ();			
		IKernelTagZZZ objTag = objReaderHTML.readTagFirstZZZ(objTagTypeTable, "tableWithIpNr");
		if(objTag!=null) {
			//### 1. Auf die Methoden des Interface beschränkt
			System.out.println("TAG found.");
			String sReturn = objTag.getValue();  //Merke: Das Eintragen des Wertes wird der uebergeordneten Methode ueberlassen.
			System.out.println("sReturnByInterface = \n" + sReturn);
			
			//Das ist eben nicht im Interface vorhanden... int iRowMax = objTag.getRowMax();
			String sLineSeparator = StringZZZ.repeat("+", sReturn.length());
			System.out.println(sLineSeparator);
			
			//### 2. Die konkrete TagTabel-Klasse verwenden
			TagTableZZZ objTable = (TagTableZZZ) objTag;
			sReturn = objTable.readValueByTagType();
			System.out.println("sReturnByTagType = \n" + sReturn);
			sLineSeparator = StringZZZ.repeat("+", sReturn.length());
			System.out.println(sLineSeparator);
			
			//+++ 2.1. ueber den TagType
			
			//+++ 2.2. spezieller über die Klasse selbst
			//java.util.List<Element>listChildren = objTable.readRowsAsElement();
			//org.jdom.Element objElemRow0 = (Element) listChildren.get(0);
			//sReturn = objElemRow0.getValue();
			java.util.List<TagTableRowZZZ> listChildren = objTable.readRows();
			TagTableRowZZZ objRow0 = listChildren.get(0);
			sReturn = objRow0.getValue();			
			System.out.println("sReturn0 = " + sReturn);
			
			TagTableRowZZZ objRow1 = listChildren.get(1);
			sReturn = objRow1.getValue();
			System.out.println("sReturn1 = " + sReturn);
			sLineSeparator = StringZZZ.repeat("+", sReturn.length());
			System.out.println(sLineSeparator);
			
			//##########################################
			sReturn = objTable.getValue();
			System.out.println("sReturn=\n" + sReturn);
			
			int iRowMax = objTable.getRowMax();
			sLineSeparator = StringZZZ.repeat("+", sReturn.length()/iRowMax);
			System.out.println(sLineSeparator);
			
			//#### hier sollte es keinen Wert geben, da keine 3 Zeilen...
			sReturn = objTable.getValue(3,1);
			System.out.println("sReturn = " + sReturn); //also "null"
			
			//##########################################
			sReturn = objTable.getValue(2,1);
			System.out.println("sReturn = " + sReturn);
			
			//##########################################
			//Nun Wert aendern
			
			boolean bReturn = objTable.setValue(2,1,"neuer test");
			System.out.println("Wert neu gesetzt: " + bReturn);
			
			sReturn = objTable.getValue(2,1);
			System.out.println("sReturn neu gesetzt = " + sReturn);
			
			//Hat das Auswirkungen auf das Document
			String sReturnDocument = objReaderHTML.getDocumentAsString();
			System.out.println("sReturnDocument neu gesetzt = " + sReturnDocument);
			
		}else {
			System.out.println("TAG NOT found.");
			//this.updateLabel("No Tag found in Page: IPNr");
		}		
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
