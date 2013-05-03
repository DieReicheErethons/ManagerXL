package com.dre.managerxl.commands.player;

import org.bukkit.command.CommandSender;

import com.dre.managerxl.MPlayer;
import com.dre.managerxl.P;
import com.dre.managerxl.commands.MCommand;
import com.dre.managerxl.util.MUtility;

public class GameMode extends MCommand {

	public GameMode() {
		this.command = "gamemode";
		this.parrent = null;
		this.help = P.p.getLanguageReader().get("Help_GameMode");
		this.permission = "mxl.cmd.player.gamemode";

		this.isConsoleCommand = false;
		this.isPlayerCommand = true;

		this.init();
	}

	@Override
	public void onExecute(String[] args, CommandSender sender) {
		MPlayer player = MPlayer.getOrCreate(sender.getName());

		if (player.getPlayer() != null) {
			if (args.length < 1) {
				if (player.getGameMode() == 1) {
					player.setGameMode(0);
				} else {
					player.setGameMode(1);
				}
			} else {
				MPlayer targetPlayer = MPlayer.get(args[0]);
				if (targetPlayer != null) {
					if (args.length < 2) {
						if (targetPlayer.getGameMode() == 1) {
							targetPlayer.setGameMode(0);
						} else {
							targetPlayer.setGameMode(1);
						}

						if (targetPlayer != player) {
							P.p.msg(sender, P.p.getLanguageReader().get("Player_TargetGameModeChanged", org.bukkit.GameMode.getByValue(targetPlayer.getGameMode()).name(), targetPlayer.getName()));
						}
					} else {
						if (targetPlayer.setGameMode(MUtility.parseInt(args[1]))) {
							if (targetPlayer != player) {
								P.p.msg(sender, P.p.getLanguageReader().get("Player_TargetGameModeChanged", org.bukkit.GameMode.getByValue(targetPlayer.getGameMode()).name(), targetPlayer.getName()));
							}
						} else {
							P.p.msg(sender, P.p.getLanguageReader().get("Error_CmdGameMode_NotExist", args[1]));
						}
					}
				} else {
					P.p.msg(sender, P.p.getLanguageReader().get("Error_PlayerNotExist", args[0]));
				}
			}
		}
	}

}
