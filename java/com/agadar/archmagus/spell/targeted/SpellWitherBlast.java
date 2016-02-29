package com.agadar.archmagus.spell.targeted;

import com.agadar.archmagus.spell.Spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/** Fires a Wither Skull. */
public class SpellWitherBlast extends Spell implements ISpellTargeted
{
	public SpellWitherBlast(int par1) 
	{
		super(par1);
		this.setName("witherblast");
	}
	
	@Override
    public int getManaCost()
    {
    	return 4;
    }
	
	@Override
	public short getCooldown()
	{
		return 40;
	}
	
	@Override
	public double getParticleAmount()
	{
		return 0.5;
	}
	
	@Override
	public String getParticleName()
	{
		return "smoke";
	}
	
	@Override
	public String getSoundName()
	{
		return "mob.wither.shoot";
	}

	@Override
	public void castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer) 
	{     	
		// Play sound.
		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);	
		
		Vec3 v3 = par3EntityPlayer.getLook(1);
		EntityWitherSkull fireball = new EntityWitherSkull(par2World, par3EntityPlayer, v3.xCoord * 50D, v3.yCoord * 50D, v3.zCoord * 50D);
		fireball.posY = par3EntityPlayer.posY + par3EntityPlayer.getEyeHeight();
		par2World.spawnEntityInWorld(fireball);
	}
}
