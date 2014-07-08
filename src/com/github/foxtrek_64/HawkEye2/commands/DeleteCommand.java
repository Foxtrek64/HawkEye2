package com.github.foxtrek_64.HawkEye2.commands;

import com.github.foxtrek_64.HawkEye2.SearchParser;
import com.github.foxtrek_64.HawkEye2.callbacks.DeleteCallback;
import com.github.foxtrek_64.HawkEye2.database.SearchQuery;
import com.github.foxtrek_64.HawkEye2.database.SearchQuery.SearchDir;
import com.github.foxtrek_64.HawkEye2.util.Util;

public class DeleteCommand extends BaseCommand {

	public DeleteCommand() {
		bePlayer = false;
		name = "delete";
		permission = "delete";
		argLength = 1;
		usage = "<parameters> <- delete database entries";
	}

	@Override
	public boolean execute() {

		//Parse arguments
		SearchParser parser = null;
		try {
			parser = new SearchParser(sender, args);
		} catch (IllegalArgumentException e) {
			Util.sendMessage(sender, "&c" + e.getMessage());
			return true;
		}

		//Create new SeachQuery with data
		new SearchQuery(new DeleteCallback(session), parser, SearchDir.DESC);
		return true;

	}

	@Override
	public void moreHelp() {
		Util.sendMessage(sender, "&cDeletes specified entries from the database permanently");
		Util.sendMessage(sender, "&cUses the same parameters and format as /hawk search");
	}

}
