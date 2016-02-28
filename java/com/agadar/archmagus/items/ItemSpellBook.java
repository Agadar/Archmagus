package com.agadar.archmagus.items;

import java.util.List;

import com.agadar.archmagus.Archmagus;
import com.agadar.archmagus.network.ManaProperties;
import com.agadar.archmagus.spell.Spell;
import com.agadar.archmagus.spell.SpellData;
import com.agadar.archmagus.spell.Spells;
import com.agadar.archmagus.spell.aoe.SpellAoE;
import com.agadar.archmagus.spell.shield.SpellShield;
import com.agadar.archmagus.spell.summon.SpellSummon;
import com.agadar.archmagus.spell.summon.SpellSummonMount;
import com.agadar.archmagus.spell.targeted.ISpellTargeted;
import com.agadar.archmagus.spell.targeted.SpellTeleport;

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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** This Item allows for casting spells. */
public class ItemSpellBook extends Item 
{
	public final String Name = "spell_book";
	
	public ItemSpellBook()
	{
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.setUnlocalizedName(Archmagus.MODID + "_" + Name);
		this.setCreativeTab(Archmagus.tabSpellBooks);
        GameRegistry.registerItem(this, Name);
	}

	/** Returns the ItemStack's spell tag. */
    public NBTTagCompound getSpellTag(ItemStack par1ItemStack)
    {
    	NBTTagCompound nbtt = par1ItemStack.getTagCompound();
    	
    	if (nbtt != null && nbtt.hasKey("spell"))
    		return (NBTTagCompound) nbtt.getTag("spell");
    	
    	return null;
    }
    
    /** Adds additional information to the item's tooltip. */
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        SpellData spellData = SpellData.readFromNBTTagCompound(this.getSpellTag(par1ItemStack));

        if (spellData.spellObj != null)
        {
        	if (spellData.spellObj instanceof SpellShield) 
        		par3List.add("Magic Ward");
        	else if (spellData.spellObj instanceof SpellAoE)
        		par3List.add("Area of Effect");
        	else if (spellData.spellObj instanceof ISpellTargeted && !(spellData.spellObj instanceof SpellTeleport))
        		par3List.add("Projectile");
        	else if (spellData.spellObj instanceof SpellSummon && !(spellData.spellObj instanceof SpellSummonMount))
        		par3List.add("Summon Minion");
        	else if (spellData.spellObj instanceof SpellSummonMount)
        		par3List.add("Summon Mount");
        	else 
        		par3List.add("Miscellaneous");
        	
        	if (spellData.spellCooldown != 0)
        	{
        		int seconds = spellData.spellCooldown / 20;		
        		int minutes = seconds / 60;
        		seconds = seconds % 60;
        		par3List.add(EnumChatFormatting.RED + "Cooldown: " + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
        	}
        }
    }
    
    /** Returns the given ItemStack's display name. */
    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack par1ItemStack)
    {
    	SpellData spellData = SpellData.readFromNBTTagCompound(this.getSpellTag(par1ItemStack));

        if (spellData.spellObj != null) 
        	return (spellData.spellObj.getTranslatedName(spellData.spellLevel));
        
        return super.getItemStackDisplayName(par1ItemStack);
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
    
    /** Returns an ItemStack of a single spell book with the given spell data. */
    public ItemStack getSpellItemStack(SpellData par1SpellData)
    {
        ItemStack itemstack = new ItemStack(this);
        itemstack.setTagCompound(new NBTTagCompound()); 	
    	NBTTagCompound spellTag = SpellData.writeToNBTTagCompound(par1SpellData);
    	itemstack.getTagCompound().setTag("spell", spellTag);    	
        return itemstack;
    }

    /** Makes the item tooltip text a light blue color. */
    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.RARE;
    }

    /** Casts the spell book's spell on right click. */
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {		
    	if (!par2World.isRemote)
    	{
    		NBTTagCompound spellTag = this.getSpellTag(par1ItemStack);
        	SpellData spellData = SpellData.readFromNBTTagCompound(spellTag);	
        	boolean inCreative = par3EntityPlayer.capabilities.isCreativeMode;
        	
        	if (!inCreative && spellData.spellCooldown > 0)
        	{
        		if (spellData.spellCooldown > 40)
        			par3EntityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Spell is on cooldown!"));
        		
        		return par1ItemStack;  
        	}
        	
        	ManaProperties props = ManaProperties.get(par3EntityPlayer);
        	
        	if (inCreative)
        	{
        		spellData.castSpell(par2World, par3EntityPlayer);
        	}
        	else if (props.consumeMana(spellData.spellObj.getManaCost()))
        	{
        		spellData.castSpell(par2World, par3EntityPlayer);
        		SpellData.startCooldown(spellTag);
        	}
        	else
        		par3EntityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Not enough mana!"));
    	}
    	
    	return par1ItemStack;
    }
    
    /** Updates the remaining cooldown every game tick. */
    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
    {
    	if (!par2World.isRemote) 
    		SpellData.tickCooldown(this.getSpellTag(par1ItemStack));
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

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return slotChanged;
    }
}
