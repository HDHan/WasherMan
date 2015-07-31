package kr.ac.ajou.lazybones.templates;

import java.io.Serializable;

public class ReadForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5295853323959141876L;
	private String credential;
	private Integer limit;

	public ReadForm(){
		
	}
	
	
	public Integer getLimit() {
		return limit;
	}


	public void setLimit(Integer limit) {
		this.limit = limit;
	}


	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

}
