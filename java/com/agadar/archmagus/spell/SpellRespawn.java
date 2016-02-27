package com.agadar.archmagus.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/** Teleports the player to his bed, and otherwise to the world spawn. */
public class SpellRespawn extends Spell 
{
	protected SpellRespawn(int par1) 
	{
		super(par1);
		this.setName("respawn");
	}
	
	@Override
    public int getManaCost()
    {
    	return 10;
    }
    
    @Override
    public short getCooldown()
    {
    	return 12000;
    }
    
	@Override
	public String getParticleName()
	{
		return "portal";
	}
	
	@Override
	public double getParticleAmount()
	{
		return 1;
	}
	
	@Override
	public String getSoundName()
	{
		return "mob.ghast.fireball";
	}

	@Override
	public void castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer) 
	{
		if (par3EntityPlayer.dimension != 0)
			return;
		
		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
		BlockPos coordSpawn = par3EntityPlayer.getBedLocation(0);

		if (coordSpawn != null)
			par3EntityPlayer.setPositionAndUpdate(coordSpawn.getX(), coordSpawn.getY() + 1, coordSpawn.getZ());
		else
		{
			coordSpawn = par2World.getSpawnPoint();
			par3EntityPlayer.setPositionAndUpdate(coordSpawn.getX(), coordSpawn.getY(), coordSpawn.getZ());
		}

		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
	}
}
