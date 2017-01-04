import zKernel.html.reader.KernelReaderHtmlZZZTest;
import zKernel.net.KernelPingHostZZZTest;
import zKernel.xml.reader.ParserXMLDOMZZZTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class LanguageMarkupAllTest {
			public static Test suite(){
			TestSuite objReturn = new TestSuite();
			//Merke: Die Tests bilden in ihrer Reihenfolge in etwa die Hierarchie im Framework ab. 
			//            Dies beim Einf�gen weiterer Tests bitte beachten.         
			
			objReturn.addTestSuite(KernelReaderHtmlZZZTest.class);
			objReturn.addTestSuite(KernelPingHostZZZTest.class);
			objReturn.addTestSuite(ParserXMLDOMZZZTest.class);

			return objReturn;
		}
		/**
		 * Hiermit eine Swing-Gui starten.
		 * Das ist bei eclipse aber nicht notwendig, außer man will alle hier eingebundenen Tests durchführen.
		 * @param args
		 */
		public static void main(String[] args) {
			//Ab Eclipse 4.4 ist junit.swingui sogar nicht mehr Bestandteil des Bundles
			//also auch nicht mehr unter der Eclipse Variablen JUNIT_HOME/junit.jar zu finden
			//junit.swingui.TestRunner.run(LanguageMarkupAllTest.class);
		}
}
