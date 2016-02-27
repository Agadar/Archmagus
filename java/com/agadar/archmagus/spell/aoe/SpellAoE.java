package com.agadar.archmagus.spell.aoe;

import java.util.List;

import com.agadar.archmagus.spell.Spell;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

/** The archetype of all Area-Of-Effect Spells. */
public abstract class SpellAoE extends Spell 
{
	protected SpellAoE(int par1) 
	{
		super(par1);
	}
	
	@Override
    public short getMaxLevel()
    {
        return 3;
    }
	
	@Override
    public short getCooldown()
    {
    	return 300;
    }
	
	@Override
	public int getManaCost()
    {
    	return 5;
    }

	@Override
	public void castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer) 
	{
		double areaSize = par1Level * 4;
		List<EntityLivingBase> entities = par2World.getEntitiesWithinAABB(EntityLivingBase.class, par3EntityPlayer.getEntityBoundingBox().expand(areaSize, areaSize, areaSize));

		if (entities.size() <= 1)
			par3EntityPlayer.addChatMessage(new ChatComponentText("No targets nearby!"));

		for (EntityLivingBase entity : entities)
		{
			if (!(entity instanceof EntityTameable && ((EntityTameable)entity).getOwner() == par3EntityPlayer) && !(entity == par3EntityPlayer))
				affectEntity(par2World, entity);
		}
	}
	
	/** Called by castSpell(...) for each EntityLiving in the area of effect. */
	protected abstract void affectEntity(World par1World, EntityLivingBase par2EntityLivingBase);
}
