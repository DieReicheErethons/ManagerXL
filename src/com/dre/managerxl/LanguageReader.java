package com.dre.managerxl;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LanguageReader {
	private Map<String,String> entries = new TreeMap<String,String>();
	private Map<String,String> defaults = new TreeMap<String,String>();

	private File file;
	private boolean changed;

	public LanguageReader(File file){
		this.setDefaults();

		/* Load */
		this.file = file;

		FileConfiguration configFile = YamlConfiguration.loadConfiguration(file);

		Set<String> keySet = configFile.getKeys(false);
		for(String key:keySet){
			entries.put(key, configFile.getString(key));
		}

		/* Check */
		this.check();
	}

	private void setDefaults(){
		
		/* Log */
		defaults.put("Log_PlayersSaved", "Spieler gespeichert");
		defaults.put("Log_PlayersLoaded", "Spieler geladen");
		defaults.put("Log_Error_NoConsoleCommand", "&6mxl &v1&4 kann man nicht als Konsole ausführen!");
		defaults.put("Log_Error_PlayersSaved", "Spieler konnten nicht gespeichert werden!");
		defaults.put("Log_Error_PlayersLoaded", "Spieler konnten nicht geladen werden!");
		
		/* Help */
		defaults.put("Help_Ban", "/ban <player> <reason> - Bannt einen Spieler");
		defaults.put("Help_TimeBan", "/timeban <player> <time> <reason> - Bannt einen Spieler für eine bestimmte Zeit");
		defaults.put("Help_UnBan", "/unban <player> - Entbannt einen Spieler");
		
		/* Player */
		defaults.put("Player_Kick_Ban", "&4Du wurdest gebannt. Grund: &6&v1");
		defaults.put("Player_Kick_TimeBan", "&4Du wurdest gebannt für &6&v2&4. Grund: &6&v1");
		
		/* CMDs */
		defaults.put("Cmd_Ban_Success", "&6Spieler &4&v1&6 wurde erfolgreich gebannt!");
		defaults.put("Cmd_Ban_DefaultReason", "Du hast gegen die Regeln verstossen!");
		defaults.put("Cmd_TimeBan_Success", "&6Spieler &4&v1&6 wurde erfolgreich für &4&v2&6 gebannt!");
		defaults.put("Cmd_UnBan_Success", "&6Spieler &4&v1&6 wurde erfolgreich entbannt!");
		
		/* Errors */
		defaults.put("Error_NoPermissions","&4Du hast keine Erlaubnis dies zu tun!");
		defaults.put("Error_CmdBan_AlreadyBanned", "&4Spieler &6&v1&4 ist schon gebannt!");
		defaults.put("Error_CmdBan_NotBanned", "&4Spieler &6&v1&4 ist nicht gebannt!");
		defaults.put("Error_NoPlayerCommand", "&6/mxl &v1&4 kann man nicht als Spieler ausführen!");
		defaults.put("Error_NoPermissions", "&4Du hast keine Erlaubnis dies zu tun!");
		defaults.put("Error_CmdNotExist1","&4Befehl &6&v1&4 existiert nicht!");
		defaults.put("Error_CmdNotExist2","&4Bitte gib &6/mxl help&4 für Hilfe ein!");
		
		/* Format */
		defaults.put("Format_Days","Tage");
		defaults.put("Format_Hours", "Stunden");
		defaults.put("Format_Minutes", "Minuten");
		defaults.put("Format_Seconds", "Sekunden");
	}

	private void check(){
		for(String defaultEntry:defaults.keySet()){
			if(!entries.containsKey(defaultEntry)){
				entries.put(defaultEntry,defaults.get(defaultEntry));
				changed = true;
			}
		}
	}

	public void save(){
		if(changed){
			/* Copy old File */
			File source = new File(file.getPath());
			String filePath = file.getPath();
			File temp = new File(filePath.substring(0,filePath.length()-4)+"_old.yml");

	        if(temp.exists())
	            temp.delete();

	        source.renameTo(temp);

			/* Save */
			FileConfiguration configFile = new YamlConfiguration();

			for(String key:entries.keySet()){
				configFile.set(key, entries.get(key));
			}

			try {
				configFile.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String get(String key, String... args){
		String entry = entries.get(key);

		if(entry!=null){
			int i=0;
			for(String arg:args){
				if(arg != null){
					i++;
					entry = entry.replace("&v"+i, arg);
				}
			}
		} else {
			entry = "%placeholder%";
		}

		return entry;
	}
}
