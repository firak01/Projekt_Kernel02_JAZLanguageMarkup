/*
 * Created on 04.01.2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package custom.zKernel.html.writer;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.ecs.Document;
import org.apache.ecs.Element;
import org.apache.ecs.html.H1;
import org.apache.ecs.html.Html;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.html.writer.KernelWriterHtmlByEcsZZZ;
import basic.zKernel.html.writer.KernelWriterHtmlZZZ;
import basic.zKernel.markup.content.ContentEcsZZZ;
import basic.zKernel.markup.content.IKernelContentEcsZZZ;
import basic.zKernel.markup.content.IKernelContentFileZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.markup.content.ContentPageIPZZZ;

//import custom.zzzKernel.ExceptionZZZ;
//import custom.zzzKernel.KernelZZZ;
//import custom.zzzKernel.LogZZZ;
//import custom.zzzKernel.markup.content.ContentPageIPZZZ;
//import basic.zzzKernel.html.writer.KernelWriterHTMLZZZ;

/**Der verwendete DefaultWriter ist vom Typ ECS, das ist durch die verwendete Elternklasse festgelegt.
 * In anderen Projekten aber durchaus ein anderer Writertyp möglich.
 * @author Lindhauer
 *
 */
public class WriterHtmlZZZ extends KernelWriterHtmlByEcsZZZ {

	/**
	 * @param objKernel
	 * @param objLog
	 * @param saFlagControl
	 */
	public WriterHtmlZZZ(IKernelZZZ objKernel, String[] saFlagControl) throws ExceptionZZZ {		
		super(objKernel, saFlagControl);
	}	
}
