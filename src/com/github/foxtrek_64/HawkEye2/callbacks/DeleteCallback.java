package com.github.foxtrek_64.HawkEye2.callbacks;

import org.bukkit.command.CommandSender;

import com.github.foxtrek_64.HawkEye2.PlayerSession;
import com.github.foxtrek_64.HawkEye2.database.SearchQuery.SearchError;
import com.github.foxtrek_64.HawkEye2.util.Util;

public class DeleteCallback extends BaseCallback {

	private final CommandSender sender;
	public int deleted;

	public DeleteCallback(PlayerSession session) {
		sender = session.getSender();
		Util.sendMessage(sender, "&cDeleting matching results...");
	}

	@Override
	public void execute() {
		Util.sendMessage(sender, "&c" + deleted + " entries removed from database.");
	}

	@Override
	public void error(SearchError error, String message) {
		Util.sendMessage(sender, message);
	}

}
