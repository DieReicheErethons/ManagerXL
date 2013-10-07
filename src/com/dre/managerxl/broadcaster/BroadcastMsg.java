package com.dre.managerxl.broadcaster;

import java.util.HashMap;

import org.apache.commons.lang.time.DateFormatUtils;

import com.dre.managerxl.Broadcast;

public class BroadcastMsg {
	
	public static HashMap<Integer, BroadcastMsg> messages = new HashMap<Integer, BroadcastMsg>();
	
	private int id;
	
	public static int idCounter;
	
	private String type;
	private String msg;
	private long endTime;
	private long startTime;
	
	private int timeLevel;
	
	public BroadcastMsg(int id, String type, String msg, long endTime, long startTime){
		this.id=id;
		this.type=type;
		this.msg = msg;
		this.endTime = endTime;
		this.startTime = startTime;
		messages.put(id, this);
		calculateTimeLevel();
	}

	public BroadcastMsg(String type, String msg, long endTime){
		this.id=idCounter;
		idCounter++;
		this.type=type;
		this.msg = msg;
		if(!type.equalsIgnoreCase("Broadcast")){
			this.endTime = endTime;
		}else{
			this.endTime = Long.MAX_VALUE;
		}
		this.startTime = System.currentTimeMillis();
		messages.put(id, this);
		calculateTimeLevel();
	}

	public String getType() {
		return type;
	}
	
	public String getMsg(){
		return msg;
	}
	
	public long getEndTime(){
		return endTime;
	}
	
	public long getStartTime(){
		return startTime;
	}
	
	public int getId(){
		return id;
	}

	public String getMsgAsBroadcast() {
		String beginColor = Broadcast.broadcastColor;
		String text = beginColor + "["+Broadcast.broadcastText+"]:&f" + msg;
		return text;
	}

	public String getMsgAsNews() {
		String beginColor = Broadcast.timeColors.get(timeLevel);
		String text = beginColor + "["+Broadcast.newsText+"]:&f" + msg;
		return text;
	}

	public String getMsgAsDate() {
		String beginColor = Broadcast.timeColors.get(Broadcast.timeColors.size()-1-timeLevel);
		String text = beginColor + "["+Broadcast.dateText+" "+DateFormatUtils.format(endTime, "dd MM yy HH:mm")+"]:&f" + msg;
		return text;
	}
	
	public void calculateTimeLevel(){
		long diff = endTime-startTime;
		if(diff == 0){timeLevel = 0; return;}
		timeLevel = (int)((System.currentTimeMillis() - diff) / 
				(diff / Broadcast.timeColors.size()));
		return;
	}
	
}
