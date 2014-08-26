package com.agadar.archmagus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

import com.agadar.archmagus.entity.EntityRisenSkeleton;
import com.agadar.archmagus.entity.EntityRisenWitherSkeleton;
import com.agadar.archmagus.entity.EntityRisenZombie;
import com.agadar.archmagus.entity.EntityRisenZombiePigman;
import com.agadar.archmagus.entity.EntitySummonedCaveSpider;
import com.agadar.archmagus.entity.EntityRisenHorse;
import com.agadar.archmagus.entity.EntitySummonedSpider;
import com.agadar.archmagus.entity.EntitySummonedWitch;
import com.agadar.archmagus.entity.EntitySummonedWolf;
import com.agadar.archmagus.misc.GuiManaBar;
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

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/** The proxy that is used client-side. */
public class ClientProxy extends CommonProxy 
{
	@Override
	public void registerRenderers() 
	{
		/** Entity renderers. */
		RenderingRegistry.registerEntityRenderingHandler(EntitySummonedWolf.class, new RenderSummonedWolf(new ModelSummonedWolf(), new ModelSummonedWolf(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityRisenSkeleton.class, new RenderRisenSkeleton());
		RenderingRegistry.registerEntityRenderingHandler(EntityRisenWitherSkeleton.class, new RenderRisenWitherSkeleton());
		RenderingRegistry.registerEntityRenderingHandler(EntityRisenZombie.class, new RenderRisenZombie());
		RenderingRegistry.registerEntityRenderingHandler(EntityRisenZombiePigman.class, new RenderRisenZombiePigman());
		RenderingRegistry.registerEntityRenderingHandler(EntitySummonedWitch.class, new RenderSummonedWitch());
		RenderingRegistry.registerEntityRenderingHandler(EntitySummonedSpider.class, new RenderSummonedSpider());
		RenderingRegistry.registerEntityRenderingHandler(EntitySummonedCaveSpider.class, new RenderSummonedCaveSpider());
		RenderingRegistry.registerEntityRenderingHandler(EntityRisenHorse.class, new RenderRisenHorse(new ModelHorse(), 0.75F));
		
		/** Gui renderers. */
		MinecraftForge.EVENT_BUS.register(new GuiManaBar(Minecraft.getMinecraft()));
	}

	@Override
	public EntityPlayer getPlayerFromMessageContext(MessageContext ctx)
	{
		return Minecraft.getMinecraft().thePlayer;
	}
}
