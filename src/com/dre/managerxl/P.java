package com.dre.managerxl;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapCommonAPI;

import com.dre.managerxl.broadcaster.Broadcaster;
import com.dre.managerxl.commands.MCommand;
import com.dre.managerxl.listeners.PlayerListener;
import com.dre.managerxl.listeners.ServerListener;

public class P extends JavaPlugin {
	public static P p;

	/* Other Plugins */
	public DynmapCommonAPI dynmap;

	/* Config */
	public Config config;

	/* Language Reader */
	private LanguageReader languageReader;

	public LanguageReader getLanguageReader() {
		return languageReader;
	}
	
	/* Broadcaster */
	private Broadcaster broadcaster;
	
	public Broadcaster getBroadcast(){
		return broadcaster;
	}

	@Override
	public void onEnable() {
		p = this;

		// Load Config
		this.config = new Config(new File(this.getDataFolder(), "config.yml"));

		// Load LanguageReader
		this.languageReader = new LanguageReader(new File(p.getDataFolder(), "languages/default.yml"));

		// Load Broadcaster
		this.broadcaster= new Broadcaster();
		
		// Setup Permissions
		setupPermissions();

		// Init Listeners
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new ServerListener(), this);

		// Setup Commands
		MCommand.initCommands();

		// Init schedulers
		initSchedulers();
		
		// Load
		LoadAll();

		// Check Dynmap
		Plugin dynmapPlugin = Bukkit.getPluginManager().getPlugin("dynmap");
		if (dynmapPlugin != null) {
			this.dynmap = ((DynmapCommonAPI) dynmapPlugin);
		}
	}

	@Override
	public void onDisable() {
		// Save
		SaveAll();
	}
	
	
	public void initSchedulers() {
		p.getServer().getScheduler().scheduleSyncRepeatingTask(p, new Runnable() {
			public void run() {
				SaveAll();
			}
		}, 0L, 18000L);
	}
	
	// Save and Load
	public void SaveAll() {
		if (MPlayer.SaveAsYml(new File(this.getDataFolder(), "players.yml"))) {
			P.p.log(getLanguageReader().get("Log_PlayersSaved"));
		} else {
			P.p.log(Level.WARNING, getLanguageReader().get("Log_Error_PlayersSaved"));
		}
		
		broadcaster.save();
	}

	public void LoadAll() {
		// Players
		if (MPlayer.LoadAsYml(new File(this.getDataFolder(), "players.yml"))) {
			P.p.log(getLanguageReader().get("Log_PlayersLoaded"));
		} else {
			P.p.log(Level.WARNING, getLanguageReader().get("Log_Error_PlayersLoaded"));
		}
	}

	// Msg
	public void msg(CommandSender sender, String msg) {
		msg = replaceColors(msg);
		sender.sendMessage(msg);
	}

	public String replaceColors(String msg) {
		if (msg != null) {
			msg = msg.replace("&0", ChatColor.getByChar("0").toString());
			msg = msg.replace("&1", ChatColor.getByChar("1").toString());
			msg = msg.replace("&2", ChatColor.getByChar("2").toString());
			msg = msg.replace("&3", ChatColor.getByChar("3").toString());
			msg = msg.replace("&4", ChatColor.getByChar("4").toString());
			msg = msg.replace("&5", ChatColor.getByChar("5").toString());
			msg = msg.replace("&6", ChatColor.getByChar("6").toString());
			msg = msg.replace("&7", ChatColor.getByChar("7").toString());
			msg = msg.replace("&8", ChatColor.getByChar("8").toString());
			msg = msg.replace("&9", ChatColor.getByChar("9").toString());
			msg = msg.replace("&a", ChatColor.getByChar("a").toString());
			msg = msg.replace("&b", ChatColor.getByChar("b").toString());
			msg = msg.replace("&c", ChatColor.getByChar("c").toString());
			msg = msg.replace("&d", ChatColor.getByChar("d").toString());
			msg = msg.replace("&e", ChatColor.getByChar("e").toString());
			msg = msg.replace("&f", ChatColor.getByChar("f").toString());
			msg = msg.replace("&k", ChatColor.getByChar("k").toString());
			msg = msg.replace("&l", ChatColor.getByChar("l").toString());
			msg = msg.replace("&m", ChatColor.getByChar("m").toString());
			msg = msg.replace("&n", ChatColor.getByChar("n").toString());
			msg = msg.replace("&o", ChatColor.getByChar("o").toString());
			msg = msg.replace("&r", ChatColor.getByChar("r").toString());
		}

		return msg;
	}

	// Permissions
	private Permission permissionProvider = null;

	private Boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			this.permissionProvider = permissionProvider.getProvider();
		}
		return (this.permissionProvider != null);
	}

	public Permission getPermissionHandler() {
		return permissionProvider;
	}

	// Logger
	public void log(String msg) {
		log(Level.INFO, msg);
	}

	public void log(Level level, Object msg) {
		Logger.getLogger("Minecraft").log(level, "[" + this.getDescription().getFullName() + "] " + msg);
	}
}
