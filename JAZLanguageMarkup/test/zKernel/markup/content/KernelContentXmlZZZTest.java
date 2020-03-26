package zKernel.markup.content;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;
import basic.zBasic.util.file.DriveEasyZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.KernelLogZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.html.TagTypeInputZZZ;
import basic.zKernel.html.TagTypeZHtmlZZZ;
import basic.zKernel.html.TagZHtmlZZZ;
import basic.zKernel.html.reader.KernelReaderHtmlZZZ;
import basic.zKernel.markup.content.KernelContentXmlZZZ;
import basic.zUtil.io.KernelFileZZZ;

public class KernelContentXmlZZZTest extends TestCase{
	private KernelZZZ objKernel;
	private ContentXmlDummyZZZ objContentTest=null;
	
	protected void setUp(){
		try {		
			//Kernel + Log - Object dem TestFixture hinzuf�gen. Siehe test.zzzKernel.KernelZZZTest
			objKernel = new KernelZZZ("TEST", "01", "", "ZKernelConfigLanguageMarkup_test.ini",(String)null);
								
			String sRootName = "ZResponse";
			objContentTest = new ContentXmlDummyZZZ(objKernel, sRootName);
			
			
//			F�r die Ergebnisse der Protokollierung der TEST Ergebnisse. Merke: Die einzelnen Tests haben dann jeweils noch ein Unterverzeichnis
			String sDirectoryTest = "c:\\temp\\" + ReflectCodeZZZ.getClassNameOnly(this);
			FileEasyZZZ.removeDirectory(sDirectoryTest, true);  //Ziel: Das Verzeichnis mit allem was darin ist l�schen !!!
			FileEasyZZZ.makeDirectory(sDirectoryTest);
			
			
			
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
	
	}//END setup
	
	public void tearDown() throws Exception {
		
	}
	
	
	
	//###################################################
	//Die Tests		
	public void testContructor(){
		
		//try{ 
				//+++ Hier wird ein Fehler erwarte
				KernelContentXmlZZZ objContentInit = new ContentXmlDummyZZZ();
				boolean btemp = objContentInit.getFlag("init");
				assertTrue("Unexpected: The init-Flag was expected to be set", btemp);
				
				boolean bError = false;
				try{
					Document document = objContentInit.getDocument();
				}catch(ExceptionZZZ ez){
					bError = true;
				}
				assertTrue("Unexpected: No Root name should exist - Error should happen", bError);
				
			 
				//+++ This is not correct when using the test object
				btemp = objContentTest.getFlag("init");
				assertFalse("Unexpected: The init flag was expected NOT to be set", btemp);
				
				//+++ Nun eine Log-Ausgabe (Notes-Log)
				KernelLogZZZ objKernelLog = objContentTest.getLogObject();
				assertNotNull(objKernelLog);				
				objKernelLog.Write(ReflectCodeZZZ.getMethodCurrentName() + "# succesfully created");
					
		//}catch(ExceptionZZZ ez){
		//	fail("Method throws an exception." + ez.getMessageLast());			
		//}
	}//END testConstructor
	
	public void testSetGetVarFromString(){
		main:{
		try{
			
			//Erstmaliges Setzen des Wertes
			boolean btemp = objContentTest.setVar("zMessage", "Daten erfolgreich verarbeitet"); // Ein zMessageTag, das direkt unterhalb des Root sein soll
			assertTrue(btemp);
			
			//TEST
			String sDirectoryTest = "c:\\temp\\" + ReflectCodeZZZ.getClassNameOnly(this) + "\\" + ReflectCodeZZZ.getMethodCurrentName();
			FileEasyZZZ.removeDirectory(sDirectoryTest, true);  //Ziel: Das Verzeichnis mit allem was darin ist l�schen !!!
			FileEasyZZZ.makeDirectory(sDirectoryTest);
			
			String[] saFlag = {"use_file_expansion"};
			KernelFileZZZ objFileZ = new KernelFileZZZ(sDirectoryTest, "test.xml", 3, saFlag);
			//+++++++++++++++++++++++++++++++++++++++++++++
			File objFile = new File(objFileZ.PathNameTotalExpandedNextCompute());  //.getNameExpandedNext());
			objContentTest.toFile(objFile);
			//+++++++++++++++++++++++++++++++++++++++++++++
			String stemp = objContentTest.getVarString("zMessage");
			assertEquals("Daten erfolgreich verarbeitet", stemp);	
			
			//Zweites Setzen des Wertes dies f�gt einen zweiten Tag gleichen Namens hinzu.
			btemp = objContentTest.setVar("zMessage", "Dies �berschreibt den Wert nicht, sondern h�ngt an");
			assertTrue(btemp);
			//+++++++++++++++++++++++++++++++++++++++++++++
			objFile = new File(objFileZ.PathNameTotalExpandedNextCompute());  //.getNameExpandedNext());
			objContentTest.toFile(objFile);
			//+++++++++++++++++++++++++++++++++++++++++++++
			stemp = objContentTest.getVarString("zMessage");
			assertEquals("Daten erfolgreich verarbeitet", stemp);	//Der erste String bleibt durch das Anh�ngen erhalten 			
			String stemp2 = objContentTest.getVarString("zMessage");
			assertEquals(stemp, stemp2);	//Das muss auch beim 2. Zugriff den gleichen Wert geben.
			stemp2 = objContentTest.getVarFirstString("zMessage");
			assertEquals(stemp, stemp2);	//Das muss auch beim indexbasiertem Zugriff den gleichen Wert geben.
			stemp2 = objContentTest.getVarNthString("zMessage", 0);
			assertEquals(stemp, stemp2);	//Das muss auch beim indexbasiertem Zugriff den gleichen Wert geben.
			
			
			stemp = objContentTest.getVarNthString("zMessage", 1);
			assertEquals("Dies �berschreibt den Wert nicht, sondern h�ngt an", stemp);  //indexbasierter Zugriff auf die 2. zMessage
			
			
			
			
			
			//#### Das Ersetzen ##################################################
			btemp = objContentTest.replaceVarFirst("zMessage","�berschrieben");
			assertTrue(btemp);
			//+++++++++++++++++++++++++++++++++++++++++++++
			objFile = new File(objFileZ.PathNameTotalExpandedNextCompute()); //.getNameExpandedNext());	
			objContentTest.toFile(objFile);  //Das soll dann eine neue Datei sein, die um einen Z�hler erweitert wurde			
			//++++++++++++++++++++++++++++++++++++++++++++++
			stemp = objContentTest.getVarNthString("zMessage", 0);
			assertEquals("�berschrieben", stemp);			
		
			
			
			//Wenn es die variable noch nicht gab, wird false zur�ckgegeben
			btemp = objContentTest.replaceVarFirst("blabla","Alles blabla oder was");
			assertFalse(btemp);
			//+++++++++++++++++++++++++++++++++++++++++++++
			objFile = new File(objFileZ.PathNameTotalExpandedNextCompute()); //.getNameExpandedNext());	
			objContentTest.toFile(objFile);  //Das soll dann eine neue Datei sein, die um einen Z�hler erweitert wurde			
			//++++++++++++++++++++++++++++++++++++++++++++++
			stemp2 = objContentTest.getVarLastString("blabla");			//Die Variable wurde aber ans Ende angeh�ngt
			assertEquals("Alles blabla oder was", stemp2);	
			
			//Nun ersetzt
			btemp = objContentTest.replaceVarLast("blabla","mal was sinnvolles");
			assertTrue(btemp);
			//+++++++++++++++++++++++++++++++++++++++++++++
			objFile = new File(objFileZ.PathNameTotalExpandedNextCompute()); //.getNameExpandedNext());	
			objContentTest.toFile(objFile);  //Das soll dann eine neue Datei sein, die um einen Z�hler erweitert wurde			
			//++++++++++++++++++++++++++++++++++++++++++++++
			stemp2 = objContentTest.getVarLastString("blabla");
			assertEquals("mal was sinnvolles", stemp2);	
			
			
			
			
			//################### Nun mal einen String nach vorne setzen
			btemp = objContentTest.setVarAsFirst("zMessage", "my message as first"); //!!! Das mus ge�nert werden. So wird �berschrieben
			assertTrue(btemp);
			//+++++++++++++++++++++++++++++++++++++++++++++
			objFile = new File(objFileZ.PathNameTotalExpandedNextCompute()); //.getNameExpandedNext());	
			objContentTest.toFile(objFile);  //Das soll dann eine neue Datei sein, die um einen Z�hler erweitert wurde			
			//++++++++++++++++++++++++++++++++++++++++++++++
			stemp = objContentTest.getVarFirstString("zMessage");
			assertEquals("my message as first", stemp);
			stemp = objContentTest.getVarLastString("zMessage");
			
			
			assertEquals("Dies �berschreibt den Wert nicht, sondern h�ngt an", stemp);
			
			
			//TODO:  setVarAsNth(String sVariableName, String sVariableValue, int iIndex) throws ExceptionZZZ 
			
			//TODO: XML-Wie es von einem Notes-Dokument geliefert w�rde als Variable dem intern verwaltetetm JDOM-Dokument hinzuf�gen
			
			
			/*
			//Den Content mit Hashmaps belegen, die den gleichen <Tag> haben.
			HashMapMultiZZZ objHmDocument1 = new HashMapMultiZZZ();
			objHmDocument1.put("UniversalId", "0123456789");		
			objHmDocument1.put("Description", "Neuerstelltes Dokument");
			objContentTest.setVar("Document", objHmDocument1);
			
			HashMapMultiZZZ objHmDocument2 = new HashMapMultiZZZ();
			objHmDocument2.put("UniversalId", "987654321");
			objHmDocument2.put("Description", "Upgedatetes Dokument");
			objContentTest.setVar("Document", objHmDocument2);
			
			// .getVarHm testen, 
			HashMapMultiZZZ objHmTemp = (HashMapMultiZZZ) objContentTest.getVarHm("Document");
			assertEquals(objHmDocument1, objHmTemp);
			
						
			//TODO .getVarHmNext(objHmTemp) testen
			
			//todo goon .setVar entwickeln, das je nach eingabeparameter die korrekte untermethode aufruft
			//Todo goon .getVar entwickeln, entsprechend
			
			//Merke: Beim Zusammenbauen des XML-Baums muss dann gepr�ft werden welche Klasse zur�ckgegeben wird.
			
			*/
			
		
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());			
		}	
		}//end main
	}
	
	
	public void testSetGetVarFromJdom(){
		main:{
		try{
		
			//Merke: Da die Vorbereitungen nur f�r diesen Fall notwendig sind, sollte das nicht ins "setup" des Tests aufgenommen werden.
			//1. Lies eine Datei aus, die das XML-Beispiel enth�lt. Hier ist es das Ergebnis aus notesdocument.getXML()
			//    Da hier aber keine Notes-Klassen bekannt sind, wird �ber die Datei gegangen.					
			/*   Hier der code
		PipedWriter pipeOut = new PipedWriter();
		PipedReader pipeIn = new PipedReader();
		pipeIn.connect(pipeOut);
		//Fehler: "Allready connected"    pipeOut.connect(pipeIn);
		
		doccur.generateXML(pipeOut);  //also ohne Umweg �ber den String
		pipeOut.close();		
		SAXBuilder saxbuilder = new SAXBuilder();
		org.jdom.Document docjdom = saxbuilder.build(pipeIn);
		pipeIn.close();
		*/
			
			
			//#### VORBEREITUNG: PROTI�KOLLIERUNG DER ERGEBNISSE DER TESTAUFRUFE
			String sDirectoryTest = "c:\\temp\\" + ReflectCodeZZZ.getClassNameOnly(this) + "\\" + ReflectCodeZZZ.getMethodCurrentName();
			FileEasyZZZ.removeDirectory(sDirectoryTest, true);  //Ziel: Das Verzeichnis mit allem was darin ist l�schen !!!
			FileEasyZZZ.makeDirectory(sDirectoryTest);
			
			String[] saFlag = {"use_file_expansion"};
			KernelFileZZZ objFileZ = new KernelFileZZZ(sDirectoryTest, "test.xml", 3, saFlag);
			File objFile = null; //Variable f�r die einzelnen Dateien
			
			
			//#### Das erste Dokument hinzuf�gen
			//1. Datei �ffnen
			//File objFileXml = objKernel.getParameterFileByProgramAlias(this.getClass().getName(), "XML_CONTENT", "FileToRead");  //Merke: Der ApplicationKey ist schon im KernelObject vorgegeben.
			File objFileXml = objKernel.getParameterFileByProgramAlias("Test", "XML_CONTENT", "FileToRead");
			if(objFileXml==null){
				ExceptionZZZ ez = new ExceptionZZZ("Kann Datei für diesen Test nicht finden.", KernelZZZ.iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}			
			if(objFileXml.exists()==false){
				ExceptionZZZ ez = new ExceptionZZZ("Die für diesen Test angegebene Datei existiert nicht: '" + objFileXml.getPath() + "'", KernelZZZ.iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//2. Mache aus der Datei ein JDom-Document
			FileReader objReaderFile = new FileReader(objFileXml);
			SAXBuilder saxbuilder = new SAXBuilder();
			org.jdom.Document docjdom = saxbuilder.build(objReaderFile);
			objReaderFile.close();
			
			//+++++++++++++++++++++++++++
			//3. Der eigentliche TEST +++++++++++++++++++++++
			boolean btemp = objContentTest.setVar("docCarrier", docjdom);
			assertTrue(btemp);
			
		   
			//+++ PROTOKOLLIERUNG DES MERGENISSES DES METHODENAUFRUFS ++++++++++++++++++++++++++++++++++++++++++
			objFile = new File(objFileZ.PathNameTotalExpandedNextCompute());  //.getNameExpandedNext());
			objContentTest.toFile(objFile);
			//+++++++++++++++++++++++++++++++++++++++++++++

			//### Das zweite Dokument hinzuf�gen			
			objFileXml = objKernel.getParameterFileByProgramAlias("Test", "XML_Content", "FileToRead2");
			if(objFileXml==null){
				ExceptionZZZ ez = new ExceptionZZZ("Kann Datei f�r diesen Test nicht finden.", KernelZZZ.iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}			
			if(objFileXml.exists()==false){
				ExceptionZZZ ez = new ExceptionZZZ("Die f�r diesen Test angegebene Datei existiert nicht: '" + objFileXml.getPath() + "'", KernelZZZ.iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			objReaderFile = new FileReader(objFileXml);
			saxbuilder = new SAXBuilder();
			docjdom = saxbuilder.build(objReaderFile);
			objReaderFile.close();
			
			btemp = objContentTest.setVar("docMovie", docjdom);
			assertTrue(btemp);
			
			//+++ PROTOKOLLIERUNG DES ERGEBNISSES DES METHODENAUFRUFS ++++++++++++++++++++++++++++++++++++++++++
			objFile = new File(objFileZ.PathNameTotalExpandedNextCompute());  //.getNameExpandedNext());
			objContentTest.toFile(objFile);
			//+++++++++++++++++++++++++++++++++++++++++++++

						
			Document docjdomReturn = objContentTest.getVarDocument("docCarrier");
			assertNotNull(docjdomReturn);
			
			
//			+++ PROTOKOLLIERUNG DES ERGEBNISSES DES METHODENAUFRUFS ++++++++++++++++++++++++++++++++++++++++++
			objFile = new File(objFileZ.PathNameTotalExpandedNextCompute());
			FileOutputStream fout = new FileOutputStream(objFile);
			XMLOutputter outp = new XMLOutputter();
			outp.setFormat(Format.getPrettyFormat());
			outp.output(docjdomReturn,fout);
			fout.close();
//			+++++++++++++++++++++++++++++++++++++++++++++


		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());			
		}catch(FileNotFoundException fnfe){
			fail("Method throws a FileNotfoundException. " + fnfe.getMessage());			
		}catch (JDOMException jdome) {
			fail("Method throws a JDOMException. " + jdome.getMessage());			
		} catch (IOException ioe) {
			fail("Method throws an IOException. " + ioe.getMessage());			
		}
		}//end main
	}
	
}//END CLASS