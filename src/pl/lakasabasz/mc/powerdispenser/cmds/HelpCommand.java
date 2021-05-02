package pl.lakasabasz.mc.powerdispenser.cmds;

import java.util.List;

import org.bukkit.command.CommandSender;

public class HelpCommand {

	public void exec(List<BasicCmd> commandDatabase, CommandSender cs) {
		cs.sendMessage("Plugin PowerDispenser help:");
		for(BasicCmd bc : commandDatabase) {
			cs.sendMessage("/powerdispenser " + bc.getName() + "\t" + bc.getDescription());
		}
	}

}
