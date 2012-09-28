package com.husky.coding;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class DayZ extends JavaPlugin {

	YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/DayZ/config.yml"));
	
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new DayZListener(), this);
		createConfig();
	}

	private void createConfig() {
		boolean exists = new File(getDataFolder(), "config.yml").exists();
		if (!exists) {
			new File("plugins/DayZ").mkdir();
			config.options().header("DayZ, Husky!");
			config.set("bandage.health-gained", 4);
			try {
				config.save(new File(getDataFolder(), "config.yml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void onDisable() {

	}


}
