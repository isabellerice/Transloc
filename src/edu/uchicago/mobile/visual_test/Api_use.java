package edu.uchicago.mobile.visual_test;

import android.location.Location;

public class Api_use {

	public boolean need_switch(double lat, double longitude, String end_address)
	{
		//true if you need to switch, false otherwise
		return false;
	}
	public String[] travel_info(double lat, double longitude, String end_address)
	{
		String ret[]={"a", "b", "c"};
		//return start name, route name, end name
		return ret;
	}
	
	Location end_loc(String end_name)
	{
		//return the latitude and longitude
		Location location=null;
		return location;
	}
	
	public int arrival_time(String route, String stop)
	{
		//return time till arrival
		return 0;
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
	
	
	

