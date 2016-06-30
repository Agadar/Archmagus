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
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityRisenZombiePigman extends EntitySummoned
{
	public EntityRisenZombiePigman(World par1World)
    {
        super(par1World);
        
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0D, false));
        this.tasks.addTask(3, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        
        this.setSize(0.6F, 1.8F);
        this.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
        this.isImmuneToFire = true;
        this.setCanPickUpLoot(true);
    }
    
    @Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
    	float attackDamage = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
    	return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), attackDamage);
    }

    @Override
    protected String getLivingSound()
    {
    	return "mob.zombiepig.zpig";
    }

    @Override
    protected String getHurtSound()
    {
    	return "mob.zombiepig.zpighurt";
    }

    @Override
    protected String getDeathSound()
    {
    	return "mob.zombiepig.zpigdeath";
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
        this.playSound("mob.zombie.step", 0.15F, 1.0F);
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
}
