package com.dre.managerxl.commands.managing;

import org.bukkit.command.CommandSender;

import com.dre.managerxl.P;
import com.dre.managerxl.commands.MCommand;

public class AddDate extends MCommand {
	public AddDate() {
		this.command = "adddate";
		this.parrent = null;
		this.help = P.p.getLanguageReader().get("Help_AddDate");
		this.permission = "mxl.cmd.managing.adddate";

		this.isConsoleCommand = true;
		this.isPlayerCommand = true;

		this.init();
	}

	@Override
	public void onExecute(String[] args, CommandSender sender) {
		
	}
}
