package com.agadar.archmagus.spells;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/** Damages and sets fire to all non-allied creatures in the area. */
public class SpellBlazeStorm extends SpellAoE 
{
	protected SpellBlazeStorm(int par1) 
	{
		super(par1);
		this.setName("blazestorm");
	}
	
	@Override
	public String getSoundName()
	{
		return "mob.ghast.fireball";
	}
	
	@Override
	public boolean castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer) 
	{
		if (!par2World.isRemote)
			par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
		
		return super.castSpell(par1Level, par2World, par3EntityPlayer);
	}

	@Override
	protected void affectEntity(World par1World, EntityLivingBase par2EntityLivingBase) 
	{
		par2EntityLivingBase.setFire(5);				
		par2EntityLivingBase.attackEntityFrom(DamageSource.onFire, 4F);	
		par2EntityLivingBase.knockBack(par2EntityLivingBase, 0F, 1F, 0F);
	}
}
