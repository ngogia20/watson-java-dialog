package com.ibm.cloudoe.utils;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GetResponseData {
	
	
	public static String parse(String file_string) throws IOException, ParseException {

		
		System.out.println("In Parse");
		
		JSONParser jp = new JSONParser();
		
		JSONObject job = (JSONObject) jp.parse(file_string.toString());
		
		JSONArray js = (JSONArray) job.get("response");
		
		GetParam pm = new GetParam();
		
		pm.setConversation_id((String) job.get("conversation_id"));
		
		pm.setClient_id((String) job.get("client_id"));
		
		String chaval = (String) js.get(0);
		
		System.out.println(chaval);
		
		return chaval;

	}

}
