package com.agadar.archmagus.entity;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySummonedHorse extends EntityHorse implements ISummoned
{
	public EntitySummonedHorse(World world) 
	{
		super(world);			
		this.tasks.taskEntries.clear();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIFollowOwnerHorse(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(2, new EntityAIWander(this, 0.7D));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
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
            
            if (owner == null || owner.isDead) 
            	this.attackEntityFrom(DamageSource.generic, this.getMaxHealth());
        }
    }
	
	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
    {
        Object p_110161_1_1 = super.onSpawnWithEgg(p_110161_1_);
        this.setGrowingAge(0);
        this.setHorseSaddled(true);
        return (IEntityLivingData)p_110161_1_1;
    }
}
