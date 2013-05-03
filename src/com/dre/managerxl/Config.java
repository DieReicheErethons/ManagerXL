package com.dre.managerxl;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	// File
	private File file;

	// MotD
	private String motd;

	public String getMotD() {
		return this.motd;
	}

	public void setMotD(String motd) {
		this.motd = motd;
	}

	public Config(File file) {
		if (!file.exists()) {
			P.p.saveDefaultConfig();
		}

		this.file = file;
		FileConfiguration ymlFile = YamlConfiguration.loadConfiguration(this.file);

		this.motd = ymlFile.getString("MotD");
	}

	public void saveSingleConfig(String path, Object object) {
		FileConfiguration ymlFile = YamlConfiguration.loadConfiguration(this.file);

		ymlFile.set(path, object);

		try {
			ymlFile.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
