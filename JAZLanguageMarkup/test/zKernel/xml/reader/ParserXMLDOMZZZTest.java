package zKernel.xml.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.xml.reader.DTDErrorZZZ;
import basic.zKernel.xml.reader.ParserXMLDOMZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import custom.zUtil.io.FileZZZ;

public class ParserXMLDOMZZZTest extends TestCase{
		//+++ Pfad ermitteln	 
		private final String sFILE_DIRECTORY_DEFAULT = "test" + File.separator + ReflectCodeUnensuredZZZ.getPackagePathForConstant(this);
		private final static String sFILE_NAME_DEFAULT = new String("ursuppe.xml");
		//TODO: Das liefert noch einen Fehler, die XSD-Datei scheint noch nicht korrekt zu sein:   private final static String sFILE_NAME_DEFAULT = new String("ursuppe mit verweis auf xsd.xml");
		
		//Kernel und Log-Objekt
		private KernelZZZ objKernel;
		
		//+++ Die Beispieldatei/Das Beispieldocument
		private File objFile;
		private Document objDoc; //W3C - XML Dokument
		
				
		/// +++ Die eigentlichen Test-Objekte
		// Parsen einer Datei
		private ParserXMLDOMZZZ  objParserFileTest;
		
		// Parsen keiner expliziten Datei. Es wird ein XML-Dokument schon im Konstruktor �bergeben.
		private ParserXMLDOMZZZ objParserDocTest;

		protected void setUp(){
			try {			
				
				/*  FGL: Die Beispieldatei wird nicht erzeugt, sie befindet sich in dem Projekt
				 * 
				 * 
				
				//### Eine Beispieldatei. Merke: Die Eintr�ge werden immer neu geschrieben und nicht etwa angeh�ngt.
				//Merke: Es soll nicht versucht werden die Datei z.B. mit der Datei aus dem FileIniZZZTest 
				//Merke: Erst wenn es �berhaupt einen Test gibt, wird diese Datei erstellt
				String sFilePathTotal = FileZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_DEFAULT );
				Stream objStreamFile = new Stream(sFilePathTotal, 1);  //This is not enough, to create the file			
				objStreamFile.println(";This is a temporarily test file for FileIniTest.");      //Now the File is created. This is a comment line
				objStreamFile.println("#This is another commentline");
				objStreamFile.println("[Section A]");
				objStreamFile.println("Testentry1=Testvalue1");			
				objStreamFile.println("Testentry2=Testvalue2");
				
				...
				
				objStreamFile.close();
				
				objFile = new File(sFilePathTotal);
				
				
				*/
								
				//Kernel + Log - Object dem TestFixture hinzuf�gen. Siehe test.zzzKernel.KernelZZZTest
				//alt:objKernel = new KernelZZZ("FGL", "01", "", "ZKernelConfigKernel_test.ini",(String)null);
				objKernel = new KernelZZZ("TEST", "01", "", "ZKernelConfigLanguageMarkup_test.ini",(String)null);
		
				
				//### Die TestObjecte						
				//+++The main object used for testing: FILE Based
				objFile = new File(sFILE_DIRECTORY_DEFAULT + File.separator + sFILE_NAME_DEFAULT);
				objParserFileTest = new ParserXMLDOMZZZ(objKernel, objFile,null,  "");
				
				//+++The main object used for testing: DOCUMENT Based
				//DocumentBuilderObjekt und Document erstellen
				DocumentBuilderFactory domfactory = DocumentBuilderFactory.newInstance();
				domfactory.setValidating(true);//+++ G�ltigkeitspr�fung aktivieren
				domfactory.setIgnoringElementContentWhitespace(true);//+++ Whitespace ignorieren			
				DocumentBuilder dombuilder=null;
				try {
					dombuilder = domfactory.newDocumentBuilder();
				} catch (ParserConfigurationException e) {					
					fail("Method throws an ParserConfigurationException, creating a DocumentBuilder" + e.getMessage());
				}
				DTDErrorZZZ eh = new DTDErrorZZZ();//+++ Einen default-Errorhandler bestimmen, das ist notwendig wg. der G�ltigkeitspr�fung. Merke: Die Original-Klasse gibt nix aus, darum mit meiner erweiterten Klasse arbeiten.
				dombuilder.setErrorHandler(eh);						
				try {
					objDoc = dombuilder.parse(objFile);
				} catch (SAXException e) {
					fail("Method throws an SAXException, parsing the file: '" + objFile.getPath() + "'" + e.getMessage());
				}//+++ Einlesen der Datei in das XML-Dokument
			
				objParserDocTest = new ParserXMLDOMZZZ(objKernel, objDoc, null, "");
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} catch (FileNotFoundException e) {
				fail("Method throws a FileNotFoundException." + e.getMessage());
			} catch (IOException e) {
				fail("Method throws an IOException." + e.getMessage());
			}		
		}//END setup
		
