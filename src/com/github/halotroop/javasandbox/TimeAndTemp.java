package com.github.halotroop.javasandbox;

import org.joml.Math;

public class TimeAndTemp
{
	public static final double maxTemp = 100;
	public static final double minTemp = -50;
	public static int dayOfYear, hourOfDay;
	public static int hours; // Hours past 0:00 on day 0
	public static double elevation;
	
	// Player should be at sea level, in a plains biome, in the middle of month 7 (day 195), at midnight (0:00)
	// Sea level is 0
	// Plains should be located at sea level, and have a very normal temperature value.
	// A day is 16 hours long, a night is 8 hours long
	// A year is 240 days long
	
	public static void main(String[] args)
	{
		elevation = 0;
		setTimeInDays(195);
		hourOfDay = getHourOfDay();
		dayOfYear = getDayOfYear();
		reportTimeAndTemp();
	}
	
	private static void reportTimeAndTemp()
	{
		double pt = getPlayerTemp();
		System.out.println("The day of the year is: " + String.valueOf(dayOfYear));
		System.out.println("The time of day is: " + String.valueOf(hourOfDay) + ":00");
		System.out.println("Your player is " + String.valueOf(pt) + " degrees Celcius.");
		if (pt >= 50 || pt <= 0)
		{
			System.out.println("They're probably dead!");
			
			if (pt >= 100)		System.out.println("They're on boiling!");
			else if (pt <= 0)	System.out.println("They're freezing!");
		}
		else
		{
			System.out.println("They should be fine.");
			System.out.println("The temperature is OK.");
		}
	}
	
	public static void setTime(int days, int newHours)
	{	hours = ((days - 1) * 24) + newHours;	}
	
	public static void setTimeInDays(int days)
	{	hours = (days- 1) * 24;	}
	
	public static void setTimeInHours(int newHours)
	{	hours = newHours;	}
	
	private static int getHourOfDay()
	{	return hours % 24;	}

	public static int getDayOfYear()
	{	return ((hours / 24) % 240) + 1;	}
	
	public static double getPlayerTemp()
	{
		// Correct the time of day (in hours)
		if (hourOfDay >= 24)
			hourOfDay = hourOfDay % 24;
		else if (hourOfDay < 0)
			hourOfDay = 0;
		
		double fin = 0;
		
		fin = (int) (calcDayTemp() * calcGlobalTemp() + calcElevationTemp(1, 1, 1, 1));
		
		if (fin > maxTemp) fin = maxTemp;
		if (fin < minTemp) fin = minTemp;
		
		return fin;
	}
	
	public static double calcElevationTemp(double x, double e, double d, double l)
	{
		return (double) ( (-x) * (java.lang.Math.pow((-x-l), 3)) * ((-x) / (java.lang.Math.pow(e, d)) ));
	}
	
	public static double calcDayTemp()
	{
		double fineAdjustment = 10;
		double g = 3;
		
		double day = ((-hourOfDay*(hourOfDay-16))*(Math.abs(g)+1))/fineAdjustment;
		double night = ((-hourOfDay)*(hourOfDay-180))/fineAdjustment;
		
		boolean isDay = (hourOfDay <= 4) && (hourOfDay >= 20);
		
		if (isDay) return day;
		else return night;
	}
	
	public static double calcGlobalTemp()
	{
		double fineAdjustment = 1000;
		
		return (int) (((-dayOfYear)*(dayOfYear-180))/fineAdjustment);
	}
}
