package com.agadar.archmagus.items;

import java.util.List;

import com.agadar.archmagus.Archmagus;
import com.agadar.archmagus.network.SpellProperties;
import com.agadar.archmagus.network.message.SpellsMessage;
import com.agadar.archmagus.spell.Spell;
import com.agadar.archmagus.spell.SpellData;
import com.agadar.archmagus.spell.Spells;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** The book that teaches spells. */
public class ItemSpellBook extends ItemSpellBase
{
	public ItemSpellBook()
	{
		super("spell_book", Archmagus.tabSpellBooks);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		SpellData spellData = SpellData.readFromNBTTagCompound(this.getSpellTag(par1ItemStack));

        if (spellData.spellObj != null) 
        	par3List.add(spellData.spellObj.getTranslatedName(spellData.spellLevel));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (!par2World.isRemote)
    	{
			// Add the spell to the player's known spells.
    		NBTTagCompound spellTag = this.getSpellTag(par1ItemStack);
        	SpellData spellData = SpellData.readFromNBTTagCompound(spellTag);	
        	SpellProperties props = SpellProperties.get(par3EntityPlayer);
        	props.addSpell(spellData);
        	// Send a message server-> client.
        	NBTTagCompound comp = new NBTTagCompound();
        	props.saveNBTData(comp);
        	Archmagus.networkWrapper.sendTo(new SpellsMessage(comp), (EntityPlayerMP) par3EntityPlayer);
        	// Send a chat message.
        	par3EntityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "You learned the '" + spellData.spellObj.getTranslatedName(spellData.spellLevel) + "' spell!"));       	
    	}
    	
    	return par1ItemStack;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1Item, CreativeTabs par2CreativeTab, List par3List)
	{
		for (int i1 = 0; i1 < Spells.spellList.length; i1++)
    	{
    		Spell spell = Spells.spellList[i1];
    		
    		if (spell != null)
    			for (short i2 = spell.getMinLevel(); i2 <= spell.getMaxLevel(); i2++)
    				par3List.add(((ItemSpellBook) Archmagus.spell_book).getSpellItemStack(new SpellData(spell, i2)));
    	}		
	}
	
    /** Attempts to combine two spell books. Returns null if it failed.
     *  Used for combining spell books in an Anvil. */
    public ItemStack tryCombine(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
    	NBTTagCompound nbtt1 = par1ItemStack.getTagCompound();
    	NBTTagCompound nbtt2 = par2ItemStack.getTagCompound();
    	
    	if (par1ItemStack.getItem() == Archmagus.spell_book && par2ItemStack.getItem() == Archmagus.spell_book &&
    			nbtt1 != null && nbtt2 != null && nbtt1.hasKey("spell") && nbtt2.hasKey("spell"))
    	{
    		NBTTagCompound spellTag1 = nbtt1.getCompoundTag("spell");               
    		NBTTagCompound spellTag2 = nbtt2.getCompoundTag("spell"); 
    		
    		SpellData spellData1 = SpellData.readFromNBTTagCompound(spellTag1);
    		SpellData spellData2 = SpellData.readFromNBTTagCompound(spellTag2);   		
    		SpellData spellData3 = SpellData.tryCombine(spellData1, spellData2);
    		
    		if (spellData3 != null)
    			return this.getSpellItemStack(spellData3);
    	}
    	
    	return null;
    }
}
