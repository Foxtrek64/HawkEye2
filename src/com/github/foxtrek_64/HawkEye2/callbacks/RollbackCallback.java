package com.github.foxtrek_64.HawkEye2.callbacks;

import org.bukkit.command.CommandSender;

import com.github.foxtrek_64.HawkEye2.PlayerSession;
import com.github.foxtrek_64.HawkEye2.Rollback;
import com.github.foxtrek_64.HawkEye2.Rollback.RollbackType;
import com.github.foxtrek_64.HawkEye2.database.SearchQuery.SearchError;
import com.github.foxtrek_64.HawkEye2.util.Util;

/**
 * Implementation of BaseCallback for use in rollback commands
 * @author oliverw92
 */
public class RollbackCallback extends BaseCallback {

	private final PlayerSession session;
	private final CommandSender sender;
	private final RollbackType type;

	public RollbackCallback(PlayerSession session, RollbackType type) {
		this.type = type;
		this.session = session;
		sender = session.getSender();
		Util.sendMessage(sender, "&cSearching for matching results to rollback...");
	}

	@Override
	public void execute() {
		session.setRollbackResults(results);
		new Rollback(type, session);
	}

	@Override
	public void error(SearchError error, String message) {
		Util.sendMessage(session.getSender(), message);
	}

}
