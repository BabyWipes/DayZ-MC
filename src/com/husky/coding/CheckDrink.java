package com.husky.coding;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CheckDrink implements Runnable {
    private Player p;
    private int slot;
 
    public CheckDrink(Player p, int slot){
        this.p = p;
        this.slot = slot;
    }
 
    @Override
    public void run() {
        if(p.getInventory().getHeldItemSlot() == slot
                && p.getItemInHand().getType().equals(Material.GLASS_BOTTLE)){
            User.drinkFull(p);
            p.sendMessage(ChatColor.AQUA+"You drank water! Your thirst has been replenished!");
        }
    }
}