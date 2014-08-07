package com.agadar.archmagus.spell.summon;

import java.lang.reflect.Constructor;
import java.util.List;

import com.agadar.archmagus.entity.EntitySummoned;
import com.agadar.archmagus.spell.Spell;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.entity.passive.EntityTameable;

/** Summons a friendly minion that follows and protects the player. */
public class SpellSummon extends Spell 
{
	/** The constructor of the entity this spell summons. 
	 *  It is assumed the only parameter is a World reference. */
	@SuppressWarnings("rawtypes")
	private final Constructor entityConstr;
	
	@SuppressWarnings({ "rawtypes" })
	public SpellSummon(int par1, String par2Name, Class par3EntityClass)
	{
		super(par1);
		this.setName("summon." + par2Name);	
		entityConstr = getConstructor(par3EntityClass);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Constructor getConstructor(Class par1EntityClass)
	{
		try 
		{
			return par1EntityClass.getConstructor(World.class);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public short getMaxLevel()
    {
        return 4;
    }
	
	@Override
	public int getHungerCost()
    {
    	return 8;
    }
	
	@Override
	public short getCooldown()
	{
		return 1200;
	}
	
	@Override
	public double getParticleAmount()
	{
		return 0.25;
	}
	
	@Override
	public String getParticleName()
	{
		return "witchMagic";
	}

	@Override
	public boolean castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer) 
	{
		if (!par2World.isRemote)
		{
			par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
			
			@SuppressWarnings("unchecked")
			List<EntitySummoned> entities = par2World.getEntitiesWithinAABB(EntitySummoned.class, par3EntityPlayer.boundingBox.expand(20.0D, 20.0D, 20.0D));
			
			for (EntitySummoned entity : entities)
			{
				if (entity.getOwner() == par3EntityPlayer)
				{
					entity.attackEntityFrom(DamageSource.generic, entity.getMaxHealth());
				}
			}
			
			try 
			{
				int[] xSpawnOffset = { -2, 0, 2, 0 };
				int[] zSpawnOffset = { 0, 2, 0, -2 };
				
				for (int i = 0; i < getNormalizedLevel(par1Level); i++)
				{
					EntityCreature entity = (EntityCreature) entityConstr.newInstance(par2World);
					entity.setLocationAndAngles(par3EntityPlayer.posX + xSpawnOffset[i], par3EntityPlayer.posY, par3EntityPlayer.posZ + zSpawnOffset[i], entity.rotationYaw, 0.0F);					
					String comSendName = par3EntityPlayer.getCommandSenderName();
					((EntityTameable) entity).func_152115_b(par3EntityPlayer.getUniqueID().toString());
					entity.setCustomNameTag(comSendName + "'s Minion");
					entity.setAlwaysRenderNameTag(true);
					par2World.spawnEntityInWorld(entity);
				}	
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
}
