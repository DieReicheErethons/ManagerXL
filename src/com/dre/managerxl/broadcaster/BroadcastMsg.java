package com.dre.managerxl.broadcaster;

import java.util.HashMap;

public class BroadcastMsg {
	
	public static HashMap<Integer, BroadcastMsg> messages = new HashMap<Integer, BroadcastMsg>();
	
	private int id;
	
	public static int idCounter;
	
	private String type;
	private String msg;
	private long endTime;
	private long startTime;
	
	public BroadcastMsg(String type, String msg, long endTime, long startTime){
		this.id=idCounter;
		idCounter++;
		this.type=type;
		this.msg = msg;
		this.endTime = endTime;
		this.startTime = startTime;
		messages.put(id, this);
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

	public String sendBroadcast() {
		// TODO  send
		return null;
	}

	public String sendNews() {
		// TODO send news
		return null;
	}

	public String sendDate() {
		// TODO send date
		return null;
	}
	
}
