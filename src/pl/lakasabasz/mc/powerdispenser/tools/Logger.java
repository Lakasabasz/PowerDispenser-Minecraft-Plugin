package pl.lakasabasz.mc.powerdispenser.tools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class Logger {
	
	private static String prefix;
	
	static {
		prefix = "[PowerDispenser]";
	}

	public static void sendDebug(String msg) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + Logger.prefix + "[DEBUG] " + msg);
	}
	
	public static void sendDebug(String msg, CommandSender p) {
		p.sendMessage(ChatColor.AQUA + Logger.prefix + "[DEBUG] " + msg);
	}
	
	public static void sendInfo(String msg) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + Logger.prefix + "[INFO] " + msg);
	}
	
	public static void sendInfo(String msg, CommandSender p) {
		p.sendMessage(ChatColor.DARK_GREEN + Logger.prefix + "[INFO] " + msg);
	}
	
	public static void sendWarrning(String msg) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + Logger.prefix + "[WARRNING] " + msg);
	}
	
	public static void sendWarrning(String msg, CommandSender sender) {
		sender.sendMessage(ChatColor.GOLD + Logger.prefix + "[WARRNING] " + msg);
	}
	
	public static void sendError(String msg) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + Logger.prefix + "[ERROR] " + msg);
	}
	
	public static void sendError(String msg, CommandSender p) {
		p.sendMessage(ChatColor.DARK_RED + Logger.prefix + "[ERROR] " + msg);
	}

}
