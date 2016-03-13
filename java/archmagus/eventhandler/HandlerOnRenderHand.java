package archmagus.eventhandler;

import archmagus.potion.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** For hiding the player's hand in case he is polymorphed. */
public class HandlerOnRenderHand 
{
	@SubscribeEvent
	public void OnRenderHand(RenderHandEvent event)
	{
		if (Minecraft.getMinecraft().thePlayer.isPotionActive(ModPotions.polymorphed))
		{
			event.setCanceled(true);
		}
	}
}
