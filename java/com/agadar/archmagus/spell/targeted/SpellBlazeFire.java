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
        return 1;
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
		// Play sound
		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
		
		Vec3 v3 = par3EntityPlayer.getLook(1);
		EntitySmallFireball fireball = new EntitySmallFireball(par2World, par3EntityPlayer, v3.xCoord * 50D, v3.yCoord * 50D, v3.zCoord * 50D);
		fireball.posY = par3EntityPlayer.posY + par3EntityPlayer.getEyeHeight();
		par2World.spawnEntityInWorld(fireball);
	}
}

