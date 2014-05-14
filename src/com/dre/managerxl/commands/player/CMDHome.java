package com.dre.managerxl.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.dre.managerxl.MPlayer;
import com.dre.managerxl.P;
import com.dre.managerxl.commands.MCommand;

public class CMDHome extends MCommand {

	public CMDHome() {
		this.command = "home";
		this.parrent = null;
		this.help = P.p.getLanguageReader().get("Help_Home");
		this.permission = "mxl.cmd.player.home";

		this.isConsoleCommand = false;
		this.isPlayerCommand = true;

		this.init();
	}

	@Override
	public void onExecute(String[] args, CommandSender sender) {
		Player player = (Player) sender;

		if (args.length < 1) {
			MPlayer mPlayer = MPlayer.get(player.getUniqueId());

			if (mPlayer.getHome() != null) {
				player.teleport(mPlayer.getHome());
			} else {
				P.p.msg(player, P.p.getLanguageReader().get("Error_CmdHome_NoHome"));
			}
		} else {
			if (P.p.getPermissionHandler().has(sender, "mxl.cmd.player.homeother")) {
				MPlayer oPlayer = MPlayer.getFromName(args[0]);
				if (oPlayer != null) {
					if (oPlayer.getHome() != null) {
						player.teleport(oPlayer.getHome());
					} else {
						P.p.msg(player, P.p.getLanguageReader().get("Error_CmdHome_NoHome2", args[0]));
					}
				} else {
					P.p.msg(player, P.p.getLanguageReader().get("Error_PlayerNotExist", args[0]));
				}
			}
		}
	}
}
