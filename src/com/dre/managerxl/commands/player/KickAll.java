package com.dre.managerxl.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.dre.managerxl.P;
import com.dre.managerxl.commands.MCommand;
import com.dre.managerxl.util.MUtility;

public class KickAll extends MCommand{
	
	public KickAll(){
		this.command = "kickall";
		this.parrent = null;
		this.help = P.p.getLanguageReader().get("Help_KickAll");
		this.permission = "mxl.cmd.player.kickall";
		
		this.isConsoleCommand = true;
		this.isPlayerCommand = true;
		
		this.init();
	}
	
	@Override
	public void onExecute(String[] args, CommandSender sender) {
		for(Player player : Bukkit.getOnlinePlayers()){
			if(sender instanceof Player){ //Don't kick yourself
				if((Player) sender == player){
					continue;
				}
			}
			
			if(args.length > 0){
				player.kickPlayer(P.p.replaceColors(P.p.getLanguageReader().get("Cmd_KickAll", sender.getName(), MUtility.parseMessage(args, 0))));
			} else {
				player.kickPlayer(P.p.replaceColors(P.p.getLanguageReader().get("Cmd_KickAll_NoReason", sender.getName())));
			}
		}
	}
}
