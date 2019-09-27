package com.github.halotroop.javasandbox;

import org.joml.Math;

public class TimeAndTemp
{
	public static int maxTemp = 100;
	public static int minTemp = -50;
	public static int dayOfYear, hourOfDay;
	public static int hours; // Hours past 0:00 on day 0
	
	// Player should be at sea level, in a plains biome, in the middle of month 7 (day 195), at midnight (hour 0)
	// Sea level is 150
	// Plains should be located at sea level, and have a very normal temperature value.
	// A day is 16 hours long, a night is 8 hours long
	// A year is 240 days long
	
	public static void main(String[] args)
	{
		setTime(5000);
		hourOfDay = getHourOfDay();
		dayOfYear = getDayOfYear();
		reportTemp();
	}
	
	private static void reportTemp()
	{
		int pt = getPlayerTemp(150);
		System.out.println("The day of the year is: " + String.valueOf(dayOfYear));
		System.out.println("The time of day is: " + String.valueOf(hourOfDay) + ":00");
		System.out.println("Your player is " + String.valueOf(pt) + " degrees Celcius.");
		if (pt >= 100 || pt <= 0) System.out.println("They're probably dead!");
		else System.out.println("They should be fine.");
		if (pt > 100) System.out.println("They're on boiling!");
		else if (pt <= 0) System.out.println("They're freezing!");
		else System.out.println("The temperature is fine.");
	}
	
	public static void setTime(int newHours)
	{
		hours = newHours;
	}
	
	private static int getHourOfDay()
	{	
		return hours % 24;
	}

	public static int getDayOfYear()
	{		
		return ((hours / 24) % 240) + 1;
	}
	
	public static int getPlayerTemp(int elevation)
	{
		// Correct the time of day (in hours)
		if (hourOfDay >= 24)
			hourOfDay = hourOfDay % 24;
		else if (hourOfDay < 0)
			hourOfDay = 0;
		
		// Correct the time of year (in days)
		if (dayOfYear >= 240)
			dayOfYear = dayOfYear % 240;
		else if (dayOfYear < 0)
			dayOfYear = 240 - Math.abs(dayOfYear);
		if (dayOfYear == 0) dayOfYear++;
		
		int fin = 0;
		
		if (fin > maxTemp) fin = maxTemp;
		if (fin < minTemp) fin = minTemp;
		
		return fin;
	}
	
	public static float calcDayTemp(int x, int hourOfDay, float f)
	{
		float g = 3;
		float day = 0;
		float night = 0;
		boolean isDay = (hourOfDay <= 4) && (hourOfDay >= 20);
		
		
		day = ((-x*(x-16))*(Math.abs(g)+1))/f;
		
		night = ((-x)*(x-180))/f;
		
		if (isDay)
			return day;
		else
			return night;
	}
	
	public static float calcGlobalTemp(float f, int dayOfYear)
	{
		int s = 0;
		
		s = (int) (((-dayOfYear)*(dayOfYear-180))/f);
		
		return s;
	}
}
