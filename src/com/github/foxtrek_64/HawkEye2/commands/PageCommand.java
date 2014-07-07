package uk.co.oliwali.HawkEye.commands;

import uk.co.oliwali.HawkEye.DisplayManager;
import uk.co.oliwali.HawkEye.util.Util;

/**
 * Displays a page from the player's previous search results
 * @author oliverw92
 */
public class PageCommand extends BaseCommand {

	public PageCommand() {
		bePlayer = false;
		name = "page";
		permission = "page";
		argLength = 1;
		usage = "<page> <- display a page from your last search";
	}

	@Override
	public boolean execute() {
		if (!Util.isInteger(args.get(0))) {
			Util.sendMessage(sender,"&cInvalid argument format: &7" + args.get(0));
			return true;
		}
		DisplayManager.displayPage(session, Integer.parseInt(args.get(0)));
		return true;
	}

	@Override
	public void moreHelp() {
		Util.sendMessage(sender, "&cShows the specified page of results from your latest search");
	}

}