package com.dre.managerxl.commands.managing;

import org.bukkit.command.CommandSender;

import com.dre.managerxl.P;
import com.dre.managerxl.commands.MCommand;
import com.dre.managerxl.util.MUtility;

public class MotDSet extends MCommand{
	public MotDSet(){
		this.command = "motdset";
		this.parrent = null;
		this.help = P.p.getLanguageReader().get("Help_MotDSet");
		this.permission = "mxl.cmd.managing.motdset";
		
		this.isConsoleCommand = true;
		this.isPlayerCommand = true;
		
		this.init();
	}
	
	@Override
	public void onExecute(String[] args, CommandSender sender) {
		P.p.setMotD(P.p.replaceColors(MUtility.parseMessage(args, 0)));
		P.p.msg(sender, P.p.getLanguageReader().get("Cmd_MotDSet_Success"));
	}
}
