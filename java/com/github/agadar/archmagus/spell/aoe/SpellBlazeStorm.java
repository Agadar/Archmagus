package com.github.agadar.archmagus.spell.aoe;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/** Damages and sets fire to all non-allied creatures in the area. */
public class SpellBlazeStorm extends SpellAoE 
{
	@Override
	public String getName()
    {
        return "spell.blazestorm";
    }
	
	@Override
	public String getModelResourceLocationString()
    {
    	return super.getModelResourceLocationString() + "blazefire_book";
    }

	@Override
	protected void affectEntity(World par1World, EntityLivingBase par2EntityLivingBase) 
	{
		par2EntityLivingBase.setFire(4);				
		par2EntityLivingBase.attackEntityFrom(DamageSource.onFire, 4F);	
		par2EntityLivingBase.knockBack(par2EntityLivingBase, 0F, 1F, 0F);
	}
}
