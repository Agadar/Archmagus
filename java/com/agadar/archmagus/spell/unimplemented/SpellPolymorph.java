package com.agadar.archmagus.spell.unimplemented;

import com.agadar.archmagus.potion.ModPotions;
import com.agadar.archmagus.spell.Spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/** Polymorphs the caster into an animal. */
public class SpellPolymorph extends Spell 
{
	protected SpellPolymorph(int par1) 
	{
		super(par1);
		this.setName("polymorph");
	}

	@Override
    public int getManaCost()
    {
    	return 6;
    }
    
    @Override
    public short getCooldown()
    {
    	return 200;
    }
	
	@Override
	public void castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer) 
	{
		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
		par3EntityPlayer.addPotionEffect(new PotionEffect(ModPotions.polymorphed.getId(), 200, 0));			
	}
}
