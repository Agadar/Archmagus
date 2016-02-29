package com.agadar.archmagus.spell.targeted;

import com.agadar.archmagus.spell.Spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/** Fires a number of small fireballs. */
public class SpellBlazeFire extends Spell implements ISpellTargeted
{
	public SpellBlazeFire(int par1)
    {
        super(par1);
        this.setName("blazefire");
    }
	
	@Override
    public short getMaxLevel()
    {
        return 3;
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
		int[] accuracies = { 10, 8, 6 };

		for (int i = 0; i < getNormalizedLevel(par1Level); i++)
		{
			EntitySmallFireball smallfireball = new EntitySmallFireball(par2World, par3EntityPlayer, v3.xCoord + random.nextGaussian() / accuracies[par1Level - 1], v3.yCoord, v3.zCoord + random.nextGaussian() / accuracies[par1Level - 1]);
			smallfireball.shootingEntity = par3EntityPlayer;
			par2World.spawnEntityInWorld(smallfireball);
		}
	}
}

