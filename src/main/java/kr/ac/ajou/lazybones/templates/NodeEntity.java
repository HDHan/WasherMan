package kr.ac.ajou.lazybones.templates;

public class NodeEntity {
    private String NID;
	private String serialNumber;
    private String owner;
    private String productName;
    private String nodeName;
    
	public NodeEntity() {
		
	}
	
	public NodeEntity(String nid, String sn, String owner, String pn, String name) {
		this.NID = nid;
		this.serialNumber = sn;
		this.owner = owner;
		this.productName = pn;
		this.nodeName = name;
	}

	public String getNID() {
		return NID;
	}

	public void setNID(String nid) {
		this.NID = nid;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String sn) {
		this.serialNumber = sn;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String pn) {
		this.productName = pn;
	}
	
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String name) {
		this.nodeName = name;
	}

}