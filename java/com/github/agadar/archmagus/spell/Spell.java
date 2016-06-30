package com.github.agadar.archmagus.spell;

import java.util.Random;

import com.github.agadar.archmagus.Archmagus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

/** The archetype of all Spells. */
public abstract class Spell 
{
	/** A Random object used by some child classes of Spell. */
	protected final static Random random = new Random();
	/** The index of this spell in the spellList. */
	public final int effectId;
	
    protected Spell()
    {
        this.effectId = Spells.registerSpell(this);
    }
    
	/** Returns the minimum level that the spell can be. */
    public short getMinLevel()
    {
        return 1;
    }

    /** Returns the maximum level that the spell can be. */
    public short getMaxLevel()
    {
        return 1;
    }
    
    /** Returns the amount of mana the spell costs to cast. */
    public int getManaCost()
    {
    	return 1;
    }
    
    /** Returns the cooldown of this spell in game ticks. (20 ticks = 1 second) */
    public short getCooldown()
    {
    	return 20;
    }
    
    /** Returns the name of the sound played when this spell is cast. */
    public String getSoundName()
    {
    	return "mob.ghast.fireball";
    }
    
    /** Returns the name of the key in the translation table of this spell. */
    public String getName()
    {
        return "spell.spell_book";
    }
    
    /** Returns the correct translated name of the spell and the level in roman numbers. */
    public final String getTranslatedName(int par1Level)
    {
        String s = StatCollector.translateToLocal(this.getName());
        
        if (this.getMinLevel() != this.getMaxLevel())
        	return s + " " + StatCollector.translateToLocal("spell.level." + par1Level);
        
        return s;
    }
    
    /** Returns the name of the key in the translation table of the description of the spell. */
    public String getDescription()
    {
    	return "spell.description.default";
    }
    
    /** Returns the correct translated description of the spell. */
    public final String getTranslatedDescription()
    {
        return StatCollector.translateToLocal(this.getDescription());
    }
    
    /** Returns the name of the spell's ModelResourceLocation. */
    public String getModelResourceLocationString()
    {
    	return Archmagus.MODID + ":";
    }
    
    /** Ensures that a given level lies between this spell's minimum and maximum levels. */
    protected final int getNormalizedLevel(short par1Level)
    {
    	return Math.max(getMinLevel(), Math.min(par1Level, getMaxLevel()));
    }
    
    /** Casts this spell based on the given level. */
	public abstract boolean castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer);
}
