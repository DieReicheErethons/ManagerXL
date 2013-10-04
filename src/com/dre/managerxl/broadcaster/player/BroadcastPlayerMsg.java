package com.dre.managerxl.broadcaster.player;

import com.dre.managerxl.broadcaster.BroadcastMsg;

public class BroadcastPlayerMsg {

	BroadcastMsg msg;
	
	BroadcastPlayer bPlayer;
	
	private int playerLevel;
	
	private int sendCount;
	
	private long lastSend;
	
	
	public BroadcastPlayerMsg(int id,BroadcastPlayer bPlayer){
		this.msg = BroadcastMsg.messages.get(id);
		this.playerLevel = 1;
		this.lastSend = 0L;
		this.sendCount = 0;
		this.bPlayer = bPlayer;
		bPlayer.playerMsgs.put(id, this);
	}
	
	public BroadcastMsg getBroadcastMsg(){
		return msg;
	}
	
	public int getPlayerLevel(){
		return playerLevel;
	}
	
	public int getSendCount(){
		return sendCount;
	}
	
	public long getLastSend(){
		return lastSend;
	}
	
	public void setLastSend(long lastSend){
		this.lastSend = lastSend;
	}
	
	public void setSendCount(int sendCount){
		this.sendCount = sendCount;
	}
	
	public void setPlayerLevel(int playerLevel){
		this.playerLevel = playerLevel;
	}
	
}
