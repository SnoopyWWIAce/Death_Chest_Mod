package com.snoopy_wwi_ace.proxies;

import com.snoopy_wwi_ace.death_mod.DeathHandler;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {

	}

	public void init(FMLInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new DeathHandler());
	}

	public void postInit(FMLPostInitializationEvent e) {

	}
}
