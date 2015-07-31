package kr.ac.ajou.lazybones.templates;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NodeDetailedInformation {
	public NodeDetailedInformation() {

	}

	private String nid;
	private String serial;
	private String owner;
	private String pn;
	private String name;

	private List<String> sensors;
	private List<String> actuators;

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getSensors() {
		return sensors;
	}

	public void setSensors(List<String> sensors) {
		this.sensors = sensors;
	}

	public List<String> getActuators() {
		return actuators;
	}

	public void setActuators(List<String> actuators) {
		this.actuators = actuators;
	}

}
