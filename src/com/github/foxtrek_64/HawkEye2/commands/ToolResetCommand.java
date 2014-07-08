package com.github.foxtrek_64.HawkEye2.commands;

import com.github.foxtrek_64.HawkEye2.ToolManager;
import com.github.foxtrek_64.HawkEye2.util.Util;

public class ToolResetCommand extends BaseCommand {

	public ToolResetCommand() {
		name = "tool reset";
		argLength = 0;
		permission = "tool.bind";
		usage = " <- resets tool to default properties";
	}

	@Override
	public boolean execute() {
		ToolManager.resetTool(session);
		Util.sendMessage(player, "&cTool reset to default parameters");
		return true;
	}

	@Override
	public void moreHelp() {
		Util.sendMessage(sender, "&cReset HawkEye tool to default properties");
		Util.sendMessage(sender, "&cSee &7/hawk tool bind help");
	}
}