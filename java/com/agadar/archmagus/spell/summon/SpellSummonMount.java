package com.agadar.archmagus.spell.summon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.agadar.archmagus.entity.EntityRisenHorse;

/** Summons a mount for the player. */
public class SpellSummonMount extends SpellSummon 
{
	/** The horse type this spell's summoned mount is.
	 *  0 = white horse (canvas);
	 *  1 = donkey;
	 *  2 = mule;
	 *  3 = zombie;
	 *  4 = skeleton; 
	 *  5 = wither skeleton (non-vanilla). */
	protected final int horseType;
	
	@SuppressWarnings("rawtypes")
	public SpellSummonMount(int par1, String par2Name, Class par3EntityClass, int horseType) 
	{
		super(par1, par2Name, par3EntityClass);
		this.horseType = horseType;
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
		this.killExistingMinions(par2World, par3EntityPlayer);

		try 
		{
			EntityRisenHorse entity = (EntityRisenHorse) entityConstr.newInstance(par2World);
			entity.setLocationAndAngles(par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, entity.rotationYaw, 0.0F);					
			entity.setHorseSaddled(true);				
			entity.setTamedBy(par3EntityPlayer);
			entity.rotationYaw = par3EntityPlayer.rotationYaw;
			entity.rotationPitch = par3EntityPlayer.rotationPitch;
			par2World.spawnEntityInWorld(entity);
			entity.onSpawnWithEgg(null, par3EntityPlayer, this.horseType);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
