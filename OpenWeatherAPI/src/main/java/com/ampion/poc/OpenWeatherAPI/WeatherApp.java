package com.ampion.poc.OpenWeatherAPI;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherApp {
	 private static String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast?q=";
	  private static String units = "metric"; 
	  private static String API_KEY = "567634b2941bea97d0b52b43ec12a86d";
	  
		public static void weatherForecast(String city, float minTemp) {
			
			String endPoint = BASE_URL + city + "&appid=" + API_KEY + "&units=" + units;
			
			 JSONArray list;
			 String[] result = new String[11];
			 
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endPoint)).build();
			HttpResponse<String> response;
			try {
				response = client.send(request, BodyHandlers.ofString());
				
				System.out.println("response: "+response.body().toString());
				JSONObject obj = new JSONObject(response.body().toString());
				Boolean isSunny = false;
			     System.out.println(city.toUpperCase()+" Weather forecast report for next 5 days");
			     System.out.println("------------------------------------------------");
			      
			      for(int index=0; index<40; index++) {
				    
			    	  list = obj.getJSONArray("list");

			    	  JSONObject item = list.getJSONObject(index);
			          result[0] = item.getJSONArray("weather").getJSONObject(0).get("description").toString();
			          JSONObject main = item.getJSONObject("main");
			          result[1] = main.get("temp").toString();
			          result[2] = item.get("dt_txt").toString();
			          Float temp = Float.parseFloat(result[1].toString());
			          if(temp>minTemp) {
			        	  
			        	  isSunny=true;
			        	  String sentence = result[2] + " the weather is " + result[0] + " and temparature is " + result[1] + " degrees " ;
				          System.out.println(sentence);
			          }
			          
			        
			      }
			      
			      if(!isSunny) {
			        	System.out.println(" There is no Sunny day in next 5 days");
			        }
			      
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				


		}
	
	public static void main(String[] args) {
		
		WeatherApp.weatherForecast("Sydney",20);
	}

}


