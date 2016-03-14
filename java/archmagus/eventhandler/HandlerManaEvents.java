package archmagus.eventhandler;

import archmagus.Archmagus;
import archmagus.entity.ISummoned;
import archmagus.network.ManaProperties;
import archmagus.network.SpellProperties;
import archmagus.network.message.MaxManaMessage;
import archmagus.network.message.SpellsMessage;
import archmagus.spell.summon.SpellSummon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

/** Handles mana and spell properties related events. */
public class HandlerManaEvents 
{	
	/**
	 * When a player joins a world server-side, send a message from server-> client to inform client of player's maximum mana.
	 * @param event
	 */
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		
		if (event.entity instanceof EntityPlayerMP) 
		{		
			// Mana
			Archmagus.networkWrapper.sendTo(new MaxManaMessage((ManaProperties.get((EntityPlayer) event.entity).getMaxMana())), (EntityPlayerMP) event.entity);
			// Spells
			SpellProperties spellProp = SpellProperties.get((EntityPlayer) event.entity);
			NBTTagCompound comp = new NBTTagCompound();
			spellProp.saveNBTData(comp);
			Archmagus.networkWrapper.sendTo(new SpellsMessage(comp), (EntityPlayerMP) event.entity);
		}
	}
	
	/**
	 * When a player is cloned after spawning/dying/dimensional traveling, also clone the mana and spell properties.
	 * @param event
	 */
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) 
	{ 
		ManaProperties.get(event.entityPlayer).copy(ManaProperties.get(event.original));
		SpellProperties.get(event.entityPlayer).copy(SpellProperties.get(event.original));
	}
	
	/**
	 * When a player is first constructed, register his mana and spell properties.
	 * @param event
	 */
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {

		if (event.entity instanceof EntityPlayer)
		{
			if (ManaProperties.get((EntityPlayer) event.entity) == null)			
				ManaProperties.register((EntityPlayer) event.entity);
		
			if (SpellProperties.get((EntityPlayer) event.entity) == null)
				SpellProperties.register((EntityPlayer) event.entity);
		}
	}
	
	/**
	 * Handles natural mana regeneration and spell cooldowns after every tick.
	 * @param event
	 */
	@SubscribeEvent
	public void onPlayerPostTick(PlayerTickEvent event)
	{
		if (event.phase != TickEvent.Phase.END || event.side != Side.SERVER)
			return;

		// Mana
		if (event.player.getFoodStats().getFoodLevel() >= 18)
		{
			ManaProperties prop = ManaProperties.get(event.player);
			
			if (prop.getCurrentMana() < prop.getMaxMana())
			{
				prop.manaTimer++;
		
				if (prop.manaTimer >= 60)
				{
					prop.replenishMana(1);
					prop.manaTimer = 0;
				}
			}
		}
		
		// Spell cooldowns
		SpellProperties.get(event.player).tickCooldowns();
	}
	
	/**
	 * For killing off the player's minions when he travels to another dimension.
	 *
	 * @param event
	 */
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
