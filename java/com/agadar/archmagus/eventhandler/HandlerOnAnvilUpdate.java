package com.agadar.archmagus.eventhandler;

import com.agadar.archmagus.itemblock.ItemSpellBook;
import com.agadar.archmagus.itemblock.ModItemsBlocks;

import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** For making spell books combineable in anvils. */
public class HandlerOnAnvilUpdate 
{
	@SubscribeEvent
	public void OnAnvilUpdate(AnvilUpdateEvent event)
	{
		if (event.left.getItem().equals(ModItemsBlocks.spell_book) && event.right.getItem().equals(ModItemsBlocks.spell_book))
		{
			event.output = ((ItemSpellBook) ModItemsBlocks.spell_book).tryCombine(event.left, event.right);
			event.cost = 10;
		}
	}
}
