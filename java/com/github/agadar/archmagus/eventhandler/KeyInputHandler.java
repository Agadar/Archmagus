package com.github.agadar.archmagus.eventhandler;

import com.github.agadar.archmagus.Archmagus;
import com.github.agadar.archmagus.ClientProxy;
import com.github.agadar.archmagus.network.message.OpenSpellBookMessage;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

/** Handles key inputs. */
public class KeyInputHandler 
{
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) 
    {   	
        if (ClientProxy.openSpellBook.isPressed()) 
        {
        	Archmagus.networkWrapper.sendToServer(new OpenSpellBookMessage());
        }
    }
}
