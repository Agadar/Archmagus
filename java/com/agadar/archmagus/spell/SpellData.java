package com.agadar.archmagus.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/** This class is used for applying and identifying Spells onto items. */
public class SpellData implements Comparable<SpellData>
{
	/** Spell object associated with this SpellData. */
    public final Spell spellObj;
    /** Spell level associated with this SpellData. */
    public short spellLevel;
    /** The remaining cooldown associated with this SpellData. */
    public short spellCooldown;
    
    public SpellData(Spell par1Spell, short par2Level, short par3Cooldown)
    {
    	this.spellObj = par1Spell;
        this.spellLevel = par2Level;
        this.spellCooldown = par3Cooldown;
    }
    
    public SpellData(Spell par1Spell, short par2Level)
    {
        this(par1Spell, par2Level, (short) 0);
    }
    
    /** Casts the Spell associated with this SpellData. */
    public void castSpell(World par1World, EntityPlayer par2EntityPlayer)
    {
    	this.spellObj.castSpell(this.spellLevel, par1World, par2EntityPlayer);
    }
    
    /** Reads a SpellData and returns an NBTTagCompound with corresponding values. */
    public static NBTTagCompound writeToNBTTagCompound(SpellData par1SpellData)
    {
    	NBTTagCompound tag = new NBTTagCompound();  	
    	tag.setShort("id", (short) par1SpellData.spellObj.effectId);
    	tag.setShort("lvl", (short) par1SpellData.spellLevel);
    	tag.setShort("cd", (short) par1SpellData.spellCooldown);   	
    	return tag;
    }
    
    /** Reads an NBTTagCompound and returns a SpellData with corresponding values. */
    public static SpellData readFromNBTTagCompound(NBTTagCompound par1NBTTagCompound)
    {
    	Spell spell = Spells.spellList[par1NBTTagCompound.getShort("id")];
        short tagLevel = par1NBTTagCompound.getShort("lvl");
        short tagCooldown = par1NBTTagCompound.getShort("cd");      		
		return new SpellData(spell, tagLevel, tagCooldown);
    }
    
    /**
     * Starts this spellData's cooldown.
     */
    public void startCooldown()
    {
    	this.spellCooldown = this.spellObj.getCooldown();
    }
    
    /**
     * Ticks this spellData's cooldown.
     */
    public void tickCooldown()
    {
    	if (this.spellCooldown > 0)
    	{
    		this.spellCooldown--;
    	}
    }
    
    @Override
    public boolean equals(Object obj)
    {
    	if (!(obj instanceof SpellData))
            return false;
        if (obj == this)
            return true;

        SpellData objcast = (SpellData) obj;
        
        return this.spellObj.effectId == objcast.spellObj.effectId &&
        		this.spellLevel == objcast.spellLevel;
    }
    
    @Override
    public int hashCode() 
    {
    	int result = 17;
    	result = 31 * result + spellObj.effectId;
    	result = 31 * result + spellLevel;
    	return result;
    }
    
	@Override
	public int compareTo(SpellData arg0)
	{
		if (this.spellObj.effectId > arg0.spellObj.effectId)
			return 1;		
		else if (this.spellObj.effectId < arg0.spellObj.effectId)
			return -1;
		
		if (this.spellLevel > arg0.spellLevel)
			return 1;		
		else if (this.spellLevel < arg0.spellLevel)
			return -1;
		
		return 0;
	}
    
    /** Attempts to combine two SpellData's. Returns null if the combining failed.
     *  Used for combining spell books in an anvil. */
    public static SpellData tryCombine(SpellData par1SpellData, SpellData par2SpellData)
    {
    	if (par1SpellData.spellObj.effectId == par2SpellData.spellObj.effectId)
		{
			if (par1SpellData.spellLevel > par2SpellData.spellLevel)
			{
				return par1SpellData; 				
			}
			else if (par1SpellData.spellLevel == par2SpellData.spellLevel)
			{
    			short newSpellLevel = par1SpellData.spellLevel;
				
				if (par1SpellData.spellLevel + 1 <= par1SpellData.spellObj.getMaxLevel()) newSpellLevel++;
				   				
				return new SpellData(par1SpellData.spellObj, newSpellLevel);
			}
		}
    	
    	return null;
    }
}
