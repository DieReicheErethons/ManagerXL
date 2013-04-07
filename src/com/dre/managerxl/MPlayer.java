package com.dre.managerxl;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class MPlayer {
	private static Set<MPlayer> mPlayers = new HashSet<MPlayer>();
	
	private String name;
	public String getName() {return name;}
	
	public Player getPlayer(){
		if(this.isOnline){
			return P.p.getServer().getPlayer(this.name);
		}
		
		return null;
	}
	
	private boolean isOnline;
	public boolean isOnline() {return isOnline;}
	public void setOnline(boolean online) {isOnline = online;}
	
	private boolean isBanned;
	public boolean isBanned() {
		if(getBannedTime() > 0){
			if(getUntilUnBannedTime() <= 0){
				isBanned = false;
				setBannedTime(0);
			}
		}
		
		return isBanned;
	}
	public void setBanned(boolean banned){
		isBanned = banned;
		
		if(isBanned){
			if(isOnline()){
				if(getBannedTime() > 0){
					getPlayer().kickPlayer(P.p.replaceColors(P.p.getLanguageReader().get("Player_Kick_TimeBan", this.getBannedReason(), "" + this.getBannedTime())));
				} else {
					getPlayer().kickPlayer(P.p.replaceColors(P.p.getLanguageReader().get("Player_Kick_Ban", this.getBannedReason())));
				}
			}
		}
	}
	
	private long bannedTime;
	public void setBannedTime(long l) {this.bannedTime = l;}
	public long getBannedTime() {return bannedTime;}
	public long getUntilUnBannedTime() {return bannedTime - System.currentTimeMillis();}
	
	private String bannedReason;
	public void setBannedReason(String bannedReason) {this.bannedReason = bannedReason;}
	public String getBannedReason() {return bannedReason;}
	
	public MPlayer(String name){
		mPlayers.add(this);
		
		this.name = name;
	}
	
	//Statics
	public static Set<MPlayer> get(){
		return mPlayers;
	}
	
	public static MPlayer get(String name){
		for(MPlayer mPlayer : mPlayers){
			if(mPlayer.getName().equalsIgnoreCase(name)){
				return mPlayer;
			}
		}
		
		return null;
	}
	
	public static MPlayer getOrCreate(String name){
		for(MPlayer mPlayer : mPlayers){
			if(mPlayer.getName().equalsIgnoreCase(name)){
				return mPlayer;
			}
		}
		
		return new MPlayer(name);
	}

	//Save and Load Functions
	public static boolean SaveAsYml(File file){
		FileConfiguration ymlFile = new YamlConfiguration();
		
		for(MPlayer player : MPlayer.get()){
			ymlFile.set(player.getName()+".isBanned", player.isBanned());
			ymlFile.set(player.getName()+".bannedTime", player.getBannedTime());
			ymlFile.set(player.getName()+".bannedReason", player.getBannedReason());
		}
		
		try {
			ymlFile.save(file);
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	public static boolean LoadAsYml(File file){
		FileConfiguration ymlFile = YamlConfiguration.loadConfiguration(file);
		
		Set<String> keys = ymlFile.getKeys(false);
		
		for(String name : keys){
			MPlayer mPlayer = new MPlayer(name);
			mPlayer.setBanned(ymlFile.getBoolean(name+".isBanned"));
			mPlayer.setBannedTime(ymlFile.getInt(name+".bannedTime"));
			mPlayer.setBannedReason(ymlFile.getString(name+".bannedReason"));
		}
		
		return true;
	}
}
