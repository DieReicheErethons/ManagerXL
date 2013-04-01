package com.dre.managerxl;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

public class P extends JavaPlugin{
	public static P p;
	
	
	
	//Language Reader
	private LanguageReader languageReader;
	public LanguageReader getLanguageReader(){
		return languageReader;
	}
	
	
	@Override
	public void onEnable(){
		p = this;
		
		//Load LanguageReader
		languageReader = new LanguageReader(new File(p.getDataFolder(), "languages/default.yml"));
	}
	
	@Override
	public void onDisable(){
		
	}
}
