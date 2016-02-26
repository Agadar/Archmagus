package com.agadar.archmagus.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

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
	
	static
	{
		openUpPotionTypes();		

		polymorphed = new PotionBase(new ResourceLocation("polymorphed"), false, 0).setIconIndex(0, 0).setPotionName("potion.polymorphed");
		fireShield = new PotionBase(new ResourceLocation("fireShield"), false, 14981690).setIconIndex(1, 0).setPotionName("potion.shield.fire");
		earthShield = new PotionBase(new ResourceLocation("earthShield"), false, 10044730).setIconIndex(2, 0).setPotionName("potion.shield.earth");
		waterShield = new PotionBase(new ResourceLocation("waterShield"), false, 3035801).setIconIndex(3, 0).setPotionName("potion.shield.water");
		stormShield = new PotionBase(new ResourceLocation("stormShield"), false, 8171462).setIconIndex(4, 0).setPotionName("potion.shield.storm");
		frostShield = new PotionBase(new ResourceLocation("frostShield"), false, 15463164).setIconIndex(5, 0).setPotionName("potion.shield.frost");
		feared = new PotionBase(new ResourceLocation("feared"), true, 4393481).setIconIndex(6, 0).setPotionName("potion.feared");
		projectileImmunity = new PotionBase(new ResourceLocation("projectileImmunity"), false, 0).setIconIndex(7, 0).setPotionName("potion.immunity.projectile");
		knockbackImmunity = new PotionBase(new ResourceLocation("knockbackImmunity"), false, 0).setIconIndex(0, 1).setPotionName("potion.immunity.knockback");
		mana = new PotionBase(new ResourceLocation("mana"), false, 6253497).setIconIndex(1, 1).setPotionName("potion.mana.instant");
		manaRegen = new PotionBase(new ResourceLocation("manaRegen"), false, 6253497).setIconIndex(1, 1).setPotionName("potion.mana.regen");
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
