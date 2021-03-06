package com.dre.managerxl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.dre.managerxl.broadcaster.BroadcasterPlayerMsg;
import com.dre.managerxl.util.UUIDFetcher;

public class MPlayer {
	private static Set<MPlayer> mPlayers = new HashSet<MPlayer>();

	private UUID uuid;
	private boolean isBanned;
	private boolean isMuted;
	private boolean isVisible = true;
	private long bannedTime;
	private String bannedReason;
	private Location home;
	private GameMode gameMode = GameMode.SURVIVAL;
	private long lastTeleport;

	/* BroadcasterPlayer */
	public HashMap<Integer, BroadcasterPlayerMsg> playerMsgs = new HashMap<Integer, BroadcasterPlayerMsg>();

	public BroadcasterPlayerMsg getBMsg(int id) {
		BroadcasterPlayerMsg msg = playerMsgs.get(id);

		if (msg == null) {
			msg = new BroadcasterPlayerMsg(id, this);
		}

		return msg;
	}

	public MPlayer(UUID uuid) {
		mPlayers.add(this);

		this.uuid = uuid;
	}

	/* Statics */
	public static Set<MPlayer> get() {
		return mPlayers;
	}

	public static MPlayer get(UUID uuid) {
		for (MPlayer mPlayer : mPlayers) {
			if (mPlayer != null){
				if (mPlayer.getUUID().equals(uuid)) {
					return mPlayer;
				}
			}
		}

		return null;
	}

	public static MPlayer getOrCreate(UUID uuid) {
		for (MPlayer mPlayer : mPlayers) {
			if (mPlayer != null){
				if (mPlayer.getUUID().equals(uuid)) {
					return mPlayer;
				}
			}
		}
		
		P.p.log("Player " + uuid + " not recognized, creating a new one!");
		
		return new MPlayer(uuid);
	}

