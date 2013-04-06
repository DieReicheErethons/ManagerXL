package com.dre.managerxl.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;

import com.dre.managerxl.MPlayer;
import com.dre.managerxl.P;

public class PlayerListener implements Listener{
	
	@EventHandler()
	public void onPlayerLogin(PlayerLoginEvent event){
		MPlayer player = MPlayer.get(event.getPlayer().getName());
		
		if(player != null){
			if(player.isBanned()){
				event.disallow(Result.KICK_OTHER, P.p.replaceColors(P.p.getLanguageReader().get("Player_Kick_Ban", player.getBannedReason())));
			}
		}
	}
	
	@EventHandler()
	public void onPlayerJoin(PlayerJoinEvent event){
		MPlayer player = MPlayer.get(event.getPlayer().getName());
		
		if(player == null){
			player = new MPlayer(event.getPlayer().getName());
		}
		
		player.setOnline(true);
	}
	
	
	@EventHandler()
	public void onPlayerQuit(PlayerQuitEvent event){
		MPlayer player = MPlayer.get(event.getPlayer().getName());
		
		if(player == null){
			player = new MPlayer(event.getPlayer().getName());
		}
		
		player.setOnline(false);
	}
}
