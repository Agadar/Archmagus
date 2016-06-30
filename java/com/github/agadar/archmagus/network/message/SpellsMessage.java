package com.github.agadar.archmagus.network.message;

import com.github.agadar.archmagus.Archmagus;
import com.github.agadar.archmagus.network.SpellProperties;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/** The message send server->client to inform client of player's known spells. */
public class SpellsMessage implements IMessage 
{
	private NBTTagCompound knownSpells;
	
	public SpellsMessage() { }
	
	public SpellsMessage(NBTTagCompound _knownSpells)
	{
		this.knownSpells = _knownSpells;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		this.knownSpells = ByteBufUtils.readTag(buf);		
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		ByteBufUtils.writeTag(buf, knownSpells);	
	}
	
	public void process(EntityPlayer player, Side side)
	{
		SpellProperties prop = SpellProperties.get(player);
		prop.loadNBTData(this.knownSpells);	
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
	public static class Handler implements IMessageHandler<SpellsMessage, IMessage> 
	{      
		@Override
		public IMessage onMessage(SpellsMessage msg, MessageContext ctx) 
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
	private static final void checkThreadAndEnqueue(final SpellsMessage msg, final MessageContext ctx) 
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
