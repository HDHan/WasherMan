package kr.ac.ajou.lazybones.templates;

public class UserHomeEntity {
	
	private String doorSwitch;
	private String doorServo;
	private String alarm;
	private String light;
	private String temperature;
	private String humidity;
	private String occupied;
	private String securityStatus;
	
	public String getSecurityStatus() {
		return securityStatus;
	}
	public void setSecurityStatus(String securityStatus) {
		this.securityStatus = securityStatus;
	}
	
	public String getDoorSwitch() {
		return doorSwitch;
	}
	public void setDoorSwitch(String doorSwitch) {
		this.doorSwitch = doorSwitch;
	}
	public String getDoorServo() {
		return doorServo;
	}
	public void setDoorServo(String doorServo) {
		this.doorServo = doorServo;
	}
	public String getAlarm() {
		return alarm;
	}
	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}
	public String getLight() {
		return light;
	}
	public void setLight(String light) {
		this.light = light;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getOccupied() {
		return occupied;
	}
	public void setOccupied(String occupied) {
		this.occupied = occupied;
	}
	

	
}