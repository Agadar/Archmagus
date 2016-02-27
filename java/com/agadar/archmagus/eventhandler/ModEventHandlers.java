package com.agadar.archmagus.eventhandler;

import net.minecraftforge.common.MinecraftForge;

/** Responsible for registering this mod's event handlers.  */
public class ModEventHandlers 
{
	/** Registers this mod's event handlers. */
	public static void registerModEventHandlers() 
	{
		/** For handling mana-related events. */
		MinecraftForge.EVENT_BUS.register(new HandlerManaEvents());
		
		/** For making mobs drop spell books. */
		MinecraftForge.EVENT_BUS.register(new HandlerOnLivingDrops());
		 
		/** For making spell books combineable in anvils. */
		MinecraftForge.EVENT_BUS.register(new HandlerOnAnvilUpdate());
		
		/** For applying magical shield effects and the potions effects they apply. */
		MinecraftForge.EVENT_BUS.register(new HandlerOnLivingAttack());
		
		/** For spawning particles around entities holding spell books. */
		//MinecraftForge.EVENT_BUS.register(new HandlerOnLivingUpdate());
		
		/** For altering the player's model in case he is polymorphed. */
		//MinecraftForge.EVENT_BUS.register(new HandlerOnRenderLiving());
		
		/** For hiding the player's hand in case he is polymorphed. */
		//MinecraftForge.EVENT_BUS.register(new HandlerOnRenderHand());
	}
}
