package com.snoopy_wwi_ace.death_mod;

import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigHandler {
	
	public static final String HORIZONTAL_SEARCH_LIMIT = "horizontal_search_limit";
	public static final int HORIZONTAL_DEFAULT = 4;
	public static final String HORIZONTAL_COMMENT = "the distance in x/z coords that the mod will search for valid chest positions.";
	public static final String VERTICAL_SEARCH_LIMIT = "vertical_search_limit";
	public static final int VERTICAL_DEFAULT = 2;
	public static final String VERTICAL_COMMENT = "the distance in y coords that the mod will search for valid chest positions.";
	public static final String VALID_BLOCKS = "valid_blocks";
	public static final String[] VALID_BLOCKS_DEFAULT = {
			"tile.air",
			"tile.water",
			"tile.lava",
			"tile.leaves",
			"tile.tallgrass",
			"tile.deadbush",
			"tile.flower1",
			"tile.flower2",
			"tile.mushroom",
			"tile.torch",
			"tile.fire",
			"tile.crops",
			"tile.snow",
			"tile.reeds"};
	public static final String VALID_BLOCKS_COMMENT = "the blocks that may be overridden to place a chest.";
	
	private static LocationFinder locFinder = null;
	
	public static LocationFinder synchConfig(Configuration config) {
    	try {
    		config.load();
    		Property horizontalSearchLimit = config.get(config.CATEGORY_GENERAL, HORIZONTAL_SEARCH_LIMIT, HORIZONTAL_DEFAULT, HORIZONTAL_COMMENT);
    		Property verticalSearchLimit = config.get(config.CATEGORY_GENERAL, VERTICAL_SEARCH_LIMIT, VERTICAL_DEFAULT, VERTICAL_COMMENT);
    		Property validBlocks = config.get(config.CATEGORY_GENERAL, VALID_BLOCKS, VALID_BLOCKS_DEFAULT, VALID_BLOCKS_COMMENT);
    		locFinder = new LocationFinder(horizontalSearchLimit.getInt(), verticalSearchLimit.getInt(), validBlocks.getStringList());
    	} catch (Exception e) {
    		//Problem reading config, I should probably handle this...
    	} finally {
    		if(config.hasChanged())
    			config.save();
    	}
    	return locFinder;
    }

}
