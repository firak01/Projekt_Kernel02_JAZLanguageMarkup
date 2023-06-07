package basic.zKernel.html;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;

public class TagTableRowWithHeaderZZZ extends TagTableRowZZZ{
	private static final long serialVersionUID = -4171802821086651895L;
	private org.jdom.Element objElemHeader = null;
	
	public TagTableRowWithHeaderZZZ(Element objElem, Element objElemHeader) throws ExceptionZZZ {
		super(objElem);	
		TagTableRowWithHeaderNew_(objElemHeader);
	}
	
	public TagTableRowWithHeaderZZZ(IKernelZZZ objKernel, Element objElem, Element objElemHeader) throws ExceptionZZZ {
		super(objKernel, objElem);
		TagTableRowWithHeaderNew_(objElemHeader);
	}
	
	
	
	private boolean TagTableRowWithHeaderNew_(Element objElemHeader) throws ExceptionZZZ{
		this.objElemHeader = objElemHeader;
		return true;
	}
	
	public Element getHeaderElement() {
		return this.objElemHeader;
	}
	
	public void setHeaderElement(Element objElemHeader) {
		this.objElemHeader = objElemHeader;
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
	
	public List<TagTableColumnCellZZZ>getColumnCells() throws ExceptionZZZ{
		return this.readColumnCells();
	}
			
	public List<TagTableCellZZZ>readHeaderCells() throws ExceptionZZZ{
		List<TagTableCellZZZ> listReturn = new ArrayList<TagTableCellZZZ>();
		main:{
			List<Element> listElement = this.readHeaderCellsAsElement();
			for(Element element : listElement) {
				TagTableCellZZZ objColumn = new TagTableCellZZZ(element);
				listReturn.add(objColumn);
			}
		}//end main
		return listReturn;
	}
		
	public List<TagTableColumnCellZZZ>readColumnCells() throws ExceptionZZZ{
		List<TagTableColumnCellZZZ> listReturn = new ArrayList<TagTableColumnCellZZZ>();
		main:{
			
			List<Element> listElement = this.readCellsAsElement();
			List<TagTableCellZZZ> listCell = new ArrayList<TagTableCellZZZ>();
			for(Element element : listElement) {
				TagTableCellZZZ objColumn = new TagTableCellZZZ(element);
				listCell.add(objColumn);
			}
			
//			List<Element> listElementHeader = this.readHeaderCellsAsElement();
//			List<TagTableCellZZZ> listHeaderCell = new ArrayList<TagTableCellZZZ>();
//			for(Element element : listElementHeader) {
//				TagTableCellZZZ objColumn = new TagTableCellZZZ(element);
//				listHeaderCell.add(objColumn);
//			}
			
			//Annahme: Es gibt für jede Zelle nur 1 Headerzelle
			TagTableHeaderRowZZZ objHeaderRow = new TagTableHeaderRowZZZ(this.getHeaderElement());
			
			for(int i=0; i<=listCell.size()-1;i++) {
				TagTableCellZZZ objCell = listCell.get(i);
				TagTableCellZZZ objHeaderCell = objHeaderRow.getCell(i+1);
				TagTableColumnCellZZZ objColumnCell = new TagTableColumnCellZZZ(objCell.getElement(), objHeaderCell.getElement());
				listReturn.add(objColumnCell);
			}
			
		}//end main
		return listReturn;
	}
	
	//Speziel für Tabellen gilt: Sie haben keinen eigenen String-Wert, sondern weitere Elemente... Zeilen.
	@SuppressWarnings("null")
	public List<Element>readHeaderCellsAsElement() throws ExceptionZZZ {
		List<Element>listReturn = new ArrayList<Element>();
		main:{
			Element objElem = this.getElement();
			if(objElem == null) break main;
			
			@SuppressWarnings("unchecked")
			List<Element> listtemp = objElem.getChildren("TH");
			
			//Hier nun eine Liste der TagTableRowZZZ - elemente machen!!!
			//Beachte: Der Tabellen Header muss mit th - element TagTableHeaderZZZ - element sein.
			for(Element objElemtemp : listtemp) {//Das ist nur 1 Element: TBODY														
				String sName = objElemtemp.getQualifiedName();						
				if(sName.equals(TagTypeTableCellZZZ.sTAGNAME.toUpperCase())) {						
					listReturn.add(objElemtemp);		
				}				
			}				
		}
		return listReturn;
	}
	
}
