package basic.zKernel.markup.content;

import java.io.File;
import java.util.ArrayList;
import basic.zKernel.IKernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.html.TagTypeZHtmlZZZ;
import basic.zKernel.html.reader.KernelReaderHtmlZZZ;

public class ContentFileZZZ extends KernelContentZZZ implements IKernelContentFileZZZ, IKernelContentComputableZZZ {
	File objFile;
	KernelReaderHtmlZZZ objReaderHtml;
	
	public ContentFileZZZ(IKernelZZZ objKernel) {
		super(objKernel);
	}
	public ContentFileZZZ(IKernelZZZ objKernel, File objFile) throws ExceptionZZZ{
		super(objKernel);
		if(objFile==null){
			ExceptionZZZ ez = new ExceptionZZZ("File-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		this.setFilePattern(objFile);
	}

	/* (non-Javadoc)
	 * @see basic.zKernel.markup.content.KernelContentZZZ#compute()
	 * 
	 * Mit dem Flag "RemoveZHTML" wird daf�r gesorgt, das die Tags anschliessend entfernt sind.
	 */
	public boolean compute() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			
			//1. KernelReaderHtml-Objekt
			KernelReaderHtmlZZZ objReaderHtml = new KernelReaderHtmlZZZ(this.getKernelObject(), this.getFilePattern(), null);
			this.setReaderCurrent(objReaderHtml);
			
			TagTypeZHtmlZZZ objTagTypeZ = new TagTypeZHtmlZZZ(objKernel); 
			
			ArrayList listaElement = objReaderHtml.readElementAll(null, objTagTypeZ);
			if(listaElement.size()==0) break main;
			
			//2. in der Liste sind nun alles Elemente von JDOM. Die sollten aber TEXT sein.
			for(int icount = 0 ; icount <= listaElement.size()-1; icount++){
				//TYXPECAST FALSCH  !!!! org.jdom.Text text = (org.jdom.Text) listaElement.get(icount);
				org.jdom.Element elem = (org.jdom.Element) listaElement.get(icount); 
				String stemp = elem.getValue();		//Das ist der text der variable, die es zu ersetzen gilt			
				String sValue = this.getVarString(stemp); //dadurch wird f�r die variable der Wert geholt aus einer hashmap
				
				//Nun den Wert im ZHTML-Tag ersetzen. Dadurch wird automatisch der Wert im Parent Element auch ge�ndert
				elem.setText(sValue);
				
			}//END FOR
			
			
			if(this.getFlag("RemoveZHTML")==true){
				//!!! Das darf erst passieren, nachdem alle ZHTML-Tags verarbeitet worden sind. Sinst werd durch die Wertersetzung in dem Parentelement ggf. noch zu verarbeitende ZHTML-Tags auch ersetzt. 
				//das ZHTML - Tag entfernen, das zur Kennzeichnung im Quellcode eingebaut ist
				for(int icount = 0; icount <= listaElement.size()-1; icount++){
					org.jdom.Element elem = (org.jdom.Element) listaElement.get(icount); 
					org.jdom.Element elemParent = elem.getParentElement();
					if(elemParent!=null){
//						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# Parentname: " + elemParent.getName() + " #Parentvalue: " + elemParent.getValue());
						
//						Vor dem Entfernen des ZHTM-Elements muss der Wert des Parents ermittelt werden und kann nach dem Entfernen wieder ganz an das Parent Element �bergeben werden
						String sValueTotalTemp = elemParent.getValue();
					
						//Nun das element aus dem Parent entfernen
						elemParent.removeContent(elem);
						//System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#Nach dem Entfernen des ZHTML-Elemtents# Parentname: " + elemParent.getName() + " #Parentvalue2: " + elemParent.getValue());
					
						//Nun den ganzen Wert wieder an das PArent Element �bergeben.
						elemParent.setText(sValueTotalTemp);
						//System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#Nach dem Entfernen des ZHTML-Elemtents und dem Erneuten Hinzuf�gen# Parentname: " + elemParent.getName() + " #Parentvalue2: " + elemParent.getValue());
					}//end if elemParent != null
					
				}//END FOR
			}//End if flag removezhtml

			
			bReturn = true;
		}//end main
		return bReturn;
	}

	public File getFilePattern() {
		return this.objFile;
	}

	public void setFilePattern(File objFile) {
		this.objFile = objFile;
	}
	
	
	public void setReaderCurrent(KernelReaderHtmlZZZ objReader){
		this.objReaderHtml = objReader;
	}
	public KernelReaderHtmlZZZ getReaderCurrent(){
		return this.objReaderHtml;
	}
}
