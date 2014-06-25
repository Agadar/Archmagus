package com.agadar.archmagus.spells;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SpellBlazeFire extends Spell 
{
	public SpellBlazeFire(int par1)
    {
        super(par1);
        this.setName("blazeFire");
    }
	
	@Override
    public int getMaxLevel()
    {
        return 3;
    }

	@Override
	public void cast(short par1Level, World par2World, EntityPlayer par3EntityPlayer) 
	{
		par2World.playAuxSFXAtEntity((EntityPlayer)null, 1009, (int)par3EntityPlayer.posX, (int)par3EntityPlayer.posY, (int)par3EntityPlayer.posZ, 0);
		Vec3 v3 = par3EntityPlayer.getLook(1);
		
		for (int i = 0; i < getNormalizedLevel(par1Level); i++)
		{
			EntitySmallFireball smallfireball = new EntitySmallFireball(par2World, par3EntityPlayer.posX, par3EntityPlayer.posY + par3EntityPlayer.eyeHeight, par3EntityPlayer.posZ, v3.xCoord + random.nextGaussian() / 4, v3.yCoord, v3.zCoord + random.nextGaussian() / 4);
			smallfireball.shootingEntity = par3EntityPlayer;
			par2World.spawnEntityInWorld(smallfireball);
		}
	}
}