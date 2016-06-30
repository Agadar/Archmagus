package com.github.agadar.archmagus.spell.targeted;

import com.github.agadar.archmagus.spell.Spell;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SpellLightning extends Spell
{
	@Override
    public short getMaxLevel()
    {
        return 1;
    }
	
	@Override
	public int getManaCost()
    {
    	return 3;
    }
	
	@Override
	public short getCooldown()
	{
		return 40;
	}
	
	@Override
	public String getName()
    {
        return "spell.lightning";
    }
	
	@Override
	public String getDescription()
    {
    	return "spell.description.projectile";
    }
	
	@Override
	public String getModelResourceLocationString()
    {
		return super.getModelResourceLocationString() + "lightning_book";
    }
	
	@Override
	public boolean castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer)
	{
		// Do vector magics to determine where the player is aiming at.
		int distance = 50;
		Vec3 vec3 = new Vec3(par3EntityPlayer.posX, par3EntityPlayer.posY + (double)par3EntityPlayer.getEyeHeight(), par3EntityPlayer.posZ);
        Vec3 vec31 = par3EntityPlayer.getLook(1.0F);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * distance, vec31.yCoord * distance, vec31.zCoord * distance);
        MovingObjectPosition mop = par2World.rayTraceBlocks(vec3, vec32, false, false, true);
		BlockPos bp = mop.getBlockPos();
		
		// Spawn lightning bolt.
		return par2World.addWeatherEffect(new EntityLightningBolt(par2World, bp.getX() + 0.5d, bp.getY(), bp.getZ() + 0.5d));
	}

}