	/* Save and Load Functions */
	public static boolean SaveAsYml(File file) {
		FileConfiguration ymlFile = new YamlConfiguration();
		
		P.p.log("Playercount " + MPlayer.get().size());
		
		for (MPlayer player : MPlayer.get()) {
			if (player==null)
				continue;
			/* Ban */
			ymlFile.set(player.getUUID() + ".isBanned", player.isBanned());
			ymlFile.set(player.getUUID() + ".bannedTime", player.getBannedTime());
			if (player.getBannedReason() != null)
				ymlFile.set(player.getUUID() + ".bannedReason", player.getBannedReason());

			/* Mute */
			ymlFile.set(player.getUUID() + ".isMuted", player.isMuted());

			/* GameMode */
			ymlFile.set(player.getUUID() + ".GameMode", player.getGameMode().name());

			/* Home */
			if (player.getHome() != null) {
				ymlFile.set(player.getUUID() + ".home.x", player.getHome().getX());
				ymlFile.set(player.getUUID() + ".home.y", player.getHome().getY());
				ymlFile.set(player.getUUID() + ".home.z", player.getHome().getZ());
				ymlFile.set(player.getUUID() + ".home.pitch", (int) player.getHome().getPitch());
				ymlFile.set(player.getUUID() + ".home.yaw", (int) player.getHome().getYaw());
				ymlFile.set(player.getUUID() + ".home.world", player.getHome().getWorld().getName());
			}

			/* Visible */
			ymlFile.set(player.getUUID() + ".isVisible", player.isVisible());
			
			if(player.getOfflinePlayer()!=null)
				ymlFile.set(player.getUUID() + ".lastPlayerName", player.getOfflinePlayer().getName());
		}

		try {
			ymlFile.save(file);
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	public static boolean LoadAsYml(File file) {
		FileConfiguration ymlFile = YamlConfiguration.loadConfiguration(file);

		Set<String> keys = ymlFile.getKeys(false);
		
		
		HashMap<String, String> uuidMapping = new HashMap<String, String>();
		boolean excepted = false;
		ArrayList<String> uuidFails = new ArrayList<String>();
		for (String uuid : keys) {
			try{
				UUID.fromString(uuid);
				uuidMapping.put(uuid, uuid);
			}catch(Exception e){
				excepted = true;
				P.p.log("Convert " + uuid + " to the new UUID system...");
				uuidFails.add(uuid);
			}
		}
		if(excepted){
			UUIDFetcher fetcher = new UUIDFetcher(uuidFails);
			
			try {
				Map<String, UUID> result = fetcher.call();
				for(String resultKey: result.keySet()){
					uuidMapping.put(resultKey, result.get(resultKey).toString());
					P.p.log(resultKey + " has now the UUID " + result.get(resultKey).toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for (String uuid : keys) {
			
			if (
					!ymlFile.getBoolean(uuid + ".isBanned") &&
					ymlFile.getLong(uuid + ".bannedTime") == 0 &&
					!ymlFile.getBoolean(uuid + ".isMuted") &&
					ymlFile.getString(uuid + ".GameMode").equals("SURVIVAL") &&
					!ymlFile.contains(uuid + ".home") &&
					ymlFile.getBoolean(uuid + ".isVisible")
					) {
				P.p.log("SKIP");
				continue;
			}
			
			MPlayer mPlayer = null;
			String uuidString = uuidMapping.get(uuid);
			
			if(uuidString == null || uuidString.equalsIgnoreCase("null")){
				continue;
			}
			
			try {
				mPlayer = new MPlayer(UUID.fromString(uuidString));
			} catch(Exception e) {
				P.p.log("Convert " + uuid + " to the new UUID system...");
				
				UUIDFetcher fetcher = new UUIDFetcher(Arrays.asList(uuid));
				
				try {
					Map<String, UUID> result = fetcher.call();
					mPlayer = new MPlayer(result.get(uuid));
					P.p.log(uuid + " has now the UUID " + result.get(uuid));
				} catch(Exception e1) {
					P.p.log("Exception while running UUIDFetcher");
					e1.printStackTrace();
				}
			}
			
			if (mPlayer != null) {
				/* Ban */
				mPlayer.setBanned(ymlFile.getBoolean(uuid + ".isBanned"));
				mPlayer.setBannedTime(ymlFile.getLong(uuid + ".bannedTime"));
				mPlayer.setBannedReason(ymlFile.getString(uuid + ".bannedReason"));
	
				/* Mute */
				mPlayer.setMuted(ymlFile.getBoolean(uuid + ".isMuted"));
	
				/* GameMode */
				mPlayer.setGameMode(GameMode.valueOf(ymlFile.getString(uuid + ".GameMode")), false);
	
				/* Location */
				if (ymlFile.contains(uuid + ".home")) {
					World world = Bukkit.getWorld(ymlFile.getString(uuid + ".home.world"));
					if (world != null) {
						Location loc = new Location(world, ymlFile.getDouble(uuid + ".home.x"), ymlFile.getDouble(uuid + ".home.y"), ymlFile.getDouble(uuid + ".home.z"), ymlFile.getInt(uuid + ".home.pitch"), ymlFile.getInt(uuid + ".home.yaw"));
						mPlayer.setHome(loc);
					}
				}
	
				/* Visible */
				mPlayer.setVisible(ymlFile.getBoolean(uuid + ".isVisible"));
			}
		}
		if(excepted){
			SaveAsYml(file);
		}
		return true;
	}
	
	public boolean isOnline() {
		if (this.getPlayer() != null && this.getPlayer().isOnline()) {
			return true;
		}
		
		return false;
	}
	
	/* Getters and Setters */
	public UUID getUUID() {
		return uuid;
	}
	
	public Player getPlayer() {
		return P.p.getServer().getPlayer(this.uuid);
	}

	public boolean isBanned() {
		if (getBannedTime() > 0) {
			if (getUntilUnBannedTime() <= 0) {
				isBanned = false;
				setBannedTime(0);
			}
		}

		return isBanned;
	}

	public void setBanned(boolean banned) {
		isBanned = banned;

		if (isBanned) {
			if (isOnline()) {
				if (getBannedTime() > 0) {
					getPlayer().kickPlayer(P.p.replaceColors(P.p.getLanguageReader().get("Player_Kick_TimeBan", this.getBannedReason(), "" + this.getBannedTime())));
				} else {
					getPlayer().kickPlayer(P.p.replaceColors(P.p.getLanguageReader().get("Player_Kick_Ban", this.getBannedReason())));
				}
			}
		}
	}

	public void setBannedTime(long l) {
		this.bannedTime = l;
	}

	public long getBannedTime() {
		return bannedTime;
	}

	public long getUntilUnBannedTime() {
		return bannedTime - System.currentTimeMillis();
	}

	public void setBannedReason(String bannedReason) {
		this.bannedReason = bannedReason;
	}

	public String getBannedReason() {
		return bannedReason;
	}

	public Location getHome() {
		return home;
	}

	public void setHome(Location home) {
		this.home = home;
	}

	public boolean isMuted() {
		return isMuted;
	}

	public void setMuted(boolean isMuted) {
		this.isMuted = isMuted;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public boolean setGameMode(GameMode gameMode, boolean msg) {
		if (gameMode != null) {
			this.gameMode = gameMode;

			if (this.getPlayer() != null) {
				this.getPlayer().setGameMode(this.gameMode);
				if (msg) {
					P.p.msg(this.getPlayer(), P.p.getLanguageReader().get("Player_GameModeChanged", this.gameMode.name()));
				}
			}

			return true;
		}

		return false;
	}

	public boolean isVisible() {
		return this.isVisible;
	}

	public void setVisible(boolean isVisible) {
		if (this.getPlayer() != null) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (isVisible) {
					player.showPlayer(this.getPlayer());
				} else {
					player.hidePlayer(this.getPlayer());
				}
			}
		}

		// Dynmap
		if (P.p.dynmap != null) {
			P.p.dynmap.assertPlayerInvisibility(this.getPlayer().getName(), !isVisible, "ManagerXL");
		}

		this.isVisible = isVisible;
	}

	public long getLastTeleport() {
		return this.lastTeleport;
	}

	public void setLastTeleport(long time) {
		this.lastTeleport = time;
	}
	
	public OfflinePlayer getOfflinePlayer(){
		try{
			return P.p.getServer().getOfflinePlayer(this.uuid);
		}catch(Exception e){
			return null;
		}
	}
	
	public static MPlayer getFromName(String name) {
		if(Bukkit.getPlayer(name) != null) {
			return getOrCreate(Bukkit.getPlayer(name).getUniqueId());
		}
		
		UUIDFetcher fetcher = new UUIDFetcher(Arrays.asList(name));
		
		Map <String, UUID> response = null;
		try {
			response = fetcher.call();			
			return getOrCreate(response.get(name));
		} catch (Exception e) {
			P.p.log("Exception while running UUIDFetcher");
			e.printStackTrace();
		}
		
		return null;
	}
}
