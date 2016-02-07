package com.ibm.cloudoe.samples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ibm.cloudoe.utils.GetParam;
import com.ibm.cloudoe.utils.GetResponseData;

public class shoot {

	
	private static String baseURL = "https://gateway.watsonplatform.net/dialog/api";
	private static String username = "3f6f1a12-c6d8-4eed-8391-26813f9383bd";
	private static String password = "g9IiXAlWtXeU";
	private static String dialogId = "bf58fd1a-96a2-4ee9-934e-e2158d2dbb5c";

	private static final long serialVersionUID = 1L;
	private static final String CONVERSATION = "/conversation";
	private static final String PROFILE = "/profile";

	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		
		
		callWatson("delivery");

	}
	
	public static void callWatson(String input) throws IOException, ParseException {
		
		

		Request request = null;
		try {
			request = conversation(input,"602592","606709");
	
			Executor executor = Executor.newInstance().auth(username, password);
			Response response = executor.execute(request);

			HttpResponse httpResponse = response.returnResponse();
			
			
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			httpResponse.getEntity().writeTo(outstream);

			
			byte [] responseBody = outstream.toByteArray();
			String file_string="";
			for(int i = 0; i < responseBody.length; i++)
		    {
		        file_string += (char)responseBody[i];
		    }
			
			
			JSONParser jp = new JSONParser();
			
			JSONObject job = (JSONObject) jp.parse(file_string.toString());
			
			JSONArray js = (JSONArray) job.get("response");
			
			System.out.println(file_string);
			
			System.out.println((String) js.get(0));
			System.out.println((String) js.get(1));
			System.out.println((String) js.get(2));

			
			

		} catch (Exception e) {
		}

	}
	
	private static Request conversation(String chatinput,String coid,String cid) throws URISyntaxException {
		// create the request
		
		String input = chatinput;
		String conversationId = coid;
		String clientId = cid;
		URI converseURI = new URI(baseURL + "/v1/dialogs/" + dialogId+ "/conversation").normalize();
		return Request.Post(converseURI).bodyForm(
			Form.form()
				.add("input", input)
				.add("client_id", clientId)
				.add("conversation_id", conversationId)
				.build());
	}


}
