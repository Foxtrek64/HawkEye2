package com.github.foxtrek_64.HawkEye2.commands;

import java.util.ArrayList;
import java.util.List;

import com.github.foxtrek_64.HawkEye2.DataType;
import com.github.foxtrek_64.HawkEye2.database.DataManager;
import com.github.foxtrek_64.HawkEye2.util.Util;

public class InfoCommand extends BaseCommand {

	public InfoCommand() {
		name = "info";
		argLength = 0;
		permission = "info";
		usage = " <- displays hawkeye's details";
	}

	@Override
	public boolean execute() {
		List<String> acs = new ArrayList<String>();
		for (DataType type : DataType.values()) 
			if (type.isLogged()) acs.add(type.getConfigName());
		
		Util.sendMessage(sender, "&c---------------------&8[ &7HawkEye &8]&c---------------------");
		Util.sendMessage(sender, "&8  - &cQueue-load: &7" + DataManager.getQueue().size());
		Util.sendMessage(sender, "&8  - &cVersion: &7" + plugin.getDescription().getVersion());
		Util.sendMessage(sender, "&8  - &cLogged: &7" + Util.join(acs, " "));
		Util.sendMessage(session.getSender(), "&c----------------------------------------------------");
		return true;
	}

	@Override
	public void moreHelp() {
		Util.sendMessage(sender, "&cDisplays HawkEye's details");
	}
}
