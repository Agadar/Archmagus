package com.agadar.archmagus.entity;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySummonedHorse extends EntityHorse implements ISummoned
{
	private final EntityAIBase followOwner;
	
	public EntitySummonedHorse(World world) 
	{
		super(world);
		this.followOwner = new EntityAIFollowOwnerHorse(this, 1.0D, 10.0F, 2.0F);
		this.tasks.taskEntries.clear();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, this.followOwner);
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
	
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityData, EntityPlayer par2Player, int par3HorseType)
	{
		Object entityData = this.onSpawnWithEgg(par1EntityData);
        this.setGrowingAge(0);
		this.setHorseType(par3HorseType);
		this.interact(par2Player);
        return (IEntityLivingData) entityData;
	}
	
	/*@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityData)
    {
        Object entityData = super.onSpawnWithEgg(par1EntityData);
        this.setGrowingAge(0);
        this.setHorseSaddled(true);
        return (IEntityLivingData) entityData;
    }*/
	
	@Override
	protected Item getDropItem()
    {
        return null;
    }

	@Override
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
	{
		if ((this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase && this.isHorseSaddled()))
		{
			this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
			this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
			this.setRotation(this.rotationYaw, this.rotationPitch);
			this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
			p_70612_1_ = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5F;
			p_70612_2_ = ((EntityLivingBase)this.riddenByEntity).moveForward;

			if (p_70612_2_ <= 0.0F)
			{
				p_70612_2_ *= 0.25F;
				//this.field_110285_bP = 0;
			}

			if (this.onGround && this.jumpPower == 0.0F && this.isRearing())// && !this.field_110294_bI)
			{
				p_70612_1_ = 0.0F;
				p_70612_2_ = 0.0F;
			}

			if (this.jumpPower > 0.0F && !this.isHorseJumping() && this.onGround)
			{
				this.motionY = this.getHorseJumpStrength() * (double)this.jumpPower;

				if (this.isPotionActive(Potion.jump))
				{
					this.motionY += (double)((float)(this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
				}

				this.setHorseJumping(true);
				this.isAirBorne = true;

				if (p_70612_2_ > 0.0F)
				{
					float f2 = MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F);
					float f3 = MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F);
					this.motionX += (double)(-0.4F * f2 * this.jumpPower);
					this.motionZ += (double)(0.4F * f3 * this.jumpPower);
					this.playSound("mob.horse.jump", 0.4F, 1.0F);
				}

				this.jumpPower = 0.0F;
				net.minecraftforge.common.ForgeHooks.onLivingJump(this);
			}

			this.stepHeight = 1.0F;
			this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

			if (!this.worldObj.isRemote)
			{
				this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
				super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
			}

			if (this.onGround)
			{
				this.jumpPower = 0.0F;
				this.setHorseJumping(false);
			}

			this.prevLimbSwingAmount = this.limbSwingAmount;
			double d1 = this.posX - this.prevPosX;
			double d0 = this.posZ - this.prevPosZ;
			float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;

			if (f4 > 1.0F)
			{
				f4 = 1.0F;
			}

			this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
			this.limbSwing += this.limbSwingAmount;
		}
		else if (this.followOwner.shouldExecute())
		{
			if (!this.worldObj.isRemote)
			{
				this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
				super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
			}
		}
		else
		{
			this.stepHeight = 0.5F;
			this.jumpMovementFactor = 0.02F;
			super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
		}
	}
}
