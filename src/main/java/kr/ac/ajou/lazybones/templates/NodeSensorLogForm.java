package kr.ac.ajou.lazybones.templates;

import java.util.Date;

public class NodeSensorLogForm {
	
	private Long id;
	private Long nid;
	private String sensor_data;
	private Date logged_at;

	public NodeSensorLogForm(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNid() {
		return nid;
	}

	public void setNid(Long nid) {
		this.nid = nid;
	}

	public String getSensor_data() {
		return sensor_data;
	}

	public void setSensor_data(String sensor_data) {
		this.sensor_data = sensor_data;
	}

	public Date getLogged_at() {
		return logged_at;
	}

	public void setLogged_at(Date logged_at) {
		this.logged_at = logged_at;
	}
	
	


}
