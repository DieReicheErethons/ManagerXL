package com.dre.managerxl;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LanguageReader {
	private Map<String, String> entries = new TreeMap<String, String>();
	private Map<String, String> defaults = new TreeMap<String, String>();

	private File file;
	private boolean changed;

	public LanguageReader(File file) {
		this.setDefaults();

		/* Load */
		this.file = file;

		FileConfiguration configFile = YamlConfiguration.loadConfiguration(file);

		Set<String> keySet = configFile.getKeys(false);
		for (String key : keySet) {
			entries.put(key, configFile.getString(key));
		}

		/* Check */
		this.check();
	}

	private void setDefaults() {

		/* Log */
		defaults.put("Log_PlayersSaved", "Spieler gespeichert");
		defaults.put("Log_PlayersLoaded", "Spieler geladen");
		defaults.put("Log_Error_NoConsoleCommand", "&6mxl &v1&4 kann man nicht als Konsole ausf�hren!");
		defaults.put("Log_Error_PlayersSaved", "Spieler konnten nicht gespeichert werden!");
		defaults.put("Log_Error_PlayersLoaded", "Spieler konnten nicht geladen werden!");

		/* Help */
		defaults.put("Help_Ban", "/ban <player> <reason> - Bannt einen Spieler");
		defaults.put("Help_TimeBan", "/timeban <player> <time> <reason> - Bannt einen Spieler f�r eine bestimmte Zeit");
		defaults.put("Help_UnBan", "/unban <player> - Entbannt einen Spieler");
		defaults.put("Help_Home", "/home [player] - Teleportiere dich zu deinem Home oder das eines Spielers");
		defaults.put("Help_SetHome", "/sethome - Setze dein eigenes Home");
		defaults.put("Help_Kick", "/kick <player> [reason] - Kicke einen Spieler");
		defaults.put("Help_KickAll", "/kickall [reason] - Kickt alle Spieler");
		defaults.put("Help_Mute", "/mute <player> - Muted einen Spieler");
		defaults.put("Help_GameMode", "/gamemode [player] [mode] - Setzt/Wechselt den GameMode eines Spielers");
		defaults.put("Help_Invisible", "/invisible [player] - Wechselt die Sichtbarkeit von dir oder einem Spieler");
		defaults.put("Help_AddNews", "/addnews [datum] [uhrzeit] [message] - Erstellt neue News");
		defaults.put("Help_AddDate", "/adddate [datum] [uhrzeit] [message] - Erstellt neuen Event/Termin");
		defaults.put("Help_AddBroadcast", "/addbroadcast [message] - Erstellt neuen Broadcast");
		defaults.put("Help_ReloadBroadcaster", "/reloadbroadcaster - L�d die Daten des Broadcasters neu");

		/* Player */
		defaults.put("Player_Kick_Ban", "&4Du wurdest gebannt. Grund: &6&v1");
		defaults.put("Player_Kick_TimeBan", "&4Du wurdest gebannt f�r &6&v2&4. Grund: &6&v1");
		defaults.put("Player_Muted", "&4Du bist gemuted!");
		defaults.put("Player_GameModeChanged", "&6Dein GameMode hat sich in &4&v1&6 ge�ndert!");
		defaults.put("Player_TargetGameModeChanged", "&4&v2's&6 GameMode hat sich in &4&v1&6 ge�ndert!");

		/* CMDs */
		defaults.put("Cmd_Ban_Success", "&6Spieler &4&v1&6 wurde erfolgreich gebannt!");
		defaults.put("Cmd_Ban_DefaultReason", "Du hast gegen die Regeln verstossen!");
		defaults.put("Cmd_TimeBan_Success", "&6Spieler &4&v1&6 wurde erfolgreich f�r &4&v2&6 gebannt!");
		defaults.put("Cmd_UnBan_Success", "&6Spieler &4&v1&6 wurde erfolgreich entbannt!");
		defaults.put("Cmd_SetHome_Success", "&6Home wurde gesetzt!");
		defaults.put("Cmd_Kick_NoReason", "&4Du wurdest von &6&v1&4 gekickt!");
		defaults.put("Cmd_Kick", "&4Du wurdest von &6&v1&4 gekickt! Grund: &6&v2");
		defaults.put("Cmd_KickAll_NoReason", "&4Alle Spieler wurden von &6&v1&4 gekickt!");
		defaults.put("Cmd_KickAll", "&4Alle Spieler wurden von &6&v1&4 gekickt! Grund: &6&v2");
		defaults.put("Cmd_Mute_UnMuted", "&6Spieler &4&v1&6 wurde entmuted!");
		defaults.put("Cmd_Mute_TargetUnMuted", "&6Du wurdest von &4&v1&6 entmuted!");
		defaults.put("Cmd_Mute_Muted", "&6Spieler &4&v1&6 wurde gemuted!");
		defaults.put("Cmd_Mute_TargeMuted", "&4Du wurdest von &6&v1&4 gemuted!");
		defaults.put("Cmd_Visible_Invisible", "&6Du bist nun unsichtbar!");
		defaults.put("Cmd_Visible_Visible", "&6Du bist wieder sichtbar!");
		defaults.put("Cmd_Visible_InvisibleOther", "Spieler &4&v1&6 ist nun unsichtbar!");
		defaults.put("Cmd_Visible_VisibleOther", "Spieler &4&v1&6 ist wieder sichtbar!");

		/* Errors */
		defaults.put("Error_NoPermissions", "&4Du hast keine Erlaubnis dies zu tun!");
		defaults.put("Error_PlayerNotExist", "&4Spieler &6&v1&4 existiert nicht!");
		defaults.put("Error_CmdBan_AlreadyBanned", "&4Spieler &6&v1&4 ist schon gebannt!");
		defaults.put("Error_CmdBan_NotBanned", "&4Spieler &6&v1&4 ist nicht gebannt!");
		defaults.put("Error_NoConsoleCommand", "&6/mxl &v1&4 kann man nicht als Konsole ausf�hren!");
		defaults.put("Error_NoPlayerCommand", "&6/mxl &v1&4 kann man nicht als Spieler ausf�hren!");
		defaults.put("Error_CmdNotExist1", "&4Befehl &6&v1&4 existiert nicht!");
		defaults.put("Error_CmdNotExist2", "&4Bitte gib &6/mxl help&4 f�r Hilfe ein!");
		defaults.put("Error_CmdHome_NoHome", "&4Du hast noch kein Home gesetzt. Benutze bitte zuerst &6/sethome&4!");
		defaults.put("Error_CmdHome_NoHome2", "&6&v1&4 hat noch kein Home gesetzt!");
		defaults.put("Error_CmdGameMode_NotExist", "&4GameMode &6&v1&4 existiert nicht!");
		defaults.put("Error_DateNotParsable", "Versuche ein Datum wie '10.02.2013 12:00'");

		/* Format */
		defaults.put("Format_Days", "Tage");
		defaults.put("Format_Hours", "Stunden");
		defaults.put("Format_Minutes", "Minuten");
		defaults.put("Format_Seconds", "Sekunden");
	}

	private void check() {
		for (String defaultEntry : defaults.keySet()) {
			if (!entries.containsKey(defaultEntry)) {
				entries.put(defaultEntry, defaults.get(defaultEntry));
				changed = true;
			}
		}
	}

	public void save() {
		if (changed) {
			/* Copy old File */
			File source = new File(file.getPath());
			String filePath = file.getPath();
			File temp = new File(filePath.substring(0, filePath.length() - 4) + "_old.yml");

			if (temp.exists())
				temp.delete();

			source.renameTo(temp);

			/* Save */
			FileConfiguration configFile = new YamlConfiguration();

			for (String key : entries.keySet()) {
				configFile.set(key, entries.get(key));
			}

			try {
				configFile.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String get(String key, String... args) {
		String entry = entries.get(key);

		if (entry != null) {
			int i = 0;
			for (String arg : args) {
				if (arg != null) {
					i++;
					entry = entry.replace("&v" + i, arg);
				}
			}
		} else {
			entry = "%placeholder%";
		}

		return entry;
	}
}
