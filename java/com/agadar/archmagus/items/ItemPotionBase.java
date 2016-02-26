package com.agadar.archmagus.items;

import java.util.ArrayList;
import java.util.List;

import com.agadar.archmagus.Archmagus;
import com.agadar.archmagus.potion.ModPotions;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPotionBase extends ItemPotion 
{
	public final String Name = "potion_base";
	
	public ItemPotionBase()
	{
		super();
		this.setUnlocalizedName(Archmagus.MODID + "_" + Name);
        GameRegistry.registerItem(this, Name);
	}

	@SuppressWarnings("unchecked")
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item par1Item, CreativeTabs par2CreativeTab, @SuppressWarnings("rawtypes") List par3List)
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
		ItemStack itemStack = new ItemStack(Archmagus.itemPotionBase, 1, splash ? 16384 : 1);		
		List<PotionEffect> effects = new ArrayList<PotionEffect>();
		effects.add(new PotionEffect(ModPotions.mana.id, 1, amplification));		
		setEffects(itemStack, effects);
		return itemStack;
	}
	
	/** Returns an item stack of a Mana Regeneration potion. */
	public static ItemStack getManaRegenStack(boolean splash, int amplification, boolean extended)
	{
		ItemStack itemStack = new ItemStack(Archmagus.itemPotionBase, 1, splash ? 16384 : 1);		
		List<PotionEffect> effects = new ArrayList<PotionEffect>();
		effects.add(new PotionEffect(ModPotions.manaRegen.id, (int)(900 * getDurationModifier(splash, amplification, extended)), amplification));		
		setEffects(itemStack, effects);
		return itemStack;
	}
	
	/** Returns an item stack of a Restoration potion. */
	public static ItemStack getRestorationStack(boolean splash, int amplification)
	{
		ItemStack itemStack = new ItemStack(Archmagus.itemPotionBase, 1, splash ? 16384 : 1);		
		List<PotionEffect> effects = new ArrayList<PotionEffect>();
		effects.add(new PotionEffect(ModPotions.mana.id, 1, amplification));	
		effects.add(new PotionEffect(Potion.heal.id, 1, amplification));
		setEffects(itemStack, effects);
		return itemStack;
	}
	
	/** Translates the given List of PotionEffects to an NBTTagList and adds it to the given ItemStack's NBTTagCompound. */
    public static void setEffects(ItemStack par1ItemStack, List<PotionEffect> par2Effects)
    {
    	par1ItemStack.setTagCompound(new NBTTagCompound());
    	par1ItemStack.getTagCompound().setTag("CustomPotionEffects", new NBTTagList());
    	NBTTagList effectsTagList = par1ItemStack.getTagCompound().getTagList("CustomPotionEffects", 10);
    	
    	for (PotionEffect effect : par2Effects)
    	{
    		NBTTagCompound effectTag = new NBTTagCompound();
    		effect.writeCustomPotionEffectToNBT(effectTag);
    		effectsTagList.appendTag(effectTag);
    	}
    }
    
    /** Calculates the duration modifier for a potion effect according to the given parameters. */
	public static float getDurationModifier(boolean splash, int amplification, boolean extended)
	{
		float modifier = splash ? 0.75F : 1.0F;		
		for (int i = 0; i < amplification; i++)
			modifier /= 2;
		return extended ? modifier * 8 / 3 : modifier;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
		return 16777215;
    }
}
