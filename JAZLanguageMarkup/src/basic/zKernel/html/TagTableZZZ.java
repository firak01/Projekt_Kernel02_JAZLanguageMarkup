package basic.zKernel.html;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;

public class TagTableZZZ extends AbstractKernelTagZZZ{
	private static final long serialVersionUID = -4171802821086651895L;

	public TagTableZZZ(Element objElem) throws ExceptionZZZ {
		super(new TagTypeTableZZZ(), objElem);		
	}
	
	public TagTableZZZ(IKernelZZZ objKernel, Element objElem) throws ExceptionZZZ {
		super(objKernel, new TagTypeTableZZZ(), objElem);		
	}
	
	public List<TagTableRowZZZ>getRows() throws ExceptionZZZ{
		return this.readRows();
	}
	
	public int getRowMin() throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			List<TagTableRowZZZ>listRow = this.getRows();
			if(listRow.isEmpty()) {
				iReturn = 0;
			}else {
				iReturn = 1;
			}
		}//end main:
		return iReturn;
	}
	
	public int getRowMax() throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			List<TagTableRowZZZ>listRow = this.getRows();
			if(listRow.isEmpty()) {
				iReturn = 0;
			}else {
				iReturn = listRow.size();
			}
		}//end main:
		return iReturn;
	}
	
		//Speziel für Tabellen gilt: Sie haben keinen eigenen String-Wert, sondern weitere Elemente... Zeilen.
		@SuppressWarnings("null")
		public List<Element>readRowsAsElement() throws ExceptionZZZ {
			List<Element>listReturn = new ArrayList<Element>();
			main:{
				Element objElem = this.getElement();
				if(objElem == null) break main;
				
				@SuppressWarnings("unchecked")
				List<Element> listtemp = objElem.getChildren("TBODY");
				
				//Hier nun eine Liste der TagTableRowZZZ - elemente machen!!!
				//Beachte: Der Tabellen Header muss mit th - element TagTableHeaderZZZ - element sein.
				for(Element objElemtemp : listtemp) {//Das ist nur 1 Element: TBODY						
//					String sName = objElemtemp.getQualifiedName();
//					if(sName.equals("TBODY")) {
						@SuppressWarnings("unchecked")
						List<Element> listbody = objElemtemp.getChildren();
						for(Element objRowtemp : listbody){						
							String sName = objRowtemp.getQualifiedName();						
							if(sName.equals("TR")) {
								//Nun Headerzeile rausfiltern
								boolean bHeader = TagTableZZZ.isTableHeader(objRowtemp);
								if(!bHeader) {
									listReturn.add(objRowtemp);
								}
							}
						}
//					}				
				}
				
			}
			return listReturn;
		}
		
		public List<TagTableRowZZZ> readRows() throws ExceptionZZZ{
			List<TagTableRowZZZ>listReturn = new ArrayList<TagTableRowZZZ>();
			main:{
				List<Element> listElement = this.readRowsAsElement();
				for(Element element : listElement) {
					TagTableRowZZZ objRow = new TagTableRowZZZ(element);
					listReturn.add(objRow);
				}
			}//end main:
			return listReturn;
		}
		
		public TagTableRowZZZ getRow(int iRow) throws ExceptionZZZ{			
			TagTableRowZZZ objReturn = null;
			main:{
				if(iRow<=0){
					ExceptionZZZ ez = new ExceptionZZZ("Row starts with 1.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				List<TagTableRowZZZ> listRow = this.getRows();
				if(iRow > listRow.size()) break main;
				
				objReturn = listRow.get(iRow-1);			
			}//end main:
			return objReturn;
		}
		
		
		/**Read the Lines from the table. And separate them by \n 
		 * Use a default Column Seperator.
		 */
		public String readValue() throws ExceptionZZZ{
			String sReturn = null;
			main:{
				check:{
					if(this.getElement()==null){					
						ExceptionZZZ ez = new ExceptionZZZ("JElement", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}//END check
			
				//Eine Tabelle hat selbst keinen Inhalt, nur weitere Elemente (Zeilen tr, Spalten td)
				boolean bAnyRow=false;
				List<TagTableRowZZZ> listRow = this.readRows();
				for(TagTableRowZZZ objRow: listRow) {
					String sRow = objRow.getValue();					
					if(bAnyRow) {
						sReturn = sReturn + "\n" + sRow;
					}else {
						sReturn = sRow;
					}
					bAnyRow=true;
				}
			}//END main
			return sReturn;
		}
		
		public String readValueByTagType()  throws ExceptionZZZ{
			String sReturn = null;
			main:{
				org.jdom.Element objElement = this.getElement();
				if(objElement==null){					
					ExceptionZZZ ez = new ExceptionZZZ("JElement", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				TagTypeTableZZZ objTagType = (TagTypeTableZZZ) this.getTagType();					
				if(objTagType==null) {
					ExceptionZZZ ez = new ExceptionZZZ("TagType-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}

				sReturn = objTagType.readValue(objElement);
				
			}//END main
			return sReturn;
		}
		
		public String getValue(int iRow, int iColumn) throws ExceptionZZZ {
			String sReturn = null;
			main:{
				check:{
					if(iRow<=0){
						ExceptionZZZ ez = new ExceptionZZZ("Row starts with 1.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					if(iColumn<=0){
						ExceptionZZZ ez = new ExceptionZZZ("Column starts with 1.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}//END check
			
				//1. Die Reihe holen
				TagTableRowZZZ objRow = this.getRow(iRow);
				if(objRow==null) break main;
				
				//2. Aus der Reihe die Spalte (bzw. Zelle) holen
				sReturn = objRow.getValue(iColumn);
							
			}//END main
			return sReturn;
		}
		
		public boolean setValue(int iRow, int iColumn, String sValue) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				check:{
				if(iRow<=0){
					ExceptionZZZ ez = new ExceptionZZZ("Row starts with 1.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(iColumn<=0){
					ExceptionZZZ ez = new ExceptionZZZ("Column starts with 1.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}								
			}//END check
			
			//1. Die Reihe holen
			TagTableRowZZZ objRow = this.getRow(iRow);
			if(objRow==null) break main;
			
			//2. In der Reihe den Wert setzen
			bReturn = objRow.setValue(iColumn, sValue);
		
			}//end main;
			return bReturn;
		}
	
		
	
	
	
	
	//######################### STATIC 
	public static boolean isTableHeader(Element objElement) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objElement==null) {
				ExceptionZZZ ez = new ExceptionZZZ("org.jdom.Element",iERROR_PARAMETER_MISSING, TagTableZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			boolean bIsRow = TagTypeTableRowZZZ.isTableHeader(objElement);
			if(!bIsRow) break main;
						
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	public static boolean isTableRow(Element objElement) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objElement==null) {
				ExceptionZZZ ez = new ExceptionZZZ("org.jdom.Element",iERROR_PARAMETER_MISSING, TagTableZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			boolean bIsRow = TagTypeTableRowZZZ.isTableRow(objElement);
			if(!bIsRow) break main;
			
			bReturn = true;
		}//end main
		return bReturn;
	}
}
