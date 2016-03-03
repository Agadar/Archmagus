package com.agadar.archmagus.items.meshdefinitions;

import java.util.HashMap;
import java.util.Map;

import com.agadar.archmagus.Archmagus;
import com.agadar.archmagus.items.ItemSpellBook;
import com.agadar.archmagus.spell.Spell;
import com.agadar.archmagus.spell.SpellData;
import com.agadar.archmagus.spell.Spells;

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
	/** The default icon for spellbooks. */
	public final ModelResourceLocation defaultLoc = new ModelResourceLocation(Archmagus.MODID + ":spell_book", "inventory");
	/** ModelResourceLocations mapped to spell id's. */
	public final Map<Integer, ModelResourceLocation> modelLocations = new HashMap<Integer, ModelResourceLocation>();
	
	public SpellBookMeshDefinition()
	{
		// Iterate over the spells, using each spell's getModelResourceLocationString() to register a new ModelResourceLocation.
		for (Spell s : Spells.spellList)
		{
			if (s == null)
				continue;
			ModelResourceLocation loc = new ModelResourceLocation(s.getModelResourceLocationString());
			modelLocations.put(s.effectId, loc);	
		}
	}

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack)
	{
		ModelResourceLocation loc = null;
		
		if (stack.hasTagCompound())
		{
			SpellData sd = SpellData.readFromNBTTagCompound(((ItemSpellBook)Archmagus.spell_book).getSpellTag(stack));
			loc = modelLocations.get(sd.spellObj.effectId);
		}
		
		return loc == null ? defaultLoc : loc;
	}
}
