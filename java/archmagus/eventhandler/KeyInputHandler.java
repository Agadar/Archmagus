package archmagus.eventhandler;

import archmagus.Archmagus;
import archmagus.ClientProxy;
import archmagus.network.message.OpenSpellBookMessage;
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
