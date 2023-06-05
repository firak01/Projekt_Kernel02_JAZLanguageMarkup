package basic.zKernel.html.reader.writer2xml;

import java.awt.List;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.bind.JAXBElement;

import org.apache.sis.io.wkt.Symbols;
import org.jdom.Element;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.html.IKernelTagZZZ;
import basic.zKernel.html.TagInputZZZ;
import basic.zKernel.html.TagTableCellZZZ;
import basic.zKernel.html.TagTableRowZZZ;
import basic.zKernel.html.TagTableZZZ;
import basic.zKernel.html.TagTypeInputZZZ;
import basic.zKernel.html.TagTypeTableZZZ;
import basic.zKernel.html.classes.ventian.ColumnType;
import basic.zKernel.html.classes.ventian.ObjectFactory;
import basic.zKernel.html.classes.ventian.RowType;
import basic.zKernel.html.classes.ventian.TabledataType;
import basic.zKernel.html.reader.KernelReaderHtmlZZZ;
import basic.zKernel.html.writer.DebugWriterHtmlByXsltZZZ;
import basic.zKernel.net.client.KernelReaderPageZZZ;
import basic.zKernel.net.client.KernelReaderURLZZZ;

public class DebugReaderHtmlTable2XmlFileByJaxbZZZ {

	public static void main(String[] args) {
		try {
		//HTML Datei, die zuvor generiert worden ist aus Werten einer XML Datei.
		String sFilepath = "tryout\\basic\\zKernel\\html\\reader\\pagHtmlTableTagGenerated4Debug.html";
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
						
			//##########################################
			//Zuerst den Wert auslesen
			TagTableZZZ objTable = (TagTableZZZ) objTag;
			String sReturn = objTable.getValue(2,1);
			System.out.println("sReturn = " + sReturn);
			
			//##########################################
			//Nun den Wert aendern			
			boolean bReturn = objTable.setValue(2,1,"neuer test");
			System.out.println("Wert neu gesetzt: " + bReturn);
			
			sReturn = objTable.getValue(2,1);
			System.out.println("sReturn neu gesetzt = " + sReturn);
			
			//Hat das Auswirkungen auf das Document
			String sReturnDocument = objReaderHTML.getDocumentAsString();
			System.out.println("sReturnDocument neu gesetzt = " + sReturnDocument);
			
			//###########################################
			//XML Struktur mit neuem Wert aufbauen.
			//Verwende dazu die aus der xsd generierten JAXB-Klassen
			
			ObjectFactory objFactory = new ObjectFactory();			
			TabledataType objTableDataType = objFactory.createTabledataType();
			JAXBElement<TabledataType>objRootElement = objFactory.createTabledata(objTableDataType);//Das ist das Root Element, wichtig für den Marshaller!!!
			
			//TableData objTableData = objFactory.createTabledata(objTableDataType);
			ArrayList<RowType> listRowType = (ArrayList<RowType>) objTableDataType.getRow();
			
			ArrayList<TagTableRowZZZ>listRow = (ArrayList<TagTableRowZZZ>) objTable.getRows();
			int iRowIndex = -1;
			for(TagTableRowZZZ objRow : listRow) {				
				iRowIndex++;				
				//RowType objRowType = listRowType.get(iRowIndex);
				RowType objRowType = objFactory.createRowType();
				listRowType.add(objRowType);
				
				ArrayList<TagTableCellZZZ> listCell = (ArrayList<TagTableCellZZZ>) objRow.getCells();
				ArrayList<ColumnType> listColumnType = (ArrayList) objRowType.getColumn();
				int iCellIndex = -1;
				for(TagTableCellZZZ objCell : listCell) {
					iCellIndex++;
					//ColumnType objColumnType = listColumnType.get(iCellIndex);
					ColumnType objColumnType = objFactory.createColumnType();
					objColumnType.setValue(objCell.getValue());				
					
					listColumnType.add(objColumnType);
				}
			}
			
			 javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(objTableDataType.getClass().getPackage().getName());
	         javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
	         marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
	         marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	         //marshaller.marshal(objTableDataType, System.out); //Fehler:
	         //javax.xml.bind.MarshalException
	         //- with linked exception:
	         //	 [com.sun.istack.internal.SAXException2: unable to marshal type "basic.zKernel.html.classes.ventian.TabledataType" as an element because it is missing an @XmlRootElement annotation]

	         //Funktionierende Ausgabe auf die Konsole: marshaller.marshal(objRootElement, System.out);
	         
	         //https://stackoverflow.com/questions/13788617/jaxb-marshalling-java-to-output-xml-file
	         //If you are using a JAXB 2.1 or greater then you can marshal directly to a java.io.File object:
	         //File file = new File( "nosferatu.xml" );
	         String sFileRootPath = FileEasyZZZ.getFileRootPath();
	         
	         DebugWriterHtmlByXsltZZZ objDummyTarget = new DebugWriterHtmlByXsltZZZ();
	         String sPackagePath = ReflectCodeZZZ.getPackagePath(objDummyTarget);
	         String sFilePathTryout = FileEasyZZZ.joinFilePathName("tryout",sPackagePath);//sonst kommt src als erster Pfadteil
	         //String sFilePath = FileEasyZZZ.joinFilePathName(sFilePathTryout,sPackagePath);
	         String sFilePathTotal = FileEasyZZZ.joinFilePathName(sFilePathTryout, "TableDataInput4Debug.xml");
	         System.out.println("Creating new file '" + sFilePathTotal + "' by Marshaller."); 
	         File file = new File(sFilePathTotal);
	         marshaller.marshal( objRootElement, file );
	            
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
