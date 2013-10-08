package com.dre.managerxl.commands.managing;

import org.bukkit.command.CommandSender;

import com.dre.managerxl.P;
import com.dre.managerxl.commands.MCommand;

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
		
	}
}
