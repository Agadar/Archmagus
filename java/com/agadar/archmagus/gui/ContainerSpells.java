package com.agadar.archmagus.gui;

import com.agadar.archmagus.Archmagus;
import com.agadar.archmagus.items.ItemSpell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;

public class ContainerSpells extends Container
{
	public ContainerSpells(EntityPlayer player, IInventory playerInv) 
	{
		IInventory spellInv = new InventoryBasic("Spells", false, 27);
		
		// Tile Entity, Slot 0-8, Slot IDs 0-8
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 3; ++x) {
	            this.addSlotToContainer(new Slot(spellInv, x + y * 3, 62 + x * 18, 17 + y * 18));
	        }
	    }

	    // Player Inventory, Slot 9-35, Slot IDs 9-35
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
	        }
	    }

	    // Player Inventory, Slot 0-8, Slot IDs 36-44
	    for (int x = 0; x < 9; ++x) {
	        this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
	    }
	    
	    // Add player's known spells to the container as ItemSpells.
	    this.inventoryItemStacks.addAll(((ItemSpell)Archmagus.spell).getPlayerKnownSpells(player));
    }
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{

	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return false;
	}
}
