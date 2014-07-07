package com.github.foxtrek_64.HawkEye2.blocks;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import uk.co.oliwali.HawkEye.DataType;

public class TopBlock implements HawkBlock {

	//public void Restore(Block b, int id, int data) {
		public void Restore(Block b, String Material) 
		{
			//b.setTypeIdAndData(id, ((byte) data), false);
			b.setType(Material);
		}

	@Override
	public void logAttachedBlocks(Block b, Player p, DataType type) {
		return;
	}

	@Override
	public Block getCorrectBlock(Block b) {
		return b;
	}

	@Override
	public boolean isTopBlock() {
		return true;
	}
	
	@Override
	public boolean isAttached() {
		return true;
	}
}
