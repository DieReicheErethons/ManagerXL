package com.dre.managerxl.util;

import org.apache.commons.lang.math.NumberUtils;

import com.dre.managerxl.P;

public class MUtility {
	public static String parseMessage(String[] args, int start){
		String message = "";
		
		for (int i = start; i < args.length; i++) {
			message += args[i] + " ";
		}
		
		message = message.trim();
		
		return message;
	}
	
	public static int parseInt(String string){
		return NumberUtils.toInt(string, 0);
	}
	
	public static int getStringTimeToInt(String time){
		int result = 0;
		int index = 0;
		int lastIndex = 0;
		
		// Days
		index = time.indexOf("d");
		if(index > lastIndex){
			result += parseInt(time.substring(lastIndex, index)) * 24 * 60 * 60;
		}
		
		//Hours
		lastIndex = index;
		index = time.indexOf("h");
		if(index > lastIndex){
			result += parseInt(time.substring(lastIndex + 1, index)) * 60 * 60;
		}
		
		//Minutes
		lastIndex = index;
		index = time.indexOf("m");
		if(index > lastIndex){
			result += parseInt(time.substring(lastIndex + 1, index)) * 60;
		}
		
		//Seconds
		lastIndex = index;
		index = time.indexOf("s");
		if(index > lastIndex){
			result += parseInt(time.substring(lastIndex + 1, index));
		}
		
		return result * 1000; // Seconds to millisecs
	}
	
	public static String getIntTimeToString(long l){
		String result = "";
		
		l = l / 1000; // Millisecs to seconds
		
		//Days
		int dayTime = 24 * 60 * 60;
		if(l >= dayTime){
			result += ((int)(l / dayTime)) + " " + P.p.getLanguageReader().get("Format_Days") + " ";
			l = l % dayTime;
		}
		
		//Hours
		int hourTime = 60 * 60;
		if(l >= hourTime){
			result += ((int)(l / hourTime)) + " " + P.p.getLanguageReader().get("Format_Hours") + " ";
			l = l % hourTime;
		}
		
		//Minute
		int minuteTime = 60;
		if(l >= minuteTime){
			result += ((int)(l / minuteTime)) + " " + P.p.getLanguageReader().get("Format_Minutes") + " ";
			l = l % minuteTime;
		}
		
		//Seconds
		if(l >= 1){
			result += l + " " + P.p.getLanguageReader().get("Format_Seconds") + " ";
			l = l % minuteTime;
		}
		
		return result.trim();
	}
}