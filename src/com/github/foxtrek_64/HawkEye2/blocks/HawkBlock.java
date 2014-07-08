package com.github.foxtrek_64.HawkEye2.blocks;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.github.foxtrek_64.HawkEye2.DataType;

public interface HawkBlock {

	//public void Restore(Block b, int id, int data);
	public void Restore(Block b, String configName);
	
	public Block getCorrectBlock(Block b);
	
	public void logAttachedBlocks(Block b, Player p, DataType type);
	
	public boolean isTopBlock();
	
	public boolean isAttached();
	
}
