package basic.zKernel.html.writer;

public class TableHeadZZZ {
	private String sHeadLabel=null;
	private String sHeadId=null;
	
	public TableHeadZZZ() {		
	}
	
	public TableHeadZZZ(String sHeadId, String sHeadLabel) {
		this.setHeadId(sHeadId);
		this.setHeadLabel(sHeadLabel);
	}
	
	public String getHeadLabel() {
		return this.sHeadLabel;		
	}
	public void setHeadLabel(String sHeadLabel) {
		this.sHeadLabel=sHeadLabel;
	}
	
	public String getHeadId() {
		return this.sHeadId;
	}
	public void setHeadId(String sHeadId) {
		this.sHeadId = sHeadId;
	}
	
	public String toString() {
		return this.sHeadId + "=" + this.sHeadLabel;
	}
	
	
}
