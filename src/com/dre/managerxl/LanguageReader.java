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
		/* Errors */
		defaults.put("Error_NoPermissions","&4Du hast keine Erlaubnis dies zu tun!");
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
				i++;
				entry = entry.replace("&v"+i, arg);
			}
		}

		return entry;
	}
}
