package com.github.foxtrek_64.HawkEye2.commands;

import com.github.foxtrek_64.HawkEye2.ToolManager;
import com.github.foxtrek_64.HawkEye2.util.Util;

public class ToolBindCommand extends BaseCommand {

	public ToolBindCommand() {
		name = "tool bind";
		argLength = 1;
		permission = "tool.bind";
		usage = " <- bind custom parameters to the tool";
	}

	@Override
	public boolean execute() {
		ToolManager.bindTool(player, session, args);
		return true;
	}

	@Override
	public void moreHelp() {
		Util.sendMessage(sender, "&cAllows you to bind custom search parameters onto the tool");
		Util.sendMessage(sender, "&cSee &7/hawk search help for info on parameters");
	}
}