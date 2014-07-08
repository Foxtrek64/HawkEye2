package com.github.foxtrek_64.HawkEye2.commands;

import com.github.foxtrek_64.HawkEye2.DataType;
import com.github.foxtrek_64.HawkEye2.Rollback.RollbackType;
import com.github.foxtrek_64.HawkEye2.SearchParser;
import com.github.foxtrek_64.HawkEye2.callbacks.RollbackCallback;
import com.github.foxtrek_64.HawkEye2.database.SearchQuery;
import com.github.foxtrek_64.HawkEye2.database.SearchQuery.SearchDir;
import com.github.foxtrek_64.HawkEye2.util.Util;

/**
 * Previews a rollback according to the player's specified input.
 * Error handling for user input is done using exceptions to keep code neat.
 * @author oliverw92
 */
public class PreviewCommand extends BaseCommand {

	public PreviewCommand() {
		name = "preview";
		argLength = 1;
		permission = "preview";
		usage = "<parameters> <- preview rollback changes";
	}

	@Override
	public boolean execute() {

		//Check if player already has a rollback processing
		if (session.doingRollback()) {
			Util.sendMessage(sender, "&cYou already have a rollback command processing!");
			return true;
		}

		//Parse arguments
		SearchParser parser = null;
		try {

			parser = new SearchParser(player, args);
			parser.loc = null;

			//Check that supplied actions can rollback
			if (parser.actions.size() > 0) {
				for (DataType type : parser.actions)
					if (!type.canRollback()) throw new IllegalArgumentException("You cannot rollback that action type: &7" + type.getConfigName());
			}
			//If none supplied, add in all rollback types
			else {
				for (DataType type : DataType.values())
					if (type.canRollback()) parser.actions.add(type);
			}

		} catch (IllegalArgumentException e) {
			Util.sendMessage(sender, "&c" + e.getMessage());
			return true;
		}

		//Create new SearchQuery with data
		new SearchQuery(new RollbackCallback(session, RollbackType.LOCAL), parser, SearchDir.DESC);
		session.setInPreview(true);
		return true;

	}

	@Override
	public void moreHelp() {
		Util.sendMessage(sender, "&cPreviews a rollback to only you");
		Util.sendMessage(sender, "&cThis type of rollback does not affect the actual world in any way");
		Util.sendMessage(sender, "&cThe effects can be applied after using &7/hawk preview apply&c or cancelled using &7/hawk preview cancel");
		Util.sendMessage(sender, "&cThe parameters are the same as &7/hawk rollback");
	}
}
