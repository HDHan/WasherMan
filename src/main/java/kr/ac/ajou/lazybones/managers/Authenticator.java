package kr.ac.ajou.lazybones.managers;

import kr.ac.ajou.lazybones.templates.GetCredentialForm;
import kr.ac.ajou.lazybones.templates.GetCredentialResult;
import kr.ac.ajou.lazybones.templates.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class Authenticator {

	private String queryUserCommand = "http://cmu-teame-middleware.elasticbeanstalk.com/Rest/User/Credential";

	public String getCredential(String id, String password) {

		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		GetCredentialForm form = new GetCredentialForm();

		form.setId(id);
		form.setPassword(password);

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

		GetCredentialResult result = restTemplate.postForObject(queryUserCommand, entity, GetCredentialResult.class);
		if (result.getResult().equalsIgnoreCase("succeed"))
			return result.getCredential();
		else
			return null;

	}

}