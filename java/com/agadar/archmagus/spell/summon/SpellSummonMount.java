package com.agadar.archmagus.spell.summon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.agadar.archmagus.Archmagus;
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
	public SpellSummonMount(String par1Name, Class par2EntityClass, int par3HorseType) 
	{
		super(par1Name, par2EntityClass);
		this.horseType = par3HorseType;
	}
	
	@Override
	public short getMaxLevel()
    {
        return 1;
    }
	
	@Override
	public String getDescription()
    {
    	return "spell.description.summon.mount";
    }
	
	@Override
	public String getModelResourceLocationString()
    {
		return Archmagus.MODID + ":" + (horseType == 3 ? "zombie" : "skeleton") + "_book";
    }
	
	@Override
	public boolean castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer) 
	{
		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
		SpellSummon.killExistingMinions(par2World, par3EntityPlayer);
		boolean horseDidSpawn = false;

		try 
		{
			EntityRisenHorse entity = (EntityRisenHorse) entityConstr.newInstance(par2World);
			entity.onInitialSpawn(par2World.getDifficultyForLocation(new BlockPos(par3EntityPlayer)), null);
			entity.setLocationAndAngles(par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, entity.rotationYaw, 0.0F);					
			entity.setHorseSaddled(true);				
			entity.setTamedBy(par3EntityPlayer);
			entity.rotationYaw = par3EntityPlayer.rotationYaw;
			entity.rotationPitch = par3EntityPlayer.rotationPitch;
			entity.setGrowingAge(0);
			entity.setHorseType(horseType);
			horseDidSpawn = par2World.spawnEntityInWorld(entity);
			entity.interact(par3EntityPlayer);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
		return horseDidSpawn;
	}
}
