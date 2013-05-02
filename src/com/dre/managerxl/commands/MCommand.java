package com.dre.managerxl.commands;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.dre.managerxl.P;
import com.dre.managerxl.commands.managing.MotD;
import com.dre.managerxl.commands.managing.MotDSet;
import com.dre.managerxl.commands.player.Ban;
import com.dre.managerxl.commands.player.GameMode;
import com.dre.managerxl.commands.player.Home;
import com.dre.managerxl.commands.player.Invisible;
import com.dre.managerxl.commands.player.Kick;
import com.dre.managerxl.commands.player.KickAll;
import com.dre.managerxl.commands.player.Mute;
import com.dre.managerxl.commands.player.SetHome;
import com.dre.managerxl.commands.player.TimeBan;
import com.dre.managerxl.commands.player.Unban;

public abstract class MCommand {
	private static MCommandExecutor commandListener = new MCommandExecutor();
	private static Set<MCommand> commands = new HashSet<MCommand>();
	
	public static MCommand cmdHelp;
	
	protected String command;
	protected MCommand parrent;
	protected String help;
	protected String permission;
	
	protected boolean isConsoleCommand;
	protected boolean isPlayerCommand;
	
	public abstract void onExecute(String[] args, CommandSender sender);

	public String getCommand(){
		return command;
	}
	
	public MCommand getParrent(){
		return parrent;
	}
	
	public String getHelp(){
		return help;
	}
	
	public boolean isConsoleCommand(){
		return isConsoleCommand;
	}
	
	public boolean isPlayerCommand(){
		return isPlayerCommand;
	}
	
	public boolean playerHasPermissions(Player player){
		if(P.p.getPermissionHandler().playerHas(player, permission) || player.isOp()){
			return true;
		}
		
		return false;
	}
	
	public void init(){
		commands.add(this);
		P.p.getCommand(this.getCommand()).setExecutor(commandListener);
	}
	
	public void displayHelp(CommandSender sender){
		P.p.msg(sender, getHelp());
	}
	
	//Static
	public static void initCommands(){
		
		//Managing commands
		new MotD();
		new MotDSet();
		
		//Player commands
		new Ban();
		new Unban();
		new TimeBan();
		new Home();
		new SetHome();
		new Kick();
		new KickAll();
		new Mute();
		new GameMode();
		new Invisible();
	}
	
	public static Set<MCommand> get(){
		return commands;
	}
	
	public static MCommand get(String command){
		for(MCommand mCommand : commands){
			if(mCommand.getCommand().equals(command)){
				return mCommand;
			}
		}
		
		return null;
	}
}