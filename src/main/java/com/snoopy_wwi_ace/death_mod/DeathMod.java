package com.snoopy_wwi_ace.death_mod;

import com.snoopy_wwi_ace.proxies.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = DeathMod.MODID, version = DeathMod.VERSION, acceptableRemoteVersions = "*", name = DeathMod.NAME)
public class DeathMod {
	
	public static final String MODID = "death_mod";
	public static final String VERSION = "0.7b";
	public static final String NAME = "Death Mod";
	
	@SidedProxy(clientSide="com.snoopy_wwi_ace.proxies.CommonProxy", serverSide="com.snoopy_wwi_ace.proxies.ServerProxy")
	public static CommonProxy proxy;
	
	public static Configuration config;
	public static LocationFinder locFinder;
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent e) {
		config = new Configuration(e.getSuggestedConfigurationFile());
		locFinder = ConfigHandler.synchConfig(config);
		proxy.preInit(e);
    }
        
    @EventHandler
    public void init(FMLInitializationEvent e) {
    	proxy.init(e);
    }
        
    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    	proxy.postInit(e);
    }

}
