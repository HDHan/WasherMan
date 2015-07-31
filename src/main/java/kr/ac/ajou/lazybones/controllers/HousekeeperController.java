package kr.ac.ajou.lazybones.controllers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.ac.ajou.lazybones.housekeeper.HousekeeperManager;
import kr.ac.ajou.lazybones.templates.CommandWithValue;
import kr.ac.ajou.lazybones.templates.NodeDetailedInformation;
import kr.ac.ajou.lazybones.templates.NodeEntity;
import kr.ac.ajou.lazybones.templates.QueryForm;
import kr.ac.ajou.lazybones.templates.Result;
import kr.ac.ajou.lazybones.templates.ReadForm;

@Controller
public class HousekeeperController {

	private static final String QUERY_URI = "http://cmu-teame-middleware.elasticbeanstalk.com/Rest/Node/%.36s/Query";
	private static final String SENSOR_LOG_URI = "http://cmu-teame-middleware.elasticbeanstalk.com/Rest/Log/Node/%.36s/Sensor";

	@Autowired
	HousekeeperManager housekeeperManager;

	@RequestMapping(value = "/Node/List", method = RequestMethod.GET)
	public String nodeList(Model model, HttpServletRequest request) {
		String credential = (String) request.getSession().getAttribute("credential");

		List<NodeEntity> nodes = housekeeperManager.getNodes(credential);

		model.addAttribute("nodes", nodes);

		return "nodeList";
	}

	@RequestMapping(value = "/Node/{id}/Detail", method = RequestMethod.GET)
	public String nodeDetail(@PathVariable("id") String nodeId, Model model, HttpServletRequest request) {
		String credential = (String) request.getSession().getAttribute("credential");

		NodeDetailedInformation node = housekeeperManager.getNodeDetail(credential, nodeId);

		model.addAttribute("node", node);

		if (node.getSensors() != null && node.getActuators() != null) {
			String[] sensors = { "dsw", "hum", "tmp", "occ" };
			String[] actuators = { "alr", "lgt", "dsv" };

			if (node.getSensors().containsAll(Arrays.asList(sensors))
					&& node.getActuators().containsAll(Arrays.asList(actuators))) {
				model.addAttribute("isThatHouse", "YES");
				if (housekeeperManager.contains(node.getNid())) {
					model.addAttribute("subscription", true);
					model.addAttribute("recipients", housekeeperManager.getRecipients(node.getNid()));
					model.addAttribute("lightOffDuration", housekeeperManager.getLightDuration(node.getNid()));
					model.addAttribute("warningDuration", housekeeperManager.getWarningDuration(node.getNid()));
				} else
					model.addAttribute("subscription", false);
			}
		} else {
			model.addAttribute("isThatHouse", "NOPE");

		}

		return "nodeDetail";
	}

	@RequestMapping(value = "/Node/{id}/Subscribe", method = RequestMethod.GET)
	public String subscribe(HttpServletRequest request, @PathVariable("id") String nid) {
		housekeeperManager.subscribeHouseKeeper((String) request.getSession().getAttribute("credential"), nid);

		return "redirect:/Node/" + nid + "/Detail";
	}

	@RequestMapping(value = "/Node/{id}/Unsubscribe", method = RequestMethod.GET)
	public String unsubscribe(HttpServletRequest request, @PathVariable("id") String nid) {
		housekeeperManager.unsubscribeHouseKeeper(nid);

		return "redirect:/Node/" + nid + "/Detail";
	}

	@RequestMapping(value = "/Node/{id}/Add/Recipient", method = RequestMethod.POST)
	public String addRecipient(HttpServletRequest request, @PathVariable("id") String nid,
			@RequestParam("recipientname") String recipient) {
		housekeeperManager.addRecipient(nid, recipient);
		return "redirect:/Node/" + nid + "/Detail";
	}

	@RequestMapping(value = "/Node/{id}/Remove/Recipient/{index}", method = RequestMethod.GET)
	public String removeRecipient(HttpServletRequest request, @PathVariable("id") String nid,
			@PathVariable("index") int index) {
		housekeeperManager.removeRecipient(nid, index);
		return "redirect:/Node/" + nid + "/Detail";
	}

