package com.agadar.archmagus;

import org.lwjgl.input.Keyboard;

import com.agadar.archmagus.blocks.BlockManaCrystal;
import com.agadar.archmagus.blocks.BlockManaCrystalOre;
import com.agadar.archmagus.entity.EntityRisenHorse;
import com.agadar.archmagus.entity.EntityRisenSkeleton;
import com.agadar.archmagus.entity.EntityRisenWitherSkeleton;
import com.agadar.archmagus.entity.EntityRisenZombie;
import com.agadar.archmagus.entity.EntityRisenZombiePigman;
import com.agadar.archmagus.entity.EntitySummonedCaveSpider;
import com.agadar.archmagus.entity.EntitySummonedSpider;
import com.agadar.archmagus.entity.EntitySummonedWitch;
import com.agadar.archmagus.entity.EntitySummonedWolf;
import com.agadar.archmagus.eventhandler.KeyInputHandler;
import com.agadar.archmagus.gui.GuiManaBar;
import com.agadar.archmagus.items.ItemAppleMana;
import com.agadar.archmagus.items.ItemManaCrystal;
import com.agadar.archmagus.items.ItemSpellBook;
import com.agadar.archmagus.items.meshdefinitions.PotionBaseMeshDefinition;
import com.agadar.archmagus.items.meshdefinitions.SpellBookMeshDefinition;
import com.agadar.archmagus.model.ModelSummonedWolf;
import com.agadar.archmagus.render.RenderRisenHorse;
import com.agadar.archmagus.render.RenderRisenSkeleton;
import com.agadar.archmagus.render.RenderRisenWitherSkeleton;
import com.agadar.archmagus.render.RenderRisenZombie;
import com.agadar.archmagus.render.RenderRisenZombiePigman;
import com.agadar.archmagus.render.RenderSummonedCaveSpider;
import com.agadar.archmagus.render.RenderSummonedSpider;
import com.agadar.archmagus.render.RenderSummonedWitch;
import com.agadar.archmagus.render.RenderSummonedWolf;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** The proxy that is used client-side. */
public class ClientProxy extends CommonProxy 
{
	public static KeyBinding openSpellBook;	
	private final Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public void registerEntityRenderers()
	{
		/** Entity renderers. */	
		RenderingRegistry.registerEntityRenderingHandler(EntitySummonedWolf.class, new IRenderFactory<EntitySummonedWolf>() {
			@Override
			public Render<? super EntitySummonedWolf> createRenderFor(RenderManager manager) {
				return new RenderSummonedWolf(manager, new ModelSummonedWolf(), 0.5F);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityRisenHorse.class, new IRenderFactory<EntityRisenHorse>() {
			@Override
			public Render<? super EntityRisenHorse> createRenderFor(RenderManager manager) {
				return new RenderRisenHorse(manager, new ModelHorse(), 0.75F);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityRisenSkeleton.class, new IRenderFactory<EntityRisenSkeleton>() {
			@Override
			public Render<? super EntityRisenSkeleton> createRenderFor(RenderManager manager) {
				return new RenderRisenSkeleton(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityRisenWitherSkeleton.class, new IRenderFactory<EntityRisenWitherSkeleton>() {
			@Override
			public Render<? super EntityRisenWitherSkeleton> createRenderFor(RenderManager manager) {
				return new RenderRisenWitherSkeleton(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityRisenZombie.class, new IRenderFactory<EntityRisenZombie>() {
			@Override
			public Render<? super EntityRisenZombie> createRenderFor(RenderManager manager) {
				return new RenderRisenZombie(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityRisenZombiePigman.class, new IRenderFactory<EntityRisenZombiePigman>() {
			@Override
			public Render<? super EntityRisenZombiePigman> createRenderFor(RenderManager manager) {
				return new RenderRisenZombiePigman(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntitySummonedWitch.class, new IRenderFactory<EntitySummonedWitch>() {
			@Override
			public Render<? super EntitySummonedWitch> createRenderFor(RenderManager manager) {
				return new RenderSummonedWitch(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntitySummonedSpider.class, new IRenderFactory<EntitySummonedSpider>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Render<? super EntitySummonedSpider> createRenderFor(RenderManager manager) {
				return new RenderSummonedSpider(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntitySummonedCaveSpider.class, new IRenderFactory<EntitySummonedCaveSpider>() {
			@Override
			public Render<? super EntitySummonedCaveSpider> createRenderFor(RenderManager manager) {
				return new RenderSummonedCaveSpider(manager);
			}
		});
	}
	
	@Override
	public void registerRenderers() 
	{		
		/** Gui renderers. */
		MinecraftForge.EVENT_BUS.register(new GuiManaBar(Minecraft.getMinecraft()));	
		
		// Item and block renderers.
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		renderItem.getItemModelMesher().register(Archmagus.spell_book, 0,
				new ModelResourceLocation(Archmagus.MODID + ":" + ((ItemSpellBook) Archmagus.spell_book).Name, "inventory"));
		renderItem.getItemModelMesher().register(Archmagus.apple_mana, 0,
				new ModelResourceLocation(Archmagus.MODID + ":" + ((ItemAppleMana) Archmagus.apple_mana).Name, "inventory"));
		renderItem.getItemModelMesher().register(Archmagus.mana_crystal, 0,
				new ModelResourceLocation(Archmagus.MODID + ":" + ((ItemManaCrystal) Archmagus.mana_crystal).Name, "inventory"));
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(Archmagus.mana_crystal_ore), 0,
				new ModelResourceLocation(Archmagus.MODID + ":" + ((BlockManaCrystalOre) Archmagus.mana_crystal_ore).Name,
						"inventory"));
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(Archmagus.mana_crystal_block), 0,
				new ModelResourceLocation(Archmagus.MODID + ":" + ((BlockManaCrystal) Archmagus.mana_crystal_block).Name, "inventory"));
		
	}
	
	@Override
	public void registerMeshDefinitions()
	{
		// ItemMeshDefinitions for items that have multiple possible textures.
		PotionBaseMeshDefinition pbmd = new PotionBaseMeshDefinition();
		ModelLoader.setCustomMeshDefinition(Archmagus.itemPotionBase, pbmd);
		ModelBakery.registerItemVariants(Archmagus.itemPotionBase, pbmd.drinkable, pbmd.splash);

		// ItemMeshDefinitions for spellbooks.
		SpellBookMeshDefinition sbmd = new SpellBookMeshDefinition();
		ModelLoader.setCustomMeshDefinition(Archmagus.spell, sbmd);
		ModelBakery.registerItemVariants(Archmagus.spell, sbmd.defaultLoc);
		for (ModelResourceLocation mrl : sbmd.modelLocations.values())
			ModelBakery.registerItemVariants(Archmagus.spell, mrl);
	}

	@Override
	public void registerKeyBindings()
	{
		/* The key for openings the spellbook. */
		openSpellBook = new KeyBinding("key.spellbook", Keyboard.KEY_O, "key.categories.inventory");
		ClientRegistry.registerKeyBinding(openSpellBook);
		MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
	}
	
	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		// Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
		// your packets will not work as expected because you will be getting a
		// client player even when you are on the server!
		// Sounds absurd, but it's true.

		// Solution is to double-check side before returning the player:
		return (ctx.side.isClient() ? mc.thePlayer : super.getPlayerEntity(ctx));
	}

	@Override
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		return (ctx.side.isClient() ? mc : super.getThreadFromContext(ctx));
	}
}
