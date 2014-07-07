package uk.co.oliwali.HawkEye.commands;

import uk.co.oliwali.HawkEye.Undo;
import uk.co.oliwali.HawkEye.util.Util;

/**
 * Reverses the previous {@link RollbackCommand}
 * @author oliverw92
 */
public class UndoCommand extends BaseCommand {

	public UndoCommand() {
		name = "undo";
		permission = "rollback";
		usage = "<- reverses your previous rollback";
	}

	@Override
	public boolean execute() {
		new Undo(session);
		return true;
	}

	@Override
	public void moreHelp() {
		Util.sendMessage(sender, "&cReverses your previous rollback if you made a mistake with it");
	}
}
