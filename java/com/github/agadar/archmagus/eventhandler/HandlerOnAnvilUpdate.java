package com.github.agadar.archmagus.eventhandler;

import com.github.agadar.archmagus.Archmagus;
import com.github.agadar.archmagus.items.ItemSpellBook;
import com.github.agadar.archmagus.spell.SpellData;

import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** Event handler for making spell books combineable in anvils. */
public class HandlerOnAnvilUpdate 
{
	@SubscribeEvent
	public void OnAnvilUpdate(AnvilUpdateEvent event)
	{
		if (event.left.getItem().equals(Archmagus.spell_book) && event.right.getItem().equals(Archmagus.spell_book))
		{
			ItemSpellBook spell_book = ((ItemSpellBook) Archmagus.spell_book);
			event.output = spell_book.tryCombine(event.left, event.right);
			
			if (event.output != null)
			{
				int levelLeft = SpellData.readFromNBTTagCompound(spell_book.getSpellTag(event.left)).spellLevel;
				int levelRight = SpellData.readFromNBTTagCompound(spell_book.getSpellTag(event.right)).spellLevel;
				event.cost = (levelLeft + levelRight) * 5;		
			}
		}
	}
}
