package com.dre.managerxl.broadcaster.player;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

public class BroadcastPlayer {

	private Player player;
	public HashMap<Integer,BroadcastPlayerMsg> playerMsgs = new HashMap<Integer,BroadcastPlayerMsg>();
	
	private static ArrayList<BroadcastPlayer> list = new ArrayList<BroadcastPlayer>();
	
	public BroadcastPlayer(Player player){
		list.add(this);
		
		this.player=player;
		
		
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public static BroadcastPlayer getBroadcastPlayer(Player player){
		
		for(BroadcastPlayer bPlayer: list){
			if(player == bPlayer.getPlayer()){
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
