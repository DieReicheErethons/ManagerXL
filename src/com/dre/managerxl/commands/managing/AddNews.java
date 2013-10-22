package com.dre.managerxl.commands.managing;

import org.bukkit.command.CommandSender;

import com.dre.managerxl.P;
import com.dre.managerxl.broadcaster.BroadcasterMsg;
import com.dre.managerxl.commands.MCommand;
import com.dre.managerxl.util.MUtility;

public class AddNews extends MCommand {
	public AddNews() {
		this.command = "addnews";
		this.parrent = null;
		this.help = P.p.getLanguageReader().get("Help_AddNews");
		this.permission = "mxl.cmd.managing.addnews";

		this.isConsoleCommand = true;
		this.isPlayerCommand = true;

		this.init();
	}

	@Override
	public void onExecute(String[] args, CommandSender sender) {
		if (args.length > 2) {
			String type = "News";
			String msg = MUtility.parseMessage(args, 2);
			long endtime = MUtility.getStringDateToLong(args[0], args[1]);

			if (endtime == 0) {
				sender.sendMessage(P.p.getLanguageReader().get("Error_DateNotParsable"));
				return;
			}

			new BroadcasterMsg(type, msg, endtime);
		} else {
			sender.sendMessage(help);
		}

	}
}
