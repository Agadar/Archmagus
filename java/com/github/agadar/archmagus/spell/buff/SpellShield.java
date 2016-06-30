package com.github.agadar.archmagus.spell.buff;

import java.util.ArrayList;
import java.util.List;

import com.github.agadar.archmagus.Archmagus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

/** Summons a defensive magical shield, a special subtype of buffs. We currently have the following shields:
 *  Fire Shield, which causes damage and sets fire to attackers when he is hit;
 *  Earth Shield, which gives the caster the resistance and knockback immunity effects when he is hit;
 *  Water Shield, which gives the caster the regeneration and fire resistance effects when he is hit;
 *  Storm Shield, which gives the caster the speed and projectile immunity effects when he is hit;
 *  Frost Shield, which causes the slowness and weakness effects to attackers when he is hit; */
public class SpellShield extends SpellBuff
{
	/** All the potions applied by all shield spells. */
	private final static List<Potion> shieldPotions = new ArrayList<Potion>();
	
	public SpellShield(String par1Name, Potion par2Potion) 
	{
		super(par1Name, par2Potion);
		shieldPotions.add(par2Potion);
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
	public String getDescription()
    {
    	return "spell.description.shield";
    }
	
	@Override
	public String getModelResourceLocationString()
    {
		return Archmagus.MODID + ":" + buffName + "_shield_book";
    }
	
	@Override
	public int getBuffDuration(short par1Level)
	{
		return 12000;
	}

	@Override
	public boolean castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer) 
	{
		// Clear all current shield effects.
		for (Potion p : shieldPotions)
			par3EntityPlayer.removePotionEffect(p.getId());
		
		// Cast SpellBuff#castSpell.
		return super.castSpell(par1Level, par2World, par3EntityPlayer);
	}
}
