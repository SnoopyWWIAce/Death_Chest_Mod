package com.snoopy_wwi_ace.death_mod;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class DeathHandler {

	private static final int ARMOR_SLOTS = 4;
	private static final Block CHEST = Block.getBlockById(54);
	private static final int UPDATE_CLIENTS_AND_BLOCKS = 3;
	private static final int SEARCH_RANGE = 4;
	private static final int MAX_HEIGHT = 255;
	private static final int AIR = 0;
	private static final int BEDROCK = 7;
	private static final String KEEP_INVENTORY = "keepInventory";

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onDeathEvent(LivingDeathEvent event) {
		if (event.entity instanceof EntityPlayer
				&& !event.entity.worldObj.getGameRules().getGameRuleBooleanValue(KEEP_INVENTORY)) {
			logEntry("Player " + ((EntityPlayer)event.entity).getDisplayName() + " has died, analyzing inventory...");
			handleInventory((EntityPlayer) event.entity);
			logEntry("Finished creating death chest(s) for player " + ((EntityPlayer)event.entity).getDisplayName());
		}
	}

	private void handleInventory(EntityPlayer player) {
		ArrayList<ItemStack> stacks = gatherInventory(player);
		if (!stacks.isEmpty()) {
			TileEntityChest chest = findChestLocation(player);
			if (chest == null) {
				for (ItemStack current : stacks)
					player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, current));
				stacks.clear();
				return;
			}
			chest.func_145976_a(player.getDisplayName()); //Set the chest's name to the player's name.
			transferInventory(chest, stacks);
			logEntry("Created chest for " + player.getDisplayName() + " at " + chest.xCoord + " " + chest.yCoord + " " + chest.zCoord);
			if (!stacks.isEmpty()) {
				chest = findChestLocation(player);
				if (chest == null) {
					for (ItemStack current : stacks)
						player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, current));
					stacks.clear();
					return;
				}
				chest.func_145976_a(player.getDisplayName()); //Set the chest's name to the player's name.
				transferInventory(chest, stacks);
				logEntry("Created chest for " + player.getDisplayName() + " at " + chest.xCoord + " " + chest.yCoord + " " + chest.zCoord);
			}
		}
	}
	
	private void transferInventory(TileEntityChest chest, ArrayList<ItemStack> stacks) {
		for (int i = 0; i < chest.getSizeInventory() && !stacks.isEmpty(); i++) {
			chest.setInventorySlotContents(i, stacks.get(stacks.size() - 1));
			stacks.remove(stacks.size() - 1);
		}
	}

	private ArrayList<ItemStack> gatherInventory(EntityPlayer player) {
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		InventoryPlayer inv = player.inventory;
		for (int i = 0; i < inv.getSizeInventory() - ARMOR_SLOTS; i++)
			if (inv.mainInventory[i] != null) {
				stacks.add(inv.mainInventory[i]);
				inv.mainInventory[i] = null;
			}
		for (int i = 0; i < ARMOR_SLOTS; i++)
			if (inv.armorInventory[i] != null) {
				stacks.add(inv.armorInventory[i]);
				inv.armorInventory[i] = null;
			}
		return stacks;
	}
	
	private TileEntityChest findChestLocation(EntityPlayer player) {
		/*if(x < 0)
			x -= 1;
		if(z < 0)
			z -= 1;
		y = cappedY((int)y);*/
		BlockPos pos = DeathMod.locFinder.getLocation(new BlockPos(player), player.worldObj);
		if(pos == null)
			return null;
		Block replacedBlock = player.worldObj.getBlock(pos.x, pos.y, pos.z);
		player.worldObj.setBlock(pos.x, pos.y, pos.z, CHEST, 0, UPDATE_CLIENTS_AND_BLOCKS);
		TileEntityChest chest = (TileEntityChest) player.worldObj.getTileEntity(pos.x, pos.y, pos.z);
		/*if(replacedBlock != Block.getBlockById(AIR) && replacedBlock != Block.getBlockById(BEDROCK) && !(replacedBlock instanceof BlockLiquid)) {
			int data = player.worldObj.getBlockMetadata(pos.x, pos.y, pos.z);
			ItemStack replacement = new ItemStack(replacedBlock, 1, data);
			chest.setInventorySlotContents(0, replacement);
		}*/
		return chest;
	}
	
	private void logEntry(String entry) {
		System.out.println(entry);
	}

}
