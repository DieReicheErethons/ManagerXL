package com.dre.managerxl.commands;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.dre.managerxl.P;
import com.dre.managerxl.commands.managing.CMDMotD;
import com.dre.managerxl.commands.managing.CMDMotDSet;
import com.dre.managerxl.commands.player.CMDBan;
import com.dre.managerxl.commands.player.CMDGameMode;
import com.dre.managerxl.commands.player.CMDHome;
import com.dre.managerxl.commands.player.CMDInvisible;
import com.dre.managerxl.commands.player.CMDKick;
import com.dre.managerxl.commands.player.CMDKickAll;
import com.dre.managerxl.commands.player.CMDMute;
import com.dre.managerxl.commands.player.CMDSetHome;
import com.dre.managerxl.commands.player.CMDTimeBan;
import com.dre.managerxl.commands.player.CMDUnban;

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

	public String getCommand() {
		return command;
	}

	public MCommand getParrent() {
		return parrent;
	}

	public String getHelp() {
		return help;
	}

	public boolean isConsoleCommand() {
		return isConsoleCommand;
	}

	public boolean isPlayerCommand() {
		return isPlayerCommand;
	}

	public boolean playerHasPermissions(Player player) {
		if (P.p.getPermissionHandler().playerHas(player, permission) || player.isOp()) {
			return true;
		}

		return false;
	}

	public void init() {
		commands.add(this);
		P.p.getCommand(this.getCommand()).setExecutor(commandListener);
	}

	public void displayHelp(CommandSender sender) {
		P.p.msg(sender, getHelp());
	}

	// Static
	public static void initCommands() {

		// Managing commands
		new CMDMotD();
		new CMDMotDSet();

		// Player commands
		new CMDBan();
		new CMDUnban();
		new CMDTimeBan();
		new CMDHome();
		new CMDSetHome();
		new CMDKick();
		new CMDKickAll();
		new CMDMute();
		new CMDGameMode();
		new CMDInvisible();
	}

	public static Set<MCommand> get() {
		return commands;
	}

	public static MCommand get(String command) {
		for (MCommand mCommand : commands) {
			if (mCommand.getCommand().equals(command)) {
				return mCommand;
			}
		}

		return null;
	}
}