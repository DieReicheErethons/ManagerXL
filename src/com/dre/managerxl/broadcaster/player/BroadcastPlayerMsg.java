package com.dre.managerxl.broadcaster.player;

import com.dre.managerxl.broadcaster.BroadcastMsg;

public class BroadcastPlayerMsg {

	BroadcastMsg msg;
	
	BroadcastPlayer bPlayer;
	
	private int playerLevel;
	
	private int sendCount;
	
	private long lastSend;
	
	public BroadcastPlayerMsg(int id,BroadcastPlayer bPlayer, int playerLevel, int sendCount, long lastSend){
		this.msg = BroadcastMsg.messages.get(id);
		this.playerLevel = playerLevel;
		this.lastSend = lastSend;
		this.sendCount = sendCount;
		this.bPlayer = bPlayer;
		bPlayer.playerMsgs.put(id, this);
	}
	
	public BroadcastPlayerMsg(int id,BroadcastPlayer bPlayer){
		this(id, bPlayer, 1, 0, 0L);
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
