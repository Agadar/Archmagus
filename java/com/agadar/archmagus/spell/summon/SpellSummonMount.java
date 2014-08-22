package com.agadar.archmagus.spell.summon;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.agadar.archmagus.entity.EntitySummonedHorse;

/** Summons a mount for the player. */
public class SpellSummonMount extends SpellSummon 
{
	/** The vanilla horse type this spell's summoned mount is.
	 *  0 = white horse (canvas);
	 *  1 = donkey;
	 *  2 = mule;
	 *  3 = zombie;
	 *  4 = skeleton. */
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
			EntityCreature entity = (EntityCreature) entityConstr.newInstance(par2World);
			entity.setLocationAndAngles(par3EntityPlayer.posX + 2, par3EntityPlayer.posY, par3EntityPlayer.posZ, entity.rotationYaw, 0.0F);					
			String comSendName = par3EntityPlayer.getCommandSenderName();
			((EntitySummonedHorse) entity).setTamedBy(par3EntityPlayer);
			entity.setCustomNameTag(comSendName + "'s Mount");
			entity.setAlwaysRenderNameTag(true);
			par2World.spawnEntityInWorld(entity);
			entity.onSpawnWithEgg(null);
			((EntitySummonedHorse) entity).setHorseType(this.horseType);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
