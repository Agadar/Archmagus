package com.agadar.archmagus.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * Holds the maximum mana, current mana, and the mana regeneration timer for a player.
 */
public class ManaProperties implements IExtendedEntityProperties 
{
	/** The unique name of this property. */
	public final static String NAME = "ManaProperties";
	/** The index at which the player's current mana is stored in the player's DataWatcher. */
	public static final int MANA_WATCHER = 20;
	/** The player to which this instance belongs to. */
	private final EntityPlayer player;
	/** The maximum amount of mana this player can have. */
	private int maxMana;
	/** A timer used for naturally regenerating mana. */
	public int manaTimer;
	
	/** Used to register these extended properties for the given player. */
	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(NAME, new ManaProperties(player));
	}
	
	/** Returns the ManaProprties for the given player. */
	public static final ManaProperties get(EntityPlayer player)
	{
		return (ManaProperties) player.getExtendedProperties(NAME);
	}
	
	public ManaProperties(EntityPlayer player)
	{
		this.player = player;
		this.maxMana = 0;
		this.manaTimer = 0;
		this.player.getDataWatcher().addObject(MANA_WATCHER, this.maxMana);
	}
	
	/** Attempts to consume the given amount of mana. Assumes amount >= 0. */
	public boolean consumeMana(int amount)
	{
		int currentMana = this.player.getDataWatcher().getWatchableObjectInt(MANA_WATCHER);
		
		if (currentMana >= amount)
		{
			currentMana -= amount;
			this.player.getDataWatcher().updateObject(MANA_WATCHER, currentMana);
			return true;
		}
		
		return false;
	}

	/** Attempts to replenish the given amount of mana. Assumes amount >= 0. */
	public void replenishMana(int amount)
	{
		int currentMana = this.player.getDataWatcher().getWatchableObjectInt(MANA_WATCHER);
		currentMana = Math.min(currentMana + amount, this.maxMana);
		this.player.getDataWatcher().updateObject(MANA_WATCHER, currentMana);
	}
	
	/** Fully replenishes all mana points. */
	public void replenishMana()
	{
		this.player.getDataWatcher().updateObject(MANA_WATCHER, this.maxMana);
	}

	/** Returns the maximum amount of mana this player has. */
	public int getMaxMana() 
	{
		return this.maxMana;
	}
	
	/** Sets the maximum amount of mana this player has. */
	public void setMaxMana(int maxMana)
	{
		this.maxMana = MathHelper.clamp_int(maxMana, 0, 20);
	}
	
	/** Returns the current amount of mana this player has. */
	public int getCurrentMana()
	{
		return this.player.getDataWatcher().getWatchableObjectInt(MANA_WATCHER);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) 
	{
		NBTTagCompound properties = new NBTTagCompound();
		properties.setInteger("CurrentMana", this.player.getDataWatcher().getWatchableObjectInt(MANA_WATCHER));
		properties.setInteger("MaxMana", this.maxMana);
		properties.setInteger("ManaTimer", this.manaTimer);
		compound.setTag(NAME, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) 
	{
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(NAME);
		this.player.getDataWatcher().updateObject(MANA_WATCHER, properties.getInteger("CurrentMana"));
		this.maxMana = properties.getInteger("MaxMana");
		this.manaTimer = properties.getInteger("ManaTimer");
	}
	
	public void copy (ManaProperties mp)
	{
		this.player.getDataWatcher().updateObject(MANA_WATCHER, mp.getCurrentMana());
        this.maxMana = mp.maxMana;
        this.manaTimer = mp.manaTimer;
	}

	@Override
	public void init(Entity entity, World world) { }
}
