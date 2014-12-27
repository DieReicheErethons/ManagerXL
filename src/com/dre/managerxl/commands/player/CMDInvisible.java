package com.dre.managerxl.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.dre.managerxl.MPlayer;
import com.dre.managerxl.P;
import com.dre.managerxl.commands.MCommand;

public class CMDInvisible extends MCommand {

	public CMDInvisible() {
		this.command = "invisible";
		this.parrent = null;
		this.help = P.p.getLanguageReader().get("Help_Invisible");
		this.permission = "mxl.cmd.player.invisible";

		this.isConsoleCommand = true;
		this.isPlayerCommand = true;

		this.init();
	}

	@Override
	public void onExecute(String[] args, CommandSender sender) {
		if (args.length < 1) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				MPlayer mPlayer = MPlayer.getOrCreate(player.getUniqueId());

				if (mPlayer.isVisible()) {
					mPlayer.setVisible(false);

					P.p.msg(sender, P.p.getLanguageReader().get("Cmd_Visible_Invisible"));
				} else {
					mPlayer.setVisible(true);

					P.p.msg(sender, P.p.getLanguageReader().get("Cmd_Visible_Visible"));
				}
			} else {
				P.p.msg(sender, P.p.getLanguageReader().get("Error_NoConsoleCommand"));
			}
		} else {
			if (P.p.getPermissionHandler().has(sender, "mxl.cmd.player.invisibleother")) {
				MPlayer oPlayer = MPlayer.getFromName(args[0]);

				if (oPlayer != null) {
					if (oPlayer.isVisible()) {
						oPlayer.setVisible(false);

						P.p.msg(sender, P.p.getLanguageReader().get("Cmd_Visible_InvisibleOther", args[0]));
						if (oPlayer.getPlayer() != null) {
							P.p.msg(oPlayer.getPlayer(), P.p.getLanguageReader().get("Cmd_Visible_Invisible", args[0]));
						}
					} else {
						oPlayer.setVisible(true);

						P.p.msg(sender, P.p.getLanguageReader().get("Cmd_Visible_VisibleOther", args[0]));
						if (oPlayer.getPlayer() != null) {
							P.p.msg(oPlayer.getPlayer(), P.p.getLanguageReader().get("Cmd_Visible_Visible"));
						}
					}
				} else {
					P.p.msg(sender, P.p.getLanguageReader().get("Error_PlayerNotExist", args[0]));
				}
			} else {
				P.p.msg(sender, P.p.getLanguageReader().get("Error_NoPermissions", args[0]));
			}
		}
	}
}
