package com.github.foxtrek_64.HawkEye2.blocks;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.github.foxtrek_64.HawkEye2.DataType;
import com.github.foxtrek_64.HawkEye2.database.DataManager;
import com.github.foxtrek_64.HawkEye2.entry.BlockEntry;

public class TallPlant extends Default {

	@Override
	public void logAttachedBlocks(Block b, Player p, DataType type) {
		b = b.getRelative(BlockFace.UP);
		while(HawkBlockType.getHawkBlock(b.getTypeId()).equals(this)) {
			DataManager.addEntry(new BlockEntry(p, type, b));
			b = b.getRelative(BlockFace.UP);
		}
	}
	
	@Override
	public boolean isTopBlock() {
		return true;
	}
}