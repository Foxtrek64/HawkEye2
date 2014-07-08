package com.github.foxtrek_64.HawkEye2.commands;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import com.github.foxtrek_64.HawkEye2.DataType;
import com.github.foxtrek_64.HawkEye2.HawkEye;
import com.github.foxtrek_64.HawkEye2.util.Config;
import com.github.foxtrek_64.HawkEye2.util.Util;

public class ReloadCommand extends BaseCommand {

	public ReloadCommand() {
		name = "reload";
		argLength = 0;
		permission = "reload";
		usage = " <- reload hawkeye";
	}

	@Override
	public boolean execute() {
		Util.sendMessage(sender, "&c-----------&8[ &7Reload Process Started &8]&c-----------");

		HawkEye hawk = HawkEye.instance;

		hawk.reloadConfig();
		hawk.config = new Config(hawk);

		for (DataType dt : DataType.values()) {
			dt.reload();
		}

		Util.sendMessage(sender, "&8|  &7- &cConfig has been reloaded..");

		HandlerList.unregisterAll(hawk); //Unregisters all listeners registered to hawkeye

		hawk.registerListeners(Bukkit.getPluginManager());

		Util.sendMessage(sender, "&8|  &7- &cListeners have been reloaded..");

		Util.sendMessage(sender, "&c-----------&8[ &7Reload Process Finished &8]&c-----------");
		return true;
	}

	@Override
	public void moreHelp() {
		Util.sendMessage(sender, "&cReloads Hawkeye's configuration");
	}
}
