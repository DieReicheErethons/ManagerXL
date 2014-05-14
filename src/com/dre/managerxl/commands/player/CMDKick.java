package com.dre.managerxl.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.dre.managerxl.MPlayer;
import com.dre.managerxl.P;
import com.dre.managerxl.commands.MCommand;
import com.dre.managerxl.util.MUtility;

public class CMDKick extends MCommand {

	public CMDKick() {
		this.command = "kick";
		this.parrent = null;
		this.help = P.p.getLanguageReader().get("Help_Kick");
		this.permission = "mxl.cmd.player.kick";

		this.isConsoleCommand = true;
		this.isPlayerCommand = true;

		this.init();
	}

	@Override
	public void onExecute(String[] args, CommandSender sender) {
		if (args.length > 0) {
			Player player = MPlayer.getFromName(args[0]).getPlayer();
			if (player != null) {
				if (args.length > 1) {
					player.kickPlayer(P.p.replaceColors(P.p.getLanguageReader().get("Cmd_Kick", sender.getName(), MUtility.parseMessage(args, 1))));
				} else {
					player.kickPlayer(P.p.replaceColors(P.p.getLanguageReader().get("Cmd_Kick_NoReason", sender.getName())));
				}
			}
		} else {
			P.p.msg(sender, this.help);
		}
	}
}
