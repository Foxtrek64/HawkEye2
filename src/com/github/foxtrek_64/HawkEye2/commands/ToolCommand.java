package com.github.foxtrek_64.HawkEye2.commands;

import com.github.foxtrek_64.HawkEye2.ToolManager;
import com.github.foxtrek_64.HawkEye2.util.Util;

/**
 * Enables or disables search tool for players
 * @author oliverw92
 */
public class ToolCommand extends BaseCommand {

	public ToolCommand() {
		name = "tool";
		argLength = 0;
		permission = "tool";
		usage = " <- enables/disables the searching tool";
	}

	@Override
	public boolean execute() {

		//If not using tool, enable
		if (!session.isUsingTool())
			ToolManager.enableTool(session, player);

		//If using tool, disable
		else
			ToolManager.disableTool(session, player);

		return true;
	}

	@Override
	public void moreHelp() {
		Util.sendMessage(sender, "&cGives you the HawkEye tool. You can use this to see changes at specific places");
		Util.sendMessage(sender, "&cLeft click a block or place the tool to get information");
	}
}
