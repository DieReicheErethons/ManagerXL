package com.dre.managerxl.commands.player;

import org.bukkit.command.CommandSender;

import com.dre.managerxl.MPlayer;
import com.dre.managerxl.P;
import com.dre.managerxl.commands.MCommand;

public class Unban extends MCommand{
	
	public Unban(){
		this.command = "unban";
		this.parrent = null;
		this.help = P.p.getLanguageReader().get("Help_Unban");
		this.permission = "mxl.cmd.player.unban";
		
		this.isConsoleCommand = true;
		this.isPlayerCommand = true;
		
		this.init();
	}
	
	@Override
	public void onExecute(String[] args, CommandSender sender) {
		if(args.length > 0){
			MPlayer player = MPlayer.getOrCreate(args[0]);
			
			if(player.isBanned()){
				player.setBanned(false);
				
				P.p.msg(sender, P.p.getLanguageReader().get("Cmd_UnBan_Success", player.getName()));
			} else {
				P.p.msg(sender, P.p.getLanguageReader().get("Error_CmdBan_NotBanned", player.getName()));
			}
		} else {
			P.p.msg(sender, this.help);
		}
	}
}
