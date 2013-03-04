package edu.uchicago.mobile.visual_test;

import android.location.Location;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import org.json.*;


public class Api_use {

	private static String GEOCODE = "http://maps.googleapis.com/maps/api/geocode/json?";
	
	public boolean need_switch(double lat, double longitude, String end_address)
	{
		//true if you need to switch, false otherwise
		return false;
	}
	
    public String[] travel_info(double lat, double longitude, String end_address)  throws IOException, JSONException {
        String ADDRESS = "address=" + end_address.replaceAll(" ", "+");

        URL url = new URL(GEOCODE + ADDRESS + "&sensor=true");
        URLConnection connection = url.openConnection();

        String line;
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while((line = reader.readLine()) != null) {
        builder.append(line);
        }

        JSONObject jObj = new JSONObject(builder.toString());
        double lt = jObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
        double ln = jObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");

        ArrayList<HashMap> directions = RouteFinder.getDirections(lat, longitude, lt, ln);
        HashMap start = (HashMap)directions.get(0);
        HashMap route = (HashMap)directions.get(1);
        HashMap end   = (HashMap)directions.get(2);
        Trip trip = new Trip(start, route, end);


        String ret[]={(String)start.get("name"), (String)route.get("long_name"),
                      (String)end.get("name"), trip.I_EST};
        //return start name, route name, end name, arrival at initial stop
        return ret;

    //String ret[]={"a", "b", "c", "d"};
    //return ret;
        }
	
	Location end_loc(String end_name)
	{
		//return the latitude and longitude
		Location location=null;
		return location;
	}
	
	public String[] find_shuttle(double lat, double longitude, String shuttle)
	{
		//return closest stop, time estimate
		String ret[]={"a", "b", "c"};
		return ret;
	}
	
	public double get_distance(Location l1, Location l2)
	{
		double lat1=l1.getLatitude();
		double long1=l1.getLongitude();
		double lat2=l1.getLatitude();
		double long2=l2.getLongitude();
		
		double R = 6371; //radius of the earth
		double dLat=(lat1-lat2); //difference in latitudes
		double dLong=(long1-long2);//difference in longitudes
		
		double a = 
				Math.sin(dLat/2)*Math.sin(dLat/2) + 
				Math.cos((Math.PI/180)*lat1) * Math.cos((Math.PI/180)*lat2) *
				Math.sin(dLong/2) * Math.sin(dLong/2);
		
		double c = 2* Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = R * c * 1000; //distance in meters
		return d;
		
	}
}
	
	
	

