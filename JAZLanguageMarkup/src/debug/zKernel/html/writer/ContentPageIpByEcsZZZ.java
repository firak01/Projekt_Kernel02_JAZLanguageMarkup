/*
 * Created on 09.01.2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package debug.zKernel.html.writer;

import org.apache.ecs.Document;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.html.writer.KernelWriterHtmlByEcsZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.markup.content.ContentPageIPZZZ;



/**
 * @author Lindhauer
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ContentPageIpByEcsZZZ {

	public static void main(String[] args) {
		System.out.println("Start");
		main:{
			try {
	
		//1. Erstellen das Z-Kernel Objekt
		KernelZZZ objKernel = new KernelZZZ("FGL", "01", "", "", (String)null);
		
		//2. Protokoll
		LogZZZ objLog = objKernel.getLogObject();

		//3. Objekt, das dann später an einen ContentWriter übergeben werden kann
		ContentPageIPZZZ objContentStore = new ContentPageIPZZZ(objKernel, (String[]) null);
		objContentStore.setVar("IPDate", "test, heute");
		objContentStore.setVar("IPTime", "test, jetzt");
		objContentStore.compute();
		
		//4. Content Writer
		Document objDoc = new Document();
		KernelWriterHtmlByEcsZZZ objWriterHTML = new KernelWriterHtmlByEcsZZZ(objKernel,objDoc, (String[]) null);
		objWriterHTML.addContent(objContentStore);
		
		//5. Create File
		objWriterHTML.toFile("c:\\temp\\test.html");
		
		} catch (ExceptionZZZ ez) {
				System.out.println(ez.getDetailAllLast());
		}
		System.out.println("Ende");
	}//end main:
}//end method main
}//end class
