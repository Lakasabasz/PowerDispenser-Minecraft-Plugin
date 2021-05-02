package pl.lakasabasz.mc.powerdispenser.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Dispenser;

import org.bukkit.block.data.Levelled;

public class LavaScanner implements Runnable {

	private static int maxDistance = 7;
	private static Map<Location, List<List<Block>>> availableLavaBlock;
	private static List<List<Block>> templateEmptyList;
	
	static {
		availableLavaBlock = new HashMap<Location, List<List<Block>>>();
		templateEmptyList = new ArrayList<List<Block>>();
		for(int i = 0; i < maxDistance; i++) {
			templateEmptyList.add(new ArrayList<Block>());
		}
	}
	
	private static List<Location> neib(Location l){
		ArrayList<Location> ret = new ArrayList<Location>();
		ret.add(new Location(l.getWorld(), l.getX() + 1, l.getY(), l.getZ()));
		ret.add(new Location(l.getWorld(), l.getX() - 1, l.getY(), l.getZ()));
		ret.add(new Location(l.getWorld(), l.getX(), l.getY(), l.getZ() + 1));
		ret.add(new Location(l.getWorld(), l.getX(), l.getY(), l.getZ() - 1));
		ret.add(new Location(l.getWorld(), l.getX(), l.getY() + 1, l.getZ()));
		return ret;
	}
	
	private boolean isIn(List<List<Block>> lb, Block tested) {
		boolean found = false;
		for(List<Block> l : lb) {
			if(l.contains(tested)) {
				found = true;
				continue;
			}
		}
		return found;
	}
	
	public static Location getStartingPoint(Location dispenser) {
		Location l = dispenser;
		BlockFace direction = ((Dispenser) l.getBlock().getBlockData()).getFacing();
		Location startingPoint;
		if(direction.equals(BlockFace.UP)) {
			startingPoint = new Location(l.getWorld(), l.getX(), l.getY()+1, l.getZ());
		} else if(direction.equals(BlockFace.WEST)) {
			startingPoint = new Location(l.getWorld(), l.getX()-1, l.getY(), l.getZ());
		} else if(direction.equals(BlockFace.EAST)) {
			startingPoint = new Location(l.getWorld(), l.getX()+1, l.getY(), l.getZ());
		} else if(direction.equals(BlockFace.NORTH)) {
			startingPoint = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ()-1);
		} else if(direction.equals(BlockFace.SOUTH)) {
			startingPoint = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ()+1);
		} else return null;
		return startingPoint;
	}
	
	public static boolean removeClosestLava(Location dispenser) {
		List<List<Block>> llb = availableLavaBlock.get(getStartingPoint(dispenser));
		if(llb == null) {
			llb = new ArrayList<List<Block>>();
			addDispenser(getStartingPoint(dispenser));
			return false;
		}
		for(int i = 0; i < maxDistance; i++) {
			if(llb.get(i).isEmpty()) continue;
			llb.get(i).get(0).setType(Material.AIR);
			llb.get(i).remove(0);
			return true;
		}
		return false;
	}
	
	@Override
	public void run() {
		for(Location l : availableLavaBlock.keySet()) {
			List<List<Block>> llb = new ArrayList<List<Block>> (templateEmptyList);
			
			if(l.getBlock().getType().equals(Material.LAVA)) {
				llb.get(0).add(l.getBlock());
			}
			
			for(int dist = 1; dist < maxDistance; dist++) {
				for(Block block : llb.get(dist-1)) {
					List<Location> neibs = neib(block.getLocation());
					for(Location neibour : neibs) {
						if(!neibour.getBlock().getType().equals(Material.LAVA)) continue;
						if(!isIn(llb, neibour.getBlock())) {
							llb.get(dist).add(neibour.getBlock());
						}
					}
				}
			}
			for(List<Block> lb : llb) {
				lb.removeIf(b -> ((Levelled) b.getBlockData()).getLevel() != 0);
			}
			availableLavaBlock.put(l, llb);
		}
	}

	public static void removeDispenser(Location startingPoint) {
		availableLavaBlock.remove(startingPoint);
	}
	
	public static void addDispenser(Location startingPoint) {
		availableLavaBlock.put(startingPoint, new ArrayList<List<Block>>(templateEmptyList));
	}

}
