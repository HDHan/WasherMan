package kr.ac.ajou.lazybones.housekeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.ac.ajou.lazybones.templates.HouseStatus;
import kr.ac.ajou.lazybones.templates.ReadForm;
import kr.ac.ajou.lazybones.templates.Result;
import kr.ac.ajou.lazybones.util.TextBelt;

public class Housekeeper extends Thread {

	private static final String ALARM_REPLY_URI = "http://cmu-teame-housekeeper.elasticbeanstalk.com/Msg/Node/%.36s/Alarm/%.3s/%.36s";
	private static final String SENSOR_LOG_URI = "http://cmu-teame-middleware.elasticbeanstalk.com/Rest/Log/Node/%.36s/Sensor";

	private List<String> recipients = new ArrayList<>();

	private int lightOffDuration = 5;
	private int warningDuration = 5;

	public Housekeeper(String credential, String nodeId) {
		this.nodeId = nodeId;
		this.credential = credential;
	}

	public Integer getLightOffDuration() {
		return lightOffDuration;
	}

	public void setLightOffDuration(Integer lightOffDuration) {
		this.lightOffDuration = lightOffDuration;
	}

	public Integer getWarningDuration() {
		return warningDuration;
	}

	public void setWarningDuration(Integer warningDuration) {
		this.warningDuration = warningDuration;
	}

	public boolean addRecipient(String recipient) {
		if (!recipients.contains(recipient)) {
			recipients.add(recipient);
			return true;
		} else
			return false;
	}

	public boolean removeRecipient(int index) {
		try {
			recipients.remove(index);
		} catch (Exception e) {
			//
			return false;
		}
		return true;
	}

	public List<String> getRecipients() {
		return this.recipients;
	}

	public class SaValuesFormat {
		private String status;
		private String dat;

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getDat() {
			return dat;
		}

		public void setDat(String dat) {
			this.dat = dat;
		}

		public SaValuesFormat() {
			super();
		}

	}

	public class DataFormat {
		private String time;
		private String savalues;

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getSavalues() {
			return savalues;
		}

		public void setSavalues(String savalues) {
			this.savalues = savalues;
		}

		public DataFormat() {
			super();
		}

	}

	private String credential;
	private String nodeId;
	private String status = "safe";
	private HouseStatus houseStatus;

	public String getStatus() {
		return status;
	}

	public HouseStatus getHouseStatus() {
		return houseStatus;
	}

	@Override
	public void run() {

		while (!this.isInterrupted()) {

			RestTemplate restTemplate = new RestTemplate();
			ObjectMapper mapper = new ObjectMapper();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			ReadForm readForm = new ReadForm();
			readForm.setCredential(credential);
			readForm.setLimit(1);

			HttpEntity<String> entity;
			try {
				System.out.println(mapper.writeValueAsString(readForm).toString());
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				continue;
			}
			try {
				entity = new HttpEntity<String>(mapper.writeValueAsString(readForm).toString(), headers);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}

			String uri = String.format(SENSOR_LOG_URI, nodeId);

			Result result = restTemplate.postForObject(uri, entity, Result.class);
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result.getData());

			if (element.isJsonArray()) {
				JsonArray list = element.getAsJsonArray();
				JsonObject data = list.get(0).getAsJsonObject();
				JsonElement saValues = data.get("savalues");
				JsonObject parsedSaValues = parser.parse(saValues.getAsString()).getAsJsonObject();

				String status = parsedSaValues.get("status").getAsString();
				JsonObject dat = parsedSaValues.get("dat").getAsJsonObject();

				HouseStatus houseStatus = new HouseStatus();
				houseStatus.setDsw(dat.get("dsw").getAsCharacter());
				houseStatus.setDsv(dat.get("dsv").getAsCharacter());
				houseStatus.setAlr(dat.get("alr").getAsCharacter());
				houseStatus.setHum(dat.get("hum").getAsDouble());
				houseStatus.setLgt(dat.get("lgt").getAsCharacter());
				houseStatus.setOcc(dat.get("occ").getAsCharacter());
				houseStatus.setTmp(dat.get("tmp").getAsDouble());

				String temp = this.status;
				this.status = status;
				this.houseStatus = houseStatus;

				if (!temp.equals(status)) {
					if (status.equals("emer")) {
						// Send emergency messages
						for (String recipient : this.recipients) {
							try {
								TextBelt.sendSMS(recipient, "Your house is under attack!");
							} catch (UnsupportedOperationException | IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					} else if (status.equals("warn")) {
						// Send alarming message
						String replyOKUri = String.format(ALARM_REPLY_URI, nodeId, "On", credential);
						String replyNoThanksUri = String.format(ALARM_REPLY_URI, nodeId, "Off", credential);

						for (String recipient : this.recipients) {
							try {
								TextBelt.sendSMS(recipient,
										"Alarm is not turned on yet, if you want to turn it on, please visit "
												+ replyOKUri + " / If you do not want to turn it on, please visit "
												+ replyNoThanksUri);
							} catch (UnsupportedOperationException | IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}

				try {
					System.out.println(new ObjectMapper().writeValueAsString(houseStatus));
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
