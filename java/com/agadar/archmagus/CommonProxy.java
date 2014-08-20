package com.agadar.archmagus;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/** The proxy that is used server-side. */
public class CommonProxy 
{
	/** Register this mod's renderers. */
	public void registerRenderers() { }
	
	/** Returns the player from the message context. */
	public EntityPlayer getPlayerFromMessageContext(MessageContext ctx)
	{
		return ctx.getServerHandler().playerEntity;
	}
}
