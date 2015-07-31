package kr.ac.ajou.lazybones.templates;

public class GetLogdataForm {

	private String credential;
	private Long millis;
	private Integer limit;
	
	public GetLogdataForm(){
		
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public Long getMillis() {
		return millis;
	}

	public void setMillis(Long millis) {
		this.millis = millis;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
		
	
	
}
