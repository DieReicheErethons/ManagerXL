package com.dre.managerxl.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.dre.managerxl.P;

public class MCommandExecutor implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {

        MCommand mCommand = MCommand.get(command.getName());
        
        if(mCommand != null){
    		if(sender instanceof ConsoleCommandSender){
    			if(!mCommand.isConsoleCommand()){
    				P.p.msg(sender, P.p.getLanguageReader().get("Log_Error_NoConsoleCommand", mCommand.getCommand()));
    				return false;
    			}
    		}
    		
    		if(sender instanceof Player){
    			Player player = (Player) sender;
    			if(!mCommand.isPlayerCommand){
    				P.p.msg(player, P.p.getLanguageReader().get("Error_NoPlayerCommand", mCommand.command));
    				return false;
    			} else {
    				if(!mCommand.playerHasPermissions(player)){
                		P.p.msg(player, P.p.getLanguageReader().get("Error_NoPermissions"));
    					return false;
    				} 
    			}
    		}
    		
    		mCommand.onExecute(args,sender);
        } else {
        	P.p.msg(sender, P.p.getLanguageReader().get("Error_CmdNotExist1",command.getName()));
    		P.p.msg(sender, P.p.getLanguageReader().get("Error_CmdNotExist2"));
        }
		
		return false;
	}

}
