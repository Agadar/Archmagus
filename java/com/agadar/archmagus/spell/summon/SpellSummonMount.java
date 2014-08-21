package com.agadar.archmagus.spell.summon;

import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.agadar.archmagus.entity.EntitySummonedHorse;

/** Summons a mount for the player. */
public class SpellSummonMount extends SpellSummon 
{
	@SuppressWarnings("rawtypes")
	public SpellSummonMount(int par1, String par2Name, Class par3EntityClass) 
	{
		super(par1, par2Name, par3EntityClass);
	}
	
	@Override
	public short getMaxLevel()
    {
        return 1;
    }
	
	@Override
	public void castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer) 
	{
		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);

		@SuppressWarnings("unchecked")
		List<EntitySummonedHorse> entities = par2World.getEntitiesWithinAABB(EntitySummonedHorse.class, par3EntityPlayer.boundingBox.expand(20.0D, 20.0D, 20.0D));

		for (EntitySummonedHorse entity : entities)
		{
			if (entity.getTamedBy() == par3EntityPlayer)
				entity.attackEntityFrom(DamageSource.generic, entity.getMaxHealth());
		}

		try 
		{
			EntityCreature entity = (EntityCreature) entityConstr.newInstance(par2World);
			entity.setLocationAndAngles(par3EntityPlayer.posX + 2, par3EntityPlayer.posY, par3EntityPlayer.posZ, entity.rotationYaw, 0.0F);					
			String comSendName = par3EntityPlayer.getCommandSenderName();
			((EntitySummonedHorse) entity).setTamedBy(par3EntityPlayer);
			entity.setCustomNameTag(comSendName + "'s Minion");
			entity.setAlwaysRenderNameTag(true);
			par2World.spawnEntityInWorld(entity);
			entity.onSpawnWithEgg(null);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
