package com.dre.managerxl.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.dre.managerxl.MPlayer;
import com.dre.managerxl.P;
import com.dre.managerxl.util.MUtility;

public class PlayerListener implements Listener {

	@EventHandler()
	public void onPlayerLogin(PlayerLoginEvent event) {
		MPlayer player = MPlayer.get(event.getPlayer().getName());

		if (player != null) {
			if (player.isBanned()) {
				if (player.getUntilUnBannedTime() > 0) {
					event.disallow(Result.KICK_OTHER, P.p.replaceColors(P.p.getLanguageReader().get("Player_Kick_TimeBan", player.getBannedReason(), MUtility.getIntTimeToString(player.getUntilUnBannedTime()))));
				} else {
					event.disallow(Result.KICK_OTHER, P.p.replaceColors(P.p.getLanguageReader().get("Player_Kick_Ban", player.getBannedReason())));
				}
			}
		}
	}

	@EventHandler()
	public void onPlayerJoin(PlayerJoinEvent event) {
		MPlayer player = MPlayer.getOrCreate(event.getPlayer().getName());

		player.setOnline(true);
		player.setGameMode(player.getGameMode());

		// Set invisible Players
		if (!player.isVisible()) {
			for (Player oPlayer : Bukkit.getOnlinePlayers()) {
				oPlayer.hidePlayer(player.getPlayer());
				MPlayer omPlayer = MPlayer.getOrCreate(oPlayer.getName());
				if (!omPlayer.isVisible()) {
					player.getPlayer().hidePlayer(oPlayer);
				}
			}
		}

		// Set invisible for Dynmap
		if (P.p.dynmap != null) {
			P.p.dynmap.assertPlayerInvisibility(player.getName(), !player.isVisible(), "ManagerXL");
		}

	}

	@EventHandler()
	public void onPlayerQuit(PlayerQuitEvent event) {
		MPlayer player = MPlayer.get(event.getPlayer().getName());

		if (player == null) {
			player = new MPlayer(event.getPlayer().getName());
		}

		player.setOnline(false);
	}
	
	@EventHandler()
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		MPlayer player = MPlayer.getOrCreate(event.getPlayer().getName());
		
		if(player.getHome()!=null){
			event.setRespawnLocation(player.getHome());
		}
	}

	@EventHandler()
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		MPlayer mPlayer = MPlayer.getOrCreate(event.getPlayer().getName());

		if (mPlayer.isMuted()) {
			P.p.msg(event.getPlayer(), P.p.getLanguageReader().get("Player_Muted"));
			event.setCancelled(true);
		}
	}

	@EventHandler()
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		/* Teleport Compass */
		if (P.p.getPermissionHandler().has(player, "mxl.tool.tpcompass")) {
			if (event.hasItem()) {
				if (event.getItem().getType() == Material.COMPASS) {
					Location seeLocation = null;
					List<Block> sight = player.getLastTwoTargetBlocks(null, 100);
					boolean hitStone = false;

					for (Block block : sight) {
						if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
							if (block.getTypeId() != 0) {
								hitStone = true;
							}
							if (block.getTypeId() == 0 && hitStone) {
								seeLocation = block.getLocation();
								break;
							}
						} else {
							if (block.getTypeId() != 0) {
								seeLocation = block.getLocation();
								break;
							}
						}
					}

					if (seeLocation != null) {
						seeLocation.setPitch(player.getLocation().getPitch());
						seeLocation.setYaw(player.getLocation().getYaw());

						if (player.getLocation().getY() > seeLocation.getY()) {
							seeLocation.setY(seeLocation.getY() + 1);
						}

						player.teleport(seeLocation);
					}

					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler()
	public void onPlayerTeleport(PlayerTeleportEvent event){
		MPlayer mPlayer = MPlayer.getOrCreate(event.getPlayer().getName());
		
		mPlayer.setLastTeleport(System.currentTimeMillis());
	}
	
	@EventHandler()
	public void onPlayerDamage(EntityDamageEvent event){
		if(event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			MPlayer mPlayer = MPlayer.getOrCreate(player.getName());
			
			if(event.getCause() == DamageCause.SUFFOCATION){
				if(mPlayer.getLastTeleport() + 5000 > System.currentTimeMillis()){
					player.teleport(MUtility.getNearestFreePosition(player.getLocation()));					
				}
			}
		}
	}
}
