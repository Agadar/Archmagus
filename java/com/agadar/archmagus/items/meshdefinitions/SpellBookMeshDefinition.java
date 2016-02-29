package com.agadar.archmagus.items.meshdefinitions;

import java.util.HashMap;
import java.util.Map;

import com.agadar.archmagus.Archmagus;
import com.agadar.archmagus.items.ItemSpellBook;
import com.agadar.archmagus.spell.SpellData;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Required for allowing ItemSpellBook to have multiple possible textures.
 */
@SideOnly(Side.CLIENT)
public class SpellBookMeshDefinition implements ItemMeshDefinition
{
	// The 'empty' spell book.
	public final ModelResourceLocation spell_book = new ModelResourceLocation(Archmagus.MODID + ":spell_book", "inventory");
	
	// The spell books.
	public final ModelResourceLocation blazefire_book = new ModelResourceLocation(Archmagus.MODID + ":blazefire_book", "inventory");
	public final ModelResourceLocation cave_spider_book = new ModelResourceLocation(Archmagus.MODID + ":cave_spider_book", "inventory");
	public final ModelResourceLocation earth_shield_book = new ModelResourceLocation(Archmagus.MODID + ":earth_shield_book", "inventory");
	public final ModelResourceLocation fire_shield_book = new ModelResourceLocation(Archmagus.MODID + ":fire_shield_book", "inventory");
	public final ModelResourceLocation frost_shield_book = new ModelResourceLocation(Archmagus.MODID + ":frost_shield_book", "inventory");
	public final ModelResourceLocation lightning_book = new ModelResourceLocation(Archmagus.MODID + ":lightning_book", "inventory");
	public final ModelResourceLocation sheep_book = new ModelResourceLocation(Archmagus.MODID + ":sheep_book", "inventory");
	public final ModelResourceLocation skeleton_book = new ModelResourceLocation(Archmagus.MODID + ":skeleton_book", "inventory");
	public final ModelResourceLocation spider_book = new ModelResourceLocation(Archmagus.MODID + ":spider_book", "inventory");	
	public final ModelResourceLocation storm_shield_book = new ModelResourceLocation(Archmagus.MODID + ":storm_shield_book", "inventory");
	public final ModelResourceLocation teleport_book = new ModelResourceLocation(Archmagus.MODID + ":teleport_book", "inventory");
	public final ModelResourceLocation water_shield_book = new ModelResourceLocation(Archmagus.MODID + ":water_shield_book", "inventory");
	public final ModelResourceLocation witch_book = new ModelResourceLocation(Archmagus.MODID + ":witch_book", "inventory");
	public final ModelResourceLocation wither_book = new ModelResourceLocation(Archmagus.MODID + ":wither_book", "inventory");
	public final ModelResourceLocation wolf_book = new ModelResourceLocation(Archmagus.MODID + ":wolf_book", "inventory");
	public final ModelResourceLocation zombie_book = new ModelResourceLocation(Archmagus.MODID + ":zombie_book", "inventory");
	public final ModelResourceLocation zombie_pigman_book = new ModelResourceLocation(Archmagus.MODID + ":zombie_pigman_book", "inventory");

	// Spell books mapped to integers. These integers are expected to correspond to spell id's.
	public final Map<Integer, ModelResourceLocation> spellsToResources = new HashMap<Integer, ModelResourceLocation>(){/** */
		private static final long serialVersionUID = 1L;
	{
		int nextIndex = 0;		
		put(nextIndex++, blazefire_book);
		put(nextIndex++, blazefire_book);
		put(nextIndex++, wither_book);
		put(nextIndex++, wolf_book);
		put(nextIndex++, skeleton_book);
		put(nextIndex++, wither_book);
		put(nextIndex++, zombie_book);
		put(nextIndex++, zombie_pigman_book);
		put(nextIndex++, witch_book);
		put(nextIndex++, spider_book);
		put(nextIndex++, cave_spider_book);
		put(nextIndex++, teleport_book);
		put(nextIndex++, teleport_book);
		put(nextIndex++, fire_shield_book);
		put(nextIndex++, earth_shield_book);
		put(nextIndex++, water_shield_book);
		put(nextIndex++, storm_shield_book);
		put(nextIndex++, frost_shield_book);
		put(nextIndex++, blazefire_book);
		put(nextIndex++, lightning_book);
		put(nextIndex++, zombie_book);
		put(nextIndex++, skeleton_book);
	}};

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack)
	{
		if (stack.hasTagCompound())
		{
			SpellData sd = SpellData.readFromNBTTagCompound(((ItemSpellBook)Archmagus.spell_book).getSpellTag(stack));
			return spellsToResources.get(sd.spellObj.effectId);
		}
		return spell_book;		
	}
}
