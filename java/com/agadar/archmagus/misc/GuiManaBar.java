package com.agadar.archmagus.misc;

import org.lwjgl.opengl.GL11;

import com.agadar.archmagus.potion.ModPotions;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

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

		mc.mcProfiler.startSection("mana");
		ManaProperties props = ManaProperties.get(this.mc.thePlayer);
		
		if (props == null || props.getMaxMana() == 0)
			return;
		
		mc.getTextureManager().bindTexture(modIcons);
		GL11.glEnable(GL11.GL_BLEND);
		int left = event.resolution.getScaledWidth() / 2 + 91;
        int top = event.resolution.getScaledHeight() - GuiIngameForge.right_height;
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
