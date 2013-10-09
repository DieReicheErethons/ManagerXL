package com.dre.managerxl.commands.managing;

import org.bukkit.command.CommandSender;

import com.dre.managerxl.P;
import com.dre.managerxl.commands.MCommand;

public class ReloadBroadcaster extends MCommand {
	public ReloadBroadcaster() {
		this.command = "reloadbroadcaster";
		this.parrent = null;
		this.help = P.p.getLanguageReader().get("Help_ReloadBroadcaster");
		this.permission = "mxl.cmd.managing.reloadbroadcaster";

		this.isConsoleCommand = true;
		this.isPlayerCommand = true;

		this.init();
	}

	@Override
	public void onExecute(String[] args, CommandSender sender) {
		P.p.getBroadcast().loadConfig();
		P.p.getBroadcast().initializeBroadcaster();
		P.p.getBroadcast().loadMessages();
		P.p.getBroadcast().loadData();
		sender.sendMessage("Done!");
	}

}
