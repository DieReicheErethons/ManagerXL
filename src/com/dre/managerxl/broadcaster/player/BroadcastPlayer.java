package com.dre.managerxl.broadcaster.player;

import java.util.ArrayList;
import java.util.HashMap;

public class BroadcastPlayer {

	private String player;
	public HashMap<Integer,BroadcastPlayerMsg> playerMsgs = new HashMap<Integer,BroadcastPlayerMsg>();
	
	public static ArrayList<BroadcastPlayer> list = new ArrayList<BroadcastPlayer>();
	
	public BroadcastPlayer(String player){
		list.add(this);
		
		this.player=player;
		
		
	}
	
	public String getPlayer(){
		return player;
	}
	
	public static BroadcastPlayer getBroadcastPlayer(String player){
		
		for(BroadcastPlayer bPlayer: list){
			if(player.equalsIgnoreCase(bPlayer.getPlayer())){
				return bPlayer;
			}
		}
		
		return new BroadcastPlayer(player); // If not Exists create new one
	}

	public BroadcastPlayerMsg getBMsg(int id) {
		BroadcastPlayerMsg msg = playerMsgs.get(id);
		
		if(msg == null){
			msg = new BroadcastPlayerMsg(id, this);
		}
		
		return msg;
	}
	
}
