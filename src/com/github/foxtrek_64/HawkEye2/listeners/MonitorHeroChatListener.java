package com.github.foxtrek_64.HawkEye2.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.dthielke.herochat.ChannelChatEvent;

import com.github.foxtrek_64.HawkEye2.DataType;
import com.github.foxtrek_64.HawkEye2.HawkEvent;
import com.github.foxtrek_64.HawkEye2.HawkEye;
import com.github.foxtrek_64.HawkEye2.database.DataManager;
import com.github.foxtrek_64.HawkEye2.entry.DataEntry;

/**
 * HeroChat listener class for HawkEye
 */
public class MonitorHeroChatListener extends HawkEyeListener {

	public MonitorHeroChatListener(HawkEye HawkEye) {
		super(HawkEye);
	}
	@HawkEvent(dataType = DataType.HEROCHAT)
	 public void onChannelChatEvent(ChannelChatEvent event) {
		final Player player = event.getSender().getPlayer();
		Location loc  = player.getLocation();
		DataManager.addEntry(new DataEntry(player, DataType.HEROCHAT, loc, event.getChannel().getName() + ": " + event.getMessage()));
	}
}