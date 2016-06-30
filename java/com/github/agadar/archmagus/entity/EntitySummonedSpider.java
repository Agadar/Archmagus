package com.github.agadar.archmagus.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySummonedSpider extends EntitySummoned 
{
	public EntitySummonedSpider(World par1World) 
	{ 
		super(par1World);

		this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.2D, true));
        this.tasks.addTask(3, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        
        this.setSize(1.4F, 0.9F);
	}
	
    @Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        float attackDamage = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
    	return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), attackDamage);
    }
	
	@Override
	protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(18, new Byte((byte)0));
    }

	@Override
	public void onUpdate()
    {
        super.onUpdate();

        if (!this.worldObj.isRemote)
        {
            this.setBesideClimbableBlock(this.isCollidedHorizontally);
        }
    }
	
	@Override
    protected String getLivingSound()
    {
        return "mob.spider.say";
    }

	@Override
    protected String getHurtSound()
    {
        return "mob.spider.say";
    }

	@Override
    protected String getDeathSound()
    {
        return "mob.spider.death";
    }
	
    @Override
    protected String getSwimSound()
    {
        return "game.hostile.swim";
    }

    @Override
    protected String getSplashSound()
    {
        return "game.hostile.swim.splash";
    }

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound("mob.spider.step", 0.15F, 1.0F);
    }
	
    @Override
    public boolean isOnLadder()
    {
        return this.isBesideClimbableBlock();
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    @Override
    public boolean isPotionApplicable(PotionEffect par1PotionEffect)
    {
        return par1PotionEffect.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(par1PotionEffect);
    }
    
    /**
     * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
     * setBesideClimableBlock.
     */
    public boolean isBesideClimbableBlock()
    {
        return (this.dataWatcher.getWatchableObjectByte(18) & 1) != 0;
    }
    
    /**
     * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
     * false.
     */
    public void setBesideClimbableBlock(boolean par1)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(18);

        if (par1)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 &= -2;
        }

        this.dataWatcher.updateObject(18, Byte.valueOf(b0));
    }
}
