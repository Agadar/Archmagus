package com.agadar.archmagus;

import com.agadar.archmagus.entity.ModEntities;
import com.agadar.archmagus.eventhandler.ModEventHandlers;
import com.agadar.archmagus.itemblock.ModItemsBlocks;
import com.agadar.archmagus.misc.ManaCrystalGen;
import com.agadar.archmagus.misc.MaxManaMessage;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Archmagus.MODID, version = Archmagus.VERSION, name = Archmagus.NAME)
public class Archmagus 
{	
	@Instance(value = Archmagus.NAME)
	public static Archmagus instance;

	@SidedProxy(clientSide = Archmagus.CLIENTSIDE, serverSide = Archmagus.SERVERSIDE)
	public static CommonProxy proxy;
	
	/* These are the references we use. These values are the same for our entire mod, so we only have to make
	them once here, and we can always access them. */
	public static final String MODID = "archmagus";
	public static final String VERSION = "0.7.0";
	public static final String NAME = "Archmagus";
	public static final String CLIENTSIDE = "com.agadar.archmagus.ClientProxy";
	public static final String SERVERSIDE = "com.agadar.archmagus.CommonProxy";
	
	/** The message channel for this mod. */
	public static SimpleNetworkWrapper networkWrapper;
	/** The mana crystal ore generator. */
	ManaCrystalGen manaCrystalGen = new ManaCrystalGen();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{		
		/** Register the network stuff. */
		networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		networkWrapper.registerMessage(MaxManaMessage.Handler.class, MaxManaMessage.class, 0, Side.CLIENT);
		
		/** Register the mod stuff. */
		ModItemsBlocks.registerModItemsAndBlocks();	
		ModEntities.registerModEntities();	
		ModEventHandlers.registerModEventHandlers();
		GameRegistry.registerWorldGenerator(manaCrystalGen, 0);
		
		/** Register the client-only stuff. */
		proxy.registerRenderers();
	}
}
