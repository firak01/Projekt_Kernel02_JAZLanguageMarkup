package basic.zKernel.xml.reader;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

	/**
	 * @author 0823
	 *
	 * To change this generated comment edit the template variable "typecomment":
	 * Window>Preferences>Java>Templates.
	 * To enable and disable the creation of type comments go to
	 * Window>Preferences>Java>Code Generation.
	 */
	public class DTDErrorZZZ implements ErrorHandler{
		
		//Constructor
		public DTDErrorZZZ(){
		}
		
		//Overwritten, because the original doesn´t print out recoverable parsing exceptions		
		//+++++++++++++++++++++++++
		public void warning(SAXParseException e){
			System.out.println("\n+++ Parse Warning +++" + 
			"\n   Line: " + e.getLineNumber() +
			"\n   File: " + e.getSystemId() +
			"\n" + e.getMessage());
		}
		
		//+++++++++++++++++++++++++
		public void error(SAXParseException e){
			System.out.println("\n+++ Parse Error +++" + 
			"\n   Line: " + e.getLineNumber() +
			"\n   File: " + e.getSystemId() +
			"\n" + e.getMessage());
		}

		//+++++++++++++++++++++++++
		public void fatalError(SAXParseException e){
			System.out.println("\n+++ Parse Fatal Error +++" + 
			"\n   Line: " + e.getLineNumber() +
			"\n   File: " + e.getSystemId() +
			"\n" + e.getMessage());
		}
	}

