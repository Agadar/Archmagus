package com.agadar.archmagus.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.potion.Potion;

/** Responsible for managing this mod's Potions. */
public class ModPotions 
{
	/** The potion that is applied when a player is polymorphed. */
	public final static Potion polymorphed;
	/** The potions that are applied when a player casts a Shield spell. */
	public final static Potion fireShield;
	public final static Potion earthShield;
	public final static Potion waterShield;
	public final static Potion stormShield;
	public final static Potion frostShield;
	/** The potion that is applied when a player is Feared. */
	public final static Potion feared;
	/** The potion that makes the affected immune to projectiles. */
	public final static Potion projectileImmunity;
	/** The potion that makes the affected immune to knockback. */
	public final static Potion knockbackImmunity;
	/** The potion that restores mana. */
	public final static Potion mana;
	/** The potion that applies mana regeneration. */
	public final static Potion manaRegen;
	
	/** The next unique Potion Id. Should only be used and altered by getUniquePotionId(). */
	private static int nextPotionId = 32;
	
	static
	{
		openUpPotionTypes();		

		polymorphed = new PotionBase(getUniquePotionId(), false, 0).setIconIndex(0, 0).setPotionName("potion.polymorphed");
		fireShield = new PotionBase(getUniquePotionId(), false, 14981690).setIconIndex(1, 0).setPotionName("potion.shield.fire");
		earthShield = new PotionBase(getUniquePotionId(), false, 10044730).setIconIndex(2, 0).setPotionName("potion.shield.earth");
		waterShield = new PotionBase(getUniquePotionId(), false, 3035801).setIconIndex(3, 0).setPotionName("potion.shield.water");
		stormShield = new PotionBase(getUniquePotionId(), false, 8171462).setIconIndex(4, 0).setPotionName("potion.shield.storm");
		frostShield = new PotionBase(getUniquePotionId(), false, 15463164).setIconIndex(5, 0).setPotionName("potion.shield.frost");
		feared = new PotionBase(getUniquePotionId(), true, 4393481).setIconIndex(6, 0).setPotionName("potion.feared");
		projectileImmunity = new PotionBase(getUniquePotionId(), false, 0).setIconIndex(7, 0).setPotionName("potion.immunity.projectile");
		knockbackImmunity = new PotionBase(getUniquePotionId(), false, 0).setIconIndex(0, 1).setPotionName("potion.immunity.knockback");
		mana = new PotionBase(getUniquePotionId(), false, 6253497).setIconIndex(1, 1).setPotionName("potion.mana.instant");
		manaRegen = new PotionBase(getUniquePotionId(), false, 6253497).setIconIndex(1, 1).setPotionName("potion.mana.regen");
	}
	
	/** Returns the next unique Potion Id. */
	private static int getUniquePotionId()
	{
		while (nextPotionId < Potion.potionTypes.length && Potion.potionTypes[nextPotionId] != null)
			nextPotionId++;
		
		return nextPotionId;
	}
	
	/** Calling this method allows us to register new Potions and modify existing Potions. */
	private static void openUpPotionTypes()
	{
		Potion[] potionTypes = null;

	    for (Field f : Potion.class.getDeclaredFields()) 
	    {
	        f.setAccessible(true);
	        
	        try 
	        {
	            if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) 
	            {
	                Field modfield = Field.class.getDeclaredField("modifiers");
	                modfield.setAccessible(true);
	                modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

	                potionTypes = (Potion[])f.get(null);
	                final Potion[] newPotionTypes = new Potion[256];
	                System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
	                f.set(null, newPotionTypes);
	            }
	        } 
	        catch (Exception e) 
	        {
	            System.err.println(e);
	        }
	    }
	}
}
