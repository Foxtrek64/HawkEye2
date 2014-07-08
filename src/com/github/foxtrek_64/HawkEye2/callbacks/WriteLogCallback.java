package com.github.foxtrek_64.HawkEye2.callbacks;

import org.bukkit.command.CommandSender;

import com.github.foxtrek_64.HawkEye2.LogManager;
import com.github.foxtrek_64.HawkEye2.PlayerSession;
import com.github.foxtrek_64.HawkEye2.database.SearchQuery.SearchError;
import com.github.foxtrek_64.HawkEye2.util.Util;

public class WriteLogCallback extends BaseCallback {

	private final PlayerSession session;
	private final CommandSender sender;

	public WriteLogCallback(PlayerSession session) {
		this.session = session;
		sender = session.getSender();
		Util.sendMessage(sender, "&cSearching for matching results...");
	}

	@Override
	public void execute() {
		session.setSearchResults(results);
		LogManager.log(session);
	}

	@Override
	public void error(SearchError error, String message) {
		Util.sendMessage(sender, message);
	}

}
