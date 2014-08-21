package com.agadar.archmagus.itemblock;

import java.util.ArrayList;
import java.util.List;

import com.agadar.archmagus.Archmagus;
import com.agadar.archmagus.potion.ModPotions;
import com.agadar.brewingapi.BrewingRecipes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;

public class ItemPotionBase extends ItemPotion 
{
	@SideOnly(Side.CLIENT)
	private IIcon bottleDrinkable;
	@SideOnly(Side.CLIENT)
	private IIcon bottleSplash;
	@SideOnly(Side.CLIENT)
	private IIcon overlayIcon;
    
	public ItemPotionBase()
	{
		super();
		this.setUnlocalizedName("potion_base");
		this.setTextureName("potion");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item par1Item, CreativeTabs par2CreativeTab, List par3List)
    {
		/** Potion of Mana. */
		par3List.add(getManaPotionStack(false, 0));
		/** Potion of Mana (Amplified). */
		par3List.add(getManaPotionStack(false, 1));
		/** Splash Potion of Mana. */
		par3List.add(getManaPotionStack(true, 0));
		/** Splash Potion of Mana (Amplified). */
		par3List.add(getManaPotionStack(true, 1));
		
		/** Potion of Mana Regeneration. */
		par3List.add(getManaRegenPotionStack(false, 0, false));
		/** Potion of Mana Regeneration (Amplified). */
		par3List.add(getManaRegenPotionStack(false, 1, false));
		/** Potion of Mana Regeneration (Extended). */
		par3List.add(getManaRegenPotionStack(false, 0, true));
		/** Splash Potion of Mana Regeneration. */
		par3List.add(getManaRegenPotionStack(true, 0, false));
		/** Splash Potion of Mana Regeneration (Amplified). */
		par3List.add(getManaRegenPotionStack(true, 1, false));
		/** Splash Potion of Mana Regeneration (Extended). */
		par3List.add(getManaRegenPotionStack(true, 0, true));
    }
	
	/** Returns an item stack of a Mana potion. */
	public static ItemStack getManaPotionStack(boolean splash, int amplification)
	{
		ItemStack itemStack = new ItemStack(ModItemsBlocks.itemPotionBase, 1, splash ? 16482 : 8194);		
		List<PotionEffect> effects = new ArrayList<PotionEffect>();
		effects.add(new PotionEffect(ModPotions.mana.id, 1, amplification));		
		BrewingRecipes.brewing().setEffects(itemStack, effects);
		return itemStack;
	}
	
	/** Returns an item stack of a Mana Regeneration potion. */
	public static ItemStack getManaRegenPotionStack(boolean splash, int amplification, boolean extended)
	{
		ItemStack itemStack = new ItemStack(ModItemsBlocks.itemPotionBase, 1, splash ? 16398 : 8206);		
		List<PotionEffect> effects = new ArrayList<PotionEffect>();
		effects.add(new PotionEffect(ModPotions.manaRegen.id, (int)(900 * BrewingRecipes.brewing().getDurationModifier(splash, amplification, extended)), amplification));		
		BrewingRecipes.brewing().setEffects(itemStack, effects);
		return itemStack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int p_77617_1_)
    {
        return isSplash(p_77617_1_) ? this.bottleSplash : this.bottleDrinkable;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_)
    {
        return p_77618_2_ == 0 ? this.overlayIcon : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
		this.bottleDrinkable = par1IconRegister.registerIcon(Archmagus.MODID + ":bottle_drinkable");
        this.bottleSplash = par1IconRegister.registerIcon(Archmagus.MODID + ":bottle_splash");
        this.overlayIcon = par1IconRegister.registerIcon(Archmagus.MODID + ":potion_overlay");
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
		return false;
    }
}
