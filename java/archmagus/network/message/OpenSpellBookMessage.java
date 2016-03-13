package archmagus.network.message;

import archmagus.Archmagus;
import archmagus.gui.ModGuiHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/** The message that is send from client to server when the spellbook need be opened. */
public class OpenSpellBookMessage implements IMessage 
{
	@Override
	public void fromBytes(ByteBuf buf) { }

	@Override
	public void toBytes(ByteBuf buf) { }
	
	public void process(EntityPlayer player, Side side)
	{
		player.openGui(Archmagus.instance, ModGuiHandler.SPELLBOOK_GUI, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
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
	public static class Handler implements IMessageHandler<OpenSpellBookMessage, IMessage> 
	{      
		@Override
		public IMessage onMessage(OpenSpellBookMessage msg, MessageContext ctx) 
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
	private static final void checkThreadAndEnqueue(final OpenSpellBookMessage msg, final MessageContext ctx) 
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
