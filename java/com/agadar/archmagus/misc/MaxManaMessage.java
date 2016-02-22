package com.agadar.archmagus.misc;

import com.agadar.archmagus.Archmagus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
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
	
	/** The handler for this message. */
	public static class Handler implements IMessageHandler<MaxManaMessage, IMessage> 
	{      
		@Override
		public IMessage onMessage(MaxManaMessage message, MessageContext ctx) 
		{
			EntityPlayer player = Archmagus.proxy.getPlayerFromMessageContext(ctx);
			ManaProperties prop = ManaProperties.get(player);
			prop.setMaxMana(message.maxMana);	
			return null;
		}
	}
}
