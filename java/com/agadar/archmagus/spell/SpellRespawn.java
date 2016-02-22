package com.agadar.archmagus.spell;

import net.minecraft.entity.player.EntityPlayer;
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
    	return 12;
    }
    
    @Override
    public short getCooldown()
    {
    	return 6000;
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
		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
		ChunkCoordinates coordSpawn = par3EntityPlayer.getBedLocation(0);

		if (coordSpawn != null)
			par3EntityPlayer.setPositionAndUpdate(coordSpawn.posX, coordSpawn.posY + 1, coordSpawn.posZ);
		else
		{
			coordSpawn = par2World.getSpawnPoint();
			par3EntityPlayer.setPositionAndUpdate(coordSpawn.posX, coordSpawn.posY, coordSpawn.posZ);
		}

		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
	}
}
