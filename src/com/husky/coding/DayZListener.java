package com.husky.coding;

import java.io.File;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.entity.CraftZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class DayZListener implements Listener {

	YamlConfiguration data = YamlConfiguration.loadConfiguration(new File("plugins/DayZ/config.yml"));

	@EventHandler
	public void chat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		List<Entity> nearby = p.getNearbyEntities(40, 40, 40);
		Player s = null;
		for(Entity ent : nearby) {
			if(ent instanceof CraftPlayer) {
				s = (Player) ent;
				s.sendMessage(p.getName() + e.getMessage());
			}
		}
		if(p.getItemInHand().getType() == Material.EYE_OF_ENDER) {
			e.setCancelled(false);
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if(p.hasPermission("DayZ.build")) {
			e.setCancelled(false);
		} else if(e.getBlock().getType() == Material.CHEST) {
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void bandage(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(p.getItemInHand().getType() == Material.PAPER) {
			int healthgain = data.getInt("bandage.health-gained");
			int old = p.getHealth();
			p.setHealth(old + healthgain);
			p.sendMessage(ChatColor.GREEN + "You've just bandaged your wound!");
		}
	}

	@EventHandler
	public void toggleSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if(p.isSneaking()) {
			List<Entity> nearby = p.getNearbyEntities(20,20,20);
			CraftZombie z = null;
			for(Entity ent : nearby) {
				if(ent instanceof CraftZombie) {
					z = (CraftZombie) ent;
				}
			}
			if(z != null) {
				p.sendMessage(ChatColor.RED + "A zombie heard your footsteps!");
				z.setTarget(p);
			}
		}
	}
	@EventHandler
	public void EntitySpawn(EntityTargetEvent e) {
		Entity target = e.getTarget();
		Entity attacker = e.getEntity();
		if(attacker instanceof CraftZombie && target instanceof Player) {
			Player p = (Player) target;		
			if(p.isSneaking()) {
				e.setCancelled(true);
			} else {
				e.setTarget(target);
			}
		}
	}

	@EventHandler
	public void respawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		p.setTotalExperience(20);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!p.hasPlayedBefore()) {
			p.setTotalExperience(20);
		}
	}
	

}
