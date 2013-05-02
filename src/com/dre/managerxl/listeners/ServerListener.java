package com.dre.managerxl.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.dre.managerxl.P;

public class ServerListener implements Listener{
	
	@EventHandler()
	public void onServerListPing(ServerListPingEvent event){
		/* Set MotD */
		event.setMotd(P.p.getMotD());
	}
}
