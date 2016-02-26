package com.agadar.archmagus.items;

import java.util.List;

import com.agadar.archmagus.Archmagus;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** This Item allows for casting spells. */
public class ItemSpellBook extends Item 
{
	/** An array containing icons for each Spell's spell book. */
	//@SideOnly(Side.CLIENT)
	//protected IIcon[] icons;
	
	public final String Name = "spell_book";
	
	public ItemSpellBook()
	{
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.setUnlocalizedName(Archmagus.MODID + "_" + Name);
		this.setCreativeTab(Archmagus.tabSpellBooks);
        GameRegistry.registerItem(this, Name);
	}

	/** Returns the ItemStack's spell tag. If it doesn't have one then
	 *  it is first assigned one before it is returned. */
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
        /*SpellData spellData = SpellData.readFromNBTTagCompound(this.getSpellTag(par1ItemStack));

        if (spellData.spellObj != null)
        {
        	if (spellData.spellObj instanceof ISpellTargeted) par3List.add("Targeted");
        	else if (spellData.spellObj instanceof SpellSummon) par3List.add("Summoning");
        	else if (spellData.spellObj instanceof SpellShield) par3List.add("Enhancement");
        	else if (spellData.spellObj instanceof SpellAoE) par3List.add("Area of effect");
        	else par3List.add("Miscellaneous");
        	
        	if (spellData.spellCooldown != 0) par3List.add(EnumChatFormatting.RED + "Cooldown: " + (spellData.spellCooldown / 20) + " seconds");
        }*/
    }
    
    /** Returns the given ItemStack's display name. */
    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack par1ItemStack)
    {
    	/*SpellData spellData = SpellData.readFromNBTTagCompound(this.getSpellTag(par1ItemStack));

        if (spellData.spellObj != null) 
        	return (spellData.spellObj.getTranslatedName(spellData.spellLevel));*/
        
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
    		
    		/*SpellData spellData1 = SpellData.readFromNBTTagCompound(spellTag1);
    		SpellData spellData2 = SpellData.readFromNBTTagCompound(spellTag2);   		
    		SpellData spellData3 = SpellData.tryCombine(spellData1, spellData2);
    		
    		if (spellData3 != null)
    			return this.getSpellItemStack(spellData3);*/
    	}
    	
    	return null;
    }
    
    /** Returns an ItemStack of a single spell book with the given spell data. */
    /*public ItemStack getSpellItemStack(SpellData par1SpellData)
    {
        ItemStack itemstack = new ItemStack(this);
        itemstack.stackTagCompound = new NBTTagCompound();  	
    	NBTTagCompound spellTag = SpellData.writeToNBTTagCompound(par1SpellData);
    	itemstack.stackTagCompound.setTag("spell", spellTag);
    	itemstack.setItemDamage(par1SpellData.spellObj.effectId);
    	
        return itemstack;
    }*/

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
    	/*if (!par2World.isRemote)
    	{
    		NBTTagCompound spellTag = this.getSpellTag(par1ItemStack);
        	SpellData spellData = SpellData.readFromNBTTagCompound(spellTag);	
        	boolean inCreative = par3EntityPlayer.capabilities.isCreativeMode;
        	
        	if (!inCreative && spellData.spellCooldown > 0) 
        		return par1ItemStack;  
        	
        	ManaProperties props = ManaProperties.get(par3EntityPlayer);
        	
        	if (inCreative || props.consumeMana(spellData.spellObj.getManaCost()))
        	{
        		spellData.castSpell(par2World, par3EntityPlayer);
        		SpellData.startCooldown(spellTag);
        	}
    	}*/
    	
    	return par1ItemStack;
    }
    
    /** Updates the remaining cooldown every game tick. */
    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
    {
    	/*if (!par2World.isRemote) 
    		SpellData.tickCooldown(this.getSpellTag(par1ItemStack));*/
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item par1Item, CreativeTabs par2CreativeTab, List par3List)
    {
    	/*for (int i1 = 0; i1 < Spells.spellList.length; i1++)
    	{
    		Spell spell = Spells.spellList[i1];
    		
    		if (spell != null)
    		{
    			for (short i2 = spell.getMinLevel(); i2 <= spell.getMaxLevel(); i2++)
            	{
    				par3List.add(((ItemSpellBook) ModItemsBlocks.spell_book).getSpellItemStack(new SpellData(spell, i2)));
            	}
    		}
    	}*/
    }
    
    /*@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1) 
	{
		int j = MathHelper.clamp_int(par1, 0, this.icons.length - 1);
		
		if (this.icons[j] != null)
			return this.icons[j];
		
		return super.getIconFromDamage(par1);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        icons = new IIcon[256];
        IIcon blazefireIcon = par1IconRegister.registerIcon(Archmagus.MODID + ":blazefire_book");
        IIcon teleportIcon = par1IconRegister.registerIcon(Archmagus.MODID + ":teleport_book");
        IIcon witherIcon = par1IconRegister.registerIcon(Archmagus.MODID + ":wither_book");
        IIcon skeletonIcon = par1IconRegister.registerIcon(Archmagus.MODID + ":skeleton_book");
        IIcon zombieIcon = par1IconRegister.registerIcon(Archmagus.MODID + ":zombie_book");
        icons[Spells.blazefire.effectId] = blazefireIcon;
        icons[Spells.ghastfire.effectId] = skeletonIcon;
        icons[Spells.witherblast.effectId] = witherIcon;
        icons[Spells.summon_wolf.effectId] = par1IconRegister.registerIcon(Archmagus.MODID + ":wolf_book");
        icons[Spells.raise_skeleton.effectId] = skeletonIcon;
        icons[Spells.raise_wither_skeleton.effectId] = witherIcon;
        icons[Spells.raise_zombie.effectId] = zombieIcon;
        icons[Spells.raise_zombie_pigman.effectId] = par1IconRegister.registerIcon(Archmagus.MODID + ":zombie_pigman_book");
        icons[Spells.summon_witch.effectId] = par1IconRegister.registerIcon(Archmagus.MODID + ":witch_book");
        icons[Spells.summon_spider.effectId] = par1IconRegister.registerIcon(Archmagus.MODID + ":spider_book");
        icons[Spells.summon_cave_spider.effectId] = par1IconRegister.registerIcon(Archmagus.MODID + ":cave_spider_book");
        icons[Spells.teleport.effectId] = teleportIcon;
        icons[Spells.respawn.effectId] = teleportIcon;
        icons[Spells.fireShield.effectId] = par1IconRegister.registerIcon(Archmagus.MODID + ":fire_shield_book");
        icons[Spells.earthShield.effectId] = par1IconRegister.registerIcon(Archmagus.MODID + ":earth_shield_book");
        icons[Spells.waterShield.effectId] = par1IconRegister.registerIcon(Archmagus.MODID + ":water_shield_book");
        icons[Spells.stormShield.effectId] = par1IconRegister.registerIcon(Archmagus.MODID + ":storm_shield_book");
        icons[Spells.frostShield.effectId] = par1IconRegister.registerIcon(Archmagus.MODID + ":frost_shield_book");
        icons[Spells.blazestorm.effectId] = blazefireIcon;
        icons[Spells.lightningstorm.effectId] = par1IconRegister.registerIcon(Archmagus.MODID + ":lightning_book");      
        icons[Spells.raise_zombie_horse.effectId] = zombieIcon;
        icons[Spells.raise_skeleton_horse.effectId] = skeletonIcon;
        //icons[Spells.raise_wither_horse.effectId] = witherIcon;
    }*/
}
