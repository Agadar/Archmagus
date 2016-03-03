package com.agadar.archmagus.eventhandler;

import com.agadar.archmagus.Archmagus;
import com.agadar.archmagus.ClientProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

/** Handles key inputs. */
public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
    	
        if(ClientProxy.openSpellBook.isPressed())
        {
        	EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        	FMLNetworkHandler.openGui(player, Archmagus.instance, 0, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
    }
}
