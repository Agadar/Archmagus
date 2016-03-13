package archmagus.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

/**
 * A spell that gives a buff (a PotionEffect) to the player.
 */
public class SpellBuff extends Spell
{
	/** The name of this buff. */
	protected final String buffName;
	/** The potion applied by this spell. */
	protected final Potion buffPotion;
	
	public SpellBuff(String par1Name, Potion par2Potion) 
	{
		super();
		this.buffName = par1Name;
		this.buffPotion = par2Potion;
	}
	
	@Override
	public String getName()
    {
        return "spell.buff." + buffName;
    }
	
	@Override
	public int getManaCost()
    {
    	return 6;
    }
	
	@Override
	public short getCooldown()
	{
		return 200;
	}
	
	@Override
	public String getDescription()
    {
    	return "spell.description.buff";
    }
	
	@Override
	public String getModelResourceLocationString()
    {
    	return super.getModelResourceLocationString() + "sheep_book";
    }
	
	/**
	 * The duration of the PotionEffect applied by this spell.
	 *
	 * @param par1Level
	 * @return
	 */
	public int getBuffDuration(short par1Level)
	{
		return 300;
	}
	
	@Override
	public boolean castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer)
	{
		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
		par3EntityPlayer.addPotionEffect(new PotionEffect(this.buffPotion.getId(), this.getBuffDuration(par1Level), par1Level - 1));
		par3EntityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "You gain " + this.getTranslatedName(par1Level) + "."));
		return true;
	}
}
