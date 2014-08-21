package com.agadar.archmagus.spell;

import java.util.Random;

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
	/** Used in localisation and stats. */
    private String name;
	
    protected Spell(int par1)
    {
        this.effectId = par1;
        Spells.registerSpell(this, par1);
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
    
    /** Returns the name of the particles spawned when a spell book
     * that contains this spell is being held by an entity. */
    public String getParticleName()
    {
    	return "happyVillager";
    }
    
	/** Returns the amount of particles spawned per gametick when a spell book 
	 *  that contains this spell is being held by an entity. (20 ticks = 1 second) */
	public double getParticleAmount()
	{
		return 1;
	}
    
    /** Sets the spell name. */
    protected Spell setName(String par1Str)
    {
        this.name = par1Str;
        return this;
    }
    
    /** Returns the name of key in translation table of this spell. */
    private String getName()
    {
        return "spell." + this.name;
    }
    
    /** Returns the correct translated name of the spell and the level in roman numbers. */
    public String getTranslatedName(int par1)
    {
        String s = StatCollector.translateToLocal(this.getName());
        
        if (this.getMinLevel() != this.getMaxLevel())
        	return s + " " + StatCollector.translateToLocal("spell.level." + par1);
        
        return s;
    }
    
    /** Ensures that a given level lies between this spell's minimum and maximum levels. */
    protected int getNormalizedLevel(short par1Level)
    {
    	return Math.max(getMinLevel(), Math.min(par1Level, getMaxLevel()));
    }
    
    /** Casts this spell based on the given level. NOTE: it is ALWAYS assumed !par2World.isRemote. */
	public abstract void castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer);
}
