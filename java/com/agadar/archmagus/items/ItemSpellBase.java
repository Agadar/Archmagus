package com.agadar.archmagus.items;

import java.util.List;

import com.agadar.archmagus.Archmagus;
import com.agadar.archmagus.spell.SpellData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** The base class for ItemSpell and ItemSpellBook. */
public abstract class ItemSpellBase extends Item
{
	public final String Name;
	
	public ItemSpellBase(String _name, CreativeTabs _tab)
	{
		super();
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.Name = _name;
		this.setUnlocalizedName(Archmagus.MODID + "_" + Name);
		this.setCreativeTab(_tab);
        GameRegistry.registerItem(this, Name);
	}
	
	/** Returns the ItemStack's spell tag. */
    public final NBTTagCompound getSpellTag(ItemStack par1ItemStack)
    {
    	NBTTagCompound nbtt = par1ItemStack.getTagCompound();
    	
    	if (nbtt != null && nbtt.hasKey("spell"))
    		return (NBTTagCompound) nbtt.getTag("spell");
    	
    	return null;
    }
    
    /** Returns an ItemStack of a single spell or spell book with the given spell data. */
    public final ItemStack getSpellItemStack(SpellData par1SpellData)
    {
        ItemStack itemstack = new ItemStack(this);
        itemstack.setTagCompound(new NBTTagCompound()); 	
    	NBTTagCompound spellTag = SpellData.writeToNBTTagCompound(par1SpellData);
    	itemstack.getTagCompound().setTag("spell", spellTag);    	
        return itemstack;
    }
    
    /** Makes the item tooltip text a light blue color. */
    @Override
    public final EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.RARE;
    }
    
    /** Adds additional information to the item's tooltip. */
    @SuppressWarnings("rawtypes")
	@Override
	@SideOnly(Side.CLIENT)
    public abstract void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4);

    
    /** Casts the ItemSpell's spell or learns the ItemSpellBook's spell on right click. */
    @Override
    public abstract ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer);

    @SuppressWarnings({ "rawtypes" })
	@Override
    @SideOnly(Side.CLIENT)
    public abstract void getSubItems(Item par1Item, CreativeTabs par2CreativeTab, List par3List);
}
