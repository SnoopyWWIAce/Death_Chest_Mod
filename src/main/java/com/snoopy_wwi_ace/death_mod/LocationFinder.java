package com.snoopy_wwi_ace.death_mod;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class LocationFinder {
	
	private int horizontalSearch;
	private int verticalSearch;
	private String[] validBlocks;
	
	public LocationFinder(int horSearch, int verSearch, String[] overrideBlocks) {
		horizontalSearch = horSearch;
		verticalSearch = verSearch;
		validBlocks = overrideBlocks;
	}
	
	public BlockPos getLocation(BlockPos playerPos, World world) {
		if(playerPos.y <= 0)
			playerPos.y = 1;
		BlockPos chestPos = null;
		BlockPos current;
		for(int offsetX = -1 * horizontalSearch; offsetX <= horizontalSearch; offsetX ++)
			for(int offsetY = -1 * verticalSearch; offsetY <= verticalSearch; offsetY ++)
				for(int offsetZ = -1 * horizontalSearch; offsetZ <= horizontalSearch; offsetZ ++) {
					current = BlockPos.add(playerPos, new BlockPos(offsetX, offsetY, offsetZ));
					if(isBlockPosValid(current, playerPos, chestPos, world))
						chestPos = current;
				}
		return chestPos;
	}
	
	private boolean isBlockPosValid(BlockPos blockPos, BlockPos origin, BlockPos previousPos, World world) {
		if(previousPos == null)
			return blockInList(world.getBlock(blockPos.x, blockPos.y, blockPos.z).getUnlocalizedName()) && blockPos.y > 0;
		else
			return blockInList(world.getBlock(blockPos.x, blockPos.y, blockPos.z).getUnlocalizedName()) 
					&& BlockPos.getDistance(origin, blockPos) < BlockPos.getDistance(origin, previousPos);
	}
	
	private boolean blockInList(String blockID) {
		for(String current : validBlocks)
			if(blockID.compareTo(current) == 0)
				return true;
		return false;
	}

}
