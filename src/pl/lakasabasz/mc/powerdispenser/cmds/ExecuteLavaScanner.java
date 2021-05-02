package pl.lakasabasz.mc.powerdispenser.cmds;

import org.bukkit.entity.Player;

import pl.lakasabasz.mc.powerdispenser.event.LavaScanner;

public class ExecuteLavaScanner implements BasicCmd {

	@Override
	public String getDescription() {
		return "Executes lava scanner";
	}

	@Override
	public String getKeyword() {
		return "test";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "test";
	}

	@Override
	public boolean exec(String[] args, Player p) {
		LavaScanner ls = new LavaScanner();
		ls.run();
		return false;
	}

}
