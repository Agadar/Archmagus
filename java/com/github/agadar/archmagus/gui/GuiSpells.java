package com.github.agadar.archmagus.gui;

import java.io.IOException;

import com.github.agadar.archmagus.ClientProxy;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.IInventory;

/**
 * The GUI for the player's spells.
 */
public class GuiSpells extends GuiChest
{
	public GuiSpells(IInventory upperInv, IInventory lowerInv)
	{
		super(upperInv, lowerInv);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
		// If the GUI spell key was clicked, close the GUI.
		if (ClientProxy.openSpellBook.getKeyCode() == keyCode)
			this.mc.thePlayer.closeScreen();
		else
			super.keyTyped(typedChar, keyCode);
    }
}
