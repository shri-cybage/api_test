package com.demo.api.test;


import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class APITest {
  
	@Test
    public void test1() {
		
		int id = 0;
		String json = null;
		String address = null;
	    
		HttpClient client = HttpClientBuilder.create().build();
	    HttpGet request = new HttpGet("http://api.data.gov/nrel/alt-fuel-stations/v1/nearest.json?api_key=llXCuNu8ccJSZgIcW5sGlsk7d2DY5cz7GPXP19Hw&location=Austin");
	    HttpResponse response = null;
	    request.addHeader("accept", "application/json");
	    
	    try {
			response = client.execute(request);
			json = IOUtils.toString(response.getEntity().getContent());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	     
	    //System.out.println(json);
	     
	    JSONObject obj1 = new JSONObject(json);
	     
	    JSONArray array = obj1.getJSONArray("fuel_stations");
	    
	    for (Object object : array) {
	    	 JSONObject obj = (JSONObject) object;
	         //log.info("the location is {}", object.getString("location"));
	    	 if(obj.getString("station_name").equalsIgnoreCase("HYATT AUSTIN")) {
	    		 
	    		 id = obj.getInt("id");
	    		 System.out.println("ID : " + id);	    		 
	    		 break;
	    	 }
	    }
	     	     
		request = new HttpGet("http://api.data.gov/nrel/alt-fuel-stations/v1/"+ id +".json?api_key=llXCuNu8ccJSZgIcW5sGlsk7d2DY5cz7GPXP19Hw&location=Austin");
		request.addHeader("accept", "application/json");
		 
		try {
				response = client.execute(request);
				json = IOUtils.toString(response.getEntity().getContent());
		} catch (Exception e) {
				e.printStackTrace();
		} 
		     
		//System.out.println(json);
		     
		obj1 = new JSONObject(json);
		
		address = obj1.getJSONObject("alt_fuel_station").getString("street_address");
		System.out.println("Street Adress : " + address);
		
		Assert.assertEquals(address, "208 Barton Springs Rd");
		
    }
}
