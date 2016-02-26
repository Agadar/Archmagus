package com.agadar.archmagus.eventhandler;

import com.agadar.archmagus.misc.ManaProperties;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

/** For naturally regenerating mana. */
public class HandlerOnPlayerPostTick 
{
	@SubscribeEvent
	public void onPlayerPostTick(PlayerTickEvent event)
	{
		if (event.phase != TickEvent.Phase.END || event.side != Side.SERVER || event.player.getFoodStats().getFoodLevel() < 18)
			return;

		ManaProperties prop = ManaProperties.get(event.player);
		
		if (prop == null)
			return;

		if (prop.getCurrentMana() >= prop.getMaxMana())
			return;

		prop.manaTimer++;

		if (prop.manaTimer >= 60)
		{
			prop.replenishMana(1);
			prop.manaTimer = 0;
		}
	}
}
