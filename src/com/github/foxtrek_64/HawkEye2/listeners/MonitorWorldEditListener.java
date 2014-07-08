package com.github.foxtrek_64.HawkEye2.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import com.github.foxtrek_64.HawkEye2.DataType;
import com.github.foxtrek_64.HawkEye2.blocks.HawkBlock;
import com.github.foxtrek_64.HawkEye2.blocks.HawkBlockType;
import com.github.foxtrek_64.HawkEye2.blocks.SignBlock;
import com.github.foxtrek_64.HawkEye2.database.DataManager;
import com.github.foxtrek_64.HawkEye2.entry.BlockEntry;
import com.github.foxtrek_64.HawkEye2.entry.SignEntry;
import com.github.foxtrek_64.HawkEye2.util.Config;

/**
 * WorldEdit listener
 * Use EventHandler for WorldEdit Actions priorities
 * @author bob7l
 */

public class MonitorWorldEditListener implements Listener {

	WorldEditPlugin we;

	public MonitorWorldEditListener() {
		this.we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onWESuperPickaxe(PlayerInteractEvent event) {

		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Player player = event.getPlayer();

			if (we.wrapPlayer(player).isHoldingPickAxe() && (we.getSession(player).hasSuperPickAxe())) {
				Block block = event.getClickedBlock();
				Material type = block.getType();

				if (type == Material.AIR || Config.BlockFilter.contains(type.getId())) return;

				HawkBlock hb = HawkBlockType.getHawkBlock(type.getId());

				block = hb.getCorrectBlock(block);

				hb.logAttachedBlocks(block, player, DataType.SUPER_PICKAXE);

				if (hb instanceof SignBlock && DataType.SIGN_BREAK.isLogged())
					DataManager.addEntry(new SignEntry(player, DataType.SIGN_BREAK, block));
				
				else DataManager.addEntry(new BlockEntry(player, DataType.SUPER_PICKAXE, block));
			}
		}
	}
}