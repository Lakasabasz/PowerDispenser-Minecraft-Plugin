package pl.lakasabasz.mc.powerdispenser;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import net.md_5.bungee.api.ChatColor;
import pl.lakasabasz.mc.powerdispenser.cmds.BasicCmd;
import pl.lakasabasz.mc.powerdispenser.cmds.GetPowerDispenser;
import pl.lakasabasz.mc.powerdispenser.cmds.HelpCommand;
import pl.lakasabasz.mc.powerdispenser.cmds.ExecuteLavaScanner;
import pl.lakasabasz.mc.powerdispenser.event.Funcionality;
import pl.lakasabasz.mc.powerdispenser.event.LavaScanner;
import pl.lakasabasz.mc.powerdispenser.tools.CustomItemManager;
import pl.lakasabasz.mc.powerdispenser.tools.Logger;
import pl.lakasabasz.mc.powerdispenser.tools.MessageType;
import pl.lakasabasz.mc.powerdispenser.tools.MessagesContainer;
import pl.lakasabasz.mc.powerdispenser.tools.PermissionType;
import pl.lakasabasz.mc.powerdispenser.tools.PermissionsContainer;

@Plugin(name = "PowerDispenser", version = "1.0.0.0")
@Description(value = "Plugin dodający nowe funkcjonalnoci do dozownika")
@Author(value = "Łukasz Łakasabasz Mastalerz")
@org.bukkit.plugin.java.annotation.command.Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "powerdispenser", desc = "Komenda konfiguracyjna", permission = "powerdispenser.cmd", usage = "/powerdispenser"))
@Permission(name = "powerdispenser.cmd")
@Permission(name = "powerdispenser.*")
@ApiVersion(ApiVersion.Target.v1_16)
public class Main extends JavaPlugin {
	
	private List<BasicCmd> commandDatabase;
	private HelpCommand helpCommand;
	private NamespacedKey pwrdispKey;
	private static Main instance;
	
	private List<Location> powerDispenserList;
	
	@Override
	public void onEnable() {
		Bukkit.getServer().getConsoleSender().sendMessage("[PowerDispenser]" + ChatColor.DARK_GREEN + " Plugin loaded version " + this.getDescription().getVersion());
		instance = this;
		
		powerDispenserList = new ArrayList<Location>();
		
		commandDatabase = new ArrayList<BasicCmd>();
		commandDatabase.add(new GetPowerDispenser());
		commandDatabase.add(new ExecuteLavaScanner());
		helpCommand = new HelpCommand();
		Logger.sendInfo(MessagesContainer.getMessage(MessageType.INIT));
		
		pwrdispKey = new NamespacedKey(this, "crafting");
		ShapedRecipe powerdispenserrecipe = new ShapedRecipe(pwrdispKey, CustomItemManager.getTemlatePowerDispenser()).shape("ccc", "ckc", "crc")
				.setIngredient('c', Material.COBBLESTONE)
				.setIngredient('k', Material.CROSSBOW)
				.setIngredient('r', Material.REDSTONE);
		Bukkit.addRecipe(powerdispenserrecipe);
		
		Bukkit.getScheduler().runTaskTimer(this, new LavaScanner(), 1*20, 1*20);
		
		Bukkit.getPluginManager().registerEvents(new Funcionality(), this);
	}
	
	@Override
	public void onDisable() {
		Bukkit.getServer().getConsoleSender().sendMessage("[PowerDispenser]" + ChatColor.DARK_GREEN + " Plugin disabled");
		Bukkit.removeRecipe(pwrdispKey);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission(PermissionsContainer.getPermission(PermissionType.COMMAND))) {
			Logger.sendWarrning(MessagesContainer.getMessage(MessageType.PERMISSION_ERROR), sender);
		}
		
		boolean found = false;
		if(args.length > 0) {
			for(BasicCmd bc : commandDatabase) {
				if(bc.getKeyword().equalsIgnoreCase(args[0])) {
					bc.exec(args, (Player) sender);
					found = true;
					break;
				}
			}
		}
		
		if(!found) helpCommand.exec(commandDatabase, sender);
		return true;
		
	}
	
	public void addDispenser(Location l) {
		this.powerDispenserList.add(l);
	}
	
	public void removeDispenser(Location l) {
		LavaScanner.removeDispenser(LavaScanner.getStartingPoint(l));
	}

	public static Main getInstance() {
		return instance;
	}

	public List<Location> getDispenserList() {
		return this.powerDispenserList;
	}

	public void addDispenserIf(Location location) {
		if(powerDispenserList.contains(location)) return;
		powerDispenserList.add(location);
	}
}
