package com.github.foxtrek_64.HawkEye2.commands;

import java.util.ArrayList;
import java.util.List;

import com.github.foxtrek_64.HawkEye2.DataType;
import com.github.foxtrek_64.HawkEye2.SearchParser;
import com.github.foxtrek_64.HawkEye2.callbacks.SearchCallback;
import com.github.foxtrek_64.HawkEye2.database.SearchQuery;
import com.github.foxtrek_64.HawkEye2.database.SearchQuery.SearchDir;
import com.github.foxtrek_64.HawkEye2.util.Util;

/**
 * Searches for data according to the player's specified input.
 * Error handling for user input is done using exceptions to keep code neat.
 * @author oliverw92
 */
public class SearchCommand extends BaseCommand {

	public SearchCommand() {
		bePlayer = false;
		name = "search";
		argLength = 1;
		permission = "search";
		usage = "<parameters> <- search HawkEye database";
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
		new SearchQuery(new SearchCallback(session), parser, SearchDir.DESC);
		return true;

	}

	@Override
	public void moreHelp() {
		List<String> acs = new ArrayList<String>();
		for (DataType type : DataType.values()) acs.add(type.getConfigName());
		Util.sendMessage(sender, "&7There are 7 parameters you can use - &ca: p: w: r: f: t:");
		Util.sendMessage(sender, "&6Action &ca:&7 - list of actions separated by commas. Select from the following: &8" + Util.join(acs, " "));
		Util.sendMessage(sender, "&6Player &cp:&7 - list of players. &6World &cw:&7 - list of worlds");
		Util.sendMessage(sender, "&6Filter &cf:&7 - list of keywords. &6Location &cl:&7 - x,y,z location");
		Util.sendMessage(sender, "&6Radius &cr:&7 - radius to search around given location");
		Util.sendMessage(sender, "&6Time &ct:&7 - time bracket in the following format:");
		Util.sendMessage(sender, "&7  -&c Date format: yyyy-MM-dd");
		Util.sendMessage(sender, "&7  -&c t:10h45m10s &7-back specified amount of time");
		Util.sendMessage(sender, "&7  -&c t:2011-06-02,10:45:10 &7-from given date");
		Util.sendMessage(sender, "&7  -&c t:2011-06-02,10:45:10,2011-07-04,18:15:00 &7-between dates");
	}
}