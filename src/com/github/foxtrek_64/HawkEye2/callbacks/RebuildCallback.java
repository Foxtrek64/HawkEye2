package com.github.foxtrek_64.HawkEye2.callbacks;

import org.bukkit.command.CommandSender;

import com.github.foxtrek_64.HawkEye2.PlayerSession;
import com.github.foxtrek_64.HawkEye2.Rebuild;
import com.github.foxtrek_64.HawkEye2.database.SearchQuery.SearchError;
import com.github.foxtrek_64.HawkEye2.util.Util;

/**
 * Implementation of BaseCallback for use in rollback commands
 * @author oliverw92
 */
public class RebuildCallback extends BaseCallback {

	private final PlayerSession session;
	private final CommandSender sender;

	public RebuildCallback(PlayerSession session) {
		this.session = session;
		sender = session.getSender();
		Util.sendMessage(sender, "&cSearching for matching results to rebuild...");
	}

	@Override
	public void execute() {
		session.setRollbackResults(results);
		new Rebuild(session);
	}

	@Override
	public void error(SearchError error, String message) {
		Util.sendMessage(session.getSender(), message);
	}

}
