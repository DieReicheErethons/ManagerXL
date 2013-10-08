package com.dre.managerxl.commands.managing;

import org.bukkit.command.CommandSender;

import com.dre.managerxl.P;
import com.dre.managerxl.commands.MCommand;

public class AddBroadcast extends MCommand {
	public AddBroadcast() {
		this.command = "addbroadcast";
		this.parrent = null;
		this.help = P.p.getLanguageReader().get("Help_AddBroadcast");
		this.permission = "mxl.cmd.managing.addbroadcast";

		this.isConsoleCommand = true;
		this.isPlayerCommand = true;

		this.init();
	}

	@Override
	public void onExecute(String[] args, CommandSender sender) {
		
	}
}
