package com.agadar.archmagus.eventhandler;

import com.agadar.archmagus.Archmagus;
import com.agadar.archmagus.entity.ISummoned;
import com.agadar.archmagus.misc.ManaProperties;
import com.agadar.archmagus.misc.MaxManaMessage;
import com.agadar.archmagus.spell.summon.SpellSummon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

/** Handles mana-related events. */
public class HandlerManaEvents 
{	
	/**
	 * When a player joins a world server-side, send a message from server-> client to inform client of player's maximum mana.
	 * @param event
	 */
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		
		if (event.entity instanceof EntityPlayerMP) {			
			Archmagus.networkWrapper.sendTo(new MaxManaMessage((ManaProperties.get((EntityPlayer) event.entity).getMaxMana())), (EntityPlayerMP) event.entity);
		}
	}
	
	/**
	 * When a player is cloned after spawning/dying/dimensional traveling, also clone the mana properties.
	 * @param event
	 */
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) 
	{ 
		ManaProperties.get(event.entityPlayer).copy(ManaProperties.get(event.original));
	}
	
	/**
	 * When a player is first constructed, register his mana properties.
	 * @param event
	 */
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {

		if (event.entity instanceof EntityPlayer && ManaProperties.get((EntityPlayer) event.entity) == null)			
			ManaProperties.register((EntityPlayer) event.entity);
	}
	
	/**
	 * Handles natural mana regeneration after every tick.
	 * @param event
	 */
	@SubscribeEvent
	public void onPlayerPostTick(PlayerTickEvent event)
	{
		if (event.phase != TickEvent.Phase.END || event.side != Side.SERVER || event.player.getFoodStats().getFoodLevel() < 18)
			return;

		ManaProperties prop = ManaProperties.get(event.player);
		
		if (prop.getCurrentMana() >= prop.getMaxMana())
			return;

		prop.manaTimer++;

		if (prop.manaTimer >= 60)
		{
			prop.replenishMana(1);
			prop.manaTimer = 0;
		}
	}
	
	@SubscribeEvent
	public void onEntityTravelToDimension(EntityTravelToDimensionEvent event)
	{
		// If the entity is a summoned entity, then cancel the travel.
		if (event.entity instanceof ISummoned)
			event.setCanceled(true);
		
		// Else if the entity is the player, then kill his summoned minions before travel.
		else if (event.entity instanceof EntityPlayer)
			SpellSummon.killExistingMinions(event.entity.worldObj, (EntityPlayer) event.entity);
	}
}