		public void testContructor(){
			
			try{
			
					//+++An object just initialized
					ParserXMLDOMZZZ objParserInit = new ParserXMLDOMZZZ();
					boolean btemp = objParserInit.getFlag("init");
					assertTrue("Unexpected: The init flag was expected to be set", btemp);
			
					//+++Bei den Test-Objekten muss hingegen immer ein Document-Objekt vorhanden sein
					Document objDoc = objParserFileTest.getDocument();
					assertNotNull("XML-Document is null: File eingelesen.", objDoc);
					
					objDoc = objParserDocTest.getDocument();
					assertNotNull("XML-Document is null: Document �bergeben", objDoc);
					
					
			}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}//END testConstructor
		
		public void testReadRoot(){
			try{
			
			//+++ Tag des Wurzelknotens auslesen
			String stemp = objParserFileTest.readTagRootName();
			assertEquals("ursuppe", stemp);
			
			//der muss ja auch dem Objekt mit einem anderen Konstruktor entsprechen
			assertEquals("Both objects were expected to return the same root name", stemp, objParserDocTest.readTagRootName());
			
			//demzufolge muss die �berpr�fung in beiden Konstruktortyp-Objekten auch true ergeben
			assertTrue(objParserFileTest.proofDocumentRootName(stemp));
			assertTrue(objParserDocTest.proofDocumentRootName(stemp));
								
			}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
			}				
		}//END testReadRoot()
		
		public void testReadTagFirstLevel(){
			try{
				//Den wert eines ersten Knoten ausgeben, ausgehend von Root
				Node objNodeRoot = objParserFileTest.getNodeRoot();
				assertNotNull(objNodeRoot);
				
				String stemp = objParserFileTest.readTagValueFirst(objNodeRoot, "description");
				assertEquals("die erste Karte", stemp);
				
				stemp = objParserFileTest.readTagValueFirstChildren(objNodeRoot, "description");
				assertEquals("die erste Karte", stemp);
				
				stemp = objParserFileTest.readTagValueFirstChildren(objNodeRoot, "file");
				assertEquals("eins.jpg", stemp);
				
				
				//Nun eine Knoten suchen ...
				Node objNodeTarget = objParserFileTest.readNodeFirstByKey(objNodeRoot, "alias", "2");
				assertNotNull("There was at least one node expected with the name 'alias'", objNodeTarget);
				assertEquals(objNodeTarget.getNodeValue(), "2");
				
		
				// ... und auswerten.
				stemp = objParserFileTest.readTagValueFirst(objNodeTarget, "description");
				assertEquals("die zweite Karte", stemp);
		
				
			}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
			}		
		}

		/*
		
		/** void, Test: Receiving the content of an .ini-section
		* Lindhauer; 23.04.2006 09:13:04

		public void testGetVariables(){
			//This will test the number of entries in the section
			String[] saProperties = objIniFileTest.getVariables("Section testGetVariables");
			assertNotNull("Unexpected: The array of properties is null", saProperties);
			assertEquals(3, saProperties.length);
			
			//This will test the number of entries in a section, where no entry has a value !!!
			String[] saProperties2 = objIniFileTest.getVariables("Section empty testGetVariables");
			assertNotNull("Unexpected: The array of properties is null", saProperties2);
			assertEquals(5, saProperties2.length);
		}
		
		public void testGetSubject(){
			String[] saSection = objIniFileTest.getSubjects();
			assertNotNull("Unexpected: The array of sections is null", saSection);
			assertEquals(4, saSection.length);
		}
		
		*/
			
		}//END Class


