package com.dre.managerxl.commands.player;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;

import com.dre.managerxl.MPlayer;
import com.dre.managerxl.P;
import com.dre.managerxl.commands.MCommand;
import com.dre.managerxl.util.MUtility;

public class CMDGameMode extends MCommand {

	public CMDGameMode() {
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
				if (player.getGameMode() == GameMode.CREATIVE) {
					player.setGameMode(GameMode.SURVIVAL);
				} else {
					player.setGameMode(GameMode.CREATIVE);
				}
			} else {
				MPlayer targetPlayer = MPlayer.get(args[0]);
				if (targetPlayer != null) {
					if (args.length < 2) {
						if (targetPlayer.getGameMode() == GameMode.CREATIVE) {
							targetPlayer.setGameMode(GameMode.SURVIVAL);
						} else {
							targetPlayer.setGameMode(GameMode.CREATIVE);
						}

						if (targetPlayer != player) {
							P.p.msg(sender, P.p.getLanguageReader().get("Player_TargetGameModeChanged", targetPlayer.getGameMode().name(), targetPlayer.getName()));
						}
					} else {
						int gm = -1;
						String gmString = "";
						GameMode gameMode = GameMode.SURVIVAL;
						
						if(args[1].length() == 1){
							gm = MUtility.parseInt(args[1]);
						} else {
							gmString = args[1];
						}
						
						if(gm == 0 || gmString.equalsIgnoreCase("survival")) 
							gameMode = GameMode.SURVIVAL;
						
						if(gm == 1 || gmString.equalsIgnoreCase("creative")) 
							gameMode = GameMode.CREATIVE;
						
						if(gm == 2 || gmString.equalsIgnoreCase("adventure")) 
							gameMode = GameMode.ADVENTURE;
						
						if (targetPlayer.setGameMode(gameMode)) {
							if (targetPlayer != player) {
								P.p.msg(sender, P.p.getLanguageReader().get("Player_TargetGameModeChanged", targetPlayer.getGameMode().name(), targetPlayer.getName()));
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
