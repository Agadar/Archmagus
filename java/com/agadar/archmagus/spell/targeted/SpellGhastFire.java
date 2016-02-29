package com.agadar.archmagus.spell.targeted;

import com.agadar.archmagus.spell.Spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/** Fires a large fireball. */
public class SpellGhastFire extends Spell implements ISpellTargeted
{
	public SpellGhastFire(int par1) 
	{
		super(par1);
		this.setName("ghastfire");
	}
	
	@Override
    public int getManaCost()
    {
    	return 3;
    }
	
	@Override
	public short getCooldown()
	{
		return 40;
	}
	
	@Override
	public String getParticleName()
	{
		return "flame";
	}
	
	@Override
	public double getParticleAmount()
	{
		return 0.3;
	}

	@Override
	public void castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer) 
	{
		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);			
		Vec3 v3 = par3EntityPlayer.getLook(1);
		EntityLargeFireball largefireball = new EntityLargeFireball(par2World, par3EntityPlayer, v3.xCoord * 50D, v3.yCoord * 50D, v3.zCoord * 50D);
		largefireball.posY = par3EntityPlayer.posY + par3EntityPlayer.getEyeHeight();
		par2World.spawnEntityInWorld(largefireball);
	}
}
