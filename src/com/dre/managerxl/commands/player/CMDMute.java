package com.dre.managerxl.commands.player;

import org.bukkit.command.CommandSender;

import com.dre.managerxl.MPlayer;
import com.dre.managerxl.P;
import com.dre.managerxl.commands.MCommand;

public class CMDMute extends MCommand {

	public CMDMute() {
		this.command = "mute";
		this.parrent = null;
		this.help = P.p.getLanguageReader().get("Help_Mute");
		this.permission = "mxl.cmd.player.mute";

		this.isConsoleCommand = true;
		this.isPlayerCommand = true;

		this.init();
	}

	@Override
	public void onExecute(String[] args, CommandSender sender) {
		if (args.length > 0) {
			MPlayer mPlayer = MPlayer.getFromName(args[0]);

			if (mPlayer != null) {
				if (mPlayer.isMuted()) {
					mPlayer.setMuted(false);
					P.p.msg(sender, P.p.getLanguageReader().get("Cmd_Mute_UnMuted", args[0]));

					if (mPlayer.isOnline()) {
						P.p.msg(mPlayer.getPlayer(), P.p.getLanguageReader().get("Cmd_Mute_TargetUnMuted", sender.getName()));
					}
				} else {
					mPlayer.setMuted(true);
					P.p.msg(sender, P.p.getLanguageReader().get("Cmd_Mute_Muted", args[0]));

					if (mPlayer.isOnline()) {
						P.p.msg(mPlayer.getPlayer(), P.p.getLanguageReader().get("Cmd_Mute_TargeMuted", sender.getName()));
					}
				}

			} else {
				P.p.msg(sender, P.p.getLanguageReader().get("Error_PlayerNotExist", args[0]));
			}

		} else {
			P.p.msg(sender, this.help);
		}
	}
}
