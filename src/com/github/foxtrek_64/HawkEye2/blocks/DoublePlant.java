package com.github.foxtrek_64.HawkEye2.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.github.foxtrek_64.HawkEye2.DataType;
import com.github.foxtrek_64.HawkEye2.database.DataManager;
import com.github.foxtrek_64.HawkEye2.entry.BlockEntry;

public class DoublePlant extends Default {

	@Override
	public void logAttachedBlocks(Block b, Player p, DataType type) {
		Block b2 = b.getRelative(BlockFace.UP);

		if (b2.getType() == Material.DOUBLE_PLANT) {
			DataManager.addEntry(new BlockEntry(p, type, b2));
		} else {
			b2 = b.getRelative(BlockFace.DOWN);

			if (b2.getType() == Material.DOUBLE_PLANT) {
				DataManager.addEntry(new BlockEntry(p, type, b2));
			}
		}
	}

	@Override
	public boolean isTopBlock() {
		return true;
	}
}