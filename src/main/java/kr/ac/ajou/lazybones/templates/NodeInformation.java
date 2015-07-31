package kr.ac.ajou.lazybones.templates;

import java.util.List;

public class NodeInformation {

	// Node identifier
	private String nid;

	// Serial number
	private String sn;

	// Product name
	private String pn;
	
	private List<String> sensors;
	private List<String> actuators;

//	public class Sensor {
//		// Sensor name
//		private String name;
//
//		// Sensor Description
//		private String desc;
//
//		// Sensor type
//		private String type;
//
//		private List<String> values;
//
//		public String getName() {
//			return name;
//		}
//
//		public void setName(String name) {
//			this.name = name;
//		}
//
//		public String getDesc() {
//			return desc;
//		}
//
//		public void setDesc(String desc) {
//			this.desc = desc;
//		}
//
//		public String getType() {
//			return type;
//		}
//
//		public void setType(String type) {
//			this.type = type;
//		}
//
//		public List<String> getValues() {
//			return values;
//		}
//
//		public void setValues(List<String> values) {
//			this.values = values;
//		}
//
//	}
//
//	public class Actuator {
//		// Sensor name
//		private String name;
//
//		// Sensor Description
//		private String desc;
//
//		// Sensor type
//		private String type;
//
//		private List<String> values;
//
//		public String getName() {
//			return name;
//		}
//
//		public void setName(String name) {
//			this.name = name;
//		}
//
//		public String getDesc() {
//			return desc;
//		}
//
//		public void setDesc(String desc) {
//			this.desc = desc;
//		}
//
//		public String getType() {
//			return type;
//		}
//
//		public void setType(String type) {
//			this.type = type;
//		}
//
//		public List<String> getValues() {
//			return values;
//		}
//
//		public void setValues(List<String> values) {
//			this.values = values;
//		}
//
//	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
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

//	public List<Sensor> getSensors() {
//		return sensors;
//	}
//
//	public void setSensors(List<Sensor> sensors) {
//		this.sensors = sensors;
//	}
//
//	public List<Actuator> getActuators() {
//		return actuators;
//	}
//
//	public void setActuators(List<Actuator> actuators) {
//		this.actuators = actuators;
//	}
	
	

}
