package com.github.foxtrek_64.HawkEye2.undoData;

import org.bukkit.block.BlockState;

import com.github.foxtrek_64.HawkEye2.blocks.HawkBlockType;

public class UndoBlock {

	protected BlockState state;

	public UndoBlock(BlockState state) {
		this.state = state;
	}

	public void undo() {
		if (state != null) {
			final int id = state.getTypeId();
			final int data = state.getData().getData();
			HawkBlockType.getHawkBlock(id).Restore(state.getBlock(), id, data);
		}
	}

	public BlockState getState() {
		return state;
	}
}
