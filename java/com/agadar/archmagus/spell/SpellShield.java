package com.agadar.archmagus.spell;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/** Summons a defensive magical shield. We currently have the following shields:
 *  Fire Shield, which causes damage and sets fire to attackers when he is hit;
 *  Earth Shield, which gives the caster the resistance and knockback immunity effects when he is hit;
 *  Water Shield, which gives the caster the regeneration and fire resistance effects when he is hit;
 *  Storm Shield, which gives the caster the speed and projectile immunity effects when he is hit;
 *  Frost Shield, which causes the slowness and weakness effects to attackers when he is hit; */
public class SpellShield extends Spell
{
	/** The name of this shield spell. */
	private final String shieldName;
	/** The potion applied by this spell. */
	private final Potion shieldPotion;
	/** All the potions applied by all shield spells. */
	private final static List<Potion> shieldPotions = new ArrayList<Potion>();
	
	public SpellShield(String par1Name, Potion par2Potion) 
	{
		super();
		this.shieldName = par1Name;
		this.shieldPotion = par2Potion;
		shieldPotions.add(par2Potion);
	}
	
	@Override
	public String getName()
    {
        return "spell.shield." + shieldName;
    }
	
	@Override
	public int getManaCost()
    {
    	return 4;
    }
	
	@Override
	public short getCooldown()
	{
		return 1200;
	}
	
	@Override
	public short getMaxLevel()
    {
        return 3;
    }
	
	@Override
	public String getModelResourceLocationString()
    {
    	return super.getModelResourceLocationString() + shieldName + "_shield_book";
    }

	@Override
	public boolean castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer) 
	{
		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
		clearShields(par3EntityPlayer);
		par3EntityPlayer.addPotionEffect(new PotionEffect(this.shieldPotion.getId(), 12000, par1Level - 1));
		return true;
	}
	
	/** Removes all shield effects from the given EntityPlayer.
	 *  Should be called every time before a new shield is applied so that
	 *  a player cannot have more than one type of shield on him at a time. */
	private static void clearShields(EntityPlayer par1EntityPlayer)
	{
		for (Potion p : shieldPotions)
			par1EntityPlayer.removePotionEffect(p.getId());
	}
}
