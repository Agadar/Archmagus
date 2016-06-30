package com.github.agadar.archmagus.eventhandler;

import java.util.Random;

import com.github.agadar.archmagus.Archmagus;
import com.github.agadar.archmagus.items.ItemSpell;
import com.github.agadar.archmagus.items.ItemSpellBook;
import com.github.agadar.archmagus.spell.SpellData;
import com.github.agadar.archmagus.spell.Spells;

import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** For making mobs drop spell books and preventing dead players from dropping spell items. */
public class HandlerBookDropEvents 
{
	@SubscribeEvent
	public void OnLivingDrops (LivingDropsEvent event)
	{		
		// 5% chance to drop from a regular mob.
		if (event.entity instanceof EntityMob)
		{
			int randResult = event.entity.worldObj.rand.nextInt(100);

			if (randResult < 5)
			{
				ItemStack spellBook = getRandomSpellBookStack(event.entity.worldObj.rand);			
				event.drops.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, spellBook));
			}			
		}
		// 100% chance to drop from a boss.
		else if (event.entity instanceof IBossDisplayData)
		{
			ItemStack spellBook = getRandomSpellBookStack(event.entity.worldObj.rand);
			event.drops.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, spellBook));
		}
	}
	
	/**
	 * Removes all ItemSpells from the dropped items on player death.
	 *
	 * @param event
	 */
	@SubscribeEvent
	public void OnPlayerDrops(PlayerDropsEvent event)
	{
		for (int i = 0; i < event.drops.size(); i++)
		{
			if ((event.drops.get(i).getEntityItem().getItem() instanceof ItemSpell))
			{
				event.drops.remove(i);
				i--;
			}
		}
	}
	
	/**
	 * Prevents players from dumping ItemSpells out of their inventory.
	 *
	 * @param event
	 */
	@SubscribeEvent
	public void OnItemToss(ItemTossEvent event)
	{
		if (event.entityItem.getEntityItem().getItem() instanceof ItemSpell)
			event.setCanceled(true);
	}
	
	/**
	 * Returns a random level 1 spell book.
	 *
	 * @param r
	 * @return
	 */
	private ItemStack getRandomSpellBookStack(Random r)
	{
		int randomIndex = r.nextInt(Spells.getNextIndex());
		SpellData spellData = new SpellData(Spells.spellList[randomIndex], (short)1);
		return ((ItemSpellBook) Archmagus.spell_book).getSpellItemStack(spellData);
	}
}