	@RequestMapping(value = "/Node/{id}/Light/Duration", method = RequestMethod.POST)
	public String setLightDuration(@PathVariable("id") String nodeId, HttpServletRequest request,
			@RequestParam("lightDuration") int duration) throws JsonProcessingException {

		CommandWithValue comm = new CommandWithValue();
		comm.setCommand("LightDuration");
		comm.setValue(duration);

		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		QueryForm queryForm = new QueryForm();
		queryForm.setCredential((String) request.getSession().getAttribute("credential"));
		queryForm.setCommand(mapper.writeValueAsString(comm));

		HttpEntity<String> entity;
		try {
			System.out.println(mapper.writeValueAsString(queryForm).toString());
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			entity = new HttpEntity<String>(mapper.writeValueAsString(queryForm).toString(), headers);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "redirect:/Node/" + nodeId + "/Detail";
		}

		String uri = String.format(QUERY_URI, nodeId);

		Result queryOutput = restTemplate.postForObject(uri, entity, Result.class);
		if(queryOutput.getResult().equalsIgnoreCase("succeed"))
			housekeeperManager.setLightDuration(nodeId, duration);

		return "redirect:/Node/" + nodeId + "/Detail";

	}
	
	@RequestMapping(value = "/Node/{id}/Warning/Duration", method = RequestMethod.POST)
	public String setWarningDuration(@PathVariable("id") String nodeId, HttpServletRequest request,
			@RequestParam("warningDuration") int duration) throws JsonProcessingException {

		CommandWithValue comm = new CommandWithValue();
		comm.setCommand("LightDuration");
		comm.setValue(duration);

		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		QueryForm queryForm = new QueryForm();
		queryForm.setCredential((String) request.getSession().getAttribute("credential"));
		queryForm.setCommand(mapper.writeValueAsString(comm));

		HttpEntity<String> entity;
		try {
			System.out.println(mapper.writeValueAsString(queryForm).toString());
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "redirect:/Node/" + nodeId + "/Detail";
			
		}
		try {
			entity = new HttpEntity<String>(mapper.writeValueAsString(queryForm).toString(), headers);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "redirect:/Node/" + nodeId + "/Detail";
		}

		String uri = String.format(QUERY_URI, nodeId);

		Result queryOutput = restTemplate.postForObject(uri, entity, Result.class);
		if(queryOutput.getResult().equalsIgnoreCase("succeed"))
			housekeeperManager.setWarningDuration(nodeId, duration);

		return "redirect:/Node/" + nodeId + "/Detail";

	}

	/*
	 * private String queryUserCommand =
	 * "http://cmu-teame-middleware.elasticbeanstalk.com/Node/${nodeID}/Query";
	 * private String readSensorLog =
	 * "http://cmu-teame-middleware.elasticbeanstalk.com/Log/Node/${nodeID}/Sensor";
	 */

	/*
	 * private String queryUserCommand =
	 * "http://192.168.1.143:8080/Middleware/Node/2b31ab72-d643-49fb-9991-059845a60e79/Query";
	 * private String readSensorLog =
	 * "http://192.168.1.143:8080/Middleware/Log/Node/2b31ab72-d643-49fb-9991-059845a60e79/Sensor";
	 */

	private final static String alarmOn = "{\"command\":\"control\",\"alarm\":\"on\"}";
	private final static String alarmOff = "{\"command\":\"control\",\"alarm\":\"off\"}";
	private final static String doorOpen = "{\"command\":\"control\",\"doorServo\":\"open\"}";
	private final static String doorClose = "{\"command\":\"control\",\"doorServo\":\"close\"}";
	private final static String lightOn = "{\"command\":\"control\",\"light\":\"on\"}";
	private final static String lightOff = "{\"command\":\"control\",\"light\":\"off\"}";

