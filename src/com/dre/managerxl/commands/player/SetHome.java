package com.dre.managerxl.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.dre.managerxl.MPlayer;
import com.dre.managerxl.P;
import com.dre.managerxl.commands.MCommand;

public class SetHome extends MCommand{

	public SetHome(){
		this.command = "sethome";
		this.parrent = null;
		this.help = P.p.getLanguageReader().get("Help_SetHome");
		this.permission = "mxl.cmd.player.sethome";
		
		this.isConsoleCommand = false;
		this.isPlayerCommand = true;
		
		this.init();
	}
	
	@Override
	public void onExecute(String[] args, CommandSender sender) {
		Player player = (Player) sender;
		MPlayer mPlayer = MPlayer.getOrCreate(sender.getName());
		
		mPlayer.setHome(player.getLocation());
		P.p.msg(player, P.p.getLanguageReader().get("Cmd_SetHome_Success"));
	}

}