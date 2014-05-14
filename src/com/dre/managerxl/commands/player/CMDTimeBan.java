package com.dre.managerxl.commands.player;

import org.bukkit.command.CommandSender;

import com.dre.managerxl.MPlayer;
import com.dre.managerxl.P;
import com.dre.managerxl.commands.MCommand;
import com.dre.managerxl.util.MUtility;

public class CMDTimeBan extends MCommand {

	public CMDTimeBan() {
		this.command = "timeban";
		this.parrent = null;
		this.help = P.p.getLanguageReader().get("Help_TimeBan");
		this.permission = "mxl.cmd.player.timeban";

		this.isConsoleCommand = true;
		this.isPlayerCommand = true;

		this.init();
	}

	@Override
	public void onExecute(String[] args, CommandSender sender) {
		if (args.length > 1) {
			MPlayer player = MPlayer.getFromName(args[0]);

			if (!player.isBanned()) {
				String message = MUtility.parseMessage(args, 2);

				if (message.isEmpty()) {
					message = P.p.getLanguageReader().get("Cmd_Ban_DefaultReason");
				}

				long time = MUtility.getStringTimeToLong(args[1]);

				player.setBannedTime(System.currentTimeMillis() + time);
				player.setBannedReason(message);
				player.setBanned(true);

				P.p.msg(sender, P.p.getLanguageReader().get("Cmd_TimeBan_Success", args[0], MUtility.getLongTimeToString(time)));
			} else {
				P.p.msg(sender, P.p.getLanguageReader().get("Error_CmdBan_AlreadyBanned", args[0]));
			}
		} else {
			P.p.msg(sender, this.help);
		}
	}

}
