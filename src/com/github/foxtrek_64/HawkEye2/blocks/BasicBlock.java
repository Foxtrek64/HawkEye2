package com.github.foxtrek_64.HawkEye2.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.github.foxtrek_64.HawkEye2.DataType;
import com.github.foxtrek_64.HawkEye2.database.DataManager;
import com.github.foxtrek_64.HawkEye2.entry.SignEntry;
import com.github.foxtrek_64.HawkEye2.util.BlockUtil;

public class BasicBlock extends Default {

	
	@Override
	public void logAttachedBlocks(Block b, Player p, DataType type) {
		for(BlockFace face: BlockUtil.faces) {
			Block attch = b.getRelative(face);
			if (attch.getType() == Material.WALL_SIGN) {
				if (DataType.SIGN_BREAK.isLogged())
					DataManager.addEntry(new SignEntry(p, DataType.SIGN_BREAK, attch));
			}
		}
	}
}