package com.agadar.archmagus;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;

import com.agadar.archmagus.blocks.BlockManaCrystal;
import com.agadar.archmagus.blocks.BlockManaCrystalOre;
import com.agadar.archmagus.eventhandler.ModEventHandlers;
import com.agadar.archmagus.items.ItemAppleMana;
import com.agadar.archmagus.items.ItemManaCrystal;
import com.agadar.archmagus.items.ItemPotionBase;
import com.agadar.archmagus.items.ItemSpellBook;
import com.agadar.archmagus.items.StrictBrewingRecipe;
import com.agadar.archmagus.misc.ManaCrystalGen;
import com.agadar.archmagus.misc.MaxManaMessage;
import com.agadar.archmagus.misc.PotionBaseMeshDefinition;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@net.minecraftforge.fml.common.Mod(modid = Archmagus.MODID, version = Archmagus.VERSION, name = Archmagus.NAME)
public class Archmagus
{
	@Instance(value = Archmagus.NAME)
	public static Archmagus instance;

	@SidedProxy(clientSide = Archmagus.CLIENTSIDE, serverSide = Archmagus.SERVERSIDE)
	public static CommonProxy proxy;

	/*
	 * These are the references we use. These values are the same for our entire
	 * mod, so we only have to make them once here, and we can always access
	 * them.
	 */
	public static final String MODID = "archmagus";
	public static final String VERSION = "0.8.0";
	public static final String NAME = "Archmagus";
	public static final String CLIENTSIDE = "com.agadar.archmagus.ClientProxy";
	public static final String SERVERSIDE = "com.agadar.archmagus.CommonProxy";

	/** The message channel for this mod. */
	public static SimpleNetworkWrapper networkWrapper;
	/** The mana crystal ore generator. */
	ManaCrystalGen manaCrystalGen = new ManaCrystalGen();

