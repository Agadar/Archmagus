package com.github.agadar.archmagus.items;

import com.github.agadar.archmagus.Archmagus;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemManaCrystal extends Item 
{
	public final String Name = "mana_crystal";
	
	public ItemManaCrystal()
	{
		super();
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setUnlocalizedName(Archmagus.MODID + "_" + Name);
        GameRegistry.registerItem(this, Name);
	}
}
