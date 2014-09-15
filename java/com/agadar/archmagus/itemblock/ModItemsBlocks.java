package com.agadar.archmagus.itemblock;

import com.agadar.archmagus.Archmagus;
import com.agadar.brewingapi.BrewingRecipes;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/** Responsible for instantiating and registering this mod's items and blocks. */
public class ModItemsBlocks 
{
	/** The creative tab for the spell books. */
 	public final static CreativeTabs tabSpellBooks = new CreativeTabs("tabSpellBooks") 
	{
	    @Override
	    @SideOnly(Side.CLIENT)
	    public Item getTabIconItem() 
	    { 
	    	return ModItemsBlocks.spell_book; 
	    }
	};
	
	/** The spell book. */
	public final static Item spell_book = new ItemSpellBook();
	/** The mana apple. */
	public final static Item apple_mana = new ItemAppleMana();
	/** The mana crystal. */
	public final static Item mana_crystal = new Item().setUnlocalizedName("mana_crystal").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(Archmagus.MODID + ":" + "mana_crystal");
	/** The base of this mod's ItemPotions. */
	public final static Item itemPotionBase = new ItemPotionBase();
	
	/** The mana crystal ore. */
	public final static Block mana_crystal_ore = new BlockManaCrystalOre();
	/** The mana crystal block. */
	public final static Block mana_crystal_block = new BlockManaCrystal();
	
