package use.zKernel.html.step01.writer;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;

public class KernelWriterHtmlByXsltZZZ implements IConstantZZZ {
	private String sDirectoryOutput=null;
	private String sFileNameOutput=null;
	private String sFilePathTotalOutput=null;
	
	private File fileXslt=null;
	private File fileXml=null;
	private File fileHtmlOutput=null;
	
	private HashMapIndexedZZZ<Integer,TableHeadZZZ> hmIndexedTableHeaderLabel=null;
	//private Map<String,String> hmTableHeaderLabel=null;
	
	public KernelWriterHtmlByXsltZZZ() {
		
	}
	
	public String getDirectoryOutput(){
		return this.sDirectoryOutput;
	}
	public File getFileXslt() {
		return this.fileXslt;
	}
	public void setFileXslt(File fileXslt) {
		this.fileXslt = fileXslt;
	}
	public File getFileXml() {
		return this.fileXml;
	}
	public void setFileXml(File fileXml) {
		this.fileXml = fileXml;
	}
	public File getFileHtmlOutput() {
		return this.fileHtmlOutput;
	}
	public void setFileHtmlOutput(File fileHtmlOutput) {
		this.fileHtmlOutput = fileHtmlOutput;
	}
	
	public void setDirectoryOutput(String sDirectory)throws Exception{
		if(sDirectory==null)throw new Exception("Directory must not be null.");
		if(sDirectory.equals("")) throw new Exception("Directory must not be empty.");
		
//Anders als bei den Input Verzeichnissen wird noch nicht auf Vorhandensein des Verzeichnises geprüft. Das Verzeichnis wird bei der Transformation angelegt.
//		File file = new File(sDirectory);
//		if(file.exists()==false) throw new Exception("Directoy does not exist: '" + file.getAbsolutePath() + "'");
//		if(file.isDirectory()==false)throw new Exception("This is not a directory: '" + file.getAbsolutePath() + "'");
//		
		this.sDirectoryOutput = sDirectory;
	}
	
	public void setHashMapIndexedTableHeader(HashMapIndexedZZZ<Integer,TableHeadZZZ>hmIndexedHeaderLabel) {
		this.hmIndexedTableHeaderLabel = hmIndexedHeaderLabel;
	}
	public HashMapIndexedZZZ<Integer,TableHeadZZZ>getHashMapIndexedTableHeader() {
		return this.hmIndexedTableHeaderLabel;
	}
	
//	public void setHashMapTableHeader(Map<String,String>hmHeaderLabel) {
//		this.hmTableHeaderLabel = hmHeaderLabel;
//	}
//	public Map<String,String>getHashMapTableHeader() {
//		return this.hmTableHeaderLabel;
//	}
	
	public boolean transformFileOnStyle(File fileXslt, File fileXml)throws Exception{
		boolean bReturn = false;
		
		main:{
			if(fileXslt==null) throw new Exception("No Style File provided.");
			if(fileXslt.exists()==false) throw new Exception("File Style does not exist: '" + fileXslt.getAbsolutePath() + "'");
			this.setFileXslt(fileXslt);
			
			if(fileXml==null) throw new Exception("No Xml File provided.");
			if(fileXml.exists()==false) throw new Exception("File XML does not exist: '" + fileXml.getAbsolutePath() + "'");
			this.setFileXml(fileXml);
			
			System.out.println("\n");
			System.out.println("Input: XML (" + fileXml.getAbsolutePath() + ")");
			
			File fileXPageNew = this.getFileHtmlOutput();
			if(fileXPageNew==null) {
				String sPathNew = this.computeFilePathTotalOutput();				
				fileXPageNew = new File(sPathNew);
				this.setFileHtmlOutput(fileXPageNew);
			}
			System.out.println("Output: XML (" + fileXPageNew.getAbsolutePath() + ")");
			
			System.out.print("Transforming...");
			StreamSource xmlSource = new StreamSource(fileXml);
			StreamSource xsltSource = new StreamSource(fileXslt);
				
			OutputStream out = new java.io.FileOutputStream(fileXPageNew);
				
			Result xmlResultStream = new StreamResult(new java.io.BufferedOutputStream(out));
			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer(xsltSource);
			if(trans==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Unable to create Transformer-Object. Maybe an error in xslt. Watch the log/console.", iERROR_RUNTIME, this, ReflectCodeZZZ.getPositionCurrent());
				throw ez;
			}
			
			//java - Retrieving Hashmap value in XSLT
			//Voraussetzung in xslt im stylesheet Tag setzen ... <... xmlns:map="xalan://java.util.Map" extension-element-prefixes="map">
			//siehe: https://xml.apache.org/xalan-j/extensions.html#supported-lang
								
//			Map<String,String> mapTableHeadLabel = this.getHashMapTableHeader();
//			trans.setParameter("mapTableHeadLabel", mapTableHeadLabel);		
			
			HashMapIndexedZZZ<Integer,TableHeadZZZ> hmIndexedTableHeadLabel = this.getHashMapIndexedTableHeader();
			System.out.println("mapIndexedTableHeadLabel.toString() = '" + hmIndexedTableHeadLabel.toString() + "'");
			trans.setParameter("mapTableHeadLabelIndexed", hmIndexedTableHeadLabel);
						
			trans.transform(xmlSource, xmlResultStream);						
			out.close();						
			System.out.println("Success!");
												
			bReturn = true;
		}// end main
		return bReturn;
	}
	
	public String computeFilePathTotalOutput() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			File fileXslt = this.getFileXslt();
			if(fileXslt==null) {
				ExceptionZZZ ez = new ExceptionZZZ("File Xslt as Property missing", iERROR_RUNTIME);
				throw ez;
			};
			if(fileXslt.exists()==false){
				ExceptionZZZ ez = new ExceptionZZZ("File Style does not exist: '" + fileXslt.getAbsolutePath() + "'", iERROR_PROPERTY_VALUE);
				throw ez;
			}
			
			String sDirectoryOutput=this.getDirectoryOutput();
			if(StringZZZ.isEmpty(sDirectoryOutput)){
				sDirectoryOutput = FileEasyZZZ.getParent(fileXslt);
			}
			String sPathNew = sDirectoryOutput + File.separator + FileEasyZZZ.getNameWithChangedEnd(fileXslt, "html");
			sReturn = sPathNew;
		}//end main:
		return sReturn;
	}
}
