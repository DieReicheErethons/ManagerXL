package com.dre.managerxl.broadcaster;

import com.dre.managerxl.MPlayer;

public class BroadcasterPlayerMsg {

	BroadcasterMsg msg;

	MPlayer mPlayer;

	private int playerLevel;

	private int sendCount;

	private long lastSend;

	public BroadcasterPlayerMsg(int id, MPlayer mPlayer, int playerLevel, int sendCount, long lastSend) {
		this.msg = BroadcasterMsg.messages.get(id);
		this.playerLevel = playerLevel;
		this.lastSend = lastSend;
		this.sendCount = sendCount;
		this.mPlayer = mPlayer;
		this.mPlayer.playerMsgs.put(id, this);
	}

	public BroadcasterPlayerMsg(int id, MPlayer bPlayer) {
		this(id, bPlayer, 1, 0, 0L);
	}

	public BroadcasterMsg getBroadcastMsg() {
		return msg;
	}

	public int getPlayerLevel() {
		return playerLevel;
	}

	public int getSendCount() {
		return sendCount;
	}

	public long getLastSend() {
		return lastSend;
	}

	public void setLastSend(long lastSend) {
		this.lastSend = lastSend;
	}

	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}

	public void setPlayerLevel(int playerLevel) {
		this.playerLevel = playerLevel;
	}

}
