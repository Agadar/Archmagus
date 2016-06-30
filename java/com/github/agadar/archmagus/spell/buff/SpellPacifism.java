package com.github.agadar.archmagus.spell.buff;

import java.util.List;

import com.github.agadar.archmagus.potion.ModPotions;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * The buff spell that applies Pacifism to the caster.
 */
public class SpellPacifism extends SpellBuff
{
	public SpellPacifism()
	{
		super("pacifism", ModPotions.pacifism);
	}

	@Override
	public int getManaCost()
    {
    	return 8;
    }
	
	@Override
	public short getCooldown()
	{
		return 1200;
	}
	
	@Override
	public int getBuffDuration(short par1Level)
	{
		return 600;
	}
	
	@Override
	public boolean castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer)
	{
		// Set all nearby hostiles' attacktarget to null.
		double areaSize = 30;
		List<EntityLiving> entities = par2World.getEntitiesWithinAABB(EntityLiving.class, par3EntityPlayer.getEntityBoundingBox().expand(areaSize, areaSize, areaSize));
		
		for (EntityLiving entity : entities)
			if (entity.getAttackTarget() == par3EntityPlayer)
			{
				entity.setAttackTarget(null);
				entity.setRevengeTarget(null);
			}
		
		// Cast the buff.
		return super.castSpell(par1Level, par2World, par3EntityPlayer);
	}
}
