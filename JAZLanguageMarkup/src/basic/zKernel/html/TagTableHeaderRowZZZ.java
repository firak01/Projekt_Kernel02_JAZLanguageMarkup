package basic.zKernel.html;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;

public class TagTableHeaderRowZZZ extends AbstractKernelTagZZZ{
	private static final long serialVersionUID = -4171802821086651895L;

	public TagTableHeaderRowZZZ(Element objElem) throws ExceptionZZZ {
		super(new TagTypeTableRowZZZ(), objElem);		
	}
	
	public TagTableHeaderRowZZZ(IKernelZZZ objKernel, Element objElem) throws ExceptionZZZ {
		super(objKernel, new TagTypeTableRowZZZ(), objElem);		
	}
	
	public TagTableCellZZZ getCell(int iCell) throws ExceptionZZZ {
		TagTableCellZZZ objReturn = null;
		main:{
			if(iCell<=0){
				ExceptionZZZ ez = new ExceptionZZZ("Cell starts with 1.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			List<TagTableCellZZZ> listCell = this.getCells();
			if(iCell > listCell.size()) break main;
			
			objReturn = listCell.get(iCell-1);
		}
		return objReturn;
	}
	
	public List<TagTableCellZZZ>getCells() throws ExceptionZZZ{
		return this.readCells();
	}
	
	public List<TagTableCellZZZ>readCells() throws ExceptionZZZ{
		List<TagTableCellZZZ> listReturn = new ArrayList<TagTableCellZZZ>();
		main:{
			List<Element> listElement = this.readCellsAsElement();
			for(Element element : listElement) {
				TagTableCellZZZ objColumn = new TagTableCellZZZ(element);
				listReturn.add(objColumn);
			}
		}//end main
		return listReturn;
	}
	
	//Speziel für Tabellen gilt: Sie haben keinen eigenen String-Wert, sondern weitere Elemente... Zeilen.
		@SuppressWarnings("null")
		public List<Element>readCellsAsElement() throws ExceptionZZZ {
			List<Element>listReturn = new ArrayList<Element>();
			main:{
				Element objElem = this.getElement();
				if(objElem == null) break main;
				
				@SuppressWarnings("unchecked")
				List<Element> listtemp = objElem.getChildren("TH");//!!! DAS MACHT DIE HEADER ROW AUS !!!
				
				//Hier nun eine Liste der TagTableRowZZZ - elemente machen!!!
				//Beachte: Der Tabellen Header muss mit th - element TagTableHeaderZZZ - element sein.
				for(Element objElemtemp : listtemp) {//Das ist nur 1 Element: TBODY														
					String sName = objElemtemp.getQualifiedName();						
					if(sName.equals(TagTypeTableHeaderCellZZZ.sTAGNAME.toUpperCase())) {						
						listReturn.add(objElemtemp);		
					}				
				}				
			}
			return listReturn;
		}
		
		public String getValue(int iCell) throws ExceptionZZZ {
			String sReturn = null;
			main:{
				if(iCell<=0){
					ExceptionZZZ ez = new ExceptionZZZ("Cell starts with 1.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				TagTableCellZZZ objCell = this.getCell(iCell);
				if(objCell == null) break main;
				
				sReturn = objCell.getValue();
			}
			return sReturn;
		}
		
		public boolean setValue(int iCell, String sValue) throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				if(iCell<=0){
					ExceptionZZZ ez = new ExceptionZZZ("Cell starts with 1.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				TagTableCellZZZ objCell = this.getCell(iCell);
				if(objCell == null) break main;
				
				bReturn = objCell.setValue(sValue);
			}
			return bReturn;
			
		}
		
		public boolean isTableRow() throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				org.jdom.Element objElement = this.getElement();
				bReturn = TagTypeTableRowZZZ.isTableRow(objElement);
			}//end main:
			return bReturn;
		}
		
		public boolean isTableHeader() throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				org.jdom.Element objElement = this.getElement();
				bReturn = TagTypeTableRowZZZ.isTableHeader(objElement);
			}//end main:
			return bReturn;
		}
}
