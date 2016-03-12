package com.agadar.archmagus.items;

import java.util.ArrayList;
import java.util.List;

import com.agadar.archmagus.Archmagus;
import com.agadar.archmagus.network.ManaProperties;
import com.agadar.archmagus.network.SpellProperties;
import com.agadar.archmagus.spell.Spell;
import com.agadar.archmagus.spell.SpellData;
import com.agadar.archmagus.spell.Spells;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** This Item allows for casting spells. */
public final class ItemSpell extends ItemSpellBase 
{
	public ItemSpell()
	{
		super("spell", Archmagus.tabSpells);
	}

    /** Adds additional information to the item's tooltip. */
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public final void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        SpellData spellData = SpellData.readFromNBTTagCompound(this.getSpellTag(par1ItemStack));

        if (spellData.spellObj != null)
        {
        	par3List.add(spellData.spellObj.getTranslatedDescription());

        	if (spellData.spellCooldown != 0)
        	{
        		int seconds = spellData.spellCooldown / 20;		
        		int minutes = seconds / 60;
        		seconds = seconds % 60;
        		par3List.add(EnumChatFormatting.RED + "Cooldown: " + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
        	}
        }
    }

    /** Casts the spell book's spell on right click. */
    @Override
    public final ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {		
    	if (!par2World.isRemote)
    	{
    		// Retrieve relevant variables.
    		NBTTagCompound spellTag = this.getSpellTag(par1ItemStack);
        	SpellData spellData = SpellData.readFromNBTTagCompound(spellTag);	
        	boolean inCreative = par3EntityPlayer.capabilities.isCreativeMode;
        	
        	// If the spell is on cooldown and the player is not in creative, abort.
        	if (!inCreative && spellData.spellCooldown > 0)
        	{
        		if (spellData.spellCooldown > 40)
        			par3EntityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Spell is on cooldown!"));
        		
        		return par1ItemStack;  
        	}
        	
        	// Retrieve mana properties
        	ManaProperties props = ManaProperties.get(par3EntityPlayer);
        	
        	// If the player is in creative or has enough mana, cast the spell.
        	if (inCreative) 
        		spellData.castSpell(par2World, par3EntityPlayer);
        	else if (props.consumeMana(spellData.spellObj.getManaCost()))
        	{
        		// Only cast the spell if the player actually knows the spell.
        		if (playerKnowsSpell(spellData, par3EntityPlayer))
        		{
        			spellData.castSpell(par2World, par3EntityPlayer);

        			// Start the cooldown of ALL ItemSpells in the player's inventory that are equal to this.
        			for (int i = 0; i < par3EntityPlayer.inventory.getSizeInventory(); i++)
        			{
        				ItemStack curStack = par3EntityPlayer.inventory.getStackInSlot(i);
        				NBTTagCompound curSpellTag = this.getSpellTag(curStack);
        				
        				if (curSpellTag == null) 
        					continue;
        					
        	        	SpellData curSpellData = SpellData.readFromNBTTagCompound(curSpellTag);
        	        	
        	        	if (curSpellData.equals(spellData))
        	        		SpellData.startCooldown(curSpellTag);
        			}
        		}
        		else
        		{
        			par3EntityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "You do not know this spell!"));
        			par1ItemStack.stackSize--;
        		}
        	}
        	else
        		par3EntityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Not enough mana!"));
    	}
    	
    	return par1ItemStack;
    }
    
    /**
     * Returns whether or not the given player knows the given spell.
     *
     * @param par1SpellData
     * @param par2EntityPlayer
     * @return
     */
    protected final boolean playerKnowsSpell(SpellData par1SpellData, EntityPlayer par2EntityPlayer)
    {
    	List<SpellData> knownSpells = SpellProperties.get(par2EntityPlayer).getKnownSpells();
    	return knownSpells.contains(par1SpellData);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    @SideOnly(Side.CLIENT)
    public final void getSubItems(Item par1Item, CreativeTabs par2CreativeTab, List par3List)
    {
    	for (int i1 = 0; i1 < Spells.spellList.length; i1++)
    	{
    		Spell spell = Spells.spellList[i1];
    		
    		if (spell != null)
    			for (short i2 = spell.getMinLevel(); i2 <= spell.getMaxLevel(); i2++)
    				par3List.add(((ItemSpell) Archmagus.spell).getSpellItemStack(new SpellData(spell, i2)));
    	}
    }
    
    /** Updates the remaining cooldown every game tick. */
    @Override
    public final void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
    {
    	if (!par2World.isRemote) 
    	{
    		SpellData.tickCooldown(this.getSpellTag(par1ItemStack));
    	}
    }

    @Override
    public final boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return slotChanged;
    }
    
    /** Returns the given ItemStack's display name. */
    @Override
    @SideOnly(Side.CLIENT)
    public final String getItemStackDisplayName(ItemStack par1ItemStack)
    {
    	SpellData spellData = SpellData.readFromNBTTagCompound(this.getSpellTag(par1ItemStack));

        if (spellData.spellObj != null) 
        	return (spellData.spellObj.getTranslatedName(spellData.spellLevel));
        
        return super.getItemStackDisplayName(par1ItemStack);
    }
    
    @Override
    public final EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.RARE;
    }
    
    /**
     * Returns a list of ItemStacks of ItemSpells of spells known by the given player.
     *
     * @param _player
     * @return
     */
    public final List<ItemStack> getPlayerKnownSpells(EntityPlayer _player)
    {
    	List<ItemStack> itemSpells = new ArrayList<ItemStack>();
    	SpellProperties props = SpellProperties.get(_player);
    	
    	for (SpellData spellData : props.getKnownSpells())
    		itemSpells.add(((ItemSpell)Archmagus.spell).getSpellItemStack(spellData));
    	
    	return itemSpells;
    }
}
