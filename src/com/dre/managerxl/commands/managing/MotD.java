package com.dre.managerxl.commands.managing;

import org.bukkit.command.CommandSender;

import com.dre.managerxl.P;
import com.dre.managerxl.commands.MCommand;

public class MotD extends MCommand{
	
	public MotD(){
		this.command = "motd";
		this.parrent = null;
		this.help = P.p.getLanguageReader().get("Help_MotD");
		this.permission = "mxl.cmd.managing.motd";
		
		this.isConsoleCommand = true;
		this.isPlayerCommand = true;
		
		this.init();
	}
	
	@Override
	public void onExecute(String[] args, CommandSender sender) {
		P.p.msg(sender, P.p.getMotD());
	}
}
