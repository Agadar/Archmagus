package archmagus.network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import archmagus.spell.SpellData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * Holds all the spells a player knows.
 */
public class SpellProperties implements IExtendedEntityProperties
{
	/** The unique name of this property. */
	public final static String NAME = "SpellProperties";
	/** The spells known by the player. */
	private final List<SpellData> knownSpells = new ArrayList<SpellData>();
	
	/** Used to register these extended properties for the given player. */
	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(NAME, new SpellProperties());
	}
	
	/** Returns the SpellProperties for the given player. */
	public static final SpellProperties get(EntityPlayer player)
	{
		return (SpellProperties) player.getExtendedProperties(NAME);
	}

	/** Adds a new spell to the player's repertoire. */
	public void addSpell(SpellData spellData)
	{
		if (!knownSpells.contains(spellData))
		{
			knownSpells.add(spellData);
			Collections.sort(knownSpells);
		}
	}
	
	/** Returns a new list containing the known spells. */
	public List<SpellData> getKnownSpells()
	{
		return new ArrayList<SpellData>(knownSpells);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) 
	{
		NBTTagList taglist = new NBTTagList();
		for (SpellData sd : knownSpells)
			taglist.appendTag(SpellData.writeToNBTTagCompound(sd));
		compound.setTag(NAME, taglist);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) 
	{
		NBTTagList tagList = (NBTTagList) compound.getTag(NAME);
		knownSpells.clear();		
		for (int i = 0; i < tagList.tagCount(); i++)
			knownSpells.add(SpellData.readFromNBTTagCompound(tagList.getCompoundTagAt(i)));
	}
	
	/**
	 * Ticks the cooldown of all spellData's.
	 */
	public void tickCooldowns()
	{
		for (SpellData spellData : knownSpells)
			spellData.tickCooldown();
	}
	
	/**
	 * Takes an ItemStack, reads its spell tag, and returns the corresponding SpellData from the knownSpells list.
	 * Returns null if the corresponding SpellData was not found for any reason.
	 *
	 * @param par1ItemStack
	 * @return
	 */
	public SpellData returnFromItemStack(ItemStack par1ItemStack)
	{
		if (par1ItemStack == null || par1ItemStack.getTagCompound() == null || !par1ItemStack.getTagCompound().hasKey("spell"))
			return null;

		NBTTagCompound spellTag = par1ItemStack.getTagCompound().getCompoundTag("spell");
		short effectId = spellTag.getShort("id");
		short tagLevel = spellTag.getShort("lvl");
		
		for (SpellData spellData : knownSpells)
			if (spellData.spellObj.effectId == effectId && spellData.spellLevel == tagLevel)
				return spellData;
		
		return null;
	}
	
	/**
	 * Copies the given SpellProperties to this one.
	 *
	 * @param mp
	 */
	public void copy(SpellProperties mp)
	{
		knownSpells.clear();
		knownSpells.addAll(mp.getKnownSpells());
	}

	@Override
	public void init(Entity entity, World world) { }
}
