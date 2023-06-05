package basic.zKernel.html.writer;

import java.io.File;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import basic.zBasic.util.file.FileEasyZZZ;

public class KernelWriterHtmlByXsltZZZ {
	private String sDirectoryOutput=null;
	
	public KernelWriterHtmlByXsltZZZ() {
		
	}
	
	public String getDirectoryOutput(){
		return this.sDirectoryOutput;
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
	
	
	public boolean transformFileOnStyle(File fileXslt, File fileXml, int iRun)throws Exception{
		boolean bReturn = false;
		
		main:{
			if(fileXslt==null) throw new Exception("No Style File provided.");
			if(fileXslt.exists()==false) throw new Exception("File Style does not exist: '" + fileXslt.getAbsolutePath() + "'");
			
			if(fileXml==null) break main;
			if(fileXml.exists()==false) break main;
			
			System.out.println("\n");
			System.out.println("Input: XML (" + fileXml.getAbsolutePath() + ")");			
			//String sPathNew = this.getDirectoryOutput() + File.separator + "xsltStep" + (iRun) + File.separator + fileXml.getName();
			String sPathNew = this.getDirectoryOutput() + File.separator + FileEasyZZZ.getNameWithChangedEnd(fileXslt, "html");
			File fileXPageNew = new File(sPathNew);			
			System.out.println("Output: XML (" + fileXPageNew + ")");
			
			System.out.print("Transforming...");

			StreamSource xmlSource = new StreamSource(fileXml);
			StreamSource xsltSource = new StreamSource(fileXslt);
				
			OutputStream out = new java.io.FileOutputStream(fileXPageNew);
				
			Result xmlResultStream = new StreamResult(new java.io.BufferedOutputStream(out));
			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer(xsltSource);

			trans.transform(xmlSource, xmlResultStream);
			
			out.close();
				
			System.out.print(" Success!");
			
												
			bReturn = true;
		}// end main
		return bReturn;
	}
}
