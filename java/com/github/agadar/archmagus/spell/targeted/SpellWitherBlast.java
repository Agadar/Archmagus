package com.github.agadar.archmagus.spell.targeted;

import com.github.agadar.archmagus.spell.Spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/** Fires a Wither Skull. */
public class SpellWitherBlast extends Spell
{
	@Override
    public int getManaCost()
    {
    	return 4;
    }
	
	@Override
	public short getCooldown()
	{
		return 40;
	}
	
	@Override
	public String getSoundName()
	{
		return "mob.wither.shoot";
	}
	
	@Override
	public String getName()
    {
        return "spell.witherblast";
    }
	
	@Override
	public String getDescription()
    {
    	return "spell.description.projectile";
    }
	
	@Override
	public String getModelResourceLocationString()
    {
    	return super.getModelResourceLocationString() + "wither_skeleton_book";
    }

	@Override
	public boolean castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer) 
	{     	
		// Play sound.
		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);	
		
		Vec3 v3 = par3EntityPlayer.getLook(1);
		EntityWitherSkull fireball = new EntityWitherSkull(par2World, par3EntityPlayer, v3.xCoord * 50D, v3.yCoord * 50D, v3.zCoord * 50D);
		fireball.posY = par3EntityPlayer.posY + par3EntityPlayer.getEyeHeight();
		return par2World.spawnEntityInWorld(fireball);
	}
}
