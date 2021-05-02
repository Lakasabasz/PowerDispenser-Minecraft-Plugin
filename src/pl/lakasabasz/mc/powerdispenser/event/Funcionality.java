package pl.lakasabasz.mc.powerdispenser.event;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import io.papermc.paper.event.block.BlockFailedDispenseEvent;
import pl.lakasabasz.mc.powerdispenser.Main;
import pl.lakasabasz.mc.powerdispenser.tools.CustomItemManager;

public class Funcionality implements Listener {
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(e.getBlock().getType().equals(Material.DISPENSER)) {
			if(CustomItemManager.isPowerDispenser(e.getItemInHand())) {
				TileState ts = (TileState) e.getBlock().getState();
				ts.getPersistentDataContainer().set(CustomItemManager.getKey(), PersistentDataType.INTEGER, 1);
				ts.update();
				Main.getInstance().addDispenser(e.getBlock().getLocation());
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if(e.getBlock().getType().equals(Material.DISPENSER)) {
			if(((TileState)e.getBlock().getState()).getPersistentDataContainer().has(CustomItemManager.getKey(), PersistentDataType.INTEGER)) {
				e.setDropItems(false);
				e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), CustomItemManager.getTemlatePowerDispenser());
				Main.getInstance().removeDispenser(e.getBlock().getLocation());
			}
		}
	}
	
	@EventHandler
	public void onBlockPowered(BlockFailedDispenseEvent e) {
		onDispenserPowered(e.getBlock());
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onBlockNoPowered(BlockDispenseEvent e) {
		if(onDispenserPowered(e.getBlock())) {
			e.setCancelled(true);
		}
	}
	
	private boolean onDispenserPowered(Block b) {
		if(b.getType().equals(Material.DISPENSER)) {
			if(((TileState) b.getState()).getPersistentDataContainer().has(CustomItemManager.getKey(), PersistentDataType.INTEGER)) {				
				Main.getInstance().addDispenserIf(b.getLocation());
				Container c = (Container) b.getState();
				if(c.getInventory().contains(Material.BUCKET) && c.getInventory().firstEmpty() != -1) {
					if(LavaScanner.removeClosestLava(b.getLocation())) {
						c.getInventory().addItem(new ItemStack(Material.LAVA_BUCKET));
						b.getLocation().getWorld().playSound(b.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 2, 1);
						ItemStack is = c.getInventory().getItem(c.getInventory().first(Material.BUCKET));
						if(is.getAmount() > 1) {
							is.setAmount(is.getAmount()-1);
						} else {
							is.setType(Material.AIR);
						}
						c.getInventory().setItem(c.getInventory().first(Material.BUCKET), is);
					} else {
						b.getLocation().getWorld().spawnParticle(Particle.ASH,
								b.getLocation().getX(), b.getLocation().getY(), b.getLocation().getZ(), 100, 2, 2, 2);
						b.getLocation().getWorld().playSound(b.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 1, 1);
					}
				}
				return true;
			}
		}
		return false;
	}
	
}
