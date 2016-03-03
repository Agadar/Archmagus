package com.agadar.archmagus.network;

import com.agadar.archmagus.gui.GuiSpellBook;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/* This mod's handler for all GUI's besides the Mana bar. */
public class ModGuiHandler implements IGuiHandler 
{
	public static final int SPELLBOOK_GUI = 0;
	
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    	if (ID == SPELLBOOK_GUI)
            return new GuiSpellBook();
        return null;
    }
}