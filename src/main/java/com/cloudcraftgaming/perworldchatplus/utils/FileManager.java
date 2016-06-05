package com.cloudcraftgaming.perworldchatplus.utils;

import com.cloudcraftgaming.perworldchatplus.Main;

import java.io.File;
import java.util.List;

/**
 * Created by: NovaFox161
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 *
 * This class handles several file related functions and methods.
 */
public class FileManager {
	/**
	 * Creates the default config.yml file (Really only used on startup by PerWorldChatPlus).
	 */
	public static void createConfig() {
		File file = new File(Main.plugin.getDataFolder() + File.separator + "config.yml");
		if (!(file.exists())) {
			Main.plugin.getLogger().info("Generating config.yml in folder /PerWorldChatPlus/");
			
			Main.plugin.getConfig().addDefault("DO NOT DELETE", "PerWorldChatPlus is developed and managed by Shades161");
			Main.plugin.getConfig().addDefault("Config Version", Main.plugin.conVersion);
			Main.plugin.getConfig().addDefault("Check for Updates", true);
			Main.plugin.getConfig().addDefault("Lang", "En");
			Main.plugin.getConfig().addDefault("Announce Dev Join", true);
			Main.plugin.getConfig().addDefault("Prefix", "&5[PerWorldChat]");

			Main.plugin.getConfig().addDefault("Global.Prefix", "&4[Global]");
			Main.plugin.getConfig().addDefault("Global.Override", "!wc");
			Main.plugin.getConfig().addDefault("Global.Always Global", false);
			Main.plugin.getConfig().addDefault("Global.TimedGlobal.Allow", true);
			Main.plugin.getConfig().addDefault("Global.TimedGlobal.Announce", true);
			Main.plugin.getConfig().addDefault("Global.TimedGlobal.DefaultTime", 5);
			Main.plugin.getConfig().addDefault("Global.TimedGlobal.On", false);
			Main.plugin.getConfig().addDefault("Alert.Mention.OnName", true);
			Main.plugin.getConfig().addDefault("Alert.Mention.RequirePermission", false);
			Main.plugin.getConfig().addDefault("Alert.Mention.RequireAtSymbol", false);
			Main.plugin.getConfig().addDefault("Alert.Mention.SendNotice", false);

			List<String> sharesList = Main.plugin.getConfig().getStringList("SharesList");
			sharesList.add("world");
			Main.plugin.getConfig().set("SharesList", sharesList);

			Main.plugin.getConfig().addDefault("Chat.Swear.Block", true);
			Main.plugin.getConfig().addDefault("Chat.Swear.Replace", "***");
			Main.plugin.getConfig().addDefault("Chat.Swear.Kick", false);
			List<String> blockedSwears = Main.plugin.getConfig().getStringList("Chat.Swear.Blocked");
			blockedSwears.add("Crap");
			blockedSwears.add("Shit");
			Main.plugin.getConfig().set("Chat.Swear.Blocked", blockedSwears);


			Main.plugin.getConfig().options().copyDefaults(true);
			Main.plugin.saveConfig();
			
			Main.plugin.getConfig().options().copyDefaults(true);
			Main.plugin.saveConfig();
		}
	}

	/**
	 * This is a massive utility function that generates the default files used by PerWorldChatPlus (utility only, used on startup).
	 */
	public static void createFiles() {
		if (!(Main.plugin.sharesFile.exists())) {
			Main.plugin.getLogger().info("Generating shares.yml in folder /PerWorldChatPlus/");
			List<String> list1 = Main.plugin.shares.getStringList("world");
			list1.add("world_nether");
			list1.add("world_the_end");
			Main.plugin.shares.set("world", list1);
			Main.plugin.shares.options().copyDefaults(true);
			Main.plugin.saveCustomConfig(Main.plugin.shares, Main.plugin.sharesFile);
			
			Main.plugin.shares.options().copyDefaults(true);
			Main.plugin.saveCustomConfig(Main.plugin.shares, Main.plugin.sharesFile);
		}
		if (!(Main.plugin.dataFile.exists())) {
			Main.plugin.getLogger().info("Generating data.yml in folder /PerWorldChatPlus/");
			Main.plugin.data.addDefault("DO NOT DELETE", "PerWorldChatPlus is developed and managed by Shades161");
			Main.plugin.data.options().copyDefaults(true);
			Main.plugin.saveCustomConfig(Main.plugin.data, Main.plugin.dataFile);
			
			Main.plugin.data.options().copyDefaults(true);
			Main.plugin.saveCustomConfig(Main.plugin.data, Main.plugin.dataFile);
		}
		if (!(Main.plugin.alertFile.exists())) {
			Main.plugin.getLogger().info("Generating alerts.yml in folder /PerWorldChatPlus/Data/");
			Main.plugin.alerts.addDefault("DO NOT DELETE", "PerWorldChatPlus is developed and managed by Shades161");
			Main.plugin.alerts.options().copyDefaults(true);
			Main.plugin.saveCustomConfig(Main.plugin.alerts, Main.plugin.alertFile);
		
			Main.plugin.alerts.options().copyDefaults(true);
			Main.plugin.saveCustomConfig(Main.plugin.alerts, Main.plugin.alertFile);
		}
		if (!(Main.plugin.worldSpyFile.exists())) {
			Main.plugin.getLogger().info("Generating worldSpy.yml in folder /PerWorldChatPlus/Data/");
			Main.plugin.worldSpyYml.addDefault("DO NOT DELETE", "PerWorldChatPlus is developed and managed by Shades161");
			Main.plugin.worldSpyYml.options().copyDefaults(true);
			Main.plugin.saveCustomConfig(Main.plugin.worldSpyYml, Main.plugin.worldSpyFile);

			Main.plugin.worldSpyYml.options().copyDefaults(true);
			Main.plugin.saveCustomConfig(Main.plugin.worldSpyYml, Main.plugin.worldSpyFile);
		}
	}

	/**
	 * A nice little function that compares the currently existing file versions with the new ones.
	 * If they are out of date, this will automatically disable the plugin.
	 */
	public static void checkFileVersion() {
		if (!(Main.plugin.getConfig().getString("Config Version").equalsIgnoreCase(Main.plugin.conVersion))) {
			Main.plugin.getLogger().severe("Config.yml outdated! Plugin will not work properly! "
					+ "Delete the file and restart the server to update it!");
			Main.plugin.getLogger().info("Shutting down plugin to prevent further errors....");
			Main.plugin.getServer().getPluginManager().disablePlugin(Main.plugin);
		} else if (!(MessageManager.getMessageYml().getDouble("Messages Version") == Main.plugin.messageVersion)) {
			Main.plugin.getLogger().severe("Your messages files are outdated! Plugin will not work properly! "
					+ "Delete the messages folder and restart the server to update it!");
			Main.plugin.getLogger().info("Shutting down plugin to prevent further errors....");
			Main.plugin.getServer().getPluginManager().disablePlugin(Main.plugin);
		}
	}
}