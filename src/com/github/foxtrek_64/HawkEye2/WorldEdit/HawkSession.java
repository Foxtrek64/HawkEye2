package com.github.foxtrek_64.HawkEye2.WorldEdit;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockState;

import com.github.foxtrek_64.HawkEye2.DataType;
import com.github.foxtrek_64.HawkEye2.database.DataManager;
import com.github.foxtrek_64.HawkEye2.entry.BlockChangeEntry;
import com.github.foxtrek_64.HawkEye2.entry.BlockEntry;
import com.github.foxtrek_64.HawkEye2.entry.SignEntry;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bags.BlockBag;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitWorld;

public class HawkSession extends EditSession {

	private LocalPlayer player;

	public HawkSession(LocalWorld world, int maxBlocks, LocalPlayer player) {
		super(world, maxBlocks);
		this.player = player;
	}

	public HawkSession(LocalWorld world, int maxBlocks, BlockBag blockBag, LocalPlayer player) {
		super(world, maxBlocks, blockBag);
		this.player = player;
	}

	@Override
	public boolean rawSetBlock(Vector v, BaseBlock block) {
		World world = ((BukkitWorld) player.getWorld()).getWorld();
		BlockState bs = null;
		int b = world.getBlockTypeIdAt(v.getBlockX(), v.getBlockY(), v.getBlockZ());
		int bdata = world.getBlockAt(v.getBlockX(), v.getBlockY(), v.getBlockZ()).getData();

		//We need to get the blockstate BEFORE "rawSetBlock" to properly log signs!
		if (b == 63 || b == 68) {
			bs = world.getBlockAt(v.getBlockX(), v.getBlockY(), v.getBlockZ()).getState();
		}

		if (super.rawSetBlock(v, block)) {
			Location loc = new Location(world, v.getBlockX(), v.getBlockY(), v.getBlockZ());

			if (block.getType() != 0) {
					DataManager.addEntry(new BlockChangeEntry(player.getName(), DataType.WORLDEDIT_PLACE, loc, b, bdata, block.getType(), block.getData()));
			} else {
				if ((b == 63 || b == 68) && DataType.SIGN_BREAK.isLogged()) {
					DataManager.addEntry(new SignEntry(player.getName(), DataType.SIGN_BREAK, bs));
				} else
					DataManager.addEntry(new BlockEntry(player.getName(), DataType.WORLDEDIT_BREAK, b, bdata, loc));
			}
			return true;
		}
		return false;
	}
}
