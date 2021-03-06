package com.dre.managerxl.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.dre.managerxl.MPlayer;
import com.dre.managerxl.P;
import com.dre.managerxl.util.MUtility;

public class PlayerListener implements Listener {

	@EventHandler()
	public void onPlayerLogin(PlayerLoginEvent event) {
		MPlayer player = MPlayer.getOrCreate(event.getPlayer().getUniqueId());

		if (player != null) {
			if (player.isBanned()) {
				if (player.getUntilUnBannedTime() > 0) {
					event.disallow(Result.KICK_OTHER, P.p.replaceColors(P.p.getLanguageReader().get("Player_Kick_TimeBan", player.getBannedReason(), MUtility.getLongTimeToString(player.getUntilUnBannedTime()))));
				} else {
					event.disallow(Result.KICK_OTHER, P.p.replaceColors(P.p.getLanguageReader().get("Player_Kick_Ban", player.getBannedReason())));
				}
			}
		}
	}

	@EventHandler()
	public void onPlayerJoin(PlayerJoinEvent event) {
		MPlayer player = MPlayer.getOrCreate(event.getPlayer().getUniqueId());

		player.setGameMode(player.getGameMode(), false);

		// Set invisible Players
		if (!player.isVisible()) {
			for (Player oPlayer : Bukkit.getOnlinePlayers()) {
				oPlayer.hidePlayer(player.getPlayer());
				MPlayer omPlayer = MPlayer.getOrCreate(oPlayer.getUniqueId());
				if (!omPlayer.isVisible()) {
					player.getPlayer().hidePlayer(oPlayer);
				}
			}
		}

		// Set invisible for Dynmap
		if (P.p.dynmap != null) {
			P.p.dynmap.assertPlayerInvisibility(player.getPlayer().getName(), !player.isVisible(), "ManagerXL");
		}

	}

	@EventHandler()
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		MPlayer player = MPlayer.get(event.getPlayer().getUniqueId());

		if (player != null && player.getHome() != null) {
			event.setRespawnLocation(player.getHome());
		}
	}

	@EventHandler()
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		MPlayer player = MPlayer.get(event.getPlayer().getUniqueId());

		if (player != null && player.isMuted()) {
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
					Block lastBlockInSight = MUtility.getLastBlockInSight(player, 100);
					
					if(lastBlockInSight != null){
						if(lastBlockInSight.getType() != Material.AIR){
							Location seeLocation = lastBlockInSight.getLocation();
							if (seeLocation != null) {
								seeLocation.setPitch(player.getLocation().getPitch());
								seeLocation.setYaw(player.getLocation().getYaw());

								if (player.getLocation().getY() > seeLocation.getY()) {
									seeLocation.setY(seeLocation.getY() + 1);
								}

								player.teleport(seeLocation);
							}
						}
					}

					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler()
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		MPlayer player = MPlayer.get(event.getPlayer().getUniqueId());
		
		if (player != null) {
			player.setLastTeleport(System.currentTimeMillis());
		}
	}

	@EventHandler()
	public void onPlayerDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			MPlayer mPlayer = MPlayer.get(player.getUniqueId());

			if (mPlayer != null
					&& event.getCause() == DamageCause.SUFFOCATION
					&& mPlayer.getLastTeleport() + 5000 > System.currentTimeMillis()) {
				player.teleport(MUtility.getNearestFreePosition(player.getLocation()));
			}
		}
	}
}