	@RequestMapping(value = "/Node/{id}/homeState")
	public @ResponseBody Result HomeState(Model model, @PathVariable("id") String nodeId, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ReadForm readForm = new ReadForm();
		readForm.setCredential((String) request.getSession().getAttribute("credential"));
		readForm.setLimit(1);

		HttpEntity<String> entity;
		try {
			System.out.println(mapper.writeValueAsString(readForm).toString());
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			entity = new HttpEntity<String>(mapper.writeValueAsString(readForm).toString(), headers);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		String uri = String.format(SENSOR_LOG_URI, nodeId);

		Result result = restTemplate.postForObject(uri, entity, Result.class);

		return result;
	}

	@RequestMapping(value = "/Node/{id}/Alarm/On", method = RequestMethod.GET)
	public @ResponseBody Result AlarmOn(@PathVariable("id") String nodeId, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		QueryForm queryForm = new QueryForm();
		queryForm.setCredential((String) request.getSession().getAttribute("credential"));
		queryForm.setCommand(alarmOn);

		HttpEntity<String> entity;
		try {
			System.out.println(mapper.writeValueAsString(queryForm).toString());
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			entity = new HttpEntity<String>(mapper.writeValueAsString(queryForm).toString(), headers);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		String uri = String.format(QUERY_URI, nodeId);

		Result queryOutput = restTemplate.postForObject(uri, entity, Result.class);
		return queryOutput;
	}

	@RequestMapping(value = "/Node/{id}/Alarm/Off", method = RequestMethod.GET)
	public @ResponseBody Result AlarmOff(@PathVariable("id") String nodeId, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		QueryForm queryForm = new QueryForm();
		queryForm.setCredential((String) request.getSession().getAttribute("credential"));
		queryForm.setCommand(alarmOff);

		HttpEntity<String> entity;
		try {
			System.out.println(mapper.writeValueAsString(queryForm).toString());
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			entity = new HttpEntity<String>(mapper.writeValueAsString(queryForm).toString(), headers);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		String uri = String.format(QUERY_URI, nodeId);

		Result queryOutput = restTemplate.postForObject(uri, entity, Result.class);

		return queryOutput;
	}

	@RequestMapping(value = "/Node/{id}/Door/Open", method = RequestMethod.GET)
	@ResponseBody
	public Result DoorOpen(@PathVariable("id") String nodeId, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		QueryForm queryForm = new QueryForm();
		queryForm.setCredential((String) request.getSession().getAttribute("credential"));
		queryForm.setCommand(doorOpen);

		HttpEntity<String> entity;
		try {
			System.out.println(mapper.writeValueAsString(queryForm).toString());
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			entity = new HttpEntity<String>(mapper.writeValueAsString(queryForm).toString(), headers);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		String uri = String.format(QUERY_URI, nodeId);

		Result queryOutput = restTemplate.postForObject(uri, entity, Result.class);

		return queryOutput;
	}

	@RequestMapping(value = "/Node/{id}/Door/Close", method = RequestMethod.GET)
	@ResponseBody
	public Result DoorClose(@PathVariable("id") String nodeId, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		QueryForm queryForm = new QueryForm();
		queryForm.setCredential((String) request.getSession().getAttribute("credential"));
		queryForm.setCommand(doorClose);

		HttpEntity<String> entity;
		try {
			System.out.println(mapper.writeValueAsString(queryForm).toString());
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			entity = new HttpEntity<String>(mapper.writeValueAsString(queryForm).toString(), headers);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		String uri = String.format(QUERY_URI, nodeId);

		Result queryOutput = restTemplate.postForObject(uri, entity, Result.class);
		return queryOutput;
	}

	@RequestMapping(value = "/Node/{id}/Light/On")
	@ResponseBody
	public Result LightOn(@PathVariable("id") String nodeId, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		QueryForm queryForm = new QueryForm();
		queryForm.setCredential((String) request.getSession().getAttribute("credential"));
		queryForm.setCommand(lightOn);

		HttpEntity<String> entity;
		try {
			System.out.println(mapper.writeValueAsString(queryForm).toString());
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			entity = new HttpEntity<String>(mapper.writeValueAsString(queryForm).toString(), headers);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		String uri = String.format(QUERY_URI, nodeId);

		Result queryOutput = restTemplate.postForObject(uri, entity, Result.class);
		return queryOutput;
	}

	@RequestMapping(value = "/Node/{id}/Light/Off")
	@ResponseBody
	public Result LightOff(@PathVariable("id") String nodeId, HttpServletRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		QueryForm queryForm = new QueryForm();
		queryForm.setCredential((String) request.getSession().getAttribute("credential"));
		queryForm.setCommand(lightOff);

		HttpEntity<String> entity;
		try {
			System.out.println(mapper.writeValueAsString(queryForm).toString());
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			entity = new HttpEntity<String>(mapper.writeValueAsString(queryForm).toString(), headers);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		String uri = String.format(QUERY_URI, nodeId);

		Result queryOutput = restTemplate.postForObject(uri, entity, Result.class);
		return queryOutput;
	}

}
