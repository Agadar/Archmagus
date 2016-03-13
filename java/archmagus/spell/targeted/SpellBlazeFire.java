package archmagus.spell.targeted;

import archmagus.spell.Spell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/** Fires a number of small fireballs. */
public class SpellBlazeFire extends Spell
{	
	@Override
    public short getMaxLevel()
    {
        return 1;
    }
	
	@Override
	public int getManaCost()
    {
    	return 2;
    }
	
	@Override
	public String getName()
    {
        return "spell.blazefire";
    }
	
	@Override
	public String getDescription()
    {
    	return "spell.description.projectile";
    }
	
	@Override
	public String getModelResourceLocationString()
    {
    	return super.getModelResourceLocationString() + "blazefire_book";
    }

	@Override
	public boolean castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer) 
	{
		// Play sound.
		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
		// Summon fireball.
		Vec3 v3 = par3EntityPlayer.getLook(1);
		EntitySmallFireball fireball = new EntitySmallFireball(par2World, par3EntityPlayer, v3.xCoord * 50D, v3.yCoord * 50D, v3.zCoord * 50D);
		fireball.posY = par3EntityPlayer.posY + par3EntityPlayer.getEyeHeight();
		return par2World.spawnEntityInWorld(fireball);
	}
}

