package com.agadar.archmagus.spell.targeted;

import com.agadar.archmagus.spell.Spell;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
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
		int distance = distances[getNormalizedLevel(par1Level) - 1];
		Minecraft mc = Minecraft.getMinecraft();

		if((mc.getRenderViewEntity().rayTrace(distance, 1.0F) != null))
		{
			par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
			BlockPos bp = mc.getRenderViewEntity().rayTrace(distance, 1.0F).getBlockPos();
			//int blockHitX = mc.getRenderViewEntity().rayTrace(distance, 1.0F).blockX;
			//int blockHitY = mc.getRenderViewEntity().rayTrace(distance, 1.0F).blockY;
			//int blockHitZ = mc.getRenderViewEntity().rayTrace(distance, 1.0F).blockZ;
			par3EntityPlayer.setPositionAndUpdate(bp.getX(), bp.getY() + 1, bp.getZ());
			par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
		} 
	}
}
