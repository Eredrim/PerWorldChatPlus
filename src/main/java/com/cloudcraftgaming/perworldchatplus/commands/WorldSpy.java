package com.cloudcraftgaming.perworldchatplus.commands;

import com.cloudcraftgaming.perworldchatplus.Main;
import com.cloudcraftgaming.perworldchatplus.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * Created by Nova Fox on 2/1/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 *
 * This class handles worldSpy related functions because the base command class would be too messy with it there.
 * This is really only for that command and probably shouldn't be used for anything else.
 */
public class WorldSpy {
    protected static void worldSpy(Player player, String type) {
        UUID uuid = player.getUniqueId();
        if (type.equalsIgnoreCase("on")) {
            if (Main.plugin.data.getString("Players." + uuid + ".WorldSpy").equalsIgnoreCase("False")) {
                Main.plugin.data.set("Players." + uuid + ".WorldSpy", true);
                Main.plugin.saveCustomConfig(Main.plugin.data, Main.plugin.dataFile);
                String msgOr = MessageManager.getMessageYml().getString("Command.WorldSpy.TurnOn");
                String worldListOr = "";
                if (Main.plugin.worldSpyYml.contains("Players." + uuid)) {
                    worldListOr = Main.plugin.worldSpyYml.getString("Players." + uuid);
                }
                String msg = msgOr.replaceAll("%worlds%", worldListOr.replace("[", "").replace("]", ""));
                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
            } else {
                String msg = MessageManager.getMessageYml().getString("Command.WorldSpy.AlreadyOn");
                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
            }
        } else if (type.equalsIgnoreCase("off")) {
            if (Main.plugin.data.getString("Players." + uuid + ".WorldSpy").equalsIgnoreCase("True")) {
                Main.plugin.data.set("Players." + uuid + ".WorldSpy", false);
                Main.plugin.saveCustomConfig(Main.plugin.data, Main.plugin.dataFile);
                String msg = MessageManager.getMessageYml().getString("Command.WorldSpy.TurnOff");
                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
            } else {
                String msg = MessageManager.getMessageYml().getString("Command.WorldSpy.AlreadyOff");
                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
            }
        } else {
            List<String> list = Main.plugin.worldSpyYml.getStringList("Players." + uuid);
            if (player.hasPermission("pwcp.worldspy." + type)) {
                if (list.contains(type.toLowerCase())) {
                    list.remove(type.toLowerCase());
                    Main.plugin.worldSpyYml.set("Players." + uuid, list);
                    Main.plugin.saveCustomConfig(Main.plugin.worldSpyYml, Main.plugin.worldSpyFile);
                    String msgOr = MessageManager.getMessageYml().getString("Command.WorldSpy.RemoveWorld");
                    String msg = msgOr.replaceAll("%world%", type);
                    player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                } else {
                    list.add(type.toLowerCase());
                    Main.plugin.worldSpyYml.set("Players." + uuid, list);
                    Main.plugin.saveCustomConfig(Main.plugin.worldSpyYml, Main.plugin.worldSpyFile);
                    String msgOr = MessageManager.getMessageYml().getString("Command.WorldSpy.AddWorld");
                    String msg = msgOr.replaceAll("%world%", type);
                    player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                }
            } else {
                String msg = MessageManager.getMessageYml().getString("Command.WorldSpy.NoPerm");
                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
            }
        }
    }
}