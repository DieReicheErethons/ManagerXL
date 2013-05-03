package com.dre.managerxl.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.dre.managerxl.P;

public class ServerListener implements Listener{
	
	@EventHandler()
	public void onServerListPing(ServerListPingEvent event){
		if(P.p.config.getMotD()!=""){
			event.setMotd(P.p.replaceColors(P.p.config.getMotD()));
		}
	}
}
