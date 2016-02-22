package com.agadar.archmagus.itemblock;

import java.util.ArrayList;
import java.util.List;

import com.agadar.archmagus.Archmagus;
import com.agadar.archmagus.potion.ModPotions;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		par3List.add(getManaStack(false, 0));
		/** Potion of Mana (Amplified). */
		par3List.add(getManaStack(false, 1));
		/** Splash Potion of Mana. */
		par3List.add(getManaStack(true, 0));
		/** Splash Potion of Mana (Amplified). */
		par3List.add(getManaStack(true, 1));
		
		/** Potion of Mana Regeneration. */
		par3List.add(getManaRegenStack(false, 0, false));
		/** Potion of Mana Regeneration (Amplified). */
		par3List.add(getManaRegenStack(false, 1, false));
		/** Potion of Mana Regeneration (Extended). */
		par3List.add(getManaRegenStack(false, 0, true));
		/** Splash Potion of Mana Regeneration. */
		par3List.add(getManaRegenStack(true, 0, false));
		/** Splash Potion of Mana Regeneration (Amplified). */
		par3List.add(getManaRegenStack(true, 1, false));
		/** Splash Potion of Mana Regeneration (Extended). */
		par3List.add(getManaRegenStack(true, 0, true));
    }
	
	/** Returns an item stack of a Mana potion. */
	public static ItemStack getManaStack(boolean splash, int amplification)
	{
		ItemStack itemStack = new ItemStack(ModItemsBlocks.itemPotionBase, 1, splash ? 16384 : 1);		
		List<PotionEffect> effects = new ArrayList<PotionEffect>();
		effects.add(new PotionEffect(ModPotions.mana.id, 1, amplification));		
		BrewingRecipes.brewing().setEffects(itemStack, effects);
		return itemStack;
	}
	
	/** Returns an item stack of a Mana Regeneration potion. */
	public static ItemStack getManaRegenStack(boolean splash, int amplification, boolean extended)
	{
		ItemStack itemStack = new ItemStack(ModItemsBlocks.itemPotionBase, 1, splash ? 16384 : 1);		
		List<PotionEffect> effects = new ArrayList<PotionEffect>();
		effects.add(new PotionEffect(ModPotions.manaRegen.id, (int)(900 * BrewingRecipes.brewing().getDurationModifier(splash, amplification, extended)), amplification));		
		BrewingRecipes.brewing().setEffects(itemStack, effects);
		return itemStack;
	}
	
	/** Returns an item stack of a Restoration potion. */
	public static ItemStack getRestorationStack(boolean splash, int amplification)
	{
		ItemStack itemStack = new ItemStack(ModItemsBlocks.itemPotionBase, 1, splash ? 16384 : 1);		
		List<PotionEffect> effects = new ArrayList<PotionEffect>();
		effects.add(new PotionEffect(ModPotions.mana.id, 1, amplification));	
		effects.add(new PotionEffect(Potion.heal.id, 1, amplification));
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
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
		return 16777215;
    }
	
	@Override
	public IIcon getIcon(ItemStack par1ItemStack, int par2Pass)
	{
		if (par2Pass == 0)
			return this.overlayIcon;

		return super.getIcon(par1ItemStack, par2Pass);
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
		return pass == 0;
    }
}
