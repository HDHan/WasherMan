package kr.ac.ajou.lazybones.templates;

import java.util.Date;

public class UserCommandLogForm {

	private Long id;
	private String uid;
	private String command;
	private Date logged_at;

	public UserCommandLogForm(){
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Date getLogged_at() {
		return logged_at;
	}

	public void setLogged_at(Date logged_at) {
		this.logged_at = logged_at;
	}
	
	

}
