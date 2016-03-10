package com.agadar.archmagus.network.message;

import com.agadar.archmagus.Archmagus;
import com.agadar.archmagus.network.ManaProperties;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;

/** The message that is send when a player's maximum mana has changed. */
public class MaxManaMessage implements IMessage 
{
	private int maxMana;
	
	public MaxManaMessage() { }
	
	public MaxManaMessage(int maxMana)
	{
		this.maxMana = maxMana;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		this.maxMana = ByteBufUtils.readVarInt(buf, 1);		
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		ByteBufUtils.writeVarInt(buf, this.maxMana, 1);	
	}
	
	public void process(EntityPlayer player, Side side)
	{
		ManaProperties prop = ManaProperties.get(player);
		prop.setMaxMana(this.maxMana);	
	}
	
	/**
	 * Whether this message requires the main thread to be processed (i.e. it
	 * requires that the world, player, and other objects are in a valid state).
	 */
	protected boolean requiresMainThread() 
	{
		return true;
	}
	
	/** The handler for this message. */
	public static class Handler implements IMessageHandler<MaxManaMessage, IMessage> 
	{      
		@Override
		public IMessage onMessage(MaxManaMessage msg, MessageContext ctx) 
		{
			if (msg.requiresMainThread()) {
				checkThreadAndEnqueue(msg, ctx);
			} else {
				msg.process(Archmagus.proxy.getPlayerEntity(ctx), ctx.side);
			}
			return null;
		}
	}
	
	/**
	 * 1.8 ONLY: Ensures that the message is being handled on the main thread
	 */
	private static final void checkThreadAndEnqueue(final MaxManaMessage msg, final MessageContext ctx) 
	{
		IThreadListener thread = Archmagus.proxy.getThreadFromContext(ctx);
		// pretty much copied straight from vanilla code, see {@link PacketThreadUtil#checkThreadAndEnqueue}
		thread.addScheduledTask(new Runnable() {
			public void run() {
				msg.process(Archmagus.proxy.getPlayerEntity(ctx), ctx.side);
			}
		});
	}
}
