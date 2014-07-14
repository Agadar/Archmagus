package com.agadar.archmagus.spells.shield;

import net.minecraft.potion.Potion;

import com.agadar.archmagus.potions.ModPotions;

/** Summons the Storm Shield. */
public class SpellStormShield extends SpellShield 
{
	public SpellStormShield(int par1) 
	{
		super(par1, "storm");
	}
	
	@Override
	public String getSoundName()
	{
		return "ambient.weather.thunder";
	}

	@Override
	public Potion getShieldEffect() 
	{
		return ModPotions.stormShield;
	}
}