package com.agadar.archmagus.entity;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySummonedHorse extends EntityHorse implements ISummoned
{
	public EntitySummonedHorse(World world) 
	{
		super(world);
	}
	
	/** Returns the player who tamed this horse. */
	public EntityLivingBase getTamedBy()
    {
        try
        {
            UUID uuid = UUID.fromString(this.dataWatcher.getWatchableObjectString(21));
            return uuid == null ? null : this.worldObj.func_152378_a(uuid);
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }
    }
	
	@Override
	public boolean interact(EntityPlayer player)
    {
		if (this.isAdultHorse() && this.riddenByEntity == null && this.getTamedBy() == player)
		{
			player.rotationYaw = this.rotationYaw;
			player.rotationPitch = this.rotationPitch;
			this.setEatingHaystack(false);
			this.setRearing(false);

			if (!this.worldObj.isRemote)
			{
				player.mountEntity(this);
			}
			
			return true;
		}
		
		return false;
    }
	
	@Override
	public void onUpdate()
    {
        super.onUpdate();

        if (!this.worldObj.isRemote)
        {
        	EntityLivingBase owner = this.getTamedBy();
            
            if (owner == null || owner.isDead || this.riddenByEntity == null) 
            	this.attackEntityFrom(DamageSource.generic, this.getMaxHealth());
        }
    }
	
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityData, EntityPlayer par2Player, int par3HorseType)
	{
		Object entityData = this.onSpawnWithEgg(par1EntityData);
        this.setGrowingAge(0);
		this.setHorseType(par3HorseType);
		this.interact(par2Player);
        return (IEntityLivingData) entityData;
	}
	
	@Override
	protected Item getDropItem()
    {
        return null;
    }
}
