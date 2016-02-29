package com.agadar.archmagus.spell.targeted;

import com.agadar.archmagus.spell.Spell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/** Teleports the player to where he is aiming, up to a certain distance. */
public class SpellTeleport extends Spell implements ISpellTargeted
{
	/**
	 * Holds the distances this spell can teleport a player, where the distance with 
	 * index 0 is the distance the player can teleport if the spell is level 1, 
	 * and where the distance with index 1 is the distance the player can teleport if
	 * the spell is level 2, etcetera.
	 */
	private final int[] distances = { 10, 15, 20};
	
	public SpellTeleport(int par1) 
	{
		super(par1);
		this.setName("teleport");
	}
	
    @Override
    public int getManaCost()
    {
    	return 4;
    }
    
    @Override
    public short getCooldown()
    {
    	return 300;
    }
    
    @Override
    public short getMaxLevel()
    {
        return (short) distances.length;
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
	public void castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer) 
	{
		// Do vector magics to determine where we're teleporting the player to.
		int distance = distances[getNormalizedLevel(par1Level) - 1];
		Vec3 vec3 = new Vec3(par3EntityPlayer.posX, par3EntityPlayer.posY + (double)par3EntityPlayer.getEyeHeight(), par3EntityPlayer.posZ);
        Vec3 vec31 = par3EntityPlayer.getLook(1.0F);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * distance, vec31.yCoord * distance, vec31.zCoord * distance);
        MovingObjectPosition mop = par2World.rayTraceBlocks(vec3, vec32, false, false, true);
		BlockPos bp = par2World.getHeight(mop.getBlockPos());
		
		// Prevent players from abusing the teleport spell by teleporting too many blocks up or down.
		if (bp.getY() - par3EntityPlayer.posY > distance || par3EntityPlayer.posY - bp.getY() > distance)
			return;
		
		// Make sounds and teleport the player.
		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
		par3EntityPlayer.setPositionAndUpdate(bp.getX() + 0.5d, bp.getY(), bp.getZ() + 0.5d);
		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
	}
}
