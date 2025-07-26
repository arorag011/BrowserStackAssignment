package com.utilities;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiUtility {
	
			
	
	public String translateAPIResponse(String spanishText){
		HttpResponse<String> response = null ;
		try {
		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
		arr.put(spanishText);
		json.put("target_lang", "en");
		json.put("text", arr);
		HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://openl-translate.p.rapidapi.com/translate/bulk"))
                .headers("Content-Type", "application/json", "x-rapidapi-host", "openl-translate.p.rapidapi.com"
                		, "x-rapidapi-key", "847f791d59msh236cafdbe782e89p15b3f4jsna3ade0e41a4f")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();
		  HttpClient client = HttpClient.newHttpClient();
          response = client.send(request, HttpResponse.BodyHandlers.ofString());
          
		}catch(URISyntaxException u) {
			System.out.println("invalid Url");
		}catch(InterruptedException i) {
			System.out.println("something went wrong");
		}catch(IOException io) {
			System.out.println("something went wrong");
		}
          return response.body();
		
	}

}
