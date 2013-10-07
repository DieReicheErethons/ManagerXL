package com.dre.managerxl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
	public static String broadcastColor = "&2";
	
	public static String broadcastText = "Broadcast";
	public static String newsText = "News";
	public static String dateText = "Date";
	
	public static String broadcastFolderName = "Broadcaster";
	public static String broadcastDataFileName = "data.yml";
	public static String broadcastMsgFileName = "msg.yml";
	
	public static ArrayList<String> timeColors = new ArrayList<String>();
	
	private File broadcastFolder;
	private File broadcastDataFile;
	private File broadcastMsgFile;
	
	public Broadcast(){
		
		initializeBroadcaster();
		
		timeColors.add("&4");
		timeColors.add("&c");
		timeColors.add("&6");
		timeColors.add("&e");
		timeColors.add("&2");
		timeColors.add("&a");
		this.load();
		
		initBroadcastSchedulers();
	}
	
	private void initializeBroadcaster() {
		broadcastFolder = new File(P.p.getDataFolder(), broadcastFolderName);
		
		if(!broadcastFolder.exists()){
			broadcastFolder.mkdirs();
		}
		
		broadcastDataFile = new File(P.p.getDataFolder(), broadcastFolderName+"/"+broadcastDataFileName);
		
		if(!broadcastDataFile.exists()){
			try {
				broadcastDataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		broadcastMsgFile = new File(P.p.getDataFolder(), broadcastFolderName+"/"+broadcastMsgFileName);
		
		if(!broadcastMsgFile.exists()){
			try {
				broadcastMsgFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void initBroadcastSchedulers() {
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
		
		BroadcastPlayer bPlayer = BroadcastPlayer.getBroadcastPlayer(player.getName());
		BroadcastPlayerMsg bMsg = bPlayer.getBMsg(msg.getId());
		
		bMsg.setSendCount(bMsg.getSendCount()+1);
		
		bMsg.setLastSend(System.currentTimeMillis());
		
		int newPlayerLevel = (bMsg.getSendCount()/sendsPerLevel) + 1;
		if(newPlayerLevel > maxLevel){newPlayerLevel=maxLevel;}
		
		bMsg.setPlayerLevel(newPlayerLevel);
		
		if(msg.getType().equalsIgnoreCase("Broadcast")){
			player.sendMessage(msg.getMsgAsBroadcast());
		}else if(msg.getType().equalsIgnoreCase("News")){
			player.sendMessage(msg.getMsgAsNews());
		}else if(msg.getType().equalsIgnoreCase("Date")){
			player.sendMessage(msg.getMsgAsDate());
		}
		
	}
	
	public static long getNextSendTime(Player player, BroadcastMsg msg){
		BroadcastPlayer bPlayer = BroadcastPlayer.getBroadcastPlayer(player.getName());
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

	public void save() {
		saveMessages();
		saveData();
	}
	
	public void load(){
		loadMessages();
		loadData();
	}
	
	public void saveMessages(){
		FileConfiguration file = new YamlConfiguration();
		
		file.set("idCounterBroadcasterVar",BroadcastMsg.idCounter);
		
		
		for(Integer id:BroadcastMsg.messages.keySet()){
			BroadcastMsg message = BroadcastMsg.messages.get(id);
			file.set(id+".type", message.getType());
			file.set(id+".msg", message.getMsg());
			file.set(id+".endTime", message.getEndTime());
			file.set(id+".startTime", message.getStartTime());
		}
		
		try {
			file.save(broadcastMsgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void saveData(){
		
	}
	
	public void loadMessages(){
		FileConfiguration file = YamlConfiguration.loadConfiguration(broadcastMsgFile);
		
		BroadcastMsg.idCounter = file.getInt("idCounterBroadcasterVar");
		
		String type;
		String msg;
		long endTime;
		long startTime;
		
		for(String id: file.getKeys(false)){
			if(!id.equalsIgnoreCase("idCounterBroadcasterVar")){
				type = file.getString(id+".type");
				msg = file.getString(id+".msg");
				endTime = file.getLong(id+".endTime");
				startTime = file.getLong(id+".startTime");
				new BroadcastMsg(Integer.parseInt(id), type, msg, endTime, startTime);
			}
		}
	}
	
	public void loadData(){
		FileConfiguration file = YamlConfiguration.loadConfiguration(broadcastDataFile);
		BroadcastPlayer bPlayer;
		int playerLevel;
		int sendCount;
		long lastSend;
		
		
		
		for(String name: file.getKeys(false)){
			bPlayer = BroadcastPlayer.getBroadcastPlayer(name);
			ConfigurationSection section = file.getConfigurationSection(name);
			for(String id: section.getKeys(false)){
				playerLevel = file.getInt(name+"."+id+".playerLevel");
				sendCount = file.getInt(name+"."+id+".sendCount");
				lastSend = file.getInt(name+"."+id+".lastSend");
				new BroadcastPlayerMsg(Integer.parseInt(id), bPlayer, playerLevel, sendCount, lastSend);
			}
		}
	}
}
