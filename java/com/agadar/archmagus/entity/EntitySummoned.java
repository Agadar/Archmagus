package com.agadar.archmagus.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntitySummoned extends EntityTameable implements ISummoned
{
	public EntitySummoned(World par1World) 
	{
		super(par1World);
		
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
        
        this.setTamed(true);
        this.setPathToEntity((PathEntity)null);
        this.setAttackTarget((EntityLivingBase)null);
	}
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
    }

	@Override
	public EntityAgeable createChild(EntityAgeable var1) 
	{
		return null;
	}
	
    @Override
    public boolean isAIEnabled()
    {
        return true;
    }
    
    @Override
    protected Item getDropItem()
    {
    	return Item.getItemById(-1);
    }
    
    @Override
    public boolean isBreedingItem(ItemStack par1ItemStack)
    {
        return false;
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 0;
    }

    @Override
    public boolean canMateWith(EntityAnimal par1EntityAnimal)
    {
        return false;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }
    
    @Override
	public void onUpdate()
    {
        super.onUpdate();

        if (this.worldObj.isRemote) return;
        
        EntityLivingBase owner = this.getOwner();
        if (owner != null) 
        {
        	if (owner.isDead) this.attackEntityFrom(DamageSource.generic, this.getMaxHealth());
        }
        else this.attackEntityFrom(DamageSource.generic, this.getMaxHealth());
    }
}
