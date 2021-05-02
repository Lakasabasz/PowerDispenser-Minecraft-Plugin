package pl.lakasabasz.mc.powerdispenser.cmds;

import org.bukkit.entity.Player;

public interface BasicCmd {
	public String getDescription();
	public String getKeyword();
	public String getName();
	
	public boolean exec(String[] args, Player p);
}
