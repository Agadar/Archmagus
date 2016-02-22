package com.agadar.archmagus.eventhandler;

import com.agadar.archmagus.Archmagus;
import com.agadar.archmagus.CommonProxy;
import com.agadar.archmagus.misc.ManaProperties;
import com.agadar.archmagus.misc.MaxManaMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** Handles mana-related events. */
public class HandlerManaEvents 
{
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event) {

		if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
			
			// create a new NBT Tag Compound to store the IExtendedEntityProperties data
			NBTTagCompound playerData = new NBTTagCompound();
			
			// write the data to the new compound
			event.entity.getExtendedProperties(ManaProperties.NAME).saveNBTData(playerData);
			
			// and store it in our proxy
			CommonProxy.storeEntityData(((EntityPlayer) event.entity).getDisplayName() + ManaProperties.NAME, playerData);
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		
		if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {

			// before syncing the properties, we must first check if the player
			// has some saved in the proxy
			// recall that 'getEntityData' also removes it from the map, so be
			// sure to store it locally
			NBTTagCompound playerData = CommonProxy.getEntityData(((EntityPlayer) event.entity).getDisplayName() + ManaProperties.NAME);
			
			// If there was a compound saved, load the data back to the player.
			if (playerData != null) {
				event.entity.getExtendedProperties(ManaProperties.NAME).loadNBTData(playerData);
				
				// Restore mana to full
				ManaProperties.get((EntityPlayer) event.entity).replenishMana();
			}

			// finally, we sync the data between server and client
			Archmagus.networkWrapper.sendTo(new MaxManaMessage(ManaProperties.get((EntityPlayer) event.entity).getMaxMana()), (EntityPlayerMP) event.entity);
		}
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		/*
		 * Be sure to check if the entity being constructed is the correct type
		 * for the extended properties you're about to add! The null check may
		 * not be necessary - I only use it to make sure properties are only
		 * registered once per entity
		 */
		if (event.entity instanceof EntityPlayer && ManaProperties.get((EntityPlayer) event.entity) == null)
			// This is how extended properties are registered using our
			// convenient method from earlier
			ManaProperties.register((EntityPlayer) event.entity);
	}
}
