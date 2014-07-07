package uk.co.oliwali.HawkEye.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import uk.co.oliwali.HawkEye.DataType;
import uk.co.oliwali.HawkEye.database.DataManager;
import uk.co.oliwali.HawkEye.entry.BlockEntry;
import uk.co.oliwali.HawkEye.entry.SignEntry;
import uk.co.oliwali.HawkEye.util.BlockUtil;

public class Default implements HawkBlock {

	@Override
	//public void Restore(Block b, int id, int data) {
	public void Restore(Block b, String Material) 
	{
		//b.setTypeIdAndData(id, ((byte) data), false);
		b.setType(Material);
	}

	@Override
	public void logAttachedBlocks(Block b, Player p, DataType type) {
		Block topb = b.getRelative(BlockFace.UP);
		HawkBlock hb = HawkBlockType.getHawkBlock(topb.getType());
		if (hb.isTopBlock()) {
			hb.logAttachedBlocks(topb, p, type);
			if (hb instanceof SignBlock && DataType.SIGN_BREAK.isLogged())
				DataManager.addEntry(new SignEntry(p, DataType.SIGN_BREAK, hb.getCorrectBlock(topb)));
			else DataManager.addEntry(new BlockEntry(p, type, hb.getCorrectBlock(topb)));
		}

		for(BlockFace face: BlockUtil.faces) {
			Block attch = b.getRelative(face);
			hb = HawkBlockType.getHawkBlock(attch.getType());
			if (hb.isAttached() && BlockUtil.isAttached(b, attch)) {
				hb.logAttachedBlocks(attch, p, type);
				if (attch.getType() == Material.WALL_SIGN && DataType.SIGN_BREAK.isLogged())
					DataManager.addEntry(new SignEntry(p, DataType.SIGN_BREAK, hb.getCorrectBlock(attch)));
				else DataManager.addEntry(new BlockEntry(p, type, hb.getCorrectBlock(attch)));
			}
		}
	}

	@Override
	public Block getCorrectBlock(Block b) {
		return b;
	}

	@Override
	public boolean isTopBlock() {
		return false;
	}
	
	@Override
	public boolean isAttached() {
		return false;
	}
}
