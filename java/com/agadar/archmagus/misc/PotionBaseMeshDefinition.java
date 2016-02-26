package com.agadar.archmagus.misc;

import com.agadar.archmagus.Archmagus;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class PotionBaseMeshDefinition implements ItemMeshDefinition
{
	public final ModelResourceLocation drinkable = new ModelResourceLocation(Archmagus.MODID + ":bottle_drinkable",
			"inventory");
	public final ModelResourceLocation splash = new ModelResourceLocation(Archmagus.MODID + ":bottle_splash",
			"inventory");

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack)
	{
		return stack.getItemDamage() == 16384 ? splash : drinkable;
	}
}
