package com.nerdswbnerds.easywarp.commands;

import com.nerdswbnerds.easywarp.Utils;
import com.nerdswbnerds.easywarp.managers.WarpManager;
import com.nerdswbnerds.easywarp.objects.Warp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		if (cmd.getName().equalsIgnoreCase("warp")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED + "Error: /" + label + " <warp> || /" + label + " <player> <warp>");
				sender.sendMessage(ChatColor.RED + "Help: To list warps, use /listwarps");
				return true;
			}

			if (args.length >= 1) {
				Player target = null;

				String warpName = args[0];

				if (args.length > 1) {
					if (!sender.hasPermission("easywarp.command.warpother")) {
						sender.sendMessage(ChatColor.RED + "Error: You need the 'easywarp.command.warpother' permission node to do this.");
						return true;
					}

					target = Bukkit.getPlayerExact(args[0]);
					warpName = args[1];

					if (target == null || !target.isOnline()) {
						sender.sendMessage(ChatColor.RED + "Error: Player not found.");
						return true;
					}
				} else {
					if (!(sender instanceof Player)) {
						sender.sendMessage(ChatColor.RED + "Error: You must be a player to do this.");
						return true;
					}

					if (!sender.hasPermission("easywarp.command.warp")) {
						sender.sendMessage(ChatColor.RED + "Error: You need the 'easywarp.command.warp' permission node to do this.");
						return true;
					}

					target = (Player) sender;
				}

				if (!WarpManager.isWarp(warpName)) {
					sender.sendMessage(ChatColor.RED + "Error: Warp not found. /listwarps");
					return true;
				}

				Warp to = WarpManager.getWarp(warpName);

				if ((!(sender instanceof Player) || ((Player) sender) != target)) {
					sender.sendMessage(Utils.getPrefix() + ChatColor.RED + target.getName() + ChatColor.GREEN + " has been warped to " + ChatColor.RED + to.getName() + ".");

					Utils.warpOther(target, to);
				} else {
					Utils.warpSelf(target, to);
				}
			}

			return true;
		}

		return false;
	}
}