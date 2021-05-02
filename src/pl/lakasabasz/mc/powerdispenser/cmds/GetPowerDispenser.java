package pl.lakasabasz.mc.powerdispenser.cmds;

import org.bukkit.entity.Player;

import pl.lakasabasz.mc.powerdispenser.tools.CustomItemManager;

public class GetPowerDispenser implements BasicCmd {

	@Override
	public String getDescription() {
		return "Gives power dispenser item";
	}

	@Override
	public String getKeyword() {
		// TODO Auto-generated method stub
		return "givepowerdispenser";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "givepowerdispenser";
	}

	@Override
	public boolean exec(String[] args, Player p) {
		p.getInventory().addItem(CustomItemManager.getTemlatePowerDispenser());
		return true;
	}

}
