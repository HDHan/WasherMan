package kr.ac.ajou.lazybones.housekeeper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.ajou.lazybones.templates.AuthorizationForm;
import kr.ac.ajou.lazybones.templates.NodeDetailedInformation;
import kr.ac.ajou.lazybones.templates.NodeEntity;
import kr.ac.ajou.lazybones.templates.Result;

@Repository
public class HousekeeperManager {

	private final String NODE_LIST_URL = "http://cmu-teame-middleware.elasticbeanstalk.com/Rest/Node/List";
	private final String NODE_DETAIL_URL = "http://cmu-teame-middleware.elasticbeanstalk.com/Rest/Node/%.36s/Info";

	Map<String, Housekeeper> housekeepers = new HashMap<>();

	public boolean subscribeHouseKeeper(String credential, String nid) {
		Housekeeper housekeeper = new Housekeeper(credential, nid);
		housekeepers.put(nid, housekeeper);
		housekeeper.start();
		return true;
	}

	public boolean unsubscribeHouseKeeper(String nid) {
		Housekeeper housekeeper = housekeepers.get(nid);
		housekeeper.interrupt();
		housekeepers.remove(nid);
		return true;
	}
	
	public List<String> getRecipients(String nid){
		return housekeepers.get(nid).getRecipients();
	}
	
	public boolean addRecipient(String nid, String recipient){
		return housekeepers.get(nid).addRecipient(recipient);
	}
	
	public boolean removeRecipient(String nid, int index){
		return housekeepers.get(nid).removeRecipient(index);
	}	
	
	public boolean contains(String nid){
		return housekeepers.containsKey(nid);
	}
	
	public void setLightDuration(String nid, int dur){
		this.housekeepers.get(nid).setLightOffDuration(dur);
	}
	public void setWarningDuration(String nid, int dur){
		this.housekeepers.get(nid).setWarningDuration(dur);
	}
	
	public int getLightDuration(String nid){
		return this.housekeepers.get(nid).getLightOffDuration();
	}
	public int getWarningDuration(String nid){
		return this.housekeepers.get(nid).getWarningDuration();
	}

	public List<NodeEntity> getNodes(String credential) {

		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		AuthorizationForm form = new AuthorizationForm();
		form.setCredential(credential);

		HttpEntity<String> entity;
		try {
			System.out.println(mapper.writeValueAsString(form).toString());
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			entity = new HttpEntity<String>(mapper.writeValueAsString(form).toString(), headers);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		Result result = restTemplate.postForObject(NODE_LIST_URL, entity, Result.class);
		if (result.getResult().equalsIgnoreCase("succeed")) {

			try {
				return mapper.readValue(result.getData(), new TypeReference<List<NodeEntity>>() {
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;

	}

	public NodeDetailedInformation getNodeDetail(String credential, String nodeId) {

		// if(nodeId.contains("/"))
		// return null;

		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		AuthorizationForm form = new AuthorizationForm();
		form.setCredential(credential);

		HttpEntity<String> entity;
		try {
			System.out.println(mapper.writeValueAsString(form).toString());
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			entity = new HttpEntity<String>(mapper.writeValueAsString(form).toString(), headers);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		String url = String.format(NODE_DETAIL_URL, nodeId);

		Result result = restTemplate.postForObject(url, entity, Result.class);
		if (result.getResult().equalsIgnoreCase("succeed")) {
			try {
				return mapper.readValue(result.getData(), new TypeReference<NodeDetailedInformation>() {
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;

	}

}
