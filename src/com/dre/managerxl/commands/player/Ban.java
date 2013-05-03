package com.dre.managerxl.commands.player;

import org.bukkit.command.CommandSender;

import com.dre.managerxl.MPlayer;
import com.dre.managerxl.P;
import com.dre.managerxl.commands.MCommand;
import com.dre.managerxl.util.MUtility;

public class Ban extends MCommand {

	public Ban() {
		this.command = "ban";
		this.parrent = null;
		this.help = P.p.getLanguageReader().get("Help_Ban");
		this.permission = "mxl.cmd.player.ban";

		this.isConsoleCommand = true;
		this.isPlayerCommand = true;

		this.init();
	}

	@Override
	public void onExecute(String[] args, CommandSender sender) {
		if (args.length > 0) {
			MPlayer player = MPlayer.getOrCreate(args[0]);

			if (!player.isBanned()) {
				String message = MUtility.parseMessage(args, 1);

				if (message.isEmpty()) {
					message = P.p.getLanguageReader().get("Cmd_Ban_DefaultReason");
				}

				player.setBannedReason(message);
				player.setBannedTime(0);
				player.setBanned(true);

				P.p.msg(sender, P.p.getLanguageReader().get("Cmd_Ban_Success", player.getName()));
			} else {
				P.p.msg(sender, P.p.getLanguageReader().get("Error_CmdBan_AlreadyBanned", player.getName()));
			}
		} else {
			P.p.msg(sender, this.help);
		}
	}
}
