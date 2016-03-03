package com.agadar.archmagus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** The proxy that is used server-side. */
public class CommonProxy 
{
	/** Register this mod's renderers. Call this in Init. */
	public void registerRenderers() { }
	
	/**
	 * Returns a side-appropriate EntityPlayer for use during message handling
	 */
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity;
	}

	/**
	 * Returns the current thread based on side during message handling,
	 * used for ensuring that the message is being handled by the main thread
	 */
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity.getServerForPlayer();
	}

	/** Register this mod's item mesh definitions. Call this in PreInit. */
	public void registerMeshDefinitions() { }

	/** Registers this mod's entity renderers. */
	public void registerEntityRenderers() { }

	/** Registers this mod's key bindings and corresponding events. */
	public void registerKeyBindings() { }
}
