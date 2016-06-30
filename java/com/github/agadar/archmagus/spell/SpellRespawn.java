package com.github.agadar.archmagus.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/** Teleports the player to his bed, and otherwise to the world spawn. */
public class SpellRespawn extends Spell 
{
	@Override
    public int getManaCost()
    {
    	return 10;
    }
    
    @Override
    public short getCooldown()
    {
    	return 18000;
    }
    
	@Override
	public String getName()
    {
        return "spell.respawn";
    }
	
	@Override
	public String getSoundName()
	{
		return "mob.ghast.fireball";
	}
	
	@Override
	public String getModelResourceLocationString()
    {
    	return super.getModelResourceLocationString() + "wither_skeleton_book";
    }

	@Override
	public boolean castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer) 
	{
		// Only teleport player if he's in the overworld.
		if (par3EntityPlayer.dimension != 0)
			return false;
		
		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
		BlockPos coordSpawn = par3EntityPlayer.getBedLocation(0);

		// If the player is set to a bed, teleport him there. Otherwise, teleport him to world spawn.
		if (coordSpawn != null)
			par3EntityPlayer.setPositionAndUpdate(coordSpawn.getX(), coordSpawn.getY() + 1, coordSpawn.getZ());
		else
		{
			coordSpawn = par2World.getSpawnPoint();
			par3EntityPlayer.setPositionAndUpdate(coordSpawn.getX(), coordSpawn.getY(), coordSpawn.getZ());
		}

		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
		return true;
	}
}
