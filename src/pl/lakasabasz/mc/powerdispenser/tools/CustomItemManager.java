package pl.lakasabasz.mc.powerdispenser.tools;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;
import pl.lakasabasz.mc.powerdispenser.Main;

public class CustomItemManager {
	public static ItemStack getTemlatePowerDispenser() {
		ItemStack ret = new ItemStack(Material.DISPENSER);
		ItemMeta im = ret.getItemMeta();
		im.addEnchant(Enchantment.DURABILITY, 1, true);
		im.setDisplayName(ChatColor.DARK_RED + "Potężny dozownik");
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.getPersistentDataContainer().set(getKey(), PersistentDataType.INTEGER, 0);
		ret.setItemMeta(im);
		return ret;
	}

	public static NamespacedKey getKey() {
		return new NamespacedKey(Main.getInstance(), "pwrdisp");
	}
	
	public static boolean isPowerDispenser(ItemStack is) {
		return is.getType().equals(Material.DISPENSER) && is.getItemMeta().getPersistentDataContainer().has(getKey(), PersistentDataType.INTEGER);
	}

	public static boolean isPowerDispenser(Block block) {
		return block.getType().equals(Material.DISPENSER) && ((TileState) block.getState()).getPersistentDataContainer().has(getKey(), PersistentDataType.INTEGER);
	}
}