	/** The creative tab for the spell books. */
	public final static CreativeTabs tabSpellBooks = new CreativeTabs("tabSpellBooks")
	{
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			return spell_book;
		}
	};

	/** The spell book. */
	public final static Item spell_book = new ItemSpellBook();
	/** The mana apple. */
	public final static Item apple_mana = new ItemAppleMana();
	/** The mana crystal. */
	public final static Item mana_crystal = new ItemManaCrystal();
	/** The base of this mod's ItemPotions. */
	public final static Item itemPotionBase = new ItemPotionBase();

	/** The mana crystal ore. */
	public final static Block mana_crystal_ore = new BlockManaCrystalOre();
	/** The mana crystal block. */
	public final static Block mana_crystal_block = new BlockManaCrystal();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		/** Network stuff. */
		networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		networkWrapper.registerMessage(MaxManaMessage.Handler.class, MaxManaMessage.class, 0, Side.CLIENT);

		/** Event handlers. */
		ModEventHandlers.registerModEventHandlers();

		// Register item textures for potions.
		if (event.getSide() == Side.CLIENT)
		{
			PotionBaseMeshDefinition pbmd = new PotionBaseMeshDefinition();
			ModelLoader.setCustomMeshDefinition(itemPotionBase, pbmd);
			ModelBakery.registerItemVariants(itemPotionBase, pbmd.drinkable, pbmd.splash);
		}

		/** Register the client-only stuff. */
		proxy.registerRenderers();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		// Recipes.
		GameRegistry.addRecipe(new ItemStack(apple_mana), "xxx", "xyx", "xxx", 'x', mana_crystal, 'y', Items.apple);
		GameRegistry.addRecipe(new ItemStack(mana_crystal_block), "xxx", "xxx", "xxx", 'x', mana_crystal);
		GameRegistry.addRecipe(new ItemStack(mana_crystal, 9), "x", 'x', mana_crystal_block);

		// Register item(block) textures.
		if (event.getSide() == Side.CLIENT)
		{
			RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
			renderItem.getItemModelMesher().register(spell_book, 0,
					new ModelResourceLocation(MODID + ":" + ((ItemSpellBook) spell_book).Name, "inventory"));
			renderItem.getItemModelMesher().register(apple_mana, 0,
					new ModelResourceLocation(MODID + ":" + ((ItemAppleMana) apple_mana).Name, "inventory"));
			renderItem.getItemModelMesher().register(mana_crystal, 0,
					new ModelResourceLocation(MODID + ":" + ((ItemManaCrystal) mana_crystal).Name, "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(mana_crystal_ore), 0,
					new ModelResourceLocation(MODID + ":" + ((BlockManaCrystalOre) mana_crystal_ore).Name,
							"inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(mana_crystal_block), 0,
					new ModelResourceLocation(MODID + ":" + ((BlockManaCrystal) mana_crystal_block).Name, "inventory"));
		}

		// Mana crystal ore world generator.
		GameRegistry.registerWorldGenerator(manaCrystalGen, 1);

		// Brewing recipes.
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
		BrewingRecipeRegistry.addRecipe(new StrictBrewingRecipe(awkward, new ItemStack(mana_crystal), mana));
		/** Potion of Mana + Glowstone -> Potion of Mana (Amplified). */
		BrewingRecipeRegistry.addRecipe(new StrictBrewingRecipe(mana, glowstone, manaAmpl));
		/** Potion of Mana + Gunpowder -> Splash Potion of Mana. */
		BrewingRecipeRegistry.addRecipe(new StrictBrewingRecipe(mana, gunpowder, manaSplash));
		/** Potion of Mana (Amplified) + Gunpowder -> Splash Potion of Mana (Amplified). */
		BrewingRecipeRegistry.addRecipe(new StrictBrewingRecipe(manaAmpl, gunpowder, manaSplashAmpl));
		/** Splash Potion of Mana + Glowstone -> Splash Potion of Mana (Amplified). */
		BrewingRecipeRegistry.addRecipe(new StrictBrewingRecipe(manaSplash, glowstone, manaSplashAmpl));

		/** Awkward Potion + Crystalline Apple -> Potion of Mana Regen. */
		BrewingRecipeRegistry.addRecipe(new StrictBrewingRecipe(awkward, new ItemStack(apple_mana), regen));
		/** Potion of Mana Regen + Glowstone -> Potion of Mana Regen (Amplified). */
		BrewingRecipeRegistry.addRecipe(new StrictBrewingRecipe(regen, glowstone, regenAmpl));
		/** Potion of Mana Regen (Extended) + Glowstone -> Potion of Mana Regen (Amplified). */
		BrewingRecipeRegistry.addRecipe(new StrictBrewingRecipe(regenExt, glowstone, regenAmpl));
		/** Potion of Mana Regen + Redstone -> Potion of Mana Regen (Extended). */
		BrewingRecipeRegistry.addRecipe(new StrictBrewingRecipe(regen, redstone, regenExt));
		/** Potion of Mana Regen (Amplified) + Redstone -> Potion of Mana Regen (Extended). */
		BrewingRecipeRegistry.addRecipe(new StrictBrewingRecipe(regenAmpl, redstone, regenExt));
		/** Potion of Mana Regen + Gunpowder -> Splash Potion of Mana Regen. */
		BrewingRecipeRegistry.addRecipe(new StrictBrewingRecipe(regen, gunpowder, regenSplash));
		/** Potion of Mana Regen (Amplified) + Gunpowder -> Splash Potion of Mana Regen (Amplified). */
		BrewingRecipeRegistry.addRecipe(new StrictBrewingRecipe(regenAmpl, gunpowder, regenSplashAmpl));
		/** Splash Potion of Mana Regen + Glowstone -> Splash Potion of Mana Regen (Amplified). */
		BrewingRecipeRegistry.addRecipe(new StrictBrewingRecipe(regenSplash, glowstone, regenSplashAmpl));
		/** Splash Potion of Mana Regen (Extended) + Glowstone -> Splash Potion of Mana Regen (Amplified). */
		BrewingRecipeRegistry.addRecipe(new StrictBrewingRecipe(regenSplashExt, glowstone, regenSplashAmpl));
		/** Potion of Mana Regen (Extended) + Gunpowder -> Splash Potion of Mana Regen (Extended). */
		BrewingRecipeRegistry.addRecipe(new StrictBrewingRecipe(regenExt, gunpowder, regenSplashExt));
		/** Splash Potion of Mana Regen + Redstone -> Splash Potion of Mana Regen (Extended). */
		BrewingRecipeRegistry.addRecipe(new StrictBrewingRecipe(regenSplash, redstone, regenSplashExt));
		/** Splash Potion of Mana Regen (Amplified) + Redstone -> Splash Potion of Mana Regen (Extended). */
		BrewingRecipeRegistry.addRecipe(new StrictBrewingRecipe(regenSplashAmpl, redstone, regenSplashExt));
	}
}
