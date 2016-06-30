package com.github.agadar.archmagus.entity;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityRisenSkeleton extends EntitySummoned implements IRangedAttackMob
{
    public EntityRisenSkeleton(World par1World)
    {
        super(par1World);
        
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F));
        this.tasks.addTask(3, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        
        this.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
        this.setCanPickUpLoot(true);
    }

    @Override
    protected String getLivingSound()
    {
        return "mob.skeleton.say";
    }

    @Override
    protected String getHurtSound()
    {
        return "mob.skeleton.hurt";
    }

    @Override
    protected String getDeathSound()
    {
        return "mob.skeleton.death";
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound("mob.skeleton.step", 0.15F, 1.0F);
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        return false;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (this.worldObj.isRemote)
        {
        	 this.setSize(0.6F, 1.8F);
        }
    }

    @Override
    public void updateRidden()
    {
        super.updateRidden();

        if (this.ridingEntity instanceof EntityCreature)
        {
            EntityCreature entitycreature = (EntityCreature)this.ridingEntity;
            this.renderYawOffset = entitycreature.renderYawOffset;
        }
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	EntityArrow entityarrow = new EntityArrow(this.worldObj, this, par1EntityLivingBase, 1.6F, (float)(14 - this.worldObj.getDifficulty().getDifficultyId() * 4));
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
        entityarrow.setDamage((double)(par2 * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.worldObj.getDifficulty().getDifficultyId() * 0.11F));

        if (i > 0)
        {
            entityarrow.setDamage(entityarrow.getDamage() + (double)i * 0.5D + 0.5D);
        }

        if (j > 0)
        {
            entityarrow.setKnockbackStrength(j);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0)
        {
            entityarrow.setFire(100);
        }

        this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.worldObj.spawnEntityInWorld(entityarrow);
    }

    @Override
    public double getYOffset()
    {
        return super.getYOffset() - 0.5D;
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
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
        if (this.isEntityInvulnerable(par1DamageSource))
        {
            return false;
        }
        else if (super.attackEntityFrom(par1DamageSource, par2))
        {
            Entity entity = par1DamageSource.getEntity();

            if (this.riddenByEntity != entity && this.ridingEntity != entity)
            {
                if (entity != this)
                {
                    this.setAttackTarget((EntityLivingBase) entity);
                }

                return true;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }
}
