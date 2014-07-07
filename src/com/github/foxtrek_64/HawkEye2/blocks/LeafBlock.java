package com.github.foxtrek_64.HawkEye2.blocks;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import uk.co.oliwali.HawkEye.DataType;
import uk.co.oliwali.HawkEye.database.DataManager;
import uk.co.oliwali.HawkEye.entry.BlockEntry;
import uk.co.oliwali.HawkEye.entry.SignEntry;
import uk.co.oliwali.HawkEye.util.BlockUtil;

public class LeafBlock extends BasicBlock { 

	@Override
	public void logAttachedBlocks(Block b, Player p, DataType type) {
		for(BlockFace face: BlockUtil.faces) {
			Block attch = b.getRelative(face);
			HawkBlock hb = HawkBlockType.getHawkBlock(attch.getTypeId());
			if (hb.isAttached()) {
				hb.logAttachedBlocks(attch, p, type);
				if (hb instanceof SignBlock && DataType.SIGN_BREAK.isLogged())
					DataManager.addEntry(new SignEntry(p, DataType.SIGN_BREAK, hb.getCorrectBlock(attch)));
				else if (hb instanceof VineBlock) DataManager.addEntry(new BlockEntry(p, type, hb.getCorrectBlock(attch)));
			}
		}
	}
}