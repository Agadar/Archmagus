package com.github.agadar.archmagus.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntitySummonedCaveSpider extends EntitySummonedSpider 
{
	public EntitySummonedCaveSpider(World par1World) 
	{
		super(par1World);
		
		this.setSize(0.7F, 0.5F);
	}
	
	@Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        if (super.attackEntityAsMob(par1Entity))
        {
        	((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.poison.id, 15 * 20, 0));
        	return true;
        }
        
        return false;
    }
}
