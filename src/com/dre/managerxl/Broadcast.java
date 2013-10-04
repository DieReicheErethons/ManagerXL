package com.dre.managerxl;

import org.bukkit.entity.Player;

import com.dre.managerxl.broadcaster.BroadcastMsg;
import com.dre.managerxl.broadcaster.player.BroadcastPlayer;
import com.dre.managerxl.broadcaster.player.BroadcastPlayerMsg;

public class Broadcast {
	
	//private FileConfiguration broadcastData;
	//private FileConfiguration broadcastMsgs;
	
	public static int maxLevel = 10; // TODO in die Config
	public static int sendsPerLevel = 20;
	public static int minTimeInMinutes = 10;
	public static int maxTimeInMinutes = 60;
	
	
	public Broadcast(){
		
		
		initBroadcastScheduler();
	}

	private void initBroadcastScheduler() {
		P.p.getServer().getScheduler().scheduleSyncRepeatingTask(P.p, new Runnable() {
			public void run() {
				Player[] players = P.p.getServer().getOnlinePlayers();
				
				for(Player player : players){
					for(Integer msgId: BroadcastMsg.messages.keySet()){
						BroadcastMsg msg = BroadcastMsg.messages.get(msgId);
						if(getNextSendTime(player, msg)>System.currentTimeMillis()){
							broadcastMsg(player, msg);
						}
					}
				}
				
			}
		}, 0L, 1200L);
	}
	
	public static void broadcastMsg(Player player, BroadcastMsg msg){
		
		BroadcastPlayer bPlayer = BroadcastPlayer.getBroadcastPlayer(player);
		BroadcastPlayerMsg bMsg = bPlayer.getBMsg(msg.getId());
		
		bMsg.setSendCount(bMsg.getSendCount()+1);
		
		bMsg.setLastSend(System.currentTimeMillis());
		
		int newPlayerLevel = (bMsg.getSendCount()/sendsPerLevel) + 1;
		if(newPlayerLevel > maxLevel){newPlayerLevel=maxLevel;}
		
		bMsg.setPlayerLevel(newPlayerLevel);
		
		if(msg.getType().equalsIgnoreCase("Broadcast")){
			player.sendMessage(msg.sendBroadcast());
		}else if(msg.getType().equalsIgnoreCase("News")){
			player.sendMessage(msg.sendNews());
		}else if(msg.getType().equalsIgnoreCase("Date")){
			player.sendMessage(msg.sendDate());
		}
		
	}
	
	public static long getNextSendTime(Player player, BroadcastMsg msg){
		BroadcastPlayer bPlayer = BroadcastPlayer.getBroadcastPlayer(player);
		BroadcastPlayerMsg bMsg = bPlayer.getBMsg(msg.getId());
		long lastSend = bMsg.getLastSend();
		int playerLevel = bMsg.getPlayerLevel();
		int timeDiff = maxTimeInMinutes-minTimeInMinutes;
		double time;
		if(playerLevel > maxLevel){
			time = maxTimeInMinutes;
		}else if(playerLevel > 0){
			time =(double) timeDiff /(double) maxLevel *(double) playerLevel;
		}else{
			time = minTimeInMinutes;
		}
		
		long milliTime = (long) (time * 60 * 1000);
		
		return milliTime+lastSend;
	}
}
