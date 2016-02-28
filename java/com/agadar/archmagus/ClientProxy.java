package com.agadar.archmagus;

import com.agadar.archmagus.entity.EntityRisenHorse;
import com.agadar.archmagus.entity.EntityRisenSkeleton;
import com.agadar.archmagus.entity.EntityRisenWitherSkeleton;
import com.agadar.archmagus.entity.EntityRisenZombie;
import com.agadar.archmagus.entity.EntityRisenZombiePigman;
import com.agadar.archmagus.entity.EntitySummonedCaveSpider;
import com.agadar.archmagus.entity.EntitySummonedSpider;
import com.agadar.archmagus.entity.EntitySummonedWitch;
import com.agadar.archmagus.entity.EntitySummonedWolf;
import com.agadar.archmagus.misc.GuiManaBar;
import com.agadar.archmagus.model.ModelSummonedWolf;
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
import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** The proxy that is used client-side. */
public class ClientProxy extends CommonProxy 
{
	private final Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public void registerRenderers() 
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
				return new RenderHorse(manager, new ModelHorse(), 0.75F);
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
		
		/** Gui renderers. */
		MinecraftForge.EVENT_BUS.register(new GuiManaBar(Minecraft.getMinecraft()));
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
