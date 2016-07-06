package com.cloudcraftgaming.perworldchatplus;

import com.cloudcraftgaming.perworldchatplus.commands.ChatColorCommand;
import com.cloudcraftgaming.perworldchatplus.data.WorldDataManager;
import com.cloudcraftgaming.perworldchatplus.commands.GlobalChatCommand;
import com.cloudcraftgaming.perworldchatplus.commands.PerWorldChatCommand;
import com.cloudcraftgaming.perworldchatplus.listeners.ChatListener;
import com.cloudcraftgaming.perworldchatplus.listeners.InventoryClickListener;
import com.cloudcraftgaming.perworldchatplus.listeners.JoinListener;
import com.cloudcraftgaming.perworldchatplus.utils.ChatColorInventory;
import com.cloudcraftgaming.perworldchatplus.utils.FileManager;
import com.cloudcraftgaming.perworldchatplus.utils.MessageManager;
import com.cloudcraftgaming.perworldchatplus.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Create by: NovaFox161
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class Main extends JavaPlugin {
	public static Main plugin;

	public UpdateChecker updateChecker;
	public File sharesFile = new File(this.getDataFolder() + "/shares.yml");
	public FileConfiguration shares = (YamlConfiguration.loadConfiguration(sharesFile));
	
	public void onDisable() {}
	public void onEnable() {
		plugin = this;

		//Register things
		getServer().getPluginManager().registerEvents(new ChatListener(), this);
		getServer().getPluginManager().registerEvents(new JoinListener(this), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);

		getCommand("perworldchatplus").setExecutor(new PerWorldChatCommand());
		getCommand("perworldchat").setExecutor(new PerWorldChatCommand());
		getCommand("pwcp").setExecutor(new PerWorldChatCommand());
		getCommand("pwc").setExecutor(new PerWorldChatCommand());
		getCommand("globalchat").setExecutor(new GlobalChatCommand());
		getCommand("global").setExecutor(new GlobalChatCommand());
		getCommand("chatcolor").setExecutor(new ChatColorCommand());
		getCommand("cc").setExecutor(new ChatColorCommand());

		//Do file stuff
		FileManager.createConfig();
		FileManager.createFiles();
		MessageManager.createEnglishMessagesFile();

		FileManager.checkFileVersion();

		//Make sure timed global didn't stay on somehow.
		getConfig().set("Global.TimedGlobal.On", false);
		saveConfig();

		checkUpdatesOnStart();

		//Do some other stuff
		ChatColorInventory.createChatColorInventory();
		generateWorldDataFilesOnStart();
	}
	private void checkUpdatesOnStart() {
		if (getConfig().getString("Check for Updates").equalsIgnoreCase("True")) {
			getLogger().info("Checking for updates...");
			this.updateChecker = new UpdateChecker(this, "http://dev.bukkit.org/bukkit-plugins/per-world-chat-plus/files.rss");
			if (this.updateChecker.UpdateNeeded()) {
				getLogger().info("A new update for PerWorldChatPlus is available! Version: " + updateChecker.getVersion());
				getLogger().info("Download it from: " + updateChecker.getLink());
			}
			else {
				getLogger().info("No updates found, will check again later.");
			}
		}
	}

	private void generateWorldDataFilesOnStart() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				for (World world : Bukkit.getWorlds()) {
					WorldDataManager.createWorldDataFile(world.getName());
				}
			}
		}, 20L * 2);
	}

	/**
	 * A simple method for checking if the versions are compatible.
	 * This is to help reduce updates needed when a patch comes out.
	 * @param targetVersion The version you are specifically looking for.
	 * @return True if the versions are compatible, else false.
     */
	public Boolean checkVersionCompatibility(String targetVersion) {
		return targetVersion.equals(getDescription().getVersion()) || targetVersion.startsWith("5");
	}


	 public void saveCustomConfig(FileConfiguration ymlConfig, File ymlFile) {
		 try {
			 ymlConfig.save(ymlFile);
		 } catch (IOException e) {
			e.printStackTrace();
		 }
	}
}