	/** Instantiates and registers this mod's items and blocks. */
	public static void registerModItemsAndBlocks()
	{
		/** Register the items. */
		registerItem(spell_book);
		registerItem(apple_mana);
		registerItem(mana_crystal);	
		registerItem(itemPotionBase);
		
		/** Register the blocks. */
		registerBlock(mana_crystal_ore);
		registerBlock(mana_crystal_block);
		
		/** Register the crafting recipes. */
		GameRegistry.addRecipe(new ItemStack(apple_mana), "xxx", "xyx", "xxx", 'x', mana_crystal, 'y', Items.apple);
		GameRegistry.addRecipe(new ItemStack(mana_crystal_block), "xxx", "xxx", "xxx", 'x', mana_crystal);
		GameRegistry.addRecipe(new ItemStack(mana_crystal, 9), "x", 'x', mana_crystal_block);
	
		/** Register the brewing recipes. */		
		/** Ingredients. */
		ItemStack awkward = new ItemStack(Items.potionitem, 1, 16);
		ItemStack gunpowder = new ItemStack(Items.gunpowder);
		ItemStack glowstone = new ItemStack(Items.glowstone_dust);
		ItemStack redstone = new ItemStack(Items.redstone);
		
		/** Potion of Mana. */
		ItemStack mana = ItemPotionBase.getManaStack(false, 0); 
		/** Potion of Mana (Amplified). */
		ItemStack manaAmpl = ItemPotionBase.getManaStack(false, 1);
		/** Splash Potion of Mana. */
		ItemStack manaSplash = ItemPotionBase.getManaStack(true, 0);
		/** Splash Potion of Mana (Amplified). */
		ItemStack manaSplashAmpl = ItemPotionBase.getManaStack(true, 1);
				
		/** Potion of Mana Regeneration. */
		ItemStack regen = ItemPotionBase.getManaRegenStack(false, 0, false);
		/** Potion of Mana Regeneration (Amplified). */
		ItemStack regenAmpl = ItemPotionBase.getManaRegenStack(false, 1, false);
		/** Potion of Mana Regeneration (Extended). */
		ItemStack regenExt = ItemPotionBase.getManaRegenStack(false, 0, true);
		/** Splash Potion of Mana Regeneration. */
		ItemStack regenSplash = ItemPotionBase.getManaRegenStack(true, 0, false);
		/** Splash Potion of Mana Regeneration (Amplified). */
		ItemStack regenSplashAmpl = ItemPotionBase.getManaRegenStack(true, 1, false);
		/** Splash Potion of Mana Regeneration (Extended). */
		ItemStack regenSplashExt = ItemPotionBase.getManaRegenStack(true, 0, true);

		/** Awkward Potion + Mana Crystal -> Potion of Mana. */
		BrewingRecipes.brewing().addBrewing(awkward, new ItemStack(mana_crystal), mana);
		/** Potion of Mana + Glowstone -> Potion of Mana (Amplified). */
		BrewingRecipes.brewing().addBrewing(mana, glowstone, manaAmpl);
		/** Potion of Mana + Gunpowder -> Splash Potion of Mana. */
		BrewingRecipes.brewing().addBrewing(mana, gunpowder, manaSplash);
		/** Potion of Mana (Amplified) + Gunpowder -> Splash Potion of Mana (Amplified). */
		BrewingRecipes.brewing().addBrewing(manaAmpl, gunpowder, manaSplashAmpl);
		/** Splash Potion of Mana + Glowstone -> Splash Potion of Mana (Amplified). */
		BrewingRecipes.brewing().addBrewing(manaSplash, glowstone, manaSplashAmpl);
		
		/** Awkward Potion + Crystalline Apple -> Potion of Mana Regen. */
		BrewingRecipes.brewing().addBrewing(awkward, new ItemStack(apple_mana), regen);
		/** Potion of Mana Regen + Glowstone -> Potion of Mana Regen (Amplified). */
		BrewingRecipes.brewing().addBrewing(regen, glowstone, regenAmpl);
		/** Potion of Mana Regen (Extended) + Glowstone -> Potion of Mana Regen (Amplified). */
		BrewingRecipes.brewing().addBrewing(regenExt, glowstone, regenAmpl);
		/** Potion of Mana Regen + Redstone -> Potion of Mana Regen (Extended). */
		BrewingRecipes.brewing().addBrewing(regen, redstone, regenExt);
		/** Potion of Mana Regen (Amplified) + Redstone -> Potion of Mana Regen (Extended). */
		BrewingRecipes.brewing().addBrewing(regenAmpl, redstone, regenExt);
		/** Potion of Mana Regen + Gunpowder -> Splash Potion of Mana Regen. */
		BrewingRecipes.brewing().addBrewing(regen, gunpowder, regenSplash);
		/** Potion of Mana Regen (Amplified) + Gunpowder -> Splash Potion of Mana Regen (Amplified). */
		BrewingRecipes.brewing().addBrewing(regenAmpl, gunpowder, regenSplashAmpl);
		/** Splash Potion of Mana Regen + Glowstone -> Splash Potion of Mana Regen (Amplified). */
		BrewingRecipes.brewing().addBrewing(regenSplash, glowstone, regenSplashAmpl);
		/** Splash Potion of Mana Regen (Extended) + Glowstone -> Splash Potion of Mana Regen (Amplified). */
		BrewingRecipes.brewing().addBrewing(regenSplashExt, glowstone, regenSplashAmpl);
		/** Potion of Mana Regen (Extended) + Gunpowder -> Splash Potion of Mana Regen (Extended). */
		BrewingRecipes.brewing().addBrewing(regenExt, gunpowder, regenSplashExt);
		/** Splash Potion of Mana Regen + Redstone -> Splash Potion of Mana Regen (Extended). */
		BrewingRecipes.brewing().addBrewing(regenSplash, redstone, regenSplashExt);
		/** Splash Potion of Mana Regen (Amplified) + Redstone -> Splash Potion of Mana Regen (Extended). */
		BrewingRecipes.brewing().addBrewing(regenSplashAmpl, redstone, regenSplashExt);
	}
	
	/**
     * Registers all blocks. The basic format is [MODID_NAME]
     * When you call this method, with your block assigned, it will take care of everything.
     * @param block
     */
	private static void registerBlock(Block block)
	{
		GameRegistry.registerBlock(block, Archmagus.MODID + "_" + block.getUnlocalizedName().substring(5));
	}

    /**
     * Registers all items. The basic format is [MODID_NAME]
     * When you call this method, with your item assigned, it will take care of everything.
     * @param item
     */
	private static void registerItem(Item item)
	{
		GameRegistry.registerItem(item, Archmagus.MODID + "_" + item.getUnlocalizedName().substring(5));
	}
}
