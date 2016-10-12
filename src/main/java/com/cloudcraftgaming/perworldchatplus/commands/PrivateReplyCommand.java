package com.cloudcraftgaming.perworldchatplus.commands;

import com.cloudcraftgaming.perworldchatplus.Main;
import com.cloudcraftgaming.perworldchatplus.data.PlayerDataManager;
import com.cloudcraftgaming.perworldchatplus.privatemessage.PmHandler;
import com.cloudcraftgaming.perworldchatplus.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 10/12/16.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class PrivateReplyCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("reply") || command.getName().equalsIgnoreCase("r")) {
			if (Main.plugin.getConfig().getString("PM.Enabled").equalsIgnoreCase("True")) {
				if (sender instanceof Player) {
					if (sender.hasPermission("pwcp.pm")) {
						if (args.length < 1) {
							//Not enough args, need player to and message.
							sender.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.reply.args.few"));
						} else if (args.length >= 1) {
							if (PlayerDataManager.isMessagingPlayer((Player) sender)) {
								Player playerToSendTo = Bukkit.getPlayer(PlayerDataManager.getMessagingWith((Player) sender));
								if (playerToSendTo != null) {
									//Get message together.
									String msg = "";
									for (int i = 1; i < args.length; i++) {
										String arg = args[i] + " ";
										msg = msg + arg;
									}
									PmHandler.sendPrivateReply((Player) sender, msg);
									return true;
								} else {
									//No one to reply to.
									sender.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.reply.no one"));
								}
							} else {
								//No one to reply to.
								sender.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.reply.no one"));
							}
						}
					} else {
						sender.sendMessage(MessageManager.getPrefix() + MessageManager.getNoPermMessage());
					}
				} else {
					//Sender is not a player, not currently supported.
					sender.sendMessage(MessageManager.getPrefix() + MessageManager.getPlayerOnlyMessage());
				}
			}
		}
		return false;
	}
}