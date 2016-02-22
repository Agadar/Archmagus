package com.agadar.archmagus.eventhandler;

import com.agadar.archmagus.itemblock.ItemSpellBook;
import com.agadar.archmagus.itemblock.ModItemsBlocks;
import com.agadar.archmagus.spell.Spell;
import com.agadar.archmagus.spell.SpellData;
import com.agadar.archmagus.spell.Spells;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** For making mobs drop spell books. */
public class HandlerOnLivingDrops 
{
	@SubscribeEvent
	public void OnLivingDrops (LivingDropsEvent event)
	{		
		Class<? extends Entity> entityClass = event.entity.getClass();

		if (entityClass.equals(EntityBlaze.class))
		{
			randomDrop(event, Spells.blazefire, (short) 1, 6);
			randomDrop(event, Spells.blazestorm, (short) 1, 4);
			randomDrop(event, Spells.fireShield, (short) 1, 6);
		}
		else if (entityClass.equals(EntityGhast.class))
		{
			randomDrop(event, Spells.ghastfire, (short) 1, 15);
			randomDrop(event, Spells.fireShield, (short) 1, 10);
		}
		else if (entityClass.equals(EntityWither.class))
		{
			randomDrop(event, Spells.witherblast, (short) 1, 100);
		}
		else if (entityClass.equals(EntityCreeper.class))
		{
			randomDrop(event, Spells.lightningstorm, (short) 1, 5);
			randomDrop(event, Spells.stormShield, (short) 1, 5);
		} 
		else if (entityClass.equals(EntityZombie.class))
		{
			randomDrop(event, Spells.raise_zombie, (short) 1, 5);
			randomDrop(event, Spells.raise_zombie_horse, (short) 1, 5);
			randomDrop(event, Spells.earthShield, (short) 1, 5);
		}
		else if (entityClass.equals(EntityPigZombie.class))
		{
			randomDrop(event, Spells.raise_zombie_pigman, (short) 1, 5);
			randomDrop(event, Spells.raise_zombie_horse, (short) 1, 5);
			randomDrop(event, Spells.earthShield, (short) 1, 5);
		}
		else if (entityClass.equals(EntitySkeleton.class))
		{
			int skeletonType = ((EntitySkeleton) event.entity).getSkeletonType();
			
			if (skeletonType == 0)
			{
				randomDrop(event, Spells.raise_skeleton, (short) 1, 5);
				randomDrop(event, Spells.raise_skeleton_horse, (short) 1, 5);
			}
			else if (skeletonType == 1)
			{
				randomDrop(event, Spells.raise_wither_skeleton, (short) 1, 5);
				//randomDrop(event, Spells.raise_wither_horse, (short) 1, 5);
			}
		}
		else if (entityClass.equals(EntityWolf.class))
		{
			randomDrop(event, Spells.summon_wolf, (short) 1, 5);
			randomDrop(event, Spells.frostShield, (short) 1, 5);
		}
		else if (entityClass.equals(EntityWitch.class))
		{
			randomDrop(event, Spells.summon_witch, (short) 1, 15);
		}
		else if (entityClass.equals(EntitySpider.class))
		{
			randomDrop(event, Spells.summon_spider, (short) 1, 5);
		}
		else if (entityClass.equals(EntityCaveSpider.class))
		{
			randomDrop(event, Spells.summon_cave_spider, (short) 1, 5);
		}
		else if (entityClass.equals(EntityEnderman.class))
		{
			randomDrop(event, Spells.teleport, (short) 1, 10);
			randomDrop(event, Spells.respawn, (short) 1, 5);
		}
		else if (entityClass.equals(EntitySquid.class))
		{
			randomDrop(event, Spells.waterShield, (short) 1, 5);
		}
		else if (entityClass.equals(EntitySnowman.class))
		{
			randomDrop(event, Spells.frostShield, (short) 1, 5);
		}
	}
	
	/**
	 * @param event A reference to the LivingDropsEvent.
	 * @param spell The spell which the randomly dropped spell book will have.
	 * @param level The level of the spell which the randomly dropped spell book will have.
	 * @param percentage The chance that the spell book will be dropped.
	 */
	private void randomDrop (LivingDropsEvent event, Spell spell, short level, double percentage)
	{
		double randResult = event.entity.worldObj.rand.nextDouble() * 100;
		
		if (randResult < percentage)
		{
			SpellData spellData = new SpellData(spell, level);
			ItemStack spellBookDrop = ((ItemSpellBook) ModItemsBlocks.spell_book).getSpellItemStack(spellData);
			event.drops.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, spellBookDrop));
		}
	}
}
