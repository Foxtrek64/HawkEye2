package com.github.foxtrek_64.HawkEye2.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Utility class for HawkEye.
 * All logging and messages should go through this class.
 * Contains methods for parsing strings, colours etc.
 * @author oliverw92
 */
public class Util {

	private static final Logger log = Logger.getLogger("Minecraft");

	/**
	 * Send an info level log message to console
	 * @param msg message to send
	 */
	public static void info(String msg) {
		log.info("[HawkEye] " + msg);
	}
	/**
	 * Send a warn level log message to console
	 * @param msg message to send
	 */
	public static void warning(String msg) {
		log.warning("[HawkEye] " + msg);
	}
	/**
	 * Send a severe level log message to console
	 * @param msg message to send
	 */
	public static void severe(String msg) {
		log.severe("[HawkEye] " + msg);
	}

	/**
	 * Send an debug message to console if debug is enabled
	 * @param msg message to send
	 */
	public static void debug(String msg) {
		if (Config.Debug)
			Util.debug(DebugLevel.LOW, msg);
	}

	public static void debug(DebugLevel level, String msg) {
		if (Config.Debug)
			if (Config.DebugLevel.compareTo(level) >= 0)
				Util.info("DEBUG: " + msg);
	}

	public static ChatColor getLastColor(String s) {
        int length = s.length();
        ChatColor color = ChatColor.GRAY;
        
        for (int i = length - 1; i > -1; i--) { //Search backwords, better for what we're doing!
            char ch = s.charAt(i); 
            if (ch == '&') { //The symbol for colors!
            	color = ChatColor.getByChar(s.charAt(i+1)); //If the char doesn't belong to a color, returns null
            	if (color != null) {
            		return color;
            	} else {
            		continue;
            	}
            }
        }
        return color;
	}
	
	/**
	 * Send a message to a CommandSender (can be a player or console).
	 * Has parsing built in for &a colours, as well as `n for new line
	 * @param player sender to send to
	 * @param msg message to send
	 */
	public static void sendMessage(CommandSender player, String msg) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}

	/**
	 * Turns supplied location into a simplified (1 decimal point) version
	 * @param location location to simplify
	 * @return Location
	 */
	public static Location getSimpleLocation(Location location) {
		location.setX((double)Math.round(location.getX() * 10) / 10);
		location.setY((double)Math.round(location.getY() * 10) / 10);
		location.setZ((double)Math.round(location.getZ() * 10) / 10);
		return location;
	}

	/**
	 * Checks if inputted string is an integer
	 * @param str string to check
	 * @return true if an integer, false if not
	 */
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Java version of PHP's join(array, delimiter)
	 * Takes any kind of collection (List, HashMap etc)
	 * @param s collection to be joined
	 * @param delimiter string delimiter
	 * @return String
	 */
	public static String join(Collection<?> s, String delimiter) {
		StringBuffer buffer = new StringBuffer();
		Iterator<?> iter = s.iterator();
		while (iter.hasNext()) {
			buffer.append(iter.next());
			if (iter.hasNext())
				buffer.append(delimiter);
		}
		return buffer.toString();
	}

	/**
	 * Concatenate any number of arrays of the same type
	 * @return
	 */
	public static <T> T[] concat(T[] first, T[]... rest) {

		//Read rest
		int totalLength = first.length;
		for (T[] array : rest) {
			totalLength += array.length;
		}

		//Concat with arraycopy
		T[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (T[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;

	}


	/**
	* Returns the distance between two {Location}s
	* @param from
	* @param to
	* @return double
	**/
	public static double distance(Location from, Location to) {
		return Math.sqrt(Math.pow(from.getX() - to.getX(), 2) + Math.pow(from.getY() - to.getY(), 2) + Math.pow(from.getZ() - to.getZ(), 2));
	}
	
	/**
	 * Returns the name of the supplied entity
	 * @param entity to get name of
	 * @return String name
	 */
	public static String getEntityName(Entity entity) {

		//Player
		if (entity instanceof Player) return ((Player) entity).getName();
		//Other
		else return entity.getType().getName();
	}

	/**
	 * Returns if the player has permission
	 * @param sender who is being checked
	 * @param perm string
	 * @return true / false
	 */
	public static boolean hasPerm(CommandSender sender, String perms) {
		if (!(sender instanceof Player)) return true;
		Player player = (Player)sender;
		
		boolean check = (!(player.hasPermission("hawkeye." + perms)) && (!(perms.equals("help"))) ? false : true); {
			if ((player.isOp() && Config.OpPermissions)) check = true;
			return check;
		}
	}

	public enum DebugLevel {
		NONE,
		LOW,
		HIGH;
	}

	public static String getTime(Date d1) {
		if (!(Config.isSimpleTime)) return d1.toString();

		String message = "";
		Date curdate = Calendar.getInstance().getTime();

		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date d2 = null;
		try {
			d2 = form.parse(form.format(curdate));
		} catch (ParseException e1) {
			warning(e1.getMessage());
		}

		long diff = (d2.getTime() / 1000) - (d1.getTime() / 1000);

		int seconds = (int)diff;

		if (seconds >= 86400) {
			int days = (seconds / 86400);
			seconds %= 86400;

			message = message + days + "d ";
		}
		if (seconds >= 3600) {
			int hours = seconds / 3600;
			seconds %= 3600;

			message = message + hours + "h ";
		}
		if (seconds >= 60) {
			int min = seconds / 60;
			seconds %= 60;

			message = message + min + "m ";
		} else {
			message = message + seconds + "s ";
		}
		return message;
	}

}
