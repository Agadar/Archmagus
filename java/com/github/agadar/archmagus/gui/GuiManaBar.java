package com.github.agadar.archmagus.gui;

import org.lwjgl.opengl.GL11;

import com.github.agadar.archmagus.network.ManaProperties;
import com.github.agadar.archmagus.potion.ModPotions;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * The GUI element that shows the player's mana bar. It draws the mana bar as well as the air bar
 * after the hunger bar is drawn, and cancels vanilla's own drawing of the air bar.
 *
 */
public class GuiManaBar extends Gui 
{
	/** The path to the gui's icons. */
	private static final ResourceLocation modIcons = new ResourceLocation("archmagus", "textures/gui/mana_icons.png");	
	/** A reference to the Minecraft instance. */
	private final Minecraft mc;
	
	public GuiManaBar(Minecraft mc)
	{
		super();
		this.mc = mc;
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderHungerBar(RenderGameOverlayEvent.Post event)
	{		
		if (event.isCancelable() || event.type != ElementType.FOOD)
			return;

		// Air bar
		
        mc.mcProfiler.startSection("air");
        GL11.glEnable(GL11.GL_BLEND);
        int left = event.resolution.getScaledWidth() / 2 + 91;
        int top = event.resolution.getScaledHeight() - GuiIngameForge.right_height - 10;

        if (mc.thePlayer.isInsideOfMaterial(Material.water))
        {
            int air = mc.thePlayer.getAir();
            int full = MathHelper.ceiling_double_int((double)(air - 2) * 10.0D / 300.0D);
            int partial = MathHelper.ceiling_double_int((double)air * 10.0D / 300.0D) - full;

            for (int i = 0; i < full + partial; ++i)
            {
                drawTexturedModalRect(left - i * 8 - 9, top, (i < full ? 16 : 25), 18, 9, 9);
            }
        }

        GL11.glDisable(GL11.GL_BLEND);
        mc.mcProfiler.endSection();
		
        // Mana bar
        
		ManaProperties props = ManaProperties.get(this.mc.thePlayer);
		
		if (props != null && props.getMaxMana() != 0)
		{		
			mc.mcProfiler.startSection("mana");
			mc.getTextureManager().bindTexture(modIcons);
			GL11.glEnable(GL11.GL_BLEND);
			left = event.resolution.getScaledWidth() / 2 + 91;
	        top = event.resolution.getScaledHeight() - GuiIngameForge.right_height;
	        int currentMana = props.getCurrentMana();
	        int maxMana = props.getMaxMana() / 2;        
	        int regen = -1;
	        
	        if (mc.thePlayer.isPotionActive(ModPotions.manaRegen))
	        	regen = mc.ingameGUI.getUpdateCounter() % 25;
	        
	        for (int i = 0; i < maxMana; ++i)
	        {
	            int idx = i * 2 + 1;
	            int x = left - i * 8 - 9;
	            int y = top;
	            
	        	if (i == regen)
	        		y -= 2;
	
	            this.drawTexturedModalRect(x, y, 0, 0, 9, 9);
	            
	            if (idx < currentMana)
	            	this.drawTexturedModalRect(x, y, 36, 0, 9, 9);
	            else if (idx == currentMana)
	            	this.drawTexturedModalRect(x, y, 45, 0, 9, 9);
	        }
	        
	        GL11.glDisable(GL11.GL_BLEND);
	        mc.mcProfiler.endSection();
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderAirBar(RenderGameOverlayEvent.Pre event)
	{
		if (event.type != ElementType.AIR)
			return;
		
		event.setCanceled(true);
	}
}
