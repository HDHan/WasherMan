package kr.ac.ajou.lazybones.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.ac.ajou.lazybones.templates.AlarmReplyForm;
import kr.ac.ajou.lazybones.templates.QueryForm;
import kr.ac.ajou.lazybones.templates.Result;

@Controller
public class MessageController {

	private static final String QUERY_URI = "http://cmu-teame-middleware.elasticbeanstalk.com/Rest/Node/%.36s/Query";
	
	@RequestMapping(value = "/Msg/Node/{id}/Alarm/{onOff}/{credential}", method = RequestMethod.GET)
	public String makeAlarmDecision(@PathVariable("id") String nid, @PathVariable("onOff") String onOff,
			@PathVariable("credential") String credential) {

		AlarmReplyForm form = new AlarmReplyForm();
		form.setCommand("warnReply");
		
		if(onOff.equals("On")){
			form.setReply("alarmOn");
		}else if(onOff.equals("Off")){
			form.setReply("maintain");
		}else
			return "redirect:/Node/List";
		
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		QueryForm queryForm = new QueryForm();
		queryForm.setCredential(credential);
		try {
			queryForm.setCommand(new ObjectMapper().writeValueAsString(form));
		} catch (JsonProcessingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			return "redirect:/Node/List";	
		}

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

		String uri = String.format(QUERY_URI, nid);

		Result queryOutput = restTemplate.postForObject(uri, entity, Result.class);
		return "redirect:/Node/List";
		
	}

}
