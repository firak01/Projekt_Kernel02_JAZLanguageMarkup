package basic.zKernel.net.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.xerces.parsers.DOMParser;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Document;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.html.KernelTagFactoryZZZ;
import basic.zKernel.html.TagInputZZZ;
import basic.zKernel.html.TagTypeInputZZZ;
import basic.zKernel.html.reader.KernelReaderHtmlZZZ;
import basic.zKernel.net.client.KernelReaderPageZZZ;
import basic.zKernel.net.client.KernelReaderURLZZZ;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;

public class GetClientIPPageFromWebByTable implements IConstantZZZ{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TODOGOON20230602; //Erste einmal die Page nach der Tabelle durchsuchen per DebugKlasse...
		
		KernelZZZ objKernel = null;
		LogZZZ objLog = null;
		String sURLNext=null;
		main:{
		try{
//			1. Erstellen das Z-Kernel Objekt
//			objKernel = new KernelZZZ("FGL", "01", "", "ZKernelConfigIPPage_default.ini",(String) null);
			
			//Funktioniert aber auch ohne Angabe der Datei, dann wird die Defaultdatei aus einer Konstante genommen.
			objKernel = new KernelZZZ("FGL", "01", "", "",(String) null);
			
			//2. Protokoll
			objLog = objKernel.getLogObject();

			//Lokale IP ausgeben
			InetAddress thisIP= InetAddress.getLocalHost();
			String sIP = thisIP.getHostAddress();			
			System.out.println("local IP-Number is: " + sIP);
										
			//Zur Web-Seite verbinden, dazu mal den KernelReaderURL verwenden
			String sURL = objKernel.getParameterByProgramAlias("IPPage","ProgIPReader","URL2Read").getValue(); 
			if(sURL==null){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_CONFIGURATION_MISSING+"URL String", iERROR_CONFIGURATION_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			String[] satemp = {"UseStream"};
			KernelReaderURLZZZ objReaderURL = new KernelReaderURLZZZ(objKernel, sURL,satemp, "");
			//Hier bei der itelligence bin ich hinter einem Proxy. Die auszulesende Seite ist aber im Web
			String sProxyHost = objKernel.getParameterByProgramAlias("IPPage","ProgIPReader","ProxyHost").getValue(); 
			String sProxyPort = null;
			if(sProxyHost!=null && sProxyHost.equals("")==false){				
				sProxyPort = objKernel.getParameterByProgramAlias("IPPage","ProgIPReader","ProxyPort").getValue(); 
				//TODO: Existenz des Proxies testen und nur dann enablen, wenn er auch existiert !!!
				//objReaderURL.setProxyEnabled(sProxyHost, sProxyPort);
			}
			
			KernelReaderPageZZZ objReaderPage = objReaderURL.getReaderPage();
			KernelReaderHtmlZZZ objReaderHTML = objReaderPage.getReaderHTML();
			String sDoc = objReaderHTML.getDocumentAsString();
			System.out.println("document as string: \n"+sDoc);
			
			//Nun alle input-Elemente holen
			ArrayList listaElem = new ArrayList();
			TagTypeInputZZZ objTagTypeInput = new TagTypeInputZZZ(objKernel);
			listaElem = objReaderHTML.readElementAll(null, objTagTypeInput);
			
			System.out.println("Number of elements found: " + listaElem.size());
		
			for(int icount = 0; icount < listaElem.size(); icount++){
				//In der Schleife alle Input-Felder durchgehen
			
				Element elem = (Element) listaElem.get(icount);
				TagInputZZZ objTag = (TagInputZZZ) KernelTagFactoryZZZ.createTagByElement(objKernel, elem);
				/*
				KernelTagTypeZZZ objType = objTag.getTagType();
				Class objClass = objType.getClass();
			    System.out.println(objClass.getName());
			    */
				String sName = objTag.readName();
				String sValue = objTag.readValue();
				System.out.println(sName + " ... " + sValue);
			
			    
				/* FGL: DAS IST NOCH NICHT AUSREICHEND
				KernelTagTypeZZZ objTagType = KernelTagFactoryZZZ.createTagTypeByElement(objKernel, elem);
				System.out.println(objTagType.getTagName());
				
				Class objClass = objTagType.getClass();
			    System.out.println(objClass.getName());
				*/
				
				
				/* FGL: DAS IST MIR ZU KOMPLIZIERT
				boolean bIPNrElement = false;
				List lAtt = elem.getAttributes();
				if(lAtt !=null){
					Iterator iteratorAtt = lAtt.iterator();
					while(iteratorAtt.hasNext()){
						//In dieser Schleife alle Attribute eines Inputfelds durchgehen
						Attribute att = (Attribute) iteratorAtt.next();
						System.out.print(att.getName());
						System.out.println(" ... " + att.getValue());
						if(att.getName().equals("name")){
							if(att.getValue().equals("IPNr")) bIPNrElement = true;
						}
						if(att.getName().equals("value") && bIPNrElement ==true){
							sURLNext = att.getValue();
						}
						
					}
				}
					*/			
			}//END for
			
			
			//### Neuer Verbindungsauf, diesmal mit einem Kennwortgesch�tzten Server:  MEINEM
			if(sURLNext!=null){
				if(sURLNext.equals("")){
					System.out.println("No further URL was read out.");
					break main;
				}
				System.out.println("Now: Connect to this server. First try: No password provided.");
				sURLNext = "http://" + sURLNext;
				
				//neues Connection-Object, etc.
				String[] satemp2 = {"UseStream"};
				KernelReaderURLZZZ objReaderURL2 = new KernelReaderURLZZZ(objKernel, sURLNext,"Fritz Lindhauer", "1fgl2fgl", satemp2, "");
				//Hier bei der itelligence bin ich hinter einem Proxy. Die auszulesende Seite ist aber im Web				
				if(sProxyHost!=null && sProxyHost.equals("")==false){									
					objReaderURL.setProxyEnabled(sProxyHost, sProxyPort);
				}
				
				KernelReaderPageZZZ objReaderPage2 = objReaderURL2.getReaderPage();
				KernelReaderHtmlZZZ objReaderHTML2 = objReaderPage2.getReaderHTML();
				
				//Mal alles ausgeben lassen
				org.jdom.Document doc = objReaderHTML2.getDocument();
				if(doc==null){
					System.out.println("No further document found");
					break main;
				}

				String sDoc2 = objReaderHTML2.getDocumentAsString();
				System.out.println("document as string: \n"+sDoc2);
				
				Element elemRoot = doc.getRootElement();
				if(elemRoot == null){
					System.out.println("No root found in document.");
					break main;
				}
				
				objReaderHTML.listNodes(elemRoot, 0);
				
				
			}//sURLNext!= null
		
		}catch(ExceptionZZZ ez){
			if(objLog==null){
				System.out.println(ez.getDetailAllLast());				
			}else{
				objLog.WriteLineDate(ez.getDetailAllLast());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}//END try
	}//END main
	

}
