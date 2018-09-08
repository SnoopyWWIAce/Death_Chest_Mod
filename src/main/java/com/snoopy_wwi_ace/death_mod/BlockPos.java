package com.snoopy_wwi_ace.death_mod;

import net.minecraft.entity.Entity;

public class BlockPos {
	
	public int x;
	public int y;
	public int z;
	
	public BlockPos(int xIn, int yIn, int zIn) {
		x = xIn;
		y = yIn;
		z = zIn;
	}
	
	public BlockPos(Entity entity) {
		x = (int)entity.posX;
		y = (int)entity.posY;
		z = (int)entity.posZ;
	}

	@Override
	public String toString() {
		return x + ", " + y + ", " + z;
	}
	
	public static BlockPos add(BlockPos first, BlockPos second) {
		return new BlockPos(first.x + second.x, first.y + second.y, first.z + second.z);
	}
	
	public static BlockPos subtract(BlockPos first, BlockPos second) {
		return new BlockPos(first.x - second.x, first.y - second.y, first.z - second.z);
	}
	
	public static double getDistance(BlockPos first, BlockPos second) {
		BlockPos squared = subtract(first, second);
		return Math.sqrt(squared.x * squared.x + squared.y * squared.y + squared.z * squared.z);
	}

}
