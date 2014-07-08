package com.github.foxtrek_64.HawkEye2.commands;

import com.github.foxtrek_64.HawkEye2.Rollback;
import com.github.foxtrek_64.HawkEye2.Rollback.RollbackType;
import com.github.foxtrek_64.HawkEye2.util.Util;

/**
 * Applies a local rollback to the world
 * Error handling for user input is done using exceptions to keep code neat.
 * @author oliverw92
 */
public class PreviewApplyCommand extends BaseCommand {

	public PreviewApplyCommand() {
		name = "preview apply";
		argLength = 0;
		permission = "preview";
		usage = "<- apply rollback preview";
	}

	@Override
	public boolean execute() {

		//Check if player already has a rollback processing
		if (!session.isInPreview()) {
			Util.sendMessage(sender, "&cNo preview to apply!");
			return true;
		}

		//Undo local changes to the player
		Util.sendMessage(sender, "&cAttempting to apply rollback to world...");
		new Rollback(RollbackType.GLOBAL, session);
		session.setInPreview(false);
		return true;

	}

	@Override
	public void moreHelp() {
		Util.sendMessage(sender, "&cApplies the results of a &7/hawk preview&c globally");
		Util.sendMessage(sender, "&cUntil this command is called, the preview is only visible to you");
	}

}
