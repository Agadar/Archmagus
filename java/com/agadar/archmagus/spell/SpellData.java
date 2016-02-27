package com.agadar.archmagus.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/** This class is used for applying and identifying Spells onto items. */
public class SpellData 
{
	/** Spell object associated with this SpellData. */
    public final Spell spellObj;
    /** Spell level associated with this SpellData. */
    public final short spellLevel;
    /** The remaining cooldown associated with this SpellData. */
    public final short spellCooldown;
    
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
    
    /** Reads an NBTTagCompound and reduces the remaining cooldown by one tick. */
    public static void tickCooldown(NBTTagCompound par1NBTTagCompound)
    {
    	short cooldown = par1NBTTagCompound.getShort("cd");

		if (cooldown > 0)
			par1NBTTagCompound.setShort("cd", (short) (cooldown - 1));
    }
    
    /** Reads an NBTTagCompound and starts the cooldown anew. */
    public static void startCooldown(NBTTagCompound par1NBTTagCompound)
    {
    	Spell spell = Spells.spellList[par1NBTTagCompound.getShort("id")];
    	par1NBTTagCompound.setShort("cd", spell.getCooldown());
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